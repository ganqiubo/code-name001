create table upload_pic_thumbup(
	id bigint not null primary key auto_increment COMMENT '图片id',
	upload_pic_id bigint not null COMMENT '上传图片id',
	thumbup_user_id varchar(10) not null COMMENT '点赞用户id'
);