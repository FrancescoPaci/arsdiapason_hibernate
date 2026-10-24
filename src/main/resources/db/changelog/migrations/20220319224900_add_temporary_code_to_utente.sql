-- liquibase formatted sql

-- changeset francesco:05-add-temporary-code-to-utente
-- comment: Aggiunta del campo temporary_code alla tabella utente

ALTER TABLE utente ADD temporary_code NUMERIC(6);

-- rollback ALTER TABLE utente DROP COLUMN temporary_code;