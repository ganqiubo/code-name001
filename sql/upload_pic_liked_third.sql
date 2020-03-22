create table upload_pic_liked_third(
	id bigint not null primary key auto_increment COMMENT '喜欢图片id',
	like_uid varchar(40) COMMENT '喜欢图片uid',
	gally varchar(20) not null COMMENT '图库',
	url varchar(400) not null COMMENT '图片url',
	user_name varchar(20) not null COMMENT '喜欢用户名'
	/*upload_user_name varchar(20) not null COMMENT '上传用户名',
	upload_user_photo varchar(400) not null COMMENT '上传用户头像',
	upload_place varchar(80) not null COMMENT '图片位置'*/
);

alter table upload_pic_liked_third modify column url varchar(400) not null COMMENT '图片url';

select a.like_uid as pic_uid, a.url, a.gally, a.user_name, 
	(select count(*) from upload_pic_liked_third where like_uid = a.like_uid and user_name = 'root') as has_liked 
	from (select * from  where like_uid in ('', '') and user_name = 'root') as a;
	
select (select count(*) from upload_pic_liked_third where like_uid = 'PLKzGZ9HlBY' and user_name = 'root') as has_liked, 
	(select count(*) from upload_pic_collect_third where collect_uid = 'PLKzGZ9HlBY' and user_name = 'root') as has_collected, 
	(select count(*) from upload_pic_thumbup_third where thumbup_uid = 'PLKzGZ9HlBY' and user_name = 'root') as has_thumbuped, 
	(select count(*) from upload_pic_thumbup_third where thumbup_uid = 'PLKzGZ9HlBY') as thumbup_num, 
	'root' as user_name, 
	'unsplash' as gally, 
	'' as url, 
	'PLKzGZ9HlBY' as pic_uid;
	
select (select count(*) from upload_pic_liked_third where url = 'https://images.unsplash.com/photo-1527631444682-1be3b3e9b621?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjEyMDd9&s=3f4bccf8126f63953ea5d1f15f8ff96d' and user_name = 'root') as has_liked, 
	(select count(*) from upload_pic_collect_third where url = 'https://images.unsplash.com/photo-1527631444682-1be3b3e9b621?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjEyMDd9&s=3f4bccf8126f63953ea5d1f15f8ff96d' and user_name = 'root') as has_collected, 
	(select count(*) from upload_pic_thumbup_third where url = 'https://images.unsplash.com/photo-1527631444682-1be3b3e9b621?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjEyMDd9&s=3f4bccf8126f63953ea5d1f15f8ff96d' and user_name = 'root') as has_thumbuped, 
	(select count(*) from upload_pic_thumbup_third where url = 'https://images.unsplash.com/photo-1527631444682-1be3b3e9b621?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjEyMDd9&s=3f4bccf8126f63953ea5d1f15f8ff96d') as thumbup_num, 
	'root' as user_name, 
	'pexels' as gally, 
	'https://images.unsplash.com/photo-1527631444682-1be3b3e9b621?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjEyMDd9&s=3f4bccf8126f63953ea5d1f15f8ff96d' as url, 
	'' as pic_uid;
	
select a.like_uid as pic_uid, a.gally as gallery, 
	(select count(*) from upload_pic_liked_third where url = a.url and user_name = 'root') as has_liked, 
	(select count(*) from upload_pic_collect_third where url = a.url and user_name = 'root') as has_collected, 
	(select count(*) from upload_pic_thumbup_third where url = a.url and user_name = 'root') as has_thubm_up, 
	a.url as pics_str from 
	(select * from upload_pic_liked_third where user_name = 'root' order by id desc limit 0, 5) as a;

	
