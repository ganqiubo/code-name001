#聊天室属性
create table chat_room(
	id int(4) not null primary key auto_increment COMMENT 'id',
	chat_room_uid char(20) not null unique COMMENT '聊天室id',
	chat_room_name char(20) not null default '' COMMENT '聊天室名称',
	chat_room_type int(4) not null default '1' COMMENT '聊天室类型 1: single; 2: multiply',
	max_member int(4) not null default 100000000 COMMENT '最大成员数',
	create_date char(20) not null COMMENT '创建日期'
);

#insert into chat_room(chat_room_uid, chat_room_name, chat_room_type, create_date) values ('战国群聊', '战国群聊', '2', '2016-08-11 12:12:12');