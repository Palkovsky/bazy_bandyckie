/* Cleanup for multiple runs */
/*
DROP VIEW wycieczki_osoby_potwierdzone;
DROP VIEW wycieczki_przyszle;
DROP VIEW wycieczki_osoby;
DROP VIEW wycieczki_miejsca;
DROP VIEW dostepne_wycieczki;
DROP VIEW rezerwacje_do_anulowania;
*/

/* Zadanie 3a */
CREATE VIEW wycieczki_osoby
 AS
    SELECT
        w.ID_WYCIECZKI,
        w.NAZWA,
        w.KRAJ,
        w.DATA,
        o.IMIE,
        o.NAZWISKO,
        r.STATUS
    FROM
         WYCIECZKI w
    JOIN
        REZERWACJE r ON w.ID_WYCIECZKI = r.ID_WYCIECZKI
    JOIN
        OSOBY o ON r.ID_OSOBY = o.ID_OSOBY;

/* Zadanie 3b */
CREATE VIEW wycieczki_osoby_potwierdzone
 AS
    SELECT
        *
    FROM
         wycieczki_osoby
    WHERE
          STATUS = 'Z';

/* Zadanie 3c */
CREATE VIEW wycieczki_przyszle
 AS
    SELECT
        *
    FROM
      wycieczki_osoby
    WHERE
       DATA > CURRENT_DATE;

/* Zadanie 3d */
/* Zakładamy, że anulowana rezerwacja to wolne miejsce. */
CREATE VIEW wycieczki_miejsca
 AS
    SELECT
        w.ID_WYCIECZKI,
        w.NAZWA,
        w.KRAJ,
        w.DATA,
        w.LICZBA_MIEJSC,
        w.LICZBA_MIEJSC - COUNT(r.ID_WYCIECZKI) as LICZBA_WOLNYCH_MIEJSC
 FROM
      WYCIECZKI w
 INNER JOIN
      REZERWACJE r ON w.ID_WYCIECZKI = r.ID_WYCIECZKI
 WHERE
       r.STATUS != 'A'
 GROUP BY
          w.ID_WYCIECZKI, w.NAZWA, w.KRAJ, w.DATA, w.LICZBA_MIEJSC;

/* Zadanie 3e */
CREATE VIEW dostepne_wycieczki
 AS
    SELECT
           *
    FROM
        wycieczki_miejsca
    WHERE
        LICZBA_WOLNYCH_MIEJSC > 0 AND DATA > CURRENT_DATE;

/* Zadanie 3f - POPRAWA */
CREATE VIEW rezerwacje_do_anulowania
  AS
    SELECT DISTINCT
        r.ID_WYCIECZKI,
        w.NAZWA,
        w.KRAJ,
        w.DATA
    FROM
        REZERWACJE r
    INNER JOIN
        OSOBY O on r.ID_OSOBY = O.ID_OSOBY
    INNER JOIN
        WYCIECZKI W on r.ID_WYCIECZKI = W.ID_WYCIECZKI
    WHERE
        w.DATA - CURRENT_DATE <= 7 AND r.STATUS != 'Z';

SELECT * FROM rezerwacje_do_anulowania;
