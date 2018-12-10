create table pic_labeled(
	id bigint not null primary key auto_increment COMMENT '图片标签id',
	pic_id bigint not null COMMENT '图片id',
	pic_label char(10) not null COMMENT '图片标签',
	is_delete int(4) not null default 1 COMMENT '是否已删除：1: 未被删除; 2: 已被删除'
);