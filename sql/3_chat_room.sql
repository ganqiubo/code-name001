create table chat_room(
	id int(4) not null primary key auto_increment COMMENT 'id',
	chat_room_uid char(20) not null unique COMMENT '聊天室id',
	chat_room_name char(20) not null default '' COMMENT '聊天室名称',
	chat_room_type char(20) not null default 'single' COMMENT '聊天室类型',
	max_member int(4) not null default 100000000 COMMENT '最大成员数',
	create_date char(20) not null COMMENT '创建日期'
);