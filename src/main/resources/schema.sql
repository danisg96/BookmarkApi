-- Database progetto segnalibri
CREATE DATABASE IF NOT EXISTS bookmarks;
USE bookmarks;

-- Tabella utenti
CREATE TABLE IF NOT EXISTS users (
	id INT AUTO_INCREMENT NOT NULL,
	email VARCHAR(512) NOT NULL,
	created_at DATETIME NOT NULL DEFAULT NOW(),
	updated_at DATETIME NULL,
	CONSTRAINT PK_User PRIMARY KEY (id)
);

-- Tabella segnalibri
CREATE TABLE IF NOT EXISTS bookmark (
	id INT AUTO_INCREMENT NOT NULL,
	title VARCHAR(128) NOT NULL,
	description TEXT NULL,
	url VARCHAR(1024) NOT NULL,
	user_id INT NULL,
	created_at DATETIME NOT NULL DEFAULT NOW(),
	updated_at DATETIME NULL,
	CONSTRAINT PK_Bookmark PRIMARY KEY (id),
	CONSTRAINT FK_UserBookmark FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Utente non admin
CREATE USER 'luca'@'%' IDENTIFIED BY 'luca';
GRANT ALL PRIVILEGES ON *.* TO 'luca'@'%';
FLUSH PRIVILEGES;

insert into users (id,email) values (1,'danisg96@hotmail.it');

insert into users (id,email) values (2,'danisg97@hotmail.it');

insert into bookmark (title, description, url, user_id)
values ('Sito1', 'primo sito salvato', 'https://primosito.it',1);

insert into bookmark (title, description, url, user_id)
values ('Sito2', 'secondo sito salvato', 'https://secondosito.it',1);

insert into bookmark (title, description, url, user_id)
values ('Sito1', 'primo sito salvato', 'https://primosito.com',2);

insert into bookmark (title, description, url, user_id)
values ('Sito2', 'secondo sito salvato', 'https://secondosito.com',2);