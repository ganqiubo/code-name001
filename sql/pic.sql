create table pic(
	id bigint not null primary key auto_increment COMMENT '图片id',
	upload_pic_id bigint not null COMMENT '上传图片id',
	upload_pic_url varchar(120) not null COMMENT '图片地址',
	width int(4) not null COMMENT '图片宽度', 
	height int(4) not null COMMENT '图片高度', 
	is_delete int(4) not null default 1 COMMENT '是否已删除：1: 未被删除; 2: 已被删除'
);

ALTER TABLE pic ADD INDEX index_upload_id(upload_pic_id);

select * from pic where upload_pic_id = 9;