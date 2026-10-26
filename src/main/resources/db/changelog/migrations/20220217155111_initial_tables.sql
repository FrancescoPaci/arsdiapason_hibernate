-- liquibase formatted sql

-- changeset francesco:01-create-initial-tables
-- comment: Creazione delle tabelle iniziali del database

CREATE TABLE psicologo
(
    id       INT AUTO_INCREMENT,
    nome     VARCHAR(50) NOT NULL,
    cognome  VARCHAR(50) NOT NULL,
    data_ins TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_mod TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE utente
(
    email          VARCHAR(40) NOT NULL,
    password       VARCHAR(75) NOT NULL,
    temporary_code INT CHECK (temporary_code >= 0 AND temporary_code <= 999999),
    active         BOOLEAN     NOT NULL DEFAULT 0,
    role           VARCHAR(10) NOT NULL,
    id_psicologo   INT         NOT NULL,
    data_ins       TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_mod       TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (email),
    CONSTRAINT FK_UT_psicologo FOREIGN KEY (id_psicologo) REFERENCES psicologo (id)
);

CREATE TABLE istituto
(
    id        INT AUTO_INCREMENT,
    nome      VARCHAR(50) NOT NULL,
    regione   VARCHAR(50) NOT NULL,
    provincia VARCHAR(50) NOT NULL,
    comune    VARCHAR(50) NOT NULL,
    indirizzo VARCHAR(50) NOT NULL,
    sezioni VARCHAR(120) NOT NULL,
    data_ins  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_mod  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE bambino
(
    id                  INT AUTO_INCREMENT,
    nome                VARCHAR(50) NOT NULL,
    cognome             VARCHAR(50) NOT NULL,
    sesso               VARCHAR(1)  NOT NULL,
    data_nascita        DATE        NOT NULL,
    comune_nascita      VARCHAR(30),
    comune_residenza    VARCHAR(30),
    indirizzo_residenza VARCHAR(50),
    numero_fratelli     TINYINT,
    numero_sorelle      TINYINT,
    ordine_genitura     TINYINT,
    lavoro_padre        VARCHAR(50),
    lavoro_madre        VARCHAR(50),
    titolo_studio_padre VARCHAR(50),
    titolo_studio_madre VARCHAR(50),
    figlio_adottivo     BOOLEAN,
    test_finito         BOOLEAN     NOT NULL DEFAULT 0,
    data_test           DATE,
    note                VARCHAR(300),
    data_ins            TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_mod            TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE rel_istituto_bambino
(
    id_istituto INT        NOT NULL,
    id_bambino  INT        NOT NULL,
    classe      INT        NOT NULL,
    sezione     VARCHAR(3) NOT NULL,
    data_ins    TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_mod    TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_istituto, id_bambino, classe, sezione),
    CONSTRAINT FK_RIB_istituto FOREIGN KEY (id_istituto) REFERENCES istituto (id),
    CONSTRAINT FK_RIB_bambino FOREIGN KEY (id_bambino) REFERENCES bambino (id)
);

CREATE TABLE rel_psicologo_bambino
(
    id_psicologo INT       NOT NULL,
    id_bambino   INT       NOT NULL,
    data_ins     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_mod     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_psicologo, id_bambino),
    CONSTRAINT FK_RPB_psicologo FOREIGN KEY (id_psicologo) REFERENCES psicologo (id),
    CONSTRAINT FK_RPB_bambino FOREIGN KEY (id_bambino) REFERENCES bambino (id)
);

CREATE TABLE scheda
(
    numero     TINYINT     NOT NULL,
    titolo     VARCHAR(30) NOT NULL,
    obbiettivo VARCHAR(80) NOT NULL,
    immagini   VARCHAR(10),
    data_ins   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_mod   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (numero)
);

CREATE TABLE prova_scheda
(
    id            TINYINT     NOT NULL,
    numero_scheda TINYINT     NOT NULL,
    prova         TINYINT,
    tipo          VARCHAR(10),
    testo         VARCHAR(70) NOT NULL,
    anni          TINYINT     NOT NULL,
    data_ins      TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_mod      TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT FK_PS_numScheda FOREIGN KEY (numero_scheda) REFERENCES scheda (numero)
);

CREATE TABLE rel_bambino_scheda
(
    id_bambino      INT       NOT NULL,
    id_prova_scheda TINYINT   NOT NULL,
    data_ins        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_mod        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_bambino, id_prova_scheda),
    CONSTRAINT FK_RBS_bambino FOREIGN KEY (id_bambino) REFERENCES bambino (id),
    CONSTRAINT FK_RBS_provaScheda FOREIGN KEY (id_prova_scheda) REFERENCES prova_scheda (id)
);

CREATE TABLE area_prova
(
    prova    TINYINT     NOT NULL,
    anni     TINYINT     NOT NULL,
    testo    VARCHAR(85) NOT NULL,
    data_ins TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_mod TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (prova, anni)
);

-- rollback DROP TABLE area_prova;
-- rollback DROP TABLE rel_istituto_bambino;
-- rollback DROP TABLE rel_psicologo_bambino;
-- rollback DROP TABLE rel_bambino_scheda;
-- rollback DROP TABLE prova_scheda;
-- rollback DROP TABLE scheda;
-- rollback DROP TABLE bambino;
-- rollback DROP TABLE istituto;
-- rollback DROP TABLE utente;
-- rollback DROP TABLE psicologo;