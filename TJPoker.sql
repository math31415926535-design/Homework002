CREATE DATABASE IF NOT EXISTS TJPoker
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE TJPoker;

DROP TABLE IF EXISTS game_result;
DROP TABLE IF EXISTS round_result;
DROP TABLE IF EXISTS member;

CREATE TABLE member (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50),
  password VARCHAR(50),
  name VARCHAR(50)
);

CREATE TABLE round_result (
  id INT AUTO_INCREMENT PRIMARY KEY,
  handtype VARCHAR(20),
  card_1 VARCHAR(5),
  card_2 VARCHAR(5),
  card_3 VARCHAR(5),
  card_4 VARCHAR(5),
  card_5 VARCHAR(5)
);

CREATE TABLE game_result (
  id INT AUTO_INCREMENT PRIMARY KEY,
  game_index INT,
  player_name_1 VARCHAR(50),
  player_name_2 VARCHAR(50),
  winner_name VARCHAR(50),
  round_result_1 INT,
  round_result_2 INT,
  round_result_3 INT,
  round_result_4 INT,
  round_result_5 INT,
  round_result_6 INT
);
