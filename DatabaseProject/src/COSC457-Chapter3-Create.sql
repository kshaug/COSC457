-- The line below is only for SQLite, if used.
--PRAGMA foreign_keys = ON;

-----------------------------------------------
------------- Drop the old Tables -------------
----------------------------------------------- 
drop table DEPENDENT;
drop table WORKS_ON;
drop table PROJECT;
drop table DEPT_LOCATIONS;

-- Due to a circular FK relationship between EMPLOYEE and DEPARTMENT, one of the 
-- mutual constraints need to be dropped first.
alter table EMPLOYEE drop constraint EMPLOYEE_DEPARTMENT_FK;
drop table DEPARTMENT;
drop table EMPLOYEE;

---------------------------------------------
------------- Create the Tables -------------
---------------------------------------------

-- Create table establishing keys with the attribute declarations.  This ends up
-- forcing the DBMS to automatically generate constraint names, which can make
-- things difficult if you ever need to alter the constraints.
create table EMPLOYEE
(
	SSN NUMERIC(9) primary key,
	FNAME VARCHAR(32),
	MINT VARCHAR(1),
	LNAME VARCHAR(32),
	BDATE DATE,
	ADDRESS VARCHAR(128),
	SEX VARCHAR(1),
	SALARY NUMERIC(7),
	SUPER_SSN NUMERIC(9) references EMPLOYEE (SSN),
	DNO NUMERIC(2)
);


-- Create the rest of the tables using the preferred technique.
create table DEPARTMENT
(
	DNUMBER NUMERIC(2),
	DNAME VARCHAR(32) not null,
	MGR_SSN NUMERIC(9),
	MGR_START_DATE DATE,
	constraint DEPARTMENT_PK primary key (DNUMBER),
	constraint DEPARTMENT_EMPLOYEE_FK foreign key (MGR_SSN) references EMPLOYEE (SSN)
);

create table DEPT_LOCATIONS
(
	DNUMBER NUMERIC(2),
	DLOCATION VARCHAR(32),
	constraint DEPT_LOCATIONS_PK primary key (DNUMBER, DLOCATION),
        constraint DEPTLOCATIONS_DEPARTMENT_FK foreign key (DNUMBER) references DEPARTMENT (DNUMBER)
);

create table PROJECT
(
	PNUMBER NUMERIC(2),
	PNAME VARCHAR(32),
	PLOCATION VARCHAR(32),
	DNUM NUMERIC(2),
	constraint PROJECT_PK primary key (PNUMBER),
	constraint PROJECT_DEPARTMENT_FK foreign key (DNUM) references DEPARTMENT (DNUMBER)
);

create table WORKS_ON
(
	ESSN NUMERIC(9),
	PNO NUMERIC(2),
	HOURS REAL,
	constraint WORKSON_PK primary key (ESSN, PNO),
	constraint WORKSON_EMPLOYEE_FK foreign key (ESSN) references EMPLOYEE (SSN),
    constraint WORKSON_PROJECT_FK foreign key (PNO) references PROJECT (PNUMBER)
);

create table DEPENDENT
(
	ESSN NUMERIC(9),
	DEPENDENT_NAME VARCHAR(64),
	SEX VARCHAR(1),
	BDATE DATE,
	RELATIONSHIP VARCHAR(16),
	constraint DEPENDENT_PK primary key (ESSN, DEPENDENT_NAME),
	constraint DEPENDENT_EMPLOYEE_FK foreign key (ESSN) references EMPLOYEE (SSN)
	
);

-- Add a "circular" FK relationship from EMPLOYEE to DEPARTMENT.  Circular FK's
-- are generally undesirable, but it done here to preserve the schema as illustrated
-- in the text.  Ideally, the schema could be easily altered to eliminate this issue.
alter table EMPLOYEE add constraint EMPLOYEE_DEPARTMENT_FK foreign key (DNO) references DEPARTMENT (DNUMBER);



-------------------------------------------
------------- Insert the data -------------
-------------------------------------------
INSERT INTO EMPLOYEE (FNAME, MINT, LNAME, SSN, BDATE, ADDRESS, SEX, SALARY, SUPER_SSN, DNO) 
	VALUES ('James', 'E', 'Borg', 888665555, '1977-11-10', '450 Stone, Houston, TX', 'M', 55000.00, NULL, NULL);
INSERT INTO EMPLOYEE (FNAME, MINT, LNAME, SSN, BDATE, ADDRESS, SEX, SALARY, SUPER_SSN, DNO) 
	VALUES ('Franklin', 'T', 'Wong', 333445555, '1965-12-08', '638 Voss, Houston, TX', 'M', 40000.00, 888665555, NULL);
