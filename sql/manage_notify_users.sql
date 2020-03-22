create table manage_notify_users(
	id bigint not null primary key auto_increment COMMENT 'id',
	manager varchar(40) not null COMMENT '管理员用户名',
	manager_nickname varchar(40) not null COMMENT '管理员昵称', 
	manager_photo varchar(200) not null COMMENT '管理员头像', 
	commu_room_uid varchar(40) not null COMMENT '社区uid',
	mess_uid varchar(40) not null COMMENT '推送消息uid',
	message_title varchar(100) not null COMMENT '通知消息标题',
	time_mill bigint not null COMMENT '时间', 
	user_name varchar(40) not null COMMENT '推送用户'
);

select * from manage_notify_users where user_name = '' order by id desc limit 0,1;

delete from manage_notify_users where user_name = '';



