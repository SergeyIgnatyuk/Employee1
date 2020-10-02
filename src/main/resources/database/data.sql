-- Table: users
CREATE TABLE users (
    id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity,
    username TEXT NOT NULL,
    password TEXT NOT NULL
);

-- Table: roles
CREATE TABLE roles (
    id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity,
    name TEXT NOT NULL
);

-- Table for mapping user and roles: user_roles
CREATE TABLE user_roles (
    user_id INT NOT NULL UNIQUE,
    role_id INT NOT NULL UNIQUE,

    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- Insert data
INSERT INTO users (username, password) VALUES ('admin', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');

INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_USER');

INSERT INTO user_roles VALUES (1, 1);