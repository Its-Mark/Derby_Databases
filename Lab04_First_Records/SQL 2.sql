CREATE TABLE firstRecords (
lastName        VARCHAR(50)     NOT NULL,
firstName       VARCHAR(50)     NOT NULL,
email           VARCHAR(80)     NOT NULL,
cellNumber      VARCHAR(12)     NOT NULL,
task            VARCHAR(80)     NOT NULL,
dueDate         VARCHAR(8)      NOT NULL,
CONSTRAINT      firstRecords_PK PRIMARY KEY (lastName, firstName, email, cellNumber)
);

INSERT INTO firstRecords (lastName, firstName, email, cellNumber, task, dueDate)
VALUES  ('Garcia', 'Mark', 'mark.garcia01@student.csulb.edu','323-203-5383', 'lab04', '01/28/21');

INSERT INTO firstRecords (lastName, firstName, email, cellNumber, task, dueDate)
VALUES  ('Solis', 'Brenda', 'brenda.solis@student.csulb.edu','562-555-1234', 'lab04', '01/28/21');

INSERT INTO firstRecords (lastName, firstName, email, cellNumber, task, dueDate)
VALUES  ('Delgado', 'Larry', 'larry.delgado@student.csulb.edu','562-222-3141', 'lab04', '01/28/21');

INSERT INTO firstRecords (lastName, firstName, email, cellNumber, task, dueDate)
VALUES  ('Smith', 'Jenny', 'jenny.smith@student.csulb.edu','562-867-5309', 'lab04', '01/28/21');

INSERT INTO firstRecords (lastName, firstName, email, cellNumber, task, dueDate)
VALUES  ('Garcia', 'Mark', 'mark.garcia01@student.csulb.edu','323-203-5383', 'lab04', '01/28/21');
