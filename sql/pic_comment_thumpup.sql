create table pic_comment_thumpup(
	id bigint not null primary key auto_increment COMMENT '点赞id',
	comment_id varchar(40) null COMMENT '评论id',
	thumpup_user_name varchar(40) not null COMMENT '点赞用户名'
);