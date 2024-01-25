-- Tabella utenti
CREATE TABLE users (
	id INT AUTO_INCREMENT NOT NULL,
	email VARCHAR(512) NOT NULL,
	created_at DATETIME NOT NULL DEFAULT NOW(),
	updated_at DATETIME NULL,
	CONSTRAINT PK_User PRIMARY KEY (id)
);

-- Tabella segnalibri
CREATE TABLE bookmark (
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