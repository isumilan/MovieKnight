DROP DATABASE if exists movieknight_database;

CREATE DATABASE movieknight_database;

USE movieknight_database;

CREATE TABLE Users (
	username varchar(50) primary key not null,
    password varchar(50) NOT NULL,
    profilePicture varchar(50) NOT NULL,
    description TEXT NOT NULL,
    zipcode int not null
);

CREATE TABLE Friends (
	P_Id int primary key not null,
	isRequest bool not null,
    sender varchar(50) not null,
    receiver varchar(50)
);

CREATE TABLE MovieLists (
	P_Id int primary key not null,
	list_type varchar(50) not null,
    username varchar(50) not null,
    movieID varchar(50) not null
);

CREATE TABLE EventRequests (
	P_Id int primary key not null,
    username varchar(50) not null,
    eventID varchar(50) not null
);

CREATE TABLE MovieEvents (
	eventID varchar(50) primary key not null,
    owner varchar(50) not null,
    movieID varchar(50) not null,
    description TEXT not null,
    movieTime varchar(50) not null,
    theater varchar(50) not null
);

CREATE TABLE EventParticipants (
	eventID varchar(50) primary key not null,
    isParticipating bool not null,
    username varchar(50) not null
);