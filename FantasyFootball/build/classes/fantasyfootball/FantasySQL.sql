DROP TABLE K;
DROP TABLE DEFST;
DROP TABLE QB;
DROP TABLE RB;
DROP TABLE WRTE;
DROP TABLE GAME;
DROP TABLE FANTASYUSER;

/*create table PLAYER
(
	UUID INT ,
	FNAME VARCHAR(32),
	POS VARCHAR(10),
	LNAME VARCHAR(32),
        PROTEAM VARCHAR(32),
	INJ BOOLEAN,
	DPICK INT,
	POS_UUID INT,
        PRIMARY KEY(UUID)
	
);  */

CREATE TABLE K
(
        K_UUID INT,
	BELFORTY INT,
	FORTYFIFTY INT,
	FIFTYUP INT,
	MADE INT,
        ATT INT,
        EXTRAMADE INT,
        EXTRAATT INT,
        FNAME VARCHAR(32),
	POS VARCHAR(10),
	LNAME VARCHAR(32),
        PROTEAM VARCHAR(32),
	INJ BOOLEAN,
	DPICK INT,
        PRIMARY KEY(K_UUID)
);

create table DEFST(
        DEF_UUID INT,
        TD INT,
        INTER INT,
        FR INT,
        SCK INT,
        SFTY INT,
        BLK INT,
        PA INT,
        FNAME VARCHAR(32),
	POS VARCHAR(10),
	LNAME VARCHAR(32),
        PROTEAM VARCHAR(32),
	INJ BOOLEAN,
	DPICK INT,
        PRIMARY KEY(DEF_UUID)
);

create table RB
(
        RB_UUID INT,
        RATT INT,
        RYDS INT,
        RAVG INT,
        RTD INT,
        REC INT,
        RECYDS INT,
        RECTD INT,
        TAR INT,
        FNAME VARCHAR(32),
	POS VARCHAR(10),
	LNAME VARCHAR(32),
        PROTEAM VARCHAR(32),
	INJ BOOLEAN,
	DPICK INT,
        PRIMARY KEY(RB_UUID)
);

create table WRTE
(
        WR_UUID INT,
        RATT INT,
        RYDS INT,
        RAVG INT,
        RTD INT,
        REC INT,
        RECYDS INT,
        RECTD INT,
        TAR INT,
        FNAME VARCHAR(32),
	POS VARCHAR(10),
	LNAME VARCHAR(32),
        PROTEAM VARCHAR(32),
	INJ BOOLEAN,
	DPICK INT,
        PRIMARY KEY(WR_UUID)
);

create table QB
(
        QB_UUID INT,
        PCTCOM DECIMAL,
        PYDS INT,
        PTD INT,
        INTFUM INT,
        RYDS INT,
        RTD INT,
        RATT INT,
        FNAME VARCHAR(32),
	POS VARCHAR(10),
	LNAME VARCHAR(32),
        PROTEAM VARCHAR(32),
	INJ BOOLEAN,
	DPICK INT,
        PRIMARY KEY(QB_UUID)
);

CREATE TABLE GAME
(
        G_UUID INT,
        ASCORE INT,
        HSCORE INT,
        ATEAM INT,
        HTEAM INT,
        VENUE VARCHAR(32),
        PRIMARY KEY(G_UUID)
);

CREATE TABLE FANTASYUSER
(
        AGE INT,
        FNAME VARCHAR(32),
        LNAME VARCHAR(32),
        USERNAME VARCHAR(32),
        BDATE DATE,
);

INSERT INTO DEFST VALUES(1, 0, 0, 0, 0, 0, 0, 0, 'Baltimore', 'DEF/ST', 'Ravens', 'Baltimore Ravens', FALSE, NULL);

INSERT INTO K VALUES(1, 0, 0, 0, 0, 0, 0, 0, , 'Justin', 'K', 'Tucker', 'Baltimore Ravens', FALSE, NULL);

INSERT INTO RB VALUES(1, 0, 0, 0, 0, 0, 0, 0, 0, 'Vonta', 'RB', 'Leach', 'Baltimore Ravens', FALSE, NULL);

INSERT INTO RB VALUES (2,  0, 0, 0, 0, 0, 0, 0, 0, 'Kyle', 'RB', 'Juszczyk', 'Baltimore Ravens', FALSE, NULL);

INSERT INTO QB VALUES(1, 0, 0, 0, 0, 0, 0, 0, 'Joe', 'QB', 'Flacco', 'Baltimore Ravens', FALSE, NULL);

INSERT INTO QB VALUES(2,  0, 0, 0, 0, 0, 0, 0, 'Tyrod', 'QB', 'Taylor', 'Baltimore Ravens', FALSE, NULL);

INSERT INTO RB VALUES(3, 0, 0, 0, 0, 0, 0, 0, 0, 'Bernard', 'RB', 'Pierce', 'Baltimore Ravens', FALSE, NULL);

INSERT INTO WRTE VALUES(1, 0, 0, 0, 0, 0, 0, 0, 0, 'Dallas', 'WR/TE', 'Clark', 'Baltimore Ravens', FALSE, NULL);

