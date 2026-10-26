-- liquibase formatted sql

-- changeset francesco:06-insert-default-istituto
-- comment: Inserimento del record predefinito nella tabella istituto

INSERT INTO istituto (nome, regione, provincia, comune, indirizzo, sezioni, data_ins, data_mod)
VALUES ('Nessuno', '', '', '', '', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- rollback DELETE FROM istituto WHERE id = 1;