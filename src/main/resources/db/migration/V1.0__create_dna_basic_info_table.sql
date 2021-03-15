CREATE TABLE `mutants_db`.`dna_basic_info` (
  `id` BIGINT(10) NOT NULL,
  `dna_sequence` VARCHAR(40000) NULL,
  `mutant` BIT(1) NULL,
  PRIMARY KEY (`id`));
