create table login_status(
	id int(4) not null primary key auto_increment COMMENT 'id',
	user_id int(4) not null COMMENT '用户id',
	login_status int(4) not null default 0 comment '登陆状态',
	chat_id char(20) COMMENT '会话id,默认为用户名',
	login_device char(20) COMMENT '登陆设备',
	device_type char(20) COMMENT '设备类型',
	foreign key (user_id) references users(id)
);

#insert into login_status (user_id,login_device) values("5","设备唯一标识");
#insert into login_status (user_id,login_device) values("1","设备唯一标识");