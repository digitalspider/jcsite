CREATE TABLE Student (
	studentId	INTEGER PRIMARY KEY AUTOINCREMENT,
	firstName 	text NOT NULL,
	lastName 	text,
	course		text,
	year		integer
);

INSERT into Student (firstName,lastName,course,year) VALUES ("test","user","PhD", 2012);
INSERT into Student (firstName,lastName,course,year) VALUES ("david","vittor","MBT", 2016);
INSERT into Student (firstName,lastName,course,year) VALUES ("daniela","vittor","PhD", 2014);

