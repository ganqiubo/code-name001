create table pic_comment(
	id bigint not null primary key auto_increment COMMENT '评论id',
	upload_pic_id varchar(40) null COMMENT '上传图片id',
	comment_user_name varchar(40) not null COMMENT '评论用户名',
	comment_text varchar(300) not null COMMENT '评论内容',
	comment_level int not null COMMENT '评论级别: 1:一级评论; 2:二级评论',
	one_level_id bigint not null default -1 COMMENT '回复一级评论id',
	gallery varchar(20) not null COMMENT '图库类型',
	time_milli bigint not null comment '评论时间'
);


select b.*, c.nick_name, c.photo, c.sex from (select a.*, 
	(select count(*) from pic_comment_thumpup where comment_id = a.id) as thumpups, 
	(select count(*) from pic_comment_thumpup where comment_id = a.id and thumpup_user_name = '460079878303087') as has_thumb_up 
	from pic_comment as a) as b inner join users as c where b.comment_user_name = c.user_name and b.upload_pic_id = 'gwjSN4PXXNM' and b.comment_level = 0 
	order by thumpups desc limit 3;
	
select count(*) as comments from pic_comment where upload_pic_id = 'gwjSN4PXXNM';


select b.*, c.nick_name, c.photo, c.sex from (select a.*, 
	(select count(*) from pic_comment_thumpup where comment_id = a.id) as thumpups, 
	(select count(*) from pic_comment where one_level_id = a.id) as sub_comments_num, 
	(select count(*) from pic_comment_thumpup where comment_id = a.id and thumpup_user_name = '460079878303087') as has_thumb_up
	from pic_comment as a) as b inner join users as c where b.comment_user_name = c.user_name and b.upload_pic_id = 'gwjSN4PXXNM' and b.comment_level = 0 
	order by thumpups desc;
	
select b.*, c.nick_name from pic_comment as b inner join users as c where b.comment_user_name = c.user_name and b.one_level_id = 8 and b.comment_level = 1 
	order by id desc limit 5;