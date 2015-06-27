CREATE TABLE users
(
  ID serial NOT NULL,
  email character varying(60) NOT NULL,
  joindate character varying(50) NOT NULL,
  CONSTRAINT "User_pkey" PRIMARY KEY (ID)
);

Create TYPE tripData as
(
  latitude character varying(40),
  longitude character varying(40),
  speed int,
  recTime character varying(5),
  maxAcelerometer character varying(50)
);

Create TABLE trips
(
  ID serial NOT NULL,
  userID int references users(ID),
  recordedData tripData[],
  CONSTRAINT "Trips_pkey" PRIMARY KEY (ID)
);

CREATE TYPE status as ENUM ('accepted','pending','blocked');

CREATE TABLE friends
(
  ID serial NOT NULL,
  userID int references users(ID),
  friendID int references users(ID),
  currentStatus status
);

