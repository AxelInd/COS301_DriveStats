using System;
using System.Collections;
using System.Data;
using System.Data.SqlClient;
using System.IO;
using System.Text;

namespace DataStreams.ETL
{
	public class ValidatingDataReader : IDataReader
	{
		private IDataReader reader = null;
		private bool disposed = false;
		private int currentRecord = -1;
		private DataRow[] lookup = null;
		private string tableName = null;
		private string databaseName = null;
		private string serverName = null;

		public delegate void ColumnExceptionEventHandler(ColumnExceptionEventArgs args);

		public event ColumnExceptionEventHandler ColumnException = null;

		public ValidatingDataReader(
			IDataReader reader,
			SqlConnection conn,
			SqlBulkCopy bcp) :
			this(
			reader,
			conn,
			bcp,
			null)
		{

		}

		public ValidatingDataReader(
			IDataReader reader,
			SqlConnection conn,
			SqlBulkCopy bcp,
			SqlTransaction tran)
		{
			this.reader = reader;

			if (bcp.DestinationTableName == null)
			{
				throw new Exception("SqlBulkCopy.DestinationTableName must be set before validation can be done.");
			}

			tableName = bcp.DestinationTableName;
			databaseName = conn.Database;
			serverName = conn.DataSource;

			ConnectionState origState = conn.State;

			if (conn.State == ConnectionState.Closed)
			{
				conn.Open();
			}

			#region validate source

			foreach (SqlBulkCopyColumnMapping mapping in bcp.ColumnMappings)
			{
				string sourceColumn = mapping.SourceColumn;

				if (sourceColumn.StartsWith("[") && sourceColumn.EndsWith("]"))
				{
					sourceColumn = sourceColumn.Substring(1, sourceColumn.Length - 2);
				}

				if (sourceColumn != "")
				{
					if (reader.GetOrdinal(sourceColumn) == -1)
					{
						string bestFit = null;

						for (int i = 0; i < reader.FieldCount; i++)
						{
							string existingColumn = reader.GetName(i);

							if (
								String.Equals(
									existingColumn,
									sourceColumn,
									StringComparison.InvariantCultureIgnoreCase))
							{
								bestFit = existingColumn;
							}
						}

						if (bestFit == null)
						{
							throw new Exception("Source column " + mapping.SourceColumn + " does not exist in source.");
						}
						else
						{
							throw new Exception("Source column " + mapping.SourceColumn + " does not exist in source." +
								" Column name mappings are case specific and best found match is " + bestFit + ".");
						}
					}
				}
				else
				{
					if (
						mapping.SourceOrdinal < 0 ||
						mapping.SourceOrdinal >= reader.FieldCount
						)
					{
						throw new Exception("No column exists at index " + mapping.SourceOrdinal + " in source.");
					}
				}
			}

			#endregion

			DataTable schemaTable = null;

			#region get destination metadata

			try
			{
				using (SqlCommand select = new SqlCommand("select top 0 * from " + bcp.DestinationTableName, conn))
				{
					if (tran != null)
					{
						select.Transaction = tran;
					}

					using (SqlDataReader destReader = select.ExecuteReader())
					{
						schemaTable = destReader.GetSchemaTable();
					}
				}
			}
			catch (SqlException e)
			{
				if (e.Message.StartsWith("Invalid object name"))
				{
					throw new Exception("Destination table " + bcp.DestinationTableName + " does not exist in database " + conn.Database + " on server " + conn.DataSource + ".");
				}
				else
				{
					throw;
				}
			}
			finally
			{
				if (origState == ConnectionState.Closed)
				{
					conn.Close();
				}
			}

			#endregion

			lookup = new DataRow[reader.FieldCount];

			if (bcp.ColumnMappings.Count > 0)
			{
				#region validate destination

				DataRow[] columns = new DataRow[schemaTable.Rows.Count];
				Hashtable columnLookup = new Hashtable();

				foreach (DataRow column in schemaTable.Rows)
				{
					columns[(int)column["ColumnOrdinal"]] = column;
					columnLookup[column["ColumnName"]] = column["ColumnOrdinal"];
				}

				foreach (SqlBulkCopyColumnMapping mapping in bcp.ColumnMappings)
				{
					string destColumn = mapping.DestinationColumn;

					if (destColumn.StartsWith("[") && destColumn.EndsWith("]"))
					{
						destColumn = destColumn.Substring(1, destColumn.Length - 2);
					}

					if (destColumn != "")
					{
						if (!columnLookup.ContainsKey(destColumn))
						{
							string bestFit = null;

							foreach (string existingColumn in columnLookup.Keys)
							{
								if (
									String.Equals(
										existingColumn,
										destColumn,
										StringComparison.InvariantCultureIgnoreCase))
								{
									bestFit = existingColumn;
								}
							}

							if (bestFit == null)
							{
								throw new Exception("Destination column " + mapping.DestinationColumn + " does not exist in destination table " + bcp.DestinationTableName + " in database " + conn.Database + " on server " + conn.DataSource + ".");
							}
							else
							{
								throw new Exception(
									"Destination column " + mapping.DestinationColumn + " does not exist in destination table " + bcp.DestinationTableName + " in database " + conn.Database + " on server " + conn.DataSource + "." +
									" Column name mappings are case specific and best found match is " + bestFit + "."
									);
							} // end else

						} // end if (!columnLookup.ContainsKey(destColumn))

					} // end if (destColumn != null)
					else
					{
						if (
							mapping.DestinationOrdinal < 0 ||
							mapping.DestinationOrdinal >= columns.Length
							)
						{
							throw new Exception(
								"No column exists at index " + mapping.DestinationOrdinal + " in destination table " + bcp.DestinationTableName + " in database " + conn.Database + " on server " + conn.DataSource + "."
								);
						}
					}

				} // end foreach (mapping)

				#endregion

				#region create lookup for per record validation

				// create lookup dest column definition by source index
				foreach (SqlBulkCopyColumnMapping mapping in bcp.ColumnMappings)
				{
					int sourceIndex = -1;

					string sourceColumn = mapping.SourceColumn;

					if (sourceColumn.StartsWith("[") && sourceColumn.EndsWith("]"))
					{
						sourceColumn = sourceColumn.Substring(1, sourceColumn.Length - 2);
					}

					if (sourceColumn != "")
					{
						sourceIndex = reader.GetOrdinal(sourceColumn);
					}
					else
					{
						sourceIndex = mapping.SourceOrdinal;
					}

					DataRow destColumnDef = null;

					string destColumn = mapping.DestinationColumn;

					if (destColumn.StartsWith("[") && destColumn.EndsWith("]"))
					{
						destColumn = destColumn.Substring(1, destColumn.Length - 2);
					}

					if (destColumn != "")
					{
						foreach (DataRow column in schemaTable.Rows)
						{
							if ((string) column["ColumnName"] == destColumn)
							{
								destColumnDef = column;
							}
						}

					} // end if (destColumn != null)
					else
					{
						foreach (DataRow column in schemaTable.Rows)
						{
							if ((int) column["ColumnOrdinal"] == mapping.DestinationOrdinal)
							{
								destColumnDef = column;
							}
						}
					}

					lookup[sourceIndex] = destColumnDef;

				} // end foreach (mapping)

				#endregion
			}
			else
			{
				foreach (DataRow column in schemaTable.Rows)
				{
					lookup[(int) column["ColumnOrdinal"]] = column;
				}
			}
		}