INSERT INTO EMPLOYEE (FNAME, MINT, LNAME, SSN, BDATE, ADDRESS, SEX, SALARY, SUPER_SSN, DNO) 
	VALUES ('John', 'B', 'Smith', 123456789, '1975-01-09', '731 Fondren, Houston, TX', 'M', 30000.00, 333445555, NULL);
INSERT INTO EMPLOYEE (FNAME, MINT, LNAME, SSN, BDATE, ADDRESS, SEX, SALARY, SUPER_SSN, DNO) 
	VALUES ('Jennifer', 'S', 'Wallace', 987654321, '1951-06-20', '291 Berry, Bellaire, TX', 'F', 43000.00, 888665555, NULL);
INSERT INTO EMPLOYEE (FNAME, MINT, LNAME, SSN, BDATE, ADDRESS, SEX, SALARY, SUPER_SSN, DNO) 
	VALUES ('Alicia', 'J', 'Zelaya', 999887777, '1978-01-19', '3321 Castle, Spring, TX', 'F', 25000.00, 987654321, NULL);
INSERT INTO EMPLOYEE (FNAME, MINT, LNAME, SSN, BDATE, ADDRESS, SEX, SALARY, SUPER_SSN, DNO) 
	VALUES ('Ramesh', 'K', 'Narayan', 666884444, '1972-09-15', '975 Fire Oak, Humble, TX', 'M', 38000.00, 333445555, NULL);
INSERT INTO EMPLOYEE (FNAME, MINT, LNAME, SSN, BDATE, ADDRESS, SEX, SALARY, SUPER_SSN, DNO) 
	VALUES ('Joyce', 'A', 'English', 453453453, '1982-07-31', '5631 Rice, Houston, TX', 'F', 25000.00, 333445555, NULL);
INSERT INTO EMPLOYEE (FNAME, MINT, LNAME, SSN, BDATE, ADDRESS, SEX, SALARY, SUPER_SSN, DNO) 
	VALUES ('Ahmad', 'V', 'Jabbar', 987987987, '1979-03-29', '980 Dallas, Houston, TX', 'M', 25000.00, 987654321, NULL);
INSERT INTO EMPLOYEE (FNAME, MINT, LNAME, SSN, BDATE, ADDRESS, SEX, SALARY, SUPER_SSN, DNO) 
	VALUES ('Short', NULL, 'Timer', 999999999, '1987-11-10', 'Memory Lane', 'M', 999999.99, 453453453, NULL);
	
INSERT INTO DEPARTMENT (DNAME, DNUMBER, MGR_SSN, MGR_START_DATE) 
	VALUES ('Research', 5, 333445555, '1988-05-22');
INSERT INTO DEPARTMENT (DNAME, DNUMBER, MGR_SSN, MGR_START_DATE) 
	VALUES ('Administration', 4, 987654321, '1995-01-01');
INSERT INTO DEPARTMENT (DNAME, DNUMBER, MGR_SSN, MGR_START_DATE) 
	VALUES ('Headquarters', 1, 888665555, '1981-06-19');
	
INSERT INTO DEPT_LOCATIONS (DNUMBER, DLOCATION) 
	VALUES (1, 'Houston');
INSERT INTO DEPT_LOCATIONS (DNUMBER, DLOCATION) 
	VALUES (4, 'Stafford');
INSERT INTO DEPT_LOCATIONS (DNUMBER, DLOCATION) 
	VALUES (5, 'Bellaire ');
INSERT INTO DEPT_LOCATIONS (DNUMBER, DLOCATION) 
	VALUES (5, 'Houston ');
INSERT INTO DEPT_LOCATIONS (DNUMBER, DLOCATION) 
	VALUES (5, 'Sugarland ');

INSERT INTO PROJECT (PNAME, PNUMBER, PLOCATION, DNUM) 
	VALUES ('ProductX', 1, 'Bellaire', 5);
INSERT INTO PROJECT (PNAME, PNUMBER, PLOCATION, DNUM) 
	VALUES ('ProductY', 2, 'Sugarland', 5);
INSERT INTO PROJECT (PNAME, PNUMBER, PLOCATION, DNUM) 
	VALUES ('ProductZ ', 3, 'Houston', 5);
INSERT INTO PROJECT (PNAME, PNUMBER, PLOCATION, DNUM) 
	VALUES ('Computerization', 10, 'Stafford', 4);
INSERT INTO PROJECT (PNAME, PNUMBER, PLOCATION, DNUM) 
	VALUES ('Reorganization', 20, 'Houston', 1);
