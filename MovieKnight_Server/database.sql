DROP DATABASE if exists movieknight_database;

CREATE DATABASE movieknight_database;

USE movieknight_database;

CREATE TABLE Users (
	username varchar(50) primary key not null,
    password varchar(50) NOT NULL,
    profilePicture varchar(50) DEFAULT 'NoImageAvailable.png',
    description varchar(140) DEFAULT 'no description',
    zipcode int not null,
    hasNewRequest bool DEFAULT false,
    hasNewInvite bool DEFAULT false
);

CREATE TABLE Friends (
	P_Id int(12) primary key not null auto_increment,
	accepted bool not null,
    sender varchar(50) not null,
    receiver varchar(50) not null
);

CREATE TABLE MovieLists (
	P_Id int(12) primary key not null auto_increment,
	list_type varchar(50) not null,
    username varchar(50) not null,
    movieID int not null,
    movieName varchar(50) not null
);

CREATE TABLE MovieEvents (
	eventID varchar(50) primary key not null,
    owner varchar(50) not null,
    movieID int not null,
    eventTitle varchar(250) not null,
    public_private boolean not null,
    movieTime varchar(50) not null,
    theater varchar(50) not null
);

CREATE TABLE EventParticipants (
	P_Id int(12) primary key not null auto_increment,
	eventID varchar(50) not null,
    accepted bool not null,
    username varchar(50) not null
);