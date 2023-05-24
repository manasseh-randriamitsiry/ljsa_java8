
-- BEGIN TABLE classe
DROP TABLE IF EXISTS classe;
CREATE TABLE `classe` (
  `id` int NOT NULL AUTO_INCREMENT,
  `classe` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `effectif` int NOT NULL,
  `level` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`classe`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- END TABLE classe

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
  UNIQUE KEY `UNIQUE` (`n_matricule`),
  KEY `classe` (`classe`),
  CONSTRAINT `etudiants_ibfk_1` FOREIGN KEY (`classe`) REFERENCES `classe` (`classe`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- END TABLE etudiants

-- BEGIN TABLE matiere
DROP TABLE IF EXISTS matiere;
CREATE TABLE `matiere` (
  `id` int NOT NULL AUTO_INCREMENT,
  `designation` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `abreviation` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `description` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Inserting 3 rows into matiere

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

-- BEGIN TABLE premiere_note_coeff
DROP TABLE IF EXISTS premiere_note_coeff;
CREATE TABLE `premiere_note_coeff` (
  `c_malagasy` int NOT NULL,
  `c_francais` int NOT NULL,
  `c_anglais` int NOT NULL,
  `c_histogeo` int NOT NULL,
  `c_eac` int NOT NULL,
  `c_ses` int NOT NULL,
  `c_spc` int NOT NULL,
  `c_svt` int NOT NULL,
  `c_mats` int NOT NULL,
  `c_eps` int NOT NULL,
  `c_tice` int NOT NULL,
  `c_phylo` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- END TABLE premiere_note_coeff

-- BEGIN TABLE profs
DROP TABLE IF EXISTS profs;
CREATE TABLE `profs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `n_matricule` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `nom_prof` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `prenom_prof` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `date_nais` date NOT NULL,
  `date_prise_service` date NOT NULL,
  `date_cessation_service` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `n_matricule_UNIQUE` (`n_matricule`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Inserting 1 row into profs

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- END TABLE seconde

-- BEGIN TABLE seconde_note_coeff
DROP TABLE IF EXISTS seconde_note_coeff;
CREATE TABLE `seconde_note_coeff` (
  `c_malagasy` int NOT NULL,
  `c_francais` int NOT NULL,
  `c_anglais` int NOT NULL,
  `c_histogeo` int NOT NULL,
  `c_eac` int NOT NULL,
  `c_ses` int NOT NULL,
  `c_spc` int NOT NULL,
  `c_svt` int NOT NULL,
  `c_mats` int NOT NULL,
  `c_eps` int NOT NULL,
  `c_tice` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- end

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
  CONSTRAINT `terminale_ibfk_1` FOREIGN KEY (`nmat`) REFERENCES `etudiants` (`n_matricule`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- END TABLE terminale

-- BEGIN TABLE terminale_note_coeff
DROP TABLE IF EXISTS terminale_note_coeff;
CREATE TABLE `terminale_note_coeff` (
  `c_malagasy` int DEFAULT NULL,
  `c_francais` int DEFAULT NULL,
  `c_anglais` int DEFAULT NULL,
  `c_histogeo` int DEFAULT NULL,
  `c_phylo` int DEFAULT NULL,
  `c_mats` int DEFAULT NULL,
  `c_spc` int DEFAULT NULL,
  `c_svt` int DEFAULT NULL,
  `c_ses` int DEFAULT NULL,
  `c_eps` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- END TABLE terminale_note_coeff
INSERT INTO `premiere_note_coeff` (`c_malagasy`, `c_francais`, `c_anglais`, `c_histogeo`, `c_eac`, `c_ses`, `c_spc`, `c_svt`, `c_mats`, `c_eps`, `c_tice`, `c_phylo`) VALUES ('2', '2', '2', '2', '1', '1', '1', '3', '3', '1', '1', '1');
INSERT INTO `seconde_note_coeff` (`c_malagasy`, `c_francais`, `c_anglais`, `c_histogeo`, `c_eac`, `c_ses`, `c_spc`, `c_svt`, `c_mats`, `c_eps`, `c_tice`) VALUES ('2', '2', '2', '2', '1', '1', '1', '3', '3', '1', '1');
INSERT INTO `terminale_note_coeff` (`c_malagasy`, `c_francais`, `c_anglais`, `c_histogeo`, `c_phylo`, `c_mats`, `c_spc`, `c_svt`, `c_ses`, `c_eps`) VALUES ('3', '2', '2', '2', '2', '3', '1', '3', '1', '1');