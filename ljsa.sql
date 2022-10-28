--
-- Database: `ljsa`
--

CREATE DATABASE IF NOT EXISTS `ljsa` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `ljsa`;

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
  `serie` varchar(10) COLLATE utf8_bin NOT NULL,
  `date_nais` date NOT NULL
);

--
-- Dumping data for table `etudiants`
--

INSERT INTO `etudiants` (`id`, `n_matricule`, `nom`, `prenom`, `classe`, `serie`, `date_nais`) VALUES
(33, '001A', 'RANDRIAMITSIRY', 'valimbavaka manasse', 'seconde', 'D', '1999-07-01'),
(35, '001C', 'RANDRIAMITSIRY', 'lovasoa gabriel', 'terminale', 'S', '2002-07-18'),
(44, '12345AB', 'RAKOTOARISOA', 'tahina', 'terminale', 'A2', '2022-09-28'),
(47, 'ADASDADA', 'ASDASDASDS', 'adasdasd', 'première', 'A1', '2022-06-08'),
(48, 'SD', 'SDSDS', 'kkkk', 'seconde', 'A2', '2022-10-13');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `id` int(11) NOT NULL,
  `username` varchar(45) COLLATE utf8_bin NOT NULL,
  `password` varchar(45) COLLATE utf8_bin NOT NULL
);

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
  `designation` varchar(50) NOT NULL,
  `abreviation` varchar(50) NOT NULL,
  `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
);

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
  `id` int(255) NOT NULL,
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
  `n_mat` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `trimestre` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `annee_scolaire` int(4) NOT NULL
);

--
-- Dumping data for table `premiere`
--

INSERT INTO `premiere` (`id`, `malagasy`, `francais`, `anglais`, `histogeo`, `eac`, `ses`, `spc`, `svt`, `mats`, `eps`, `tice`, `phylo`, `n_mat`, `trimestre`, `annee_scolaire`) VALUES
(5, 12, 13, 3, 8, 7, 11, 10, 5, 9, 6, 2, 1, 'EWREWR', '4', 2025),
(6, 9, 10, 6, 2, 12, 1, 11, 3, 8, 7, 4, 5, 'ADASDADA', '13', 2024);

-- --------------------------------------------------------

--
-- Table structure for table `profs`
--

CREATE TABLE `profs` (
  `id` int(11) NOT NULL,
  `n_matricule` varchar(45) COLLATE utf8_bin NOT NULL,
  `nom_prof` varchar(100) COLLATE utf8_bin NOT NULL,
  `prenom_prof` varchar(100) COLLATE utf8_bin NOT NULL,
  `date_nais` date NOT NULL
);

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
  `id` int(255) NOT NULL,
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
  `annee_scolaire` int(4) NOT NULL
);

--
-- Dumping data for table `seconde`
--

INSERT INTO `seconde` (`id`, `malagasy`, `francais`, `anglais`, `histogeo`, `eac`, `ses`, `spc`, `svt`, `mats`, `eps`, `tice`, `n_mat`, `trimestre`, `annee_scolaire`) VALUES
(5, 28, 23, 21, 20, 19, 21.2, 35, 32, 35, 18, 20.5, '001A', '1', 2022),
(6, 4, 5, 13, 6, 11, 3, 8, 9, 7, 10, 12, '001A', '1', 2022),
(7, 6, 7, 15, 8, 13, 5, 10, 111, 9, 12, 14, '001A', '16', 2022),
(8, 20, 21, 20, 20, 20, 24, 20, 20, 20, 10, 10, '001A', '1', 2021),
(9, 9, 10, 6, 2, 12, 1, 11, 3, 8, 7, 4, 'SD', '13', 5);

-- --------------------------------------------------------

--
-- Table structure for table `serie`
--

CREATE TABLE `serie` (
  `designation` varchar(50) NOT NULL,
  `abreviation` varchar(10) NOT NULL
);

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
  `nmat` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `trimestre` int(1) DEFAULT NULL,
  `annee_scolaire` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
);

--
-- Dumping data for table `terminale`
--

INSERT INTO `terminale` (`id`, `mlg`, `frs`, `anglais`, `histogeo`, `phylo`, `math`, `spc`, `svt`, `ses`, `eps`, `nmat`, `trimestre`, `annee_scolaire`) VALUES
(53, 39, 29, 9, 29, 39, 49, 49, 49, 19, 9, '001C', 1, '2023'),
(54, 9, 10, 6, 2, 1, 8, 11, 3, 4, 7, '001C', 12, '5');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `etudiants`
--
ALTER TABLE `etudiants`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UNIQUE` (`n_matricule`);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `premiere`
--
ALTER TABLE `premiere`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `profs`
--
ALTER TABLE `profs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;

--
-- AUTO_INCREMENT for table `seconde`
--
ALTER TABLE `seconde`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `terminale`
--
ALTER TABLE `terminale`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `terminale`
--
ALTER TABLE `terminale`
  ADD CONSTRAINT `terminale_ibfk_1` FOREIGN KEY (`nmat`) REFERENCES `etudiants` (`n_matricule`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