INSERT INTO WRTE VALUES(2, 0, 0, 0, 0, 0, 0, 0, 0, 'Ed', 'WR/TE', 'Dickson', 'Baltimore Ravens', FALSE, NULL);

INSERT INTO WRTE VALUES(3, 0, 0, 0, 0, 0, 0, 0, 0, 'Marlon', 'WR/TE', 'Brown', 'Baltimore Ravens', FALSE, NULL);

INSERT INTO WRTE VALUES(4, 0, 0, 0, 0, 0, 0, 0, 0, 'Tandon', 'WR/TE', 'Doss', 'Baltimore Ravens', FALSE, NULL);

INSERT INTO WRTE VALUES(5, 0, 0, 0, 0, 0, 0, 0, 0, 'Torrey', 'WR/TE', 'Smith', 'Baltimore Ravens', FALSE, NULL);

INSERT INTO WRTE VALUES(6, 0, 0, 0, 0, 0, 0, 0, 0, 'Brandon', 'WR/TE', 'Stokely', 'Baltimore Ravens', FALSE, NULL);

INSERT INTO WRTE VALUES(7, 0, 0, 0, 0, 0, 0, 0, 0, 'Deonte', 'WR/TE', 'Thompson', 'Baltimore Ravens', FALSE, NULL);

INSERT INTO WRTE VALUES(8, 0, 0, 0, 0, 0, 0, 0, 0, 'Jacoby', 'WR/TE', 'Jones', 'Baltimore Ravens', FALSE, NULL);

INSERT INTO RB VALUES(4, 0, 0, 0, 0, 0, 0, 0, 0, 'Will', 'RB', 'Johnson', 'Pittsburgh Steelers', FALSE, NULL);

INSERT INTO K VALUES(2, 0, 0, 0, 0, 0, 0, 0, 'Shaun', 'K', 'Suisham', 'Pittsburgh Steelers', FALSE, NULL);

INSERT INTO QB VALUES(3, 0, 0, 0, 0, 0, 0, 0, 'Bruce', 'QB', 'Gradkowski', 'Pittsburgh Steelers', FALSE, NULL);

INSERT INTO QB VALUES(4, 0, 0, 0, 0, 0, 0, 0, 'Landry', 'QB', 'Jones', 'Pittsburgh Steelers', FALSE, NULL);

INSERT INTO QB VALUES(5, 0, 0, 0, 0, 0, 0, 0, 'Ben', 'QB', 'Roethlisberger', 'Pittsburgh Steelers', FALSE, NULL);

INSERT INTO RB VALUES(5, 0, 0, 0, 0, 0, 0, 0, 0, 'LeVeon', 'RB', 'Bell', 'Pittsburgh Steelers', FALSE, NULL);

INSERT INTO RB VALUES(6, 0, 0, 0, 0, 0, 0, 0, 0, 'Jonathan', 'RB', 'Dwyer', 'Pittsburgh Steelers', FALSE, NULL);

INSERT INTO RB VALUES(7, 0, 0, 0, 0, 0, 0, 0, 0, 'Felix', 'RB', 'Jones', 'Pittsburgh Steelers', FALSE, NULL);

INSERT INTO DEFST VALUES(2, 0, 0, 0, 0, 0, 0, 0, 'Steelers', 'DEF/ST', 'Defense', 'Pittsburgh Steelers', FALSE, NULL);

INSERT INTO WRTE VALUES(9, 0, 0, 0, 0, 0, 0, 0, 0, 'Richard', 'WR/TE', 'Gordon', 'Pittsburgh Steelers', FALSE, NULL);

INSERT INTO WRTE VALUES(10, 0, 0, 0, 0, 0, 0, 0, 0, 'Heath', 'WR/TE', 'Miller', 'Pittsburgh Steelers', FALSE, NULL);

INSERT INTO WRTE VALUES(11, 0, 0, 0, 0, 0, 0, 0, 0, 'Michael', 'WR/TE', 'Palmer', 'Pittsburgh Steelers', FALSE, NULL);

INSERT INTO WRTE VALUES(12, 0, 0, 0, 0, 0, 0, 0, 0, 'David', 'WR/TE', 'Paulson', 'Pittsburgh Steelers', FALSE, NULL);

INSERT INTO WRTE VALUES(13, 0, 0, 0, 0, 0, 0, 0, 0, 'Antonio', 'WR/TE', 'Brown', 'Pittsburgh Steelers', FALSE, NULL);

INSERT INTO WRTE VALUES(14, 0, 0, 0, 0, 0, 0, 0, 0, 'Jerricho', 'WR/TE', 'Cotchery', 'Pittsburgh Steelers', FALSE, NULL);

INSERT INTO WRTE VALUES(15, 0, 0, 0, 0, 0, 0, 0, 0, 'Derek', 'WR/TE', 'Moye', 'Pittsburgh Steelers', FALSE, NULL);

INSERT INTO WRTE VALUES(16, 0, 0, 0, 0, 0, 0, 0, 0, 'Emmanuel', 'WR/TE', 'Sanders', 'Pittsburgh Steelers', FALSE, NULL);

INSERT INTO WRTE VALUES(17, 0, 0, 0, 0, 0, 0, 0, 0, 'Markus', 'WR/TE', 'Wheaton', 'Pittsburgh Steelers', FALSE, NULL);