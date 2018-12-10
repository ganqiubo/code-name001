create table community_message_label(
	message_uid varchar(40) not null COMMENT '消息uid', 
	user_name varchar(20) not null COMMENT '用户名', 
	label varchar(20) not null COMMENT '标签'
);