create table chat_room_close(
	id bigint not null primary key auto_increment COMMENT '请求id',
	close_user_name varchar(40) not null COMMENT '关闭用户名', 
	closed_user_name varchar(40) not null COMMENT '被关闭用户名', 
	chat_uid varchar(80) not null COMMENT '会话uid', 
	notified int(4) not null default 0 COMMENT '被关闭者是否被通知: 0: 未通知;1: 已通知' 
);

insert into chat_room_close(close_user_name, closed_user_name, chat_uid) values ();

select chat_uid from chat_room_close where closed_user_name = 'Lucy' and notified = 0;

update chat_room_close set notified = 1 where chat_uid = '';