create table user_message(
	user_message_uid varchar(40) not null COMMENT 'uid',
	foreign key (user_message_uid) references message(message_uid),
	to_user  varchar(20) not null COMMENT '送达用户',
	foreign key (to_user) references users(user_name),
	from_user  varchar(20) not null COMMENT '发送用户',
	foreign key (from_user) references users(user_name),
	chat_room_uid  varchar(20) not null COMMENT '聊天室UID',
	foreign key (chat_room_uid) references chat_room(chat_room_uid),
	is_read int(4) not null default 0 COMMENT '消息是否已被读取,0: 未被读取; 1: 已被读取;',
	is_send int(4) not null default 0 COMMENT '消息是否已被发送,0: 未被发送; 1: 已被发送; -1: 发送失败',
	send_time char(20) not null COMMENT '消息创建日期'
);