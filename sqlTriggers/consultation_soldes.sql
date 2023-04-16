-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  Dim 16 avr. 2023 à 15:18
-- Version du serveur :  10.1.29-MariaDB
-- Version de PHP :  7.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `consultation_soldes`
--

-- --------------------------------------------------------

--
-- Structure de la table `administration`
--

CREATE TABLE `administration` (
  `id` int(10) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `NUM_CPT` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `annee` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `NUM_CPT_G` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `CLE` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `NOM_CLI` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `DATE_NAIS` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `LIEU_NAIS` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `NUM_PHONE` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `PROFESSION` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `EMPLOYEUR` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SOLDE_ACTUEL` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SI_BLOQUE` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SI_SOLDE` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SI_MT_BLOQUE` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `NBR_CAR` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MT_BLOC` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Mail` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déclencheurs `client`
--
DELIMITER $$
CREATE TRIGGER `INSERT_USERS` AFTER INSERT ON `client` FOR EACH ROW BEGIN
	INSERT INTO users(NUM_CPT,PASSWORD,type)
    VALUE
    		(NEW.NUM_CPT,sha1(NEW.NUM_CPT),'USER');
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `MESSAGE_DEBIT_CREDIT` AFTER UPDATE ON `client` FOR EACH ROW BEGIN
	DECLARE MSG VARCHAR(500);
	DECLARE DESTINATAIRE VARCHAR(100);
	
	SET @DESTINATAIRE := (SELECT Mail FROM CLIENT WHERE NUM_CPT=NEW.NUM_CPT) ;
	
	IF @DESTINATAIRE IS NOT NULL THEN
		IF NEW.SOLDE_ACTUEL < OLD.SOLDE_ACTUEL  THEN
			SET @MSG := CONCAT('VOTRE COMPTE TRESORE N°: ',NEW.NUM_CPT,' A ETE DEBITER, VOTRE SOLDE ACTUEL EST : ',NEW.SOLDE_ACTUEL) ;
			INSERT INTO MESSAGE (DESTINATAIRE,MSG)
			VALUE
				(@DESTINATAIRE,@MSG);
		ELSE IF NEW.SOLDE_ACTUEL > OLD.SOLDE_ACTUEL THEN
			SET @MSG := CONCAT('VOTRE COMPTE TRESORE N°: ',NEW.NUM_CPT,' A ETE CREDITER, VOTRE SOLDE ACTUEL EST : ',NEW.SOLDE_ACTUEL) ;
			INSERT INTO MESSAGE (DESTINATAIRE,MSG)
			VALUE
				(@DESTINATAIRE,@MSG);
			END IF;
		END IF;
	END IF;	
	
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `compte_bloquer`
--

CREATE TABLE `compte_bloquer` (
  `NUM_CPT` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `DT_BLOC` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `DT_DEBLOC` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MOTIF_BLOC` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MT_OBJET_BLOC` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `contacte`
--

CREATE TABLE `contacte` (
  `id` int(10) UNSIGNED NOT NULL,
  `nom` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `prenom` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `num_compte` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `num_bureau` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `num_poste` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date` date NOT NULL,
  `mail` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `motif_contact` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `tel` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `structure` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `extractiontaxe`
--

CREATE TABLE `extractiontaxe` (
  `id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `keys`
--

CREATE TABLE `keys` (
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `key` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `message`
--

CREATE TABLE `message` (
  `id` int(10) UNSIGNED NOT NULL,
  `DESTINATAIRE` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MSG` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(1, '2014_10_12_000000_create_users_table', 1),
(2, '2014_10_12_100000_create_password_resets_table', 1),
(3, '2018_12_17_085034_create-client-table', 1),
(4, '2018_12_17_094947_create_administration_table', 1),
(5, '2018_12_17_095152_create_compte_bloquer_table', 1),
(6, '2018_12_17_095324_create_contacte_table', 1),
(7, '2018_12_17_095536_create_solde_bloquer_table', 1),
(8, '2018_12_17_095650_create_mvt_table', 1),
(9, '2019_02_05_125644_create_motifs_table', 1),
(10, '2019_03_17_091741_create_type_taxation_table', 1),
(11, '2019_03_17_103403_create_extraction_taxe_table', 1),
(12, '2019_09_23_220348_create_sessions_table', 1),
(13, '2022_04_18_120158_create_keys_table', 1),
(14, '2023_03_30_102923_message', 1);

-- --------------------------------------------------------

--
-- Structure de la table `motifs`
--

CREATE TABLE `motifs` (
  `id` int(10) UNSIGNED NOT NULL,
  `motif` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `mouvement`
--

CREATE TABLE `mouvement` (
  `CODE_OPER` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `NUM_CPT_G` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `CODE_NAT` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `AN_GEST` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MT_CREDIT` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOUR_OPER` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MOIS_OPER` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MT_DEBIT` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `NUM_CPT` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `DT_OPER` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SI_ANNULE` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `DT_ANNUL` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MT_OPER` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `password_resets`
--

CREATE TABLE `password_resets` (
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `sessions`
--

CREATE TABLE `sessions` (
  `id` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int(10) UNSIGNED DEFAULT NULL,
  `ip_address` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_agent` text COLLATE utf8mb4_unicode_ci,
  `payload` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_activity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `solde_bloquer`
--

CREATE TABLE `solde_bloquer` (
  `NUMLIGNE` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `NUM_CPT` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `DT_BLOC` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MT_BLOC` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MOTIF_BLOC` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `DT_DEBLOC` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MOTIF_DEBLOC` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SI_CERTIF` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MT_DEBLOC` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déclencheurs `solde_bloquer`
--
DELIMITER $$
CREATE TRIGGER `MESSAGE_BLOC_MONTANT` AFTER INSERT ON `solde_bloquer` FOR EACH ROW BEGIN
	DECLARE MSG VARCHAR(500);
	DECLARE DESTINATAIRE VARCHAR(100);
	
	SET @DESTINATAIRE := (SELECT Mail FROM CLIENT WHERE NUM_CPT=NEW.NUM_CPT) ;
	
	IF @DESTINATAIRE IS NOT NULL THEN
	
		SET @MSG := CONCAT('VOUS AVEZ UN MONTANT DE : ',NEW.MT_BLOC,' BLOQUER DANS VOTRE COMPTE TRESORE N°: ',NEW.NUM_CPT) ;
		INSERT INTO MESSAGE (DESTINATAIRE,MSG)
		VALUE
				(@DESTINATAIRE,@MSG);	
	END IF ;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `MESSAGE_DEBLOC_MONTANT` AFTER UPDATE ON `solde_bloquer` FOR EACH ROW BEGIN
	DECLARE MSG VARCHAR(500);
	DECLARE DESTINATAIRE VARCHAR(100);
	
	SET @DESTINATAIRE := (SELECT Mail FROM CLIENT WHERE NUM_CPT=NEW.NUM_CPT) ;
	
	IF @DESTINATAIRE IS NOT NULL THEN
	
		SET @MSG := CONCAT('LE MONTANT : ',OLD.MT_BLOC,' A ETE  DEBLOQUER DANS VOTRE COMPTE TRESORE N°: ',NEW.NUM_CPT) ;
	    INSERT INTO MESSAGE (DESTINATAIRE,MSG)
		VALUE
				(@DESTINATAIRE,@MSG);	
	END IF ;
	
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `typetaxation`
--

CREATE TABLE `typetaxation` (
  `id` int(10) UNSIGNED NOT NULL,
  `nom_taxe` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `taxe` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` int(10) UNSIGNED NOT NULL,
  `NUM_CPT` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `administration`
--
ALTER TABLE `administration`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`NUM_CPT`);

--
-- Index pour la table `compte_bloquer`
--
ALTER TABLE `compte_bloquer`
  ADD PRIMARY KEY (`NUM_CPT`,`DT_BLOC`);

--
-- Index pour la table `contacte`
--
ALTER TABLE `contacte`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `extractiontaxe`
--
ALTER TABLE `extractiontaxe`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `keys`
--
ALTER TABLE `keys`
  ADD PRIMARY KEY (`email`);

--
-- Index pour la table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `motifs`
--
ALTER TABLE `motifs`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `mouvement`
--
ALTER TABLE `mouvement`
  ADD PRIMARY KEY (`CODE_OPER`,`NUM_CPT`),
  ADD KEY `mouvement_num_cpt_foreign` (`NUM_CPT`);

--
-- Index pour la table `password_resets`
--
ALTER TABLE `password_resets`
  ADD KEY `password_resets_email_index` (`email`);

--
-- Index pour la table `sessions`
--
ALTER TABLE `sessions`
  ADD UNIQUE KEY `sessions_id_unique` (`id`);

--
-- Index pour la table `solde_bloquer`
--
ALTER TABLE `solde_bloquer`
  ADD PRIMARY KEY (`NUMLIGNE`),
  ADD KEY `solde_bloquer_num_cpt_foreign` (`NUM_CPT`);

--
-- Index pour la table `typetaxation`
--
ALTER TABLE `typetaxation`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_num_cpt_unique` (`NUM_CPT`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `administration`
--
ALTER TABLE `administration`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `contacte`
--
ALTER TABLE `contacte`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `extractiontaxe`
--
ALTER TABLE `extractiontaxe`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `message`
--
ALTER TABLE `message`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT pour la table `motifs`
--
ALTER TABLE `motifs`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `typetaxation`
--
ALTER TABLE `typetaxation`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `mouvement`
--
ALTER TABLE `mouvement`
  ADD CONSTRAINT `mouvement_num_cpt_foreign` FOREIGN KEY (`NUM_CPT`) REFERENCES `client` (`NUM_CPT`) ON DELETE CASCADE;

--
-- Contraintes pour la table `solde_bloquer`
--
ALTER TABLE `solde_bloquer`
  ADD CONSTRAINT `solde_bloquer_num_cpt_foreign` FOREIGN KEY (`NUM_CPT`) REFERENCES `client` (`NUM_CPT`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