		public int CurrentRecord
		{
			get
			{
				return currentRecord;
			}
		}

		#region IDisposable Members

		void IDisposable.Dispose()
		{
			if (!disposed)
			{
				Dispose(true);
				GC.SuppressFinalize(this);
			}
		}

		#endregion

		private void Dispose(bool disposing)
		{
			if (!disposed)
			{
				// managed resource releases
				if (disposing)
				{

				}

				// unmanaged resource releases
				(reader as IDisposable).Dispose();

				reader = null;

				disposed = true;
			}
		}

		~ValidatingDataReader()
		{
			Dispose(false);
		}

		#region IDataReader Members

		int IDataReader.RecordsAffected
		{
			get
			{
				return reader.RecordsAffected;
			}
		}

		bool IDataReader.IsClosed
		{
			get
			{
				return disposed;
			}
		}

		bool IDataReader.NextResult()
		{
			return reader.NextResult();
		}

		public void Close()
		{
			(this as IDisposable).Dispose();
		}

		bool IDataReader.Read()
		{
			bool canRead = reader.Read();

			if (canRead)
			{
				currentRecord++;
			}

			return canRead;
		}

		int IDataReader.Depth
		{
			get
			{
				return reader.Depth;
			}
		}

		DataTable IDataReader.GetSchemaTable()
		{
			return reader.GetSchemaTable();
		}

		#endregion

		#region IDataRecord Members

		int IDataRecord.GetInt32(int i)
		{
			return reader.GetInt32(i);
		}

		object IDataRecord.this[string name]
		{
			get
			{
				int ordinal = reader.GetOrdinal(name);

				if (ordinal > -1)
				{
					return (this as IDataRecord).GetValue(ordinal);
				}
				else
				{
					return reader[name];
				}
			}
		}

		object IDataRecord.this[int i]
		{
			get
			{
				return (this as IDataRecord).GetValue(i);
			}
		}

		object IDataRecord.GetValue(int i)
		{
			object columnValue = reader.GetValue(i);

