CREATE TABLE if not exists url(
                       id IDENTITY NOT NULL PRIMARY KEY,
                       full_url VARCHAR(2048) NOT NULL UNIQUE ,
                       short_url VARCHAR(100) NOT NULL);