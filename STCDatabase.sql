

CREATE DATABASE stc_task
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
	
	


CREATE TABLE permission_groups (
    id SERIAL PRIMARY KEY,
    group_name VARCHAR(50) NOT NULL
);

CREATE TABLE item (
    id SERIAL PRIMARY KEY,
    type VARCHAR(10) NOT NULL,
    name VARCHAR(255) NOT NULL,
    path VARCHAR(255),
    permission_group_id INT DEFAULT 1,
    FOREIGN KEY (permission_group_id) REFERENCES permission_groups(id)
);

CREATE TABLE files (
    id SERIAL PRIMARY KEY,
    binary_data bigint,
    type varchar(225),
    item_id INT,
    FOREIGN KEY (item_id) REFERENCES item(id)
);

CREATE TABLE permissions (
    id SERIAL PRIMARY KEY,
    user_email VARCHAR(255) NOT NULL,
    permission_level VARCHAR(10) NOT NULL, 
    group_id INT,
    FOREIGN KEY (group_id) REFERENCES permission_groups(id)
);

INSERT INTO permission_groups 
VALUES (1, 'Admin');

INSERT INTO permissions 
VALUES (1, 'mostafa@stc', 'EDIT', 1),
       (2, 'ahmed@stc', 'VIEW', 1);