INSERT INTO PROJECT (PNAME, PNUMBER, PLOCATION, DNUM) 
	VALUES ('Newbenefits', 30, 'Stafford', 4);

INSERT INTO WORKS_ON (ESSN, PNO, HOURS) 
	VALUES (123456789, 1, 32.5);
INSERT INTO WORKS_ON (ESSN, PNO, HOURS) 
	VALUES (123456789, 2, 7.5);
INSERT INTO WORKS_ON (ESSN, PNO, HOURS) 
	VALUES (666884444, 3, 40.0);
INSERT INTO WORKS_ON (ESSN, PNO, HOURS) 
	VALUES (453453453, 1, 20.0);
INSERT INTO WORKS_ON (ESSN, PNO, HOURS) 
	VALUES (453453453, 2, 20.0);
INSERT INTO WORKS_ON (ESSN, PNO, HOURS) 
	VALUES (333445555, 2, 10.0);
INSERT INTO WORKS_ON (ESSN, PNO, HOURS) 
	VALUES (333445555, 3, 10.0);
INSERT INTO WORKS_ON (ESSN, PNO, HOURS) 
	VALUES (333445555, 10, 10.0);
INSERT INTO WORKS_ON (ESSN, PNO, HOURS) 
	VALUES (333445555, 20, 10.0);
INSERT INTO WORKS_ON (ESSN, PNO, HOURS) 
	VALUES (999887777, 30, 30.0);
INSERT INTO WORKS_ON (ESSN, PNO, HOURS) 
	VALUES (999887777, 10, 10.0);
INSERT INTO WORKS_ON (ESSN, PNO, HOURS) 
	VALUES (987987987, 10, 35.0);
INSERT INTO WORKS_ON (ESSN, PNO, HOURS) 
	VALUES (987987987, 30, 5.0);
INSERT INTO WORKS_ON (ESSN, PNO, HOURS) 
	VALUES (987654321, 30, 20.0);
INSERT INTO WORKS_ON (ESSN, PNO, HOURS) 
	VALUES (987654321, 20, 15.0);
INSERT INTO WORKS_ON (ESSN, PNO, HOURS) 
	VALUES (888665555, 20, NULL);

INSERT INTO DEPENDENT (ESSN, DEPENDENT_NAME, SEX, BDATE, RELATIONSHIP) 
	VALUES (333445555, 'Alice', 'F', '1986-04-05', 'Daughter');
INSERT INTO DEPENDENT (ESSN, DEPENDENT_NAME, SEX, BDATE, RELATIONSHIP) 
	VALUES (333445555, 'Theodore', 'M', '1983-10-25', 'Son');
INSERT INTO DEPENDENT (ESSN, DEPENDENT_NAME, SEX, BDATE, RELATIONSHIP) 
	VALUES (333445555, 'Joy', 'F', '1958-05-03', 'Spouse');
INSERT INTO DEPENDENT (ESSN, DEPENDENT_NAME, SEX, BDATE, RELATIONSHIP) 
	VALUES (987654321, 'Abner', 'M', '1942-02-28', 'Spouse');
INSERT INTO DEPENDENT (ESSN, DEPENDENT_NAME, SEX, BDATE, RELATIONSHIP) 
	VALUES (123456789, 'Michael', 'M', '1988-01-04', 'Son');
INSERT INTO DEPENDENT (ESSN, DEPENDENT_NAME, SEX, BDATE, RELATIONSHIP) 
	VALUES (123456789, 'Alice', 'F', '1988-12-30', 'Daughter');
INSERT INTO DEPENDENT (ESSN, DEPENDENT_NAME, SEX, BDATE, RELATIONSHIP) 
	VALUES (123456789, 'Elizabeth', 'F', '1967-05-05', 'Spouse');



-- Now that both EMPLOYEE and DEPARTMENT are populated, we can set the departments
-- of the employees.  (With the circular constraints, we couldn't create a DEPARTMENT
-- tuple with an EMPLOYEE and visa-versa.  By allowing DNO in EMPLOYEE to be NULL,
-- we can set it after the fact.
UPDATE EMPLOYEE SET DNO=5 where SSN=123456789;
UPDATE EMPLOYEE SET DNO=5 where SSN=333445555;
UPDATE EMPLOYEE SET DNO=4 where SSN=999887777;
UPDATE EMPLOYEE SET DNO=4 where SSN=987654321;
UPDATE EMPLOYEE SET DNO=5 where SSN=666884444;
UPDATE EMPLOYEE SET DNO=5 where SSN=453453453;
UPDATE EMPLOYEE SET DNO=4 where SSN=987987987;
UPDATE EMPLOYEE SET DNO=1 where SSN=888665555;