-- liquibase formatted sql

-- changeset francesco:05-create_istituto_0
-- comment: Inserimento del record predefinito nella tabella istituto

INSERT INTO istituto (nome, regione, provincia, comune, indirizzo, sezioni, data_ins, data_mod)
VALUES ('Nessuno', 'Nessuna', 'Nessuna', 'Nessuno', 'Nessuno', 'Nessuna', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- rollback DELETE FROM istituto WHERE id = 1;