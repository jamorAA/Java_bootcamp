DROP SCHEMA IF EXISTS Chat CASCADE;
CREATE SCHEMA IF NOT EXISTS Chat;

CREATE TABLE Chat.User (
    user_id  INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    login    VARCHAR(25) NOT NULL UNIQUE,
    password VARCHAR(25) NOT NULL
);

CREATE TABLE Chat.Chatroom (
    chatroom_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    owner       INT,
    FOREIGN KEY (owner) REFERENCES Chat.User(user_id)
);

CREATE TABLE Chat.Message (
    message_id  INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    author      INT,
    room        INT,
    text        TEXT,
    time        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author) REFERENCES Chat.User(user_id),
    FOREIGN KEY (room)   REFERENCES Chat.Chatroom(chatroom_id)
);

CREATE TABLE Chat.User_Chatroom (
    user_id     INT,
    chatroom_id INT,
    PRIMARY KEY (user_id, chatroom_id),
    FOREIGN KEY (user_id)     REFERENCES Chat.User(user_id),
    FOREIGN KEY (chatroom_id) REFERENCES Chat.Chatroom(chatroom_id)
);
