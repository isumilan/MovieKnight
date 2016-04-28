-- dummy data

USE movieknight_database;

INSERT INTO users(username, password, zipcode) VALUES('Chaitanya', '5f4dcc3b5aa765d61d8327deb882cf99', 1);
INSERT INTO users(username, password, zipcode) VALUES('Isumi', '5f4dcc3b5aa765d61d8327deb882cf99', 2);
INSERT INTO users(username, password, zipcode) VALUES('Kevin', '5f4dcc3b5aa765d61d8327deb882cf99', 3);
INSERT INTO users(username, password, zipcode) VALUES('Nathan', '5f4dcc3b5aa765d61d8327deb882cf99', 4);
INSERT INTO users(username, password, zipcode) VALUES('Samuel', '5f4dcc3b5aa765d61d8327deb882cf99', 5);

INSERT INTO friends(P_Id, accepted, sender, receiver) VALUES(1, 1, 'Chaitanya', 'Isumi');
INSERT INTO friends(P_Id, accepted, sender, receiver) VALUES(2, 0, 'Chaitanya', 'Kevin');
INSERT INTO friends(P_Id, accepted, sender, receiver) VALUES(3, 1, 'Kevin', 'Isumi');
INSERT INTO friends(P_Id, accepted, sender, receiver) VALUES(4, 0, 'Nathan', 'Samuel');
INSERT INTO friends(P_Id, accepted, sender, receiver) VALUES(5, 1, 'Chaitanya', 'Nathan');
INSERT INTO friends(P_Id, accepted, sender, receiver) VALUES(6, 0, 'Samuel', 'Kevin');

INSERT INTO movieevents(eventID, owner, movieID, eventTitle, public_private, movieTime, theater)
	VALUES('1', 'Nathan', 278927, 'Jungle Book', 1, 'April 28, 10am', 'Regal');    
INSERT INTO movieevents(eventID, owner, movieID, eventTitle, public_private, movieTime, theater)
	VALUES('2', 'Nathan', 209112, 'BvS', 1, 'May 1, 2pm', 'Home');
INSERT INTO movieevents(eventID, owner, movieID, eventTitle, public_private, movieTime, theater)
	VALUES('3', 'Chaitanya', 140607, 'Star Wars VII', 1, '1pm', 'Home');
INSERT INTO movieevents(eventID, owner, movieID, eventTitle, public_private, movieTime, theater)
	VALUES('4', 'Kevin', 76341, 'Mad Max', 0, '1pm', 'Edwards');
    
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(1, '1', 1, 'Nathan');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(2, '1', 1, 'Isumi');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(3, '1', 0, 'Kevin');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(4, '2', 1, 'Nathan');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(5, '2', 1, 'Chaitanya');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(6, '2', 0, 'Kevin');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(7, '3', 1, 'Chaitanya');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(8, '3', 1, 'Nathan');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(9, '3', 0, 'Kevin');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(10, '4', 1, 'Kevin');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(11, '4', 1, 'Nathan');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(12, '4', 0, 'Samuel');

INSERT INTO movielists(P_Id, list_type, username, movieID) VALUES(1, 'towatch', 'Nathan', 278927);
INSERT INTO movielists(P_Id, list_type, username, movieID) VALUES(2, 'towatch', 'chaytanya', 278927);
INSERT INTO movielists(P_Id, list_type, username, movieID) VALUES(3, 'towatch', 'Chaitanya', 140607);
INSERT INTO movielists(P_Id, list_type, username, movieID) VALUES(4, 'watched', 'Kevin', 140607);
INSERT INTO movielists(P_Id, list_type, username, movieID) VALUES(5, 'watched', 'Kevin', 76341);
INSERT INTO movielists(P_Id, list_type, username, movieID) VALUES(6, 'liked', 'Isumi', 140607);
INSERT INTO movielists(P_Id, list_type, username, movieID) VALUES(7, 'liked', 'Kevin', 140607);
INSERT INTO movielists(P_Id, list_type, username, movieID) VALUES(8, 'liked', 'Samuel', 140607);
INSERT INTO movielists(P_Id, list_type, username, movieID) VALUES(9, 'liked', 'Nathan', 140607);