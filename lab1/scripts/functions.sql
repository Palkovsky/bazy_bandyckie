/* Zadanie 4a */

/* Pojedynczy rekord */
CREATE OR REPLACE TYPE UCZESTNICY_WYCIECZKI_RECORD AS OBJECT (
  ID_WYCIECZKI  NUMBER,
  NAZWA         VARCHAR2(100),
  KRAJ          VARCHAR2(50),
  "DATA"        DATE,
  IMIE          VARCHAR2(50),
  NAZWISKO      VARCHAR(50),
  STATUS        CHAR(1)
);

/* Kolekcja rekordów */
CREATE OR REPLACE TYPE UCZESTNICY_WYCIECZKI_TABLE IS TABLE OF UCZESTNICY_WYCIECZKI_RECORD;

CREATE OR REPLACE
FUNCTION uczestnicy_wycieczki(id INT)
    RETURN UCZESTNICY_WYCIECZKI_TABLE AS result UCZESTNICY_WYCIECZKI_TABLE;
    record_count INTEGER;
    BEGIN
        /* Upewnij się, że wycieczka istnieje */
        SELECT COUNT(*) INTO record_count FROM WYCIECZKI WHERE ID_WYCIECZKI = id;
        IF (record_count != 1) THEN
            RAISE_APPLICATION_ERROR(-20001, 'Record doest not exist.');
        END IF;

        /* Zbierz wynik do utworznej tabeli */
        SELECT UCZESTNICY_WYCIECZKI_RECORD(
            w.ID_WYCIECZKI, w.NAZWA, w.KRAJ, w.DATA, o.IMIE, o.NAZWISKO, r.STATUS)
        BULK COLLECT INTO
            result
        FROM
            WYCIECZKI w
        JOIN
            REZERWACJE r ON w.ID_WYCIECZKI = r.ID_WYCIECZKI
        JOIN
            OSOBY o ON r.ID_OSOBY = o.ID_OSOBY
        WHERE
            w.ID_WYCIECZKI = id AND r.STATUS != 'A';

        /* Zwróć tabelę */
        RETURN result;
    END uczestnicy_wycieczki;

/* Zadanie 4b */
CREATE OR REPLACE TYPE REZERWACJE_OSOBY_RECORD AS OBJECT (
  ID_WYCIECZKI  NUMBER,
  NAZWA         VARCHAR2(100),
  KRAJ          VARCHAR2(50),
  "DATA"        DATE,
  IMIE          VARCHAR2(50),
  NAZWISKO      VARCHAR(50),
  STATUS        CHAR(1)
);
CREATE OR REPLACE TYPE REZERWACJE_OSOBY_TABLE IS TABLE OF REZERWACJE_OSOBY_RECORD;

CREATE OR REPLACE
FUNCTION rezerwacje_osoby(id INT)
    RETURN REZERWACJE_OSOBY_TABLE AS result REZERWACJE_OSOBY_TABLE;
    record_count INT;
    BEGIN
        /* Upewnij się, że osoba istnieje */
        SELECT COUNT(*) INTO record_count FROM OSOBY WHERE ID_OSOBY = id;
        IF (record_count != 1) THEN
            RAISE_APPLICATION_ERROR(-20001, 'Record doest not exist.');
        END IF;

        /* Zbierz wynik do utworznej tabeli */
        SELECT REZERWACJE_OSOBY_RECORD(
            w.ID_WYCIECZKI, w.NAZWA, w.KRAJ, w.DATA, o.IMIE, o.NAZWISKO, r.STATUS)
        BULK COLLECT INTO
            result
        FROM
            WYCIECZKI w
        JOIN
            REZERWACJE r ON w.ID_WYCIECZKI = r.ID_WYCIECZKI
        JOIN
            OSOBY o ON r.ID_OSOBY = o.ID_OSOBY
        WHERE
            o.ID_OSOBY = id AND r.STATUS != 'A';

        /* Zwróć tabelę */
        RETURN result;
    END rezerwacje_osoby;