			if (i > -1 && i < lookup.Length)
			{
				DataRow columnDef = lookup[i];
				if
				(
					(
						(string) columnDef["DataTypeName"] == "varchar" ||
						(string) columnDef["DataTypeName"] == "nvarchar" ||
						(string) columnDef["DataTypeName"] == "char" ||
						(string) columnDef["DataTypeName"] == "nchar"
					) &&
					(
						columnValue != null &&
						columnValue != DBNull.Value
					)
				)
				{
					string stringValue = columnValue.ToString();

					if (stringValue.Length > (int) columnDef["ColumnSize"])
					{
						string message =
							"Column value \"" + stringValue.Replace("\"", "\\\"") + "\"" +
							" with length " + stringValue.Length.ToString("###,##0") +
							" from source column " + (this as IDataRecord).GetName(i) +
							" in record " + currentRecord.ToString("###,##0") +
							" does not fit in destination column " + columnDef["ColumnName"] +
							" with length " + ((int) columnDef["ColumnSize"]).ToString("###,##0") +
							" in table " + tableName +
							" in database " + databaseName +
							" on server " + serverName + ".";

						if (ColumnException == null)
						{
							throw new Exception(message);
						}
						else
						{
							ColumnExceptionEventArgs args = new ColumnExceptionEventArgs();

							args.DataTypeName = (string) columnDef["DataTypeName"];
							args.DataType = Type.GetType((string) columnDef["DataType"]);
							args.Value = columnValue;
							args.SourceIndex = i;
							args.SourceColumn = reader.GetName(i);
							args.DestIndex = (int) columnDef["ColumnOrdinal"];
							args.DestColumn = (string) columnDef["ColumnName"];
							args.ColumnSize = (int) columnDef["ColumnSize"];
							args.RecordIndex = currentRecord;
							args.TableName = tableName;
							args.DatabaseName = databaseName;
							args.ServerName = serverName;
							args.Message = message;

							ColumnException(args);

							columnValue = args.Value;
						}
					}
				}
			}

			return columnValue;
		}

		bool IDataRecord.IsDBNull(int i)
		{
			return reader.IsDBNull(i);
		}

		long IDataRecord.GetBytes(
			int i,
			long fieldOffset,
			byte[] buffer,
			int bufferoffset,
			int length)
		{
			return reader.GetBytes(
				i,
				fieldOffset,
				buffer,
				bufferoffset,
				length);
		}

		byte IDataRecord.GetByte(int i)
		{
			return reader.GetByte(i);
		}

		Type IDataRecord.GetFieldType(int i)
		{
			return reader.GetFieldType(i);
		}

		decimal IDataRecord.GetDecimal(int i)
		{
			return reader.GetDecimal(i);
		}

		int IDataRecord.GetValues(object[] values)
		{
			return reader.GetValues(values);
		}

		string IDataRecord.GetName(int i)
		{
			return reader.GetName(i);
		}

		int IDataRecord.FieldCount
		{
			get
			{
				return reader.FieldCount;
			}
		}

		long IDataRecord.GetInt64(int i)
		{
			return reader.GetInt64(i);
		}

		double IDataRecord.GetDouble(int i)
		{
			return reader.GetDouble(i);
		}

		bool IDataRecord.GetBoolean(int i)
		{
			return reader.GetBoolean(i);
		}

		Guid IDataRecord.GetGuid(int i)
		{
			return reader.GetGuid(i);
		}

		DateTime IDataRecord.GetDateTime(int i)
		{
			return reader.GetDateTime(i);
		}

		int IDataRecord.GetOrdinal(string name)
		{
			return reader.GetOrdinal(name);
		}

		string IDataRecord.GetDataTypeName(int i)
		{
			return reader.GetDataTypeName(i);
		}

		float IDataRecord.GetFloat(int i)
		{
			return reader.GetFloat(i);
		}

		IDataReader IDataRecord.GetData(int i)
		{
			return reader.GetData(i);
		}

		long IDataRecord.GetChars(
			int i,
			long fieldoffset,
			char[] buffer,
			int bufferoffset,
			int length)
		{
			return reader.GetChars(
				i,
				fieldoffset,
				buffer,
				bufferoffset,
				length);
		}

		string IDataRecord.GetString(int i)
		{
			return (string)(this as IDataRecord).GetValue(i);
		}

		char IDataRecord.GetChar(int i)
		{
			return reader.GetChar(i);
		}

		short IDataRecord.GetInt16(int i)
		{
			return reader.GetInt16(i);
		}

		#endregion

		public class ColumnExceptionEventArgs : EventArgs
		{
			public string DataTypeName = null;
			public Type DataType = null;
			public object Value = null;
			public int SourceIndex = -1;
			public string SourceColumn = null;
			public int DestIndex = -1;
			public string DestColumn = null;
			public int ColumnSize = -1;
			public int RecordIndex = -1;
			public string TableName = null;
			public string DatabaseName = null;
			public string ServerName = null;
			public string Message = null;
		}
	}
}