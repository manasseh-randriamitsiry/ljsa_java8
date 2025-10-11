-- BEGIN TABLE classe
CREATE TABLE IF NOT EXISTS classe (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  classe TEXT NOT NULL UNIQUE,
  effectif INTEGER NOT NULL,
  level INTEGER NOT NULL
);

-- END TABLE classe

-- BEGIN TABLE etudiants
CREATE TABLE IF NOT EXISTS etudiants (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  n_matricule TEXT NOT NULL UNIQUE,
  nom TEXT NOT NULL,
  prenom TEXT NOT NULL,
  classe TEXT NOT NULL,
  date_nais DATE NOT NULL,
  FOREIGN KEY (classe) REFERENCES classe (classe)
);

-- END TABLE etudiants

-- BEGIN TABLE matiere
CREATE TABLE IF NOT EXISTS matiere (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  designation TEXT NOT NULL,
  abreviation TEXT NOT NULL,
  description TEXT
);

-- END TABLE matiere

-- BEGIN TABLE premiere
CREATE TABLE IF NOT EXISTS premiere (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  malagasy REAL NOT NULL,
  francais REAL NOT NULL,
  anglais REAL NOT NULL,
  histogeo REAL NOT NULL,
  eac REAL NOT NULL,
  ses REAL NOT NULL,
  spc REAL NOT NULL,
  svt REAL NOT NULL,
  mats REAL NOT NULL,
  eps REAL NOT NULL,
  tice REAL NOT NULL,
  phylo REAL NOT NULL,
  n_mat TEXT NOT NULL,
  trimestre TEXT NOT NULL,
  annee_scolaire INTEGER NOT NULL
);

-- END TABLE premiere

-- BEGIN TABLE premiere_note_coeff
CREATE TABLE IF NOT EXISTS premiere_note_coeff (
  c_malagasy INTEGER NOT NULL,
  c_francais INTEGER NOT NULL,
  c_anglais INTEGER NOT NULL,
  c_histogeo INTEGER NOT NULL,
  c_eac INTEGER NOT NULL,
  c_ses INTEGER NOT NULL,
  c_spc INTEGER NOT NULL,
  c_svt INTEGER NOT NULL,
  c_mats INTEGER NOT NULL,
  c_eps INTEGER NOT NULL,
  c_tice INTEGER NOT NULL,
  c_phylo INTEGER NOT NULL
);

-- END TABLE premiere_note_coeff

-- BEGIN TABLE profs
CREATE TABLE IF NOT EXISTS profs (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  n_matricule TEXT NOT NULL UNIQUE,
  nom_prof TEXT NOT NULL,
  prenom_prof TEXT NOT NULL,
  date_nais DATE NOT NULL,
  date_prise_service DATE NOT NULL,
  date_cessation_service DATE NOT NULL
);

-- END TABLE profs

-- BEGIN TABLE seconde
CREATE TABLE IF NOT EXISTS seconde (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  malagasy REAL NOT NULL,
  francais REAL NOT NULL,
  anglais REAL NOT NULL,
  histogeo REAL NOT NULL,
  eac REAL NOT NULL,
  ses REAL NOT NULL,
  spc REAL NOT NULL,
  svt REAL NOT NULL,
  mats REAL NOT NULL,
  eps REAL NOT NULL,
  tice REAL NOT NULL,
  n_mat TEXT NOT NULL,
  trimestre TEXT NOT NULL,
  annee_scolaire INTEGER NOT NULL
);

-- END TABLE seconde

-- BEGIN TABLE seconde_note_coeff
CREATE TABLE IF NOT EXISTS seconde_note_coeff (
  c_malagasy INTEGER NOT NULL,
  c_francais INTEGER NOT NULL,
  c_anglais INTEGER NOT NULL,
  c_histogeo INTEGER NOT NULL,
  c_eac INTEGER NOT NULL,
  c_ses INTEGER NOT NULL,
  c_spc INTEGER NOT NULL,
  c_svt INTEGER NOT NULL,
  c_mats INTEGER NOT NULL,
  c_eps INTEGER NOT NULL,
  c_tice INTEGER NOT NULL
);

-- END TABLE seconde_note_coeff

-- BEGIN TABLE terminale
CREATE TABLE IF NOT EXISTS terminale (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  mlg REAL,
  frs REAL,
  anglais REAL,
  histogeo REAL,
  phylo REAL,
  math REAL,
  spc REAL,
  svt REAL,
  ses REAL,
  eps REAL,
  nmat TEXT,
  trimestre INTEGER,
  annee_scolaire TEXT,
  FOREIGN KEY (nmat) REFERENCES etudiants (n_matricule)
);

-- END TABLE terminale

-- BEGIN TABLE terminale_note_coeff
CREATE TABLE IF NOT EXISTS terminale_note_coeff (
  c_malagasy INTEGER,
  c_francais INTEGER,
  c_anglais INTEGER,
  c_histogeo INTEGER,
  c_phylo INTEGER,
  c_mats INTEGER,
  c_spc INTEGER,
  c_svt INTEGER,
  c_ses INTEGER,
  c_eps INTEGER
);

-- Insert default coefficient values
INSERT OR IGNORE INTO premiere_note_coeff (c_malagasy, c_francais, c_anglais, c_histogeo, c_eac, c_ses, c_spc, c_svt, c_mats, c_eps, c_tice, c_phylo) 
VALUES (2, 2, 2, 2, 1, 1, 1, 3, 3, 1, 1, 1);

INSERT OR IGNORE INTO seconde_note_coeff (c_malagasy, c_francais, c_anglais, c_histogeo, c_eac, c_ses, c_spc, c_svt, c_mats, c_eps, c_tice) 
VALUES (2, 2, 2, 2, 1, 1, 1, 3, 3, 1, 1);

INSERT OR IGNORE INTO terminale_note_coeff (c_malagasy, c_francais, c_anglais, c_histogeo, c_phylo, c_mats, c_spc, c_svt, c_ses, c_eps) 
VALUES (3, 2, 2, 2, 2, 3, 1, 3, 1, 1);