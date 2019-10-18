/* Zadanie 5a */
CREATE OR REPLACE PROCEDURE dodaj_rezerwacje(id_wycieczki NUMBER, id_osoby NUMBER)
AS
osoby_count INT;
wycieczki_count INT;
rezerwacje_count INT;
BEGIN
SELECT COUNT(*) INTO osoby_count FROM OSOBY WHERE ID_OSOBY = dodaj_rezerwacje.id_osoby;
SELECT COUNT(*) INTO wycieczki_count FROM DOSTEPNE_WYCIECZKI WHERE ID_WYCIECZKI = dodaj_rezerwacje.id_wycieczki;
SELECT COUNT(*) INTO rezerwacje_count FROM REZERWACJE WHERE ID_WYCIECZKI = dodaj_rezerwacje.id_wycieczki AND ID_OSOBY = dodaj_rezerwacje.id_osoby;

IF (osoby_count != 1) THEN
raise_application_error(-20000, 'Invalid person identifier.');
END IF;
IF (wycieczki_count != 1) THEN
raise_application_error(-20001, 'Invalid trip identifier.');
END IF;
IF (rezerwacje_count != 0) THEN
raise_application_error(-20002, 'Booking already exists.');
END IF;

INSERT INTO REZERWACJE(ID_WYCIECZKI, ID_OSOBY, STATUS) VALUES (id_wycieczki, id_osoby, 'N');
END dodaj_rezerwacje;

/* Zadanie 5b */
CREATE OR REPLACE PROCEDURE zmien_status_rezerwacji(id_rezerwacji NUMBER, nowy_status CHAR)
AS
  id_wycieczki NUMBER;
  stary_status CHAR;
  counter INT;
BEGIN
  /* Czy rezerwacja istnieje */
  SELECT COUNT(*) INTO counter FROM REZERWACJE 
  WHERE NR_REZERWACJI = zmien_status_rezerwacji.id_rezerwacji;
  IF counter != 1 THEN
    RAISE_APPLICATION_ERROR(-20000, 'Invalid booking identifier.');
  END IF;

  /* Pobierz informacje o starym statusie oraz identyfikatorze wyczieczki */
  SELECT STATUS INTO stary_status FROM REZERWACJE 
    WHERE NR_REZERWACJI = zmien_status_rezerwacji.id_rezerwacji;
  SELECT ID_WYCIECZKI INTO id_wycieczki FROM REZERWACJE 
    WHERE NR_REZERWACJI = zmien_status_rezerwacji.id_rezerwacji;

  /* Sprawdź czy wycieczka ma miejsce w przyszłości. Jeśli nie to błąd. */
  SELECT COUNT(*) INTO counter FROM WYCIECZKI_PRZYSZLE WHERE ID_WYCIECZKI = id_wycieczki;
  IF counter = 0 THEN
    RAISE_APPLICATION_ERROR(-20001, 'Unable to change reservation status for past trip.');
  END IF;

  /* Sprawdź czy nowy_status == 'N'. Jeśli tak to błąd. */
  IF zmien_status_rezerwacji.nowy_status = 'N' THEN
    RAISE_APPLICATION_ERROR(-20002, 'nowy_status must not be N');
  END IF;

  /* Zmiana z opłaconej i potwierdzonej na samą potwierdzoną nie ma sensu */
  IF nowy_status = 'P' AND stary_status = 'Z' THEN
    RAISE_APPLICATION_ERROR(-20003, 'The reservation is already paid.');
  END IF;

  /* Sprawdź czy stary_status=A. Jeśli tak to sprawdź czy są wolne miejsca. Jeśli nie ma: BŁAD */
  IF stary_status = 'A' THEN
    SELECT COUNT(*) INTO counter FROM DOSTEPNE_WYCIECZKI WHERE ID_WYCIECZKI = id_wycieczki;
    IF counter = 0 THEN
      RAISE_APPLICATION_ERROR(-20004, 'Unable to uncancel reservation. All free spots taken.');
    END IF;
  END IF;

  /* W końcu updatuj */
  UPDATE REZERWACJE
    SET STATUS=zmien_status_rezerwacji.nowy_status 
    WHERE NR_REZERWACJI = zmien_status_rezerwacji.id_rezerwacji;
END zmien_status_rezerwacji;

/* Zadanie 5c */
CREATE OR REPLACE PROCEDURE zmien_liczbe_miejsc(id_wycieczki NUMBER, nowa_liczba_miejsc NUMBER)
AS
zajeta_liczba_miejsc NUMBER;
counter INT;
BEGIN
/* Sprawdź czy wycieczka istnieje */
SELECT COUNT(*) INTO counter FROM WYCIECZKI
WHERE ID_WYCIECZKI = zmien_liczbe_miejsc.id_wycieczki;
IF counter != 1 THEN
RAISE_APPLICATION_ERROR(-20000, 'Invalid trip identifier.');
END IF;

/* Policz ile miejsc zajętych */
SELECT LICZBA_MIEJSC - LICZBA_WOLNYCH_MIEJSC INTO zajeta_liczba_miejsc
FROM WYCIECZKI_MIEJSCA WHERE ID_WYCIECZKI = zmien_liczbe_miejsc.id_wycieczki;
/* Jeśli liczba miejsc ujemna lub mniejsza od ilości zarezerwowanych -> BŁĄD*/
IF nowa_liczba_miejsc < 0 OR zajeta_liczba_miejsc > nowa_liczba_miejsc THEN
raise_application_error(-20001, 'Not enough spots.');
END IF;

/* Updatuj */
UPDATE WYCIECZKI SET LICZBA_MIEJSC=nowa_liczba_miejsc
WHERE ID_WYCIECZKI=zmien_liczbe_miejsc.id_wycieczki;
END zmien_liczbe_miejsc;

