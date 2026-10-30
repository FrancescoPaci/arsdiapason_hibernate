-- liquibase formatted sql

-- changeset francesco:03-populate-area-prova
-- comment: Popolamento della tabella area_prova

INSERT INTO area_prova (prova, anni, testo)
VALUES (1, 5, '10 - Percezione e rappresentazione schema corporeo');
INSERT INTO area_prova (prova, anni, testo)
VALUES (1, 6, '1 - Analisi logiche complesse organizzazione percettiva visiva grafica (copia)');
INSERT INTO area_prova (prova, anni, testo)
VALUES (1, 7, '4 - Analisi percezione visiva');
INSERT INTO area_prova (prova, anni, testo)
VALUES (1, 8, '4- Analisi percezione visiva');

INSERT INTO area_prova (prova, anni, testo)
VALUES (2, 5, '6 - Analisi percettiva-visiva-grafica');
INSERT INTO area_prova (prova, anni, testo)
VALUES (2, 6, '9 A-B-C - Comprensione esperenziale');
INSERT INTO area_prova (prova, anni, testo)
VALUES (2, 7, '6 - Analisi percettiva-visiva-grafica');
INSERT INTO area_prova (prova, anni, testo)
VALUES (2, 8, '6 - Analisi percettiva-visiva-grafica');

INSERT INTO area_prova (prova, anni, testo)
VALUES (3, 5, '2 - Percezione forma geometrica e rappresentazione');
INSERT INTO area_prova (prova, anni, testo)
VALUES (3, 6, '2 - Percezione forma geometrica e rappresentazione');
INSERT INTO area_prova (prova, anni, testo)
VALUES (3, 7, '3 - Analisi parti mancanti');
INSERT INTO area_prova (prova, anni, testo)
VALUES (3, 8, '3 - Analisi parti mancanti');

INSERT INTO area_prova (prova, anni, testo)
VALUES (4, 5, '8 - Percezione e rappresentazione parte schema corporeo');
INSERT INTO area_prova (prova, anni, testo)
VALUES (4, 6, '8 - Percezione e rappresentazione parte schema corporeo');
INSERT INTO area_prova (prova, anni, testo)
VALUES (4, 7, '8 - Percezione e rappresentazione parte schema corporeo');
INSERT INTO area_prova (prova, anni, testo)
VALUES (4, 8, '6 - Analisi percettiva-visiva-grafica');

INSERT INTO area_prova (prova, anni, testo)
VALUES (5, 5, '5 - Coordinazione grafica fine');
INSERT INTO area_prova (prova, anni, testo)
VALUES (5, 6, '5 - Coordinazione grafica fine');
INSERT INTO area_prova (prova, anni, testo)
VALUES (5, 7, '7 - Analisi particolari diversi non integrati');
INSERT INTO area_prova (prova, anni, testo)
VALUES (5, 8, '1 - Analisi logiche complesse organizzazione percettiva visiva grafica (copia)');

INSERT INTO area_prova (prova, anni, testo)
VALUES (6, 5, '11 - Coordinazione motoria fine');
INSERT INTO area_prova (prova, anni, testo)
VALUES (6, 6, '1 - Analisi logiche complesse organizzazione percettiva visiva grafica (memoria)');
INSERT INTO area_prova (prova, anni, testo)
VALUES (6, 7, '1 - Analisi logiche complesse organizzazione percettiva visiva grafica (memoria)');
INSERT INTO area_prova (prova, anni, testo)
VALUES (6, 8, '1 - Analisi logiche complesse organizzazione percettiva visiva grafica (memoria)');

-- rollback DELETE FROM area_prova;