INSERT INTO Chat.User(login, password) VALUES
    ('Mike', 'Mike2001'),
    ('Santos', '88888888'),
    ('bigboy77', 'qwertyuiop'),
    ('John', 'barsik55'),
    ('tyler_durden', '11110000');

INSERT INTO Chat.chatroom(name, owner) VALUES
    ('shrek fan club', 1),
    ('Mastur Beast official', 3),
    ('Only dad', 3),
    ('Manera', 5),
    ('super 4b', 5);

INSERT INTO Chat.Message(author, room, text) VALUES
    (1, 1, 'второй шрек лучше первого, кто не согласен, отправляю в бан'),
    (3, 2, 'Оставил пачку кириешек, улица фредди фазбэр 29. Кто нашел, должен отправить фотку'),
    (3, 3, 'Наказываю лизочку'),
    (5, 4, 'Ээээээ'),
    (5, 5, 'Ээээээуэ');

INSERT INTO Chat.User_Chatroom(user_id, chatroom_id) VALUES
    (1, 1),
    (1, 3),
    (2, 1),
    (2, 3),
    (2, 4),
    (3, 2),
    (3, 3),
    (4, 1),
    (5, 1),
    (5, 4),
    (5, 5);
