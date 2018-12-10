create table upload_pic_labels(
	id bigint not null primary key auto_increment COMMENT '图片id',
	upload_pic_id bigint not null COMMENT '上传图片id',
	label varchar(10) not null COMMENT '标签'
);