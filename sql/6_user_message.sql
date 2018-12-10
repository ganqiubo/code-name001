create table user_message(
	id int not null primary key auto_increment COMMENT 'id',
	user_message_uid varchar(40) not null COMMENT 'uid',
	foreign key (user_message_uid) references message(message_uid),
	to_user  varchar(20) not null COMMENT '送达用户',
	from_user  varchar(20) not null COMMENT '发送用户',
	chat_room_uid  varchar(20) not null COMMENT '聊天室UID',
	foreign key (chat_room_uid) references chat_room(chat_room_uid),
	is_read int(4) not null default 0 COMMENT '消息是否已被读取,0: 未被读取; 1: 已被读取;',
	is_send int(4) not null default 0 COMMENT '消息是否已被发送,0: 未被发送; 1: 已被发送; -1: 发送失败',
	send_time char(20) not null COMMENT '消息创建日期'
);

ALTER TABLE user_message ADD INDEX index_union_uid_time(chat_room_uid,send_time);
ALTER table user_message ADD INDEX index_time(send_time);

#历史聊天数
select message.message_class, message.message_content from  user_message, message where user_message.user_message_uid = message.message_uid and user_message.send_time < '2018-07-07 18:04:12' and user_message.chat_room_uid = 'root_lucy' order by user_message.send_time desc limit 20;

select b.send_time from message as a join (select * from user_message where user_message.send_time < '2018-07-07 18:04:12' and chat_room_uid = 'root_lucy' order by send_time desc limit 10) b on b.user_message_uid = a.message_uid;


select send_time from user_message where user_message.send_time < '2018-07-07 18:04:12' and chat_room_uid = 'root_lucy' order by send_time desc limit 20;

select b.send_time from message as a join (select * from user_message where id < (select id from user_message where user_message_uid = '1530948804721_root_3') and chat_room_uid = 'root_lucy' order by send_time desc limit 10) b on b.user_message_uid = a.message_uid;