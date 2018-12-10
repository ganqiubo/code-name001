create table upload_pic_reply(
	id bigint not null primary key auto_increment COMMENT 'id',
	upload_pic_id varchar(40) not null COMMENT '上传图片id',
	user_name varchar(20) not null COMMENT '回复用户名',
	nick_name varchar(20) not null COMMENT '回复用户昵称',
	reply_uid varchar(40) not null COMMENT '回复消息uid',
	is_space_travel int not null default 0 COMMENT '0: 穿越; 1: 非穿越',
	text varchar(300) not null COMMENT '回复内容',
	time_milli bigint not null COMMENT '消息创建日期毫秒',
	send_time char(20) not null COMMENT '消息创建日期'
);