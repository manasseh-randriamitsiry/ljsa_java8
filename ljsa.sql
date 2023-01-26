-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Jan 26, 2023 at 06:50 AM
-- Server version: 5.7.34
-- PHP Version: 8.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ljsa`
--

-- --------------------------------------------------------

--
-- Table structure for table `classe`
--

CREATE TABLE `classe` (
  `id` int(11) NOT NULL,
  `classe` varchar(20) COLLATE utf8_bin NOT NULL,
  `effectif` int(11) NOT NULL,
  `level` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `coefficient`
--

CREATE TABLE `coefficient` (
  `id` int(11) NOT NULL,
  `classe` varchar(20) COLLATE utf8_bin NOT NULL,
  `coefficient_total` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `etudiants`
--

CREATE TABLE `etudiants` (
  `id` int(11) NOT NULL,
  `n_matricule` varchar(10) COLLATE utf8_bin NOT NULL,
  `nom` varchar(150) COLLATE utf8_bin NOT NULL,
  `prenom` varchar(150) COLLATE utf8_bin NOT NULL,
  `classe` varchar(20) COLLATE utf8_bin NOT NULL,
  `date_nais` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`id`, `username`, `password`) VALUES
(1, 'manasseh', 'kadd');

-- --------------------------------------------------------

--
-- Table structure for table `matiere`
--

CREATE TABLE `matiere` (
  `id` int(11) NOT NULL,
  `designation` varchar(50) COLLATE utf8_bin NOT NULL,
  `abreviation` varchar(50) COLLATE utf8_bin NOT NULL,
  `description` varchar(200) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `matiere`
--

INSERT INTO `matiere` (`id`, `designation`, `abreviation`, `description`) VALUES
(10, 'Malagasy', 'MLG', 'natao hianarana ny teny malagasy '),
(11, 'Français', 'FRS', 'matiera  ho an\'ny teny frantsay'),
(12, 'HistoireGeographie', 'HG', 'mianatra ny tantaran\'i madagasikara sy izao tontolo izao');

-- --------------------------------------------------------

--
-- Table structure for table `premiere`
--

CREATE TABLE `premiere` (
  `id` int(11) NOT NULL,
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
  `n_mat` varchar(10) COLLATE utf8_bin NOT NULL,
  `trimestre` varchar(10) COLLATE utf8_bin NOT NULL,
  `annee_scolaire` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `premiere_note_coeff`
--

CREATE TABLE `premiere_note_coeff` (
  `malagasy` int(11) NOT NULL,
  `francais` int(11) NOT NULL,
  `anglais` int(11) NOT NULL,
  `histogeo` int(11) NOT NULL,
  `eac` int(11) NOT NULL,
  `ses` int(11) NOT NULL,
  `spc` int(11) NOT NULL,
  `svt` int(11) NOT NULL,
  `mats` int(11) NOT NULL,
  `eps` int(11) NOT NULL,
  `tice` int(11) NOT NULL,
  `phylo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `premiere_note_coeff`
--

INSERT INTO `premiere_note_coeff` (`malagasy`, `francais`, `anglais`, `histogeo`, `eac`, `ses`, `spc`, `svt`, `mats`, `eps`, `tice`, `phylo`) VALUES
(2, 3, 11, 4, 9, 8, 6, 7, 5, 8, 10, 12);

-- --------------------------------------------------------

--
-- Table structure for table `profs`
--

