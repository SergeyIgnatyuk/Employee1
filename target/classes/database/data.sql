-- Table: users
CREATE TABLE users (
    user_id INT GENERATED ALWAYS AS identity PRIMARY KEY,
    username TEXT NOT NULL,
    password TEXT NOT NULL
);

-- Table: roles
CREATE TABLE roles (
    role_id INT GENERATED ALWAYS AS identity PRIMARY KEY,
    name TEXT NOT NULL
);

-- Table for mapping user and roles: user_roles
CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (role_id) REFERENCES roles (role_id),

    UNIQUE (user_id, role_id)
);

-- Insert data
INSERT INTO users (username, password) VALUES ('admin', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');

INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_USER');

INSERT INTO user_roles VALUES (1, 1);