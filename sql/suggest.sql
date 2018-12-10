create table suggest(
	id bigint not null primary key auto_increment COMMENT 'id', 
	from_user_name varchar(40) not null COMMENT '吐槽用户名', 
	content varchar(200) not null COMMENT '吐槽内容', 
	suggest_time varchar(20) not null COMMENT '吐槽时间'
);