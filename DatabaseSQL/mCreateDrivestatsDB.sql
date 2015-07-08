CREATE TABLE users
(
  ID int IDENTITY(1,1) PRIMARY KEY,
  email varchar(60) NOT NULL,
  joinDate varchar(20) NOT NULL,
  averageScore float
);

Create TABLE trips
(
  ID int IDENTITY(1,1) PRIMARY KEY,
  userID int FOREIGN KEY REFERENCES users(ID),
  tripDate varchar(40),
  startLatitude varchar(12),
  startLongitude varchar(12),
);

Create TABLE tripData
(
  ID int IDENTITY(1,1) PRIMARY KEY,
  tripID int FOREIGN KEY REFERENCES trips(ID),
  latitude varchar(12),
  longitude varchar(12),
  speed float,
  recTime varchar(5),
  maxXAcelerometer float,
  maxYAcelerometer float,
  maxZAcelerometer float,
);

CREATE TABLE friends
(
  ID int IDENTITY(1,1) PRIMARY KEY,
  userID int FOREIGN KEY REFERENCES users(ID),
  friendID int FOREIGN KEY REFERENCES users(ID),
  currentStatus varchar(3),
);

