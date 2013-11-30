-- The line below is only for SQLite, if used.
--PRAGMA foreign_keys = ON;

-----------------------------------------------
------------- Drop the old Tables -------------
----------------------------------------------- 


---------------------------------------------
------------- Create the Tables -------------
---------------------------------------------

-- Create table establishing keys with the attribute declarations.  This ends up
-- forcing the DBMS to automatically generate constraint names, which can make
-- things difficult if you ever need to alter the constraints.
create table PLAYER
(
	UUID NUMERIC(9) primary key,
	FNAME VARCHAR(32),
	POS VARCHAR(1),
	LNAME VARCHAR(32),
	INJ BOOLEAN,
	DPICK NUMERIC(12),
	POS_UUID NUMERIC(9),
	
);

create table K
(
	UUID NUMERIC(9),
	BELFORTY INT(3),
	FORTYFIFTY INT(3),
	FIFTYUP INT(3),
	MADE INT(3),
        ATT INT(3),
        EXTRAMADE INT(3),
        EXTRAATT INT(3)
);

create table DEFST
(
        UUID NUMERIC(9),
        TD INT(3),
        INTER INT(3),
        FR INT(3),
        SCK INT(3),
        SFTY INT(3),
        BLK INT(3),
        PA INT(3)
);

create table RB
(
        UUID NUMERIC(9),
        RATT INT(3),
        RYDS INT(3),
        RAVG INT(3),
        RTD INT(3),
        REC INT(3),
        RECYDS INT(3),
        RECTD INT(3),
        TAR INT(3)
);

create table WRTE
(
        UUID NUMERIC(9),
        RATT INT(3),
        RYDS INT(3),
        RAVG INT(3),
        RTD INT(3),
        REC INT(3),
        RECYDS INT(3),
        RECTD INT(3),
        TAR INT(3)
);

create table QB
(
        UUID NUMERIC(9),
        PCTCOM DECIMAL(3),
        PYDS INT(3),
        PTD INT(3),
        INTFUM INT(3),
        RYDS INT(3),
        RTD INT(3),
        RATT INT(3)
);

create table GAME
(
        G_UUID NUMERIC(9),
        ASCORE INT(3),
        HSCORE INT(3),
        ATEAM INT(3),
        HTEAM INT(3),
        VENUE VARCHAR(32)
);