/* Zadanie 4c */
CREATE OR REPLACE TYPE PRZYSZLE_REZERWACJE_OSOBY_RECORD AS OBJECT (
  ID_WYCIECZKI  NUMBER,
  NAZWA         VARCHAR2(100),
  KRAJ          VARCHAR2(50),
  "DATA"        DATE,
  IMIE          VARCHAR2(50),
  NAZWISKO      VARCHAR(50),
  STATUS        CHAR(1)
);
CREATE OR REPLACE TYPE PRZYSZLE_REZERWACJE_OSOBY_TABLE IS TABLE OF PRZYSZLE_REZERWACJE_OSOBY_RECORD;

CREATE OR REPLACE
FUNCTION przyszle_rezerwacje_osoby(id INT)
    RETURN PRZYSZLE_REZERWACJE_OSOBY_TABLE AS result PRZYSZLE_REZERWACJE_OSOBY_TABLE;
    record_count INT;
    BEGIN
        /* Upewnij się, że osoba istnieje */
        SELECT COUNT(*) INTO record_count FROM OSOBY WHERE ID_OSOBY = id;
        IF (record_count != 1) THEN
            RAISE_APPLICATION_ERROR(-20001, 'Record doest not exist.');
        END IF;

        /* Zbierz wynik do utworznej tabeli */
        SELECT PRZYSZLE_REZERWACJE_OSOBY_RECORD(
            w.ID_WYCIECZKI, w.NAZWA, w.KRAJ, w.DATA, o.IMIE, o.NAZWISKO, r.STATUS)
        BULK COLLECT INTO
            result
        FROM
            WYCIECZKI w
        JOIN
            REZERWACJE r ON w.ID_WYCIECZKI = r.ID_WYCIECZKI
        JOIN
            OSOBY o ON r.ID_OSOBY = o.ID_OSOBY
        WHERE
            o.ID_OSOBY = id AND r.STATUS != 'A' AND w.DATA > CURRENT_DATE;

        /* Zwróć tabelę */
        RETURN result;
    END przyszle_rezerwacje_osoby;

/* Zadanie 4d */
CREATE OR REPLACE TYPE DOSTEPNE_WYCIECZKI_RECORD AS OBJECT (
  ID_WYCIECZKI  NUMBER,
  NAZWA         VARCHAR2(100),
  KRAJ          VARCHAR2(50),
  "DATA"        DATE,
  WOLNE_MIEJSCA NUMBER
);
CREATE OR REPLACE TYPE DOSTEPNE_WYCIECZKI_TABLE IS TABLE OF DOSTEPNE_WYCIECZKI_RECORD;

CREATE OR REPLACE
    FUNCTION dostepne_wycieczki_func(kraj WYCIECZKI.KRAJ%TYPE, data_od DATE, data_do DATE)
    RETURN DOSTEPNE_WYCIECZKI_TABLE AS result DOSTEPNE_WYCIECZKI_TABLE;
    BEGIN
        /* Upewnij się, że przedział dat jest okej */
        IF data_do < data_od THEN
            RAISE_APPLICATION_ERROR(-20001, 'Invalid date range.');
        END IF;

        /* Zbierz wynik do utworznej tabeli.
           Używamy widoku dostępne wycieczki, który bierze pod uwagę ilość miejsc. */
        SELECT DOSTEPNE_WYCIECZKI_RECORD(
            w.ID_WYCIECZKI, w.NAZWA, w.KRAJ, w.DATA, w.LICZBA_WOLNYCH_MIEJSC)
        BULK COLLECT INTO
            result
        FROM
            DOSTEPNE_WYCIECZKI w
        WHERE
             w.KRAJ = dostepne_wycieczki_func.kraj AND
             w.DATA >= data_od AND
             w.DATA <= data_do;

        /* Zwróć tabelę */
        RETURN result;
    END dostepne_wycieczki_func;
