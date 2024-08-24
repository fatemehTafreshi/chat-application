# create table `user`
# (
#     `id`       binary(16)   not null primary key,
#     `enabled`  bit          not null,
#     `password` varchar(255) not null,
#     `username` varchar(255) not null
# );
#
#
#
# create table `message`
# (
#     `message_id`   binary(16)   not null primary key,
#     `recipient_id` binary(16)   null,
#     `seen_date`    datetime(6)  null,
#     `sender_id`    binary(16)   null,
#     `sent_date`    datetime(6)  null,
#     `text`         varchar(255) not null,
#     constraint  foreign key (`sender_id`) references user (`id`),
#     constraint foreign key (`recipient_id`) references user (`id`)
# );
#
#
#
# create table `authorities`
# (
#     `authority` varchar(255) not null,
#     `user_id`  binary(16)   not null,
#     primary key (`authority`, `user_id`),
#     constraint foreign key (`user_id`) references user (`id`)
# );