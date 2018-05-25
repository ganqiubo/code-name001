#聊天室
create table chat_room_members(
	id int(4) not null primary key auto_increment COMMENT 'id',
	user_id int(4) not null COMMENT '用户id',
	chat_room_id int(4) not null COMMENT '聊天室id',
	foreign key (user_id) references users(id),
	foreign key (chat_room_id) references chat_room(id)
);

#select users.user_name as user_name from users INNER JOIN chat_room_members on (users.id = chat_room_members.user_id) where chat_room_members.chat_room_id = 7;
#insert into chat_room_members (user_id,chat_room_id) values("5","1");
#insert into chat_room_members (user_id,chat_room_id) values("6","1");
#insert into chat_room_members (user_id,chat_room_id) values("7","1");
#insert into chat_room_members (user_id,chat_room_id) values("8","1");
#insert into chat_room_members (user_id,chat_room_id) values("9","1");

#select users.user_name as user_name from users INNER JOIN chat_room_members on (users.id = chat_room_members.user_id and chat_room_members.chat_room_id = 7);
#select users.user_name as user_name from users right join chat_room_members on (users.id = chat_room_members.user_id and chat_room_members.chat_room_id = 7);