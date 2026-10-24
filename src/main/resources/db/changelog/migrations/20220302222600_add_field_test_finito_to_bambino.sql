-- liquibase formatted sql

-- changeset francesco:04-add-fields-to-bambino
-- comment: Aggiunta dei campi test_finito e data_test alla tabella bambino

ALTER TABLE bambino ADD test_finito TINYINT(1) NOT NULL DEFAULT 0;
ALTER TABLE bambino ADD data_test DATE;

-- rollback ALTER TABLE bambino DROP COLUMN test_finito;
-- rollback ALTER TABLE bambino DROP COLUMN data_test;