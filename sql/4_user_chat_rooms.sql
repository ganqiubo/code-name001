create table user_chat_rooms(
	id int(4) not null primary key auto_increment COMMENT 'id',
	user_id int(4) not null COMMENT '用户id',
	chat_room_id int(4) not null COMMENT '聊天室id',
	foreign key (user_id) references users(id),
	foreign key (chat_room_id) references chat_room(id)
);