CREATE TABLE `profs` (
  `id` int(11) NOT NULL,
  `n_matricule` varchar(45) COLLATE utf8_bin NOT NULL,
  `nom_prof` varchar(100) COLLATE utf8_bin NOT NULL,
  `prenom_prof` varchar(100) COLLATE utf8_bin NOT NULL,
  `date_nais` date NOT NULL,
  `date_prise_service` date NOT NULL,
  `date_cessation_service` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `profs`
--

INSERT INTO `profs` (`id`, `n_matricule`, `nom_prof`, `prenom_prof`, `date_nais`) VALUES
(17, '00001AB', 'RANDRIAMITSIRY', 'valimbavaka manasse', '1999-03-10'),
(23, '00002AB', 'RANDRIAMITSIRY', 'lovasoa gabriel', '1994-10-14');

-- --------------------------------------------------------

--
-- Table structure for table `seconde`
--

CREATE TABLE `seconde` (
  `id` int(11) NOT NULL,
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
  `n_mat` varchar(10) COLLATE utf8_bin NOT NULL,
  `trimestre` varchar(10) COLLATE utf8_bin NOT NULL,
  `annee_scolaire` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `seconde_note_coeff`
--

CREATE TABLE `seconde_note_coeff` (
  `malagasy` int(11) NOT NULL,
  `francais` int(11) NOT NULL,
  `anglais` int(11) NOT NULL,
  `histogeo` int(11) NOT NULL,
  `eac` int(11) NOT NULL,
  `ses` int(11) NOT NULL,
  `spc` int(11) NOT NULL,
  `svt` int(11) NOT NULL,
  `mats` int(11) NOT NULL,
  `eps` int(11) NOT NULL,
  `tice` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `seconde_note_coeff`
--

INSERT INTO `seconde_note_coeff` (`malagasy`, `francais`, `anglais`, `histogeo`, `eac`, `ses`, `spc`, `svt`, `mats`, `eps`, `tice`) VALUES
(1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `serie`
--

CREATE TABLE `serie` (
  `designation` varchar(50) COLLATE utf8_bin NOT NULL,
  `abreviation` varchar(10) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `serie`
--

INSERT INTO `serie` (`designation`, `abreviation`) VALUES
('Serie A1', 'A1'),
('Serie A2', 'A2'),
('Serie D', 'D'),
('Serie OSE', 'OSE'),
('Serie S', 'S');

-- --------------------------------------------------------

--
-- Table structure for table `terminale`
--

CREATE TABLE `terminale` (
  `id` int(11) NOT NULL,
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
  `nmat` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `trimestre` int(11) DEFAULT NULL,
  `annee_scolaire` varchar(10) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `terminale_note_coeff`
--

CREATE TABLE `terminale_note_coeff` (
  `mlg` int(11) DEFAULT NULL,
  `frs` int(11) DEFAULT NULL,
  `anglais` int(11) DEFAULT NULL,
  `histogeo` int(11) DEFAULT NULL,
  `phylo` int(11) DEFAULT NULL,
  `math` int(11) DEFAULT NULL,
  `spc` int(11) DEFAULT NULL,
  `svt` int(11) DEFAULT NULL,
  `ses` int(11) DEFAULT NULL,
  `eps` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `terminale_note_coeff`
--

INSERT INTO `terminale_note_coeff` (`mlg`, `frs`, `anglais`, `histogeo`, `phylo`, `math`, `spc`, `svt`, `ses`, `eps`) VALUES
(2, 3, 10, 4, 0, 5, 6, 7, 9, 8);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `classe`
--
ALTER TABLE `classe`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`),
  ADD UNIQUE KEY `username_UNIQUE` (`classe`);

--
-- Indexes for table `etudiants`
--
ALTER TABLE `etudiants`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UNIQUE` (`n_matricule`),
  ADD KEY `classe` (`classe`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`),
  ADD UNIQUE KEY `username_UNIQUE` (`username`);

--
-- Indexes for table `matiere`
--
ALTER TABLE `matiere`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `premiere`
--
ALTER TABLE `premiere`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `profs`
--
ALTER TABLE `profs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `n_matricule_UNIQUE` (`n_matricule`);

--
-- Indexes for table `seconde`
--
ALTER TABLE `seconde`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `serie`
--
ALTER TABLE `serie`
  ADD PRIMARY KEY (`designation`);

--
-- Indexes for table `terminale`
--
ALTER TABLE `terminale`
  ADD PRIMARY KEY (`id`),
  ADD KEY `nmat` (`nmat`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `classe`
--
ALTER TABLE `classe`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `etudiants`
--
ALTER TABLE `etudiants`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `matiere`
--
ALTER TABLE `matiere`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `premiere`
--
ALTER TABLE `premiere`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `profs`
--
ALTER TABLE `profs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `seconde`
--
ALTER TABLE `seconde`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `terminale`
--
ALTER TABLE `terminale`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `etudiants`
--
ALTER TABLE `etudiants`
  ADD CONSTRAINT `etudiants_ibfk_1` FOREIGN KEY (`classe`) REFERENCES `classe` (`classe`);

--
-- Constraints for table `terminale`
--
ALTER TABLE `terminale`
  ADD CONSTRAINT `terminale_ibfk_1` FOREIGN KEY (`nmat`) REFERENCES `etudiants` (`n_matricule`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
