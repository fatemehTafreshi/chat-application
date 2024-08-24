# create table chat.user
# (
#     id       binary(16)   not null primary key,
#     enabled  bit          not null,
#     password varchar(255) not null,
#     username varchar(255) not null
# );
#
#
# create table chat.authorities
# (
#     authority varchar(255) not null,
#     user_id   binary(16)   not null,
#     primary key (authority, user_id),
#     constraint foreign key (user_id) references user (id)
# );
#
# create table chat.message
# (
#     message_id   binary(16)   not null
#         primary key,
#     recipient_id binary(16)   null,
#     seen_date    datetime(6)  null,
#     sender_id    binary(16)   null,
#     sent_date    datetime(6)  null,
#     text         varchar(255) not null,
#     constraint  foreign key (sender_id) references user (id),
#     constraint foreign key (recipient_id) references user (id)
# );



INSERT INTO chat.message (message_id, text, sent_date, seen_date, sender_id, recipient_id)  values(uuid('7e4c2f7c-0711-4d34-ab0d-2b913af4207e'),
            'aaaaaaa',
             '2024-08-22 11:35:59',
            null,
            uuid('da6cc511-51b7-43e4-8cf0-e35bfc3b2d22'), uuid('3ae01670-78ee-46f0-bba0-8a86081220bc')
    );
