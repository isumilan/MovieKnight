-- dummy data

USE movieknight_database;

INSERT INTO users(username, password, zipcode) VALUES('chaitanya', 'cc175b9c0f1b6a831c399e269772661', 1);
INSERT INTO users(username, password, zipcode) VALUES('isumi', 'cc175b9c0f1b6a831c399e269772661', 2);
INSERT INTO users(username, password, zipcode) VALUES('kevin', 'cc175b9c0f1b6a831c399e269772661', 3);
INSERT INTO users(username, password, zipcode) VALUES('nathan', 'cc175b9c0f1b6a831c399e269772661', 4);
INSERT INTO users(username, password, zipcode) VALUES('samuel', 'cc175b9c0f1b6a831c399e269772661', 5);

INSERT INTO friends(P_Id, accepted, sender, receiver) VALUES(1, 1, 'chaitanya', 'isumi');
INSERT INTO friends(P_Id, accepted, sender, receiver) VALUES(2, 0, 'chaitanya', 'kevin');
INSERT INTO friends(P_Id, accepted, sender, receiver) VALUES(3, 1, 'kevin', 'isumi');
INSERT INTO friends(P_Id, accepted, sender, receiver) VALUES(4, 0, 'nathan', 'samuel');
INSERT INTO friends(P_Id, accepted, sender, receiver) VALUES(5, 1, 'chaitanya', 'nathan');
INSERT INTO friends(P_Id, accepted, sender, receiver) VALUES(6, 0, 'samuel', 'kevin');

INSERT INTO movieevents(eventID, owner, movieID, eventTitle, public_private, movieTime, theater)
	VALUES('1', 'nathan', 278927, 'Jungle Book', 1, '1pm', 'Regal');    
INSERT INTO movieevents(eventID, owner, movieID, eventTitle, public_private, movieTime, theater)
	VALUES('2', 'nathan', 209112, 'BvS', 1, '1pm', 'Home');
INSERT INTO movieevents(eventID, owner, movieID, eventTitle, public_private, movieTime, theater)
	VALUES('3', 'chaitanya', 140607, 'Star Wars VII', 1, '1pm', 'Home');
INSERT INTO movieevents(eventID, owner, movieID, eventTitle, public_private, movieTime, theater)
	VALUES('4', 'kevin', 76341, 'Mad Max', 0, '1pm', 'Whatever');
    
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(1, '1', 1, 'nathan');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(2, '1', 1, 'isumi');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(3, '1', 0, 'kevin');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(4, '2', 1, 'nathan');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(5, '2', 1, 'chaitanya');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(6, '2', 0, 'kevin');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(7, '3', 1, 'chaitanya');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(8, '3', 1, 'nathan');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(9, '3', 0, 'kevin');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(10, '4', 1, 'kevin');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(11, '4', 1, 'nathan');
INSERT INTO eventparticipants(P_Id, eventID, accepted, username) VALUES(12, '4', 0, 'samuel');

INSERT INTO movielists(P_Id, list_type, username, movieID) VALUES(1, 'towatch', 'nathan', 278927);
INSERT INTO movielists(P_Id, list_type, username, movieID) VALUES(2, 'towatch', 'chaytanya', 278927);
INSERT INTO movielists(P_Id, list_type, username, movieID) VALUES(3, 'towatch', 'chaitanya', 140607);
INSERT INTO movielists(P_Id, list_type, username, movieID) VALUES(4, 'watched', 'kevin', 140607);
INSERT INTO movielists(P_Id, list_type, username, movieID) VALUES(5, 'watched', 'kevin', 76341);
INSERT INTO movielists(P_Id, list_type, username, movieID) VALUES(6, 'liked', 'isumi', 140607);
INSERT INTO movielists(P_Id, list_type, username, movieID) VALUES(7, 'liked', 'kevin', 140607);
INSERT INTO movielists(P_Id, list_type, username, movieID) VALUES(8, 'liked', 'samuel', 140607);
INSERT INTO movielists(P_Id, list_type, username, movieID) VALUES(9, 'liked', 'nathan', 140607);