﻿//------------------------------------------------------------------------------
// <auto-generated>
//    This code was generated from a template.
//
//    Manual changes to this file may cause unexpected behavior in your application.
//    Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace newDBClient
{
    using System;
    using System.Data.Entity;
    using System.Data.Entity.Infrastructure;
    
    public partial class drivestatsEntities : DbContext
    {
        public drivestatsEntities()
            : base("name=drivestatsEntities")
        {
        }
    
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            throw new UnintentionalCodeFirstException();
        }
    
        public DbSet<algorithmData> algorithmDatas { get; set; }
        public DbSet<tripData> tripDatas { get; set; }
        public DbSet<trip> trips { get; set; }
        public DbSet<user> users { get; set; }
    }
}
