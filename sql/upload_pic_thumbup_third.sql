create table upload_pic_thumbup_third(
	id bigint not null primary key auto_increment COMMENT '点赞图片id',
	thumbup_uid varchar(40) COMMENT '点赞图片uid',
	gally varchar(20) not null COMMENT '图库',
	url varchar(300) not null COMMENT '图片url',
	user_name varchar(20) not null COMMENT '点赞用户名'
);