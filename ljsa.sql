-- Database export via SQLPro (https://www.sqlprostudio.com/allapps.html)
-- Exported by manasseh at 17-01-2023 14:00.
-- WARNING: This file may contain descructive statements such as DROPs.
-- Please ensure that you are running the script at the proper location.


-- BEGIN TABLE classe
DROP TABLE IF EXISTS classe;
CREATE TABLE `classe` (
  `id` int NOT NULL AUTO_INCREMENT,
  `classe` varchar(20) COLLATE utf8mb3_bin NOT NULL,
  `effectif` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`classe`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Inserting 2 rows into classe
-- Insert batch #1
INSERT INTO classe (id, classe, effectif) VALUES
(2, 'PREMIEREL', 40),
(3, 'DASDASD', 23);

-- END TABLE classe

-- BEGIN TABLE coefficient
DROP TABLE IF EXISTS coefficient;
CREATE TABLE `coefficient` (
  `id` int NOT NULL,
  `classe` varchar(20) COLLATE utf8mb3_bin NOT NULL,
  `coefficient_total` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Table coefficient contains no data. No inserts have been genrated.
-- Inserting 0 rows into coefficient


-- END TABLE coefficient

-- BEGIN TABLE etudiants
DROP TABLE IF EXISTS etudiants;
CREATE TABLE `etudiants` (
  `id` int NOT NULL AUTO_INCREMENT,
  `n_matricule` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `nom` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `prenom` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `classe` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `date_nais` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE` (`n_matricule`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Inserting 6 rows into etudiants
-- Insert batch #1
INSERT INTO etudiants (id, n_matricule, nom, prenom, classe, date_nais) VALUES
(33, '001AEE', 'RANDRIAMITSIRY', 'valimbavaka manasse', 'DSD', '1999-07-01'),
(35, '001C', 'RANDRIAMITSIRY', 'lovasoa gabriel', 'terminale', '2002-07-18'),
(44, '12345AA', 'RAKOTOARISO', 'tahin', 'DASDASD', '2022-09-28'),
(49, 'SDASD', 'ASDASD', 'asdasd', 'DSD', '2023-01-11'),
(50, 'SADFSDF', 'DSFSDF', 'gsfgs', 'DSD', '2023-01-10'),
(51, 'SFSAFASF', 'ASFFD', 'safas', 'DSD', '2023-01-10');

-- END TABLE etudiants

-- BEGIN TABLE login
DROP TABLE IF EXISTS login;
CREATE TABLE `login` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `password` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Inserting 1 row into login
-- Insert batch #1
INSERT INTO login (id, username, password) VALUES
(1, 'manasseh', 'kadd');

-- END TABLE login

-- BEGIN TABLE matiere
DROP TABLE IF EXISTS matiere;
CREATE TABLE `matiere` (
  `id` int NOT NULL AUTO_INCREMENT,
  `designation` varchar(50) COLLATE utf8mb3_bin NOT NULL,
  `abreviation` varchar(50) COLLATE utf8mb3_bin NOT NULL,
  `description` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Inserting 3 rows into matiere
-- Insert batch #1
INSERT INTO matiere (id, designation, abreviation, description) VALUES
(10, 'Malagasy', 'MLG', 'natao hianarana ny teny malagasy '),
(11, 'Français', 'FRS', 'matiera  ho an''ny teny frantsay'),
(12, 'HistoireGeographie', 'HG', 'mianatra ny tantaran''i madagasikara sy izao tontolo izao');

-- END TABLE matiere

-- BEGIN TABLE premiere
DROP TABLE IF EXISTS premiere;
CREATE TABLE `premiere` (
  `id` int NOT NULL AUTO_INCREMENT,
  `malagasy` float NOT NULL,
  `francais` float NOT NULL,
  `anglais` float NOT NULL,
  `histogeo` float NOT NULL,
  `eac` float NOT NULL,
  `ses` float NOT NULL,
  `spc` float NOT NULL,
  `svt` float NOT NULL,
  `mats` float NOT NULL,
  `eps` float NOT NULL,
  `tice` float NOT NULL,
  `phylo` float NOT NULL,
  `n_mat` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `trimestre` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `annee_scolaire` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Inserting 1 row into premiere
-- Insert batch #1
INSERT INTO premiere (id, malagasy, francais, anglais, histogeo, eac, ses, spc, svt, mats, eps, tice, phylo, n_mat, trimestre, annee_scolaire) VALUES
(5, 12, 13, 3, 8, 7, 11, 10, 10, 9, 6, 2, 1, 'EWREWR', '4', 1997);

-- END TABLE premiere

-- BEGIN TABLE premiere_note_coeff
DROP TABLE IF EXISTS premiere_note_coeff;
CREATE TABLE `premiere_note_coeff` (
  `malagasy` int NOT NULL,
  `francais` int NOT NULL,
  `anglais` int NOT NULL,
  `histogeo` int NOT NULL,
  `eac` int NOT NULL,
  `ses` int NOT NULL,
  `spc` int NOT NULL,
  `svt` int NOT NULL,
  `mats` int NOT NULL,
  `eps` int NOT NULL,
  `tice` int NOT NULL,
  `phylo` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Inserting 1 row into premiere_note_coeff
-- Insert batch #1
INSERT INTO premiere_note_coeff (malagasy, francais, anglais, histogeo, eac, ses, spc, svt, mats, eps, tice, phylo) VALUES
(2, 3, 11, 4, 9, 8, 6, 7, 5, 8, 10, 12);

-- END TABLE premiere_note_coeff

-- BEGIN TABLE profs
DROP TABLE IF EXISTS profs;
CREATE TABLE `profs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `n_matricule` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `nom_prof` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `prenom_prof` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `date_nais` date NOT NULL,
  `date_prise_service` date DEFAULT NULL,
  `date_cessation_service` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `n_matricule_UNIQUE` (`n_matricule`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Inserting 3 rows into profs
-- Insert batch #1
INSERT INTO profs (id, n_matricule, nom_prof, prenom_prof, date_nais, date_prise_service, date_cessation_service) VALUES
(17, '00001AB', 'RANDRIAMITSIRY', 'valimbavaka manasse', '1999-03-10', '2023-01-08', '2023-01-26'),
(23, '00002AB', 'RANDRIAMITSIRY', 'lovasoa gabriel', '1994-10-14', '1999-03-10', '1999-03-10'),
(64, '00231AF', 'ANDRY', 'rajoelina', '1999-01-19', '2022-01-05', '2022-01-05');

-- END TABLE profs

-- BEGIN TABLE seconde
DROP TABLE IF EXISTS seconde;
CREATE TABLE `seconde` (
  `id` int NOT NULL AUTO_INCREMENT,
  `malagasy` float NOT NULL,
  `francais` float NOT NULL,
  `anglais` float NOT NULL,
  `histogeo` float NOT NULL,
  `eac` float NOT NULL,
  `ses` float NOT NULL,
  `spc` float NOT NULL,
  `svt` float NOT NULL,
  `mats` float NOT NULL,
  `eps` float NOT NULL,
  `tice` float NOT NULL,
  `n_mat` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `trimestre` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `annee_scolaire` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Inserting 1 row into seconde
-- Insert batch #1
INSERT INTO seconde (id, malagasy, francais, anglais, histogeo, eac, ses, spc, svt, mats, eps, tice, n_mat, trimestre, annee_scolaire) VALUES
(8, 20, 21, 20, 20, 20, 24, 20, 20, 20, 10, 10, '001A', '1', 2021);

-- END TABLE seconde

-- BEGIN TABLE seconde_note_coeff
DROP TABLE IF EXISTS seconde_note_coeff;
CREATE TABLE `seconde_note_coeff` (
  `malagasy` int NOT NULL,
  `francais` int NOT NULL,
  `anglais` int NOT NULL,
  `histogeo` int NOT NULL,
  `eac` int NOT NULL,
  `ses` int NOT NULL,
  `spc` int NOT NULL,
  `svt` int NOT NULL,
  `mats` int NOT NULL,
  `eps` int NOT NULL,
  `tice` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Inserting 1 row into seconde_note_coeff
-- Insert batch #1
INSERT INTO seconde_note_coeff (malagasy, francais, anglais, histogeo, eac, ses, spc, svt, mats, eps, tice) VALUES
(1, 1, 1, 3, 1, 2, 3, 3, 3, 1, 1);

-- END TABLE seconde_note_coeff

-- BEGIN TABLE serie
DROP TABLE IF EXISTS serie;
CREATE TABLE `serie` (
  `designation` varchar(50) COLLATE utf8mb3_bin NOT NULL,
  `abreviation` varchar(10) COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`designation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Inserting 5 rows into serie
-- Insert batch #1
INSERT INTO serie (designation, abreviation) VALUES
('Serie A1', 'A1'),
('Serie A2', 'A2'),
('Serie D', 'D'),
('Serie OSE', 'OSE'),
('Serie S', 'S');

-- END TABLE serie

-- BEGIN TABLE terminale
DROP TABLE IF EXISTS terminale;
CREATE TABLE `terminale` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mlg` float DEFAULT NULL,
  `frs` float DEFAULT NULL,
  `anglais` float DEFAULT NULL,
  `histogeo` float DEFAULT NULL,
  `phylo` float DEFAULT NULL,
  `math` float DEFAULT NULL,
  `spc` float DEFAULT NULL,
  `svt` float DEFAULT NULL,
  `ses` float DEFAULT NULL,
  `eps` float DEFAULT NULL,
  `nmat` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `trimestre` int DEFAULT NULL,
  `annee_scolaire` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nmat` (`nmat`),
  CONSTRAINT `terminale_ibfk_1` FOREIGN KEY (`nmat`) REFERENCES `etudiants` (`n_matricule`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Inserting 2 rows into terminale
-- Insert batch #1
INSERT INTO terminale (id, mlg, frs, anglais, histogeo, phylo, math, spc, svt, ses, eps, nmat, trimestre, annee_scolaire) VALUES
(53, 3, 2, 9, 10, 2, 4, 8, 9, 19, 4, '001C', 1, '1998'),
(54, 9, 10, 6, 2, 1, 8, 11, 3, 4, 7, '001C', 12, '5');

-- END TABLE terminale

-- BEGIN TABLE terminale_note_coeff
DROP TABLE IF EXISTS terminale_note_coeff;
CREATE TABLE `terminale_note_coeff` (
  `mlg` int DEFAULT NULL,
  `frs` int DEFAULT NULL,
  `anglais` int DEFAULT NULL,
  `histogeo` int DEFAULT NULL,
  `phylo` int DEFAULT NULL,
  `math` int DEFAULT NULL,
  `spc` int DEFAULT NULL,
  `svt` int DEFAULT NULL,
  `ses` int DEFAULT NULL,
  `eps` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Inserting 1 row into terminale_note_coeff
-- Insert batch #1
INSERT INTO terminale_note_coeff (mlg, frs, anglais, histogeo, phylo, math, spc, svt, ses, eps) VALUES
(2, 3, 10, 4, 0, 5, 6, 7, 9, 8);

-- END TABLE terminale_note_coeff

