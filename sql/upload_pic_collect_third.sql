create table upload_pic_collect_third(
	id bigint not null primary key auto_increment COMMENT '收藏图片id',
	collect_uid varchar(40) COMMENT '收藏图片uid',
	gally varchar(20) not null COMMENT '图库',
	url varchar(300) not null COMMENT '图片url',
	user_name varchar(20) not null COMMENT '收藏用户名'
);


select a.collect_uid as pic_uid, a.gally as gallery, 
	(select count(*) from upload_pic_liked_third where url = a.url and user_name = 'root') as has_liked, 
	(select count(*) from upload_pic_collect_third where url = a.url and user_name = 'root') as has_collected, 
	(select count(*) from upload_pic_thumbup_third where url = a.url and user_name = 'root') as has_thubm_up, 
	a.url as pics_str from 
	(select * from upload_pic_collect_third where user_name = 'root' order by id desc limit 0, 5) as a;