-- Tabella utenti
CREATE TABLE users(
	id INT AUTO_INCREMENT NOT NULL,
	email VARCHAR(512) NOT NULL,
	created_at DATETIME NOT NULL DEFAULT NOW(),
	updated_at DATETIME NULL,
	PRIMARY KEY (id)
);

-- Tabella segnalibri
CREATE TABLE bookmark(
	id INT AUTO_INCREMENT NOT NULL,
	title VARCHAR(128) NOT NULL,
	description varchar(256) NULL,
	url VARCHAR(1024) NOT NULL,
	user_id INT NULL,
	created_at DATETIME NOT NULL DEFAULT NOW(),
	updated_at DATETIME NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users (id)
);

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