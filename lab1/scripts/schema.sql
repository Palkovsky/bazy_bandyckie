/* SCHEMA DEFINITION */
DROP TABLE OSOBY CASCADE CONSTRAINTS;
DROP TABLE REZERWACJE CASCADE CONSTRAINTS;
DROP TABLE WYCIECZKI CASCADE CONSTRAINTS;

CREATE TABLE OSOBY
(
  ID_OSOBY INT GENERATED ALWAYS AS IDENTITY NOT NULL
, IMIE VARCHAR2(50)
, NAZWISKO VARCHAR2(50)
, PESEL VARCHAR2(11)
, KONTAKT VARCHAR2(100)
, CONSTRAINT OSOBY_PK PRIMARY KEY
  (
    ID_OSOBY
  )
  ENABLE
);


CREATE TABLE WYCIECZKI
(
  ID_WYCIECZKI INT GENERATED ALWAYS AS IDENTITY NOT NULL
, NAZWA VARCHAR2(100)
, KRAJ VARCHAR2(50)
, DATA DATE
, OPIS VARCHAR2(200)
, LICZBA_MIEJSC INT
, CONSTRAINT WYCIECZKI_PK PRIMARY KEY
  (
    ID_WYCIECZKI
  )
  ENABLE
);


CREATE TABLE REZERWACJE
(
  NR_REZERWACJI INT GENERATED ALWAYS AS IDENTITY NOT NULL
, ID_WYCIECZKI INT
, ID_OSOBY INT
, STATUS CHAR(1)
, CONSTRAINT REZERWACJE_PK PRIMARY KEY
  (
    NR_REZERWACJI
  )
  ENABLE
);


ALTER TABLE REZERWACJE
ADD CONSTRAINT REZERWACJE_FK1 FOREIGN KEY
(
  ID_OSOBY
)
REFERENCES OSOBY
(
  ID_OSOBY
)
ENABLE;

ALTER TABLE REZERWACJE
ADD CONSTRAINT REZERWACJE_FK2 FOREIGN KEY
(
  ID_WYCIECZKI
)
REFERENCES WYCIECZKI
(
  ID_WYCIECZKI
)
ENABLE;

ALTER TABLE REZERWACJE
ADD CONSTRAINT REZERWACJE_CHK1 CHECK
(status IN ('N','P','Z','A'))
ENABLE;

/* SEED */
INSERT INTO osoby (imie, nazwisko, pesel, kontakt)
    VALUES('Adam', 'Kowalski', '87654321', 'tel: 6623');
INSERT INTO osoby (imie, nazwisko, pesel, kontakt)
    VALUES('Jan', 'Nowak', '12345678', 'tel: 2312, dzwonić po 18.00');
INSERT INTO osoby (imie, nazwisko, pesel, kontakt)
    VALUES('Wacław', 'Frydrych', '489712678', 'frydrych@agh.edu.pl');
INSERT INTO osoby (imie, nazwisko, pesel, kontakt)
    VALUES('Mariusz', 'Meszka', '237894156', 'meszka@agh.edu.pl');
INSERT INTO osoby (imie, nazwisko, pesel, kontakt)
    VALUES('Marek', 'Gajęcki', '731648726', 'mgajecki@agh.edu.pl');
INSERT INTO osoby (imie, nazwisko, pesel, kontakt)
    VALUES('Jarosław', 'Koźlak', '157894635', 'kozlak@agh.edu.pl');
INSERT INTO osoby (imie, nazwisko, pesel, kontakt)
    VALUES('Piotr', 'Faliszewski', '712347596', 'faliszw@agh.edu.pl');
INSERT INTO osoby (imie, nazwisko, pesel, kontakt)
    VALUES('Robert', 'Marcjan', '712456389', 'marcjan@agh.edu.pl');
INSERT INTO osoby (imie, nazwisko, pesel, kontakt)
    VALUES('Robert', 'Kubica', '487123549', 'ROBERT KUBICA DRAJVER BŁYSKAWICA robert@kubica.pl');
INSERT INTO osoby (imie, nazwisko, pesel, kontakt)
    VALUES('Rafał', 'Juraszek', '45978123', 'rafal@gmail.com');

INSERT INTO wycieczki (nazwa, kraj, data, opis, liczba_miejsc)
    VALUES ('Wycieczka do Paryza','Francja', TO_DATE('2019-01-01','YYYY-MM-DD'),'Ciekawa wycieczka ...',3);
INSERT INTO wycieczki (nazwa, kraj, data, opis, liczba_miejsc)
    VALUES ('Piękny Kraków','Polska',TO_DATE('2020-02-03','YYYY-MM-DD'),'Najciekawa wycieczka ...',2);
INSERT INTO wycieczki (nazwa, kraj, data, opis, liczba_miejsc)
    VALUES ('Wieliczka','Polska', TO_DATE('2020-03-03','YYYY-MM-DD'),'Zadziwiająca kopalnia ...',2);
INSERT INTO wycieczki (nazwa, kraj, data, opis, liczba_miejsc)
    VALUES ('Wycieczka do Londynu','Anglia',TO_DATE('2020-04-03','YYYY-MM-DD'), 'Lux wycieczka, Big Ben i takie tam.', 4);

INSERT INTO rezerwacje(id_wycieczki, id_osoby, status) VALUES (1,1,'N');
INSERT INTO rezerwacje(id_wycieczki, id_osoby, status) VALUES (2,2,'P');
INSERT INTO rezerwacje(id_wycieczki, id_osoby, status) VALUES (2,4,'A');
INSERT INTO rezerwacje(id_wycieczki, id_osoby, status) VALUES (4,9,'Z');
INSERT INTO rezerwacje(id_wycieczki, id_osoby, status) VALUES (4,3,'P');
INSERT INTO rezerwacje(id_wycieczki, id_osoby, status) VALUES (4,8,'P');
INSERT INTO rezerwacje(id_wycieczki, id_osoby, status) VALUES (3,5,'N');
INSERT INTO rezerwacje(id_wycieczki, id_osoby, status) VALUES (3,1,'Z');
INSERT INTO rezerwacje(id_wycieczki, id_osoby, status) VALUES (4,7,'N');
INSERT INTO rezerwacje(id_wycieczki, id_osoby, status) VALUES (1,3,'N');

SELECT * FROM WYCIECZKI;
SELECT * FROM OSOBY;
SELECT * FROM REZERWACJE;
