/* table PLAYER

This table describes the superclass attributes that each player inherits 
(
	UUID INT ,
	FNAME VARCHAR(32),
	POS VARCHAR(1),
	LNAME VARCHAR(32),
	INJ BOOLEAN,
	DPICK INT,
	POS_UUID INT,
        PRIMARY KEY(UUID)
	
);  */

CREATE TABLE K
(
	UUID INT ,
	FNAME VARCHAR(32),
	POS VARCHAR(1),
	LNAME VARCHAR(32),
	INJ BOOLEAN,
	DPICK INT,
	POS_UUID INT,
        PRIMARY KEY(UUID),
        -------------------
        K_UUID INT,
	BELFORTY INT,
	FORTYFIFTY INT,
	FIFTYUP INT,
	MADE INT,
        ATT INT,
        EXTRAMADE INT,
        EXTRAATT INT,
        PRIMARY KEY(K_UUID)
);

create table DEFST(
        UUID INT ,
	FNAME VARCHAR(32),
	POS VARCHAR(1),
	LNAME VARCHAR(32),
	INJ BOOLEAN,
	DPICK INT,
	POS_UUID INT,
        PRIMARY KEY(UUID),
        -------------------
        DEF_UUID INT,
        TD INT,
        INTER INT,
        FR INT,
        SCK INT,
        SFTY INT,
        BLK INT,
        PA INT,
        PRIMARY KEY(DEF_UUID)
);

create table RB
(
        UUID INT ,
	FNAME VARCHAR(32),
	POS VARCHAR(1),
	LNAME VARCHAR(32),
	INJ BOOLEAN,
	DPICK INT,
	POS_UUID INT,
        PRIMARY KEY(UUID),
        -------------------
        RB_UUID INT,
        RATT INT,
        RYDS INT,
        RAVG INT,
        RTD INT,
        REC INT,
        RECYDS INT,
        RECTD INT,
        TAR INT,
        PRIMARY KEY(RB_UUID)
);

create table WRTE
(
        UUID INT ,
	FNAME VARCHAR(32),
	POS VARCHAR(1),
	LNAME VARCHAR(32),
	INJ BOOLEAN,
	DPICK INT,
	POS_UUID INT,
        PRIMARY KEY(UUID),
        -----------------------------
        WR_UUID INT,
        RATT INT,
        RYDS INT,
        RAVG INT,
        RTD INT,
        REC INT,
        RECYDS INT,
        RECTD INT,
        TAR INT,
        PRIMARY KEY(WR_UUID)
);

create table QB
(
        UUID INT ,
	FNAME VARCHAR(32),
	POS VARCHAR(1),
	LNAME VARCHAR(32),
	INJ BOOLEAN,
	DPICK INT,
	POS_UUID INT,
        PRIMARY KEY(UUID),
        ----------------------
        QB_UUID INT,
        PCTCOM DECIMAL,
        PYDS INT,
        PTD INT,
        INTFUM INT,
        RYDS INT,
        RTD INT,
        RATT INT,
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

CREATE TABLE USR
(
        USERNAME VARCHAR(32),
        FNAME VARCHAR(32),
        LNAME VARCHAR(32),
        BDATE INT
        PRIMARY KEY(USERNAME)
);

CREATE TABLE TEAM
(
        TEAM_UUID INT,
        TNAME VARCHAR(32),
        FDATE INT,
        DDATE INT
);