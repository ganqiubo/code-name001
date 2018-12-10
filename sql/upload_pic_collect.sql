create table upload_pic_collect(
	id bigint not null primary key auto_increment COMMENT '图片id',
	upload_pic_id bigint not null COMMENT '上传图片id',
	collect_user_id varchar(10) not null COMMENT '收藏用户id'
);


select a.upload_pic_id as id, "脚步" as gallery, 
	(select count(*) from upload_pic_like where upload_pic_id = a.upload_pic_id and like_user_id = 3) as has_liked, 
	(select count(*) from upload_pic_collect where upload_pic_id = a.upload_pic_id and collect_user_id = 3) as has_collected,  
	(select count(*) from upload_pic_thumbup where upload_pic_id = a.upload_pic_id and thumbup_user_id = 3) as has_thubm_up,
	(select GROUP_CONCAT(concat(upload_pic_url) SEPARATOR ';') from pic where upload_pic_id = a.upload_pic_id) as pics_str  
	from (select upload_pic_id from upload_pic_like where like_user_id = 3 order by id desc limit 0, 10) as a 
	inner join upload_pic as b on a.upload_pic_id = b.id;