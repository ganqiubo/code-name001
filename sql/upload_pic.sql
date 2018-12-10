create table upload_pic(
	id bigint not null primary key auto_increment COMMENT '上传图片id',
	user_id int(4) not null COMMENT '用户id',
	foreign key (user_id) references users(id),
	upload_pic_type int(4) not null COMMENT '上传图片类型：1 普通图片; 2 位置图片',
	is_delete int(4) not null default 1 COMMENT '是否已删除：1: 未被删除; 2: 已被删除',
	uplod_pic_theme char(15) COMMENT '图片主题',
	upload_pic_country char(15) COMMENT '所在国家',
	upload_pic_province char(15) COMMENT '所在省份',
	upload_pic_city char(15) COMMENT '所在城市',
	upload_pic_district char(15) COMMENT '所在县/区',
	upload_pic_addr char(15) COMMENT '所在详细地址',
	upload_pic_longitude double COMMENT '经度',
	upload_pic_latitude double COMMENT '纬度',
	upload_pic_altitude double COMMENT '海拔',
	upload_pic_locnote char(15) COMMENT '位置描述',
	upload_pic_time char(20) not null COMMENT '图片上传日期',
	pic_time char(20) COMMENT '图片拍摄日期,暂不做处理',
	pic_loc_type int(4) COMMENT '位置类型：0: 未知; 1 GPS; 2 网络',
	upload_pic_locshow int(4) COMMENT '显示类型：1 模糊显示; 2 精确显示'
);

#添加索引
ALTER TABLE upload_pic ADD INDEX index_upload_time(upload_pic_time);
ALTER TABLE upload_pic ADD INDEX index_user_id(user_id);

select upload_pic_time from upload_pic;

explain select upload_pic_time from upload_pic where user_id = 3 and is_delete = 1 and upload_pic_time < '2018-07-24 13:30:18' order by upload_pic_time desc limit 5;


select a.id, a.upload_pic_time, a.uplod_pic_theme, 
	(select GROUP_CONCAT(concat(id, '*', upload_pic_id, '*', upload_pic_url, '*', width, '*', height) SEPARATOR ';') from pic where upload_pic_id = a.id) as pics_str,
	(select GROUP_CONCAT(label SEPARATOR ',') from upload_pic_labels where upload_pic_id = a.id) as upload_pic_label, 
	(select count(*) from upload_pic_like where upload_pic_id = a.id and like_user_id = 3) as has_liked, 
	(select count(*) from upload_pic_collect where upload_pic_id = a.id and collect_user_id = 3) as has_collected, 
	(select count(*) from upload_pic_thumbup where upload_pic_id = a.id) as thumb_up_num, 
	(select count(*) from upload_pic_thumbup where upload_pic_id = a.id and thumbup_user_id = 3) as has_thubm_up 
	from (select * from upload_pic where user_id = 3 and id > 0 order by id desc limit 10) as a;

select * from 
	(select c.*, d.sex as user_sex, d.photo, d.user_name, d.nick_name, d.age , 
	(select GROUP_CONCAT(concat(id, '*', upload_pic_id, '*', upload_pic_url, '*', width, '*', height) SEPARATOR ';') from pic where upload_pic_id = c.id) as pics_str, 
	(select GROUP_CONCAT(label SEPARATOR ',') from upload_pic_labels where upload_pic_id = c.id) as upload_pic_label, 
	(select count(*) from upload_pic_like where upload_pic_id = c.id and like_user_id = 3) as has_liked, 
	(select count(*) from upload_pic_collect where upload_pic_id = c.id and collect_user_id = 3) as has_collected, 
	(select count(*) from upload_pic_thumbup where upload_pic_id = c.id) as thumb_up_num, 
	(select count(*) from upload_pic_thumbup where upload_pic_id = c.id and thumbup_user_id = 3) as has_thubm_up 
	from (select distinct a.id as temp_id, a.* from upload_pic as a 
	inner join upload_pic_labels as b on a.id = b.upload_pic_id and b.label in ('自拍', '美女')) as c 
	inner join users as d on c.user_id = d.id) as e order by e.thumb_up_num desc, e.id desc limit 0, 2;

#位置排序
select e.distance, e.id, e.uplod_pic_theme from 
	(select c.*, d.sex as user_sex, d.photo, d.user_name, d.nick_name, d.age , 
	(select GROUP_CONCAT(concat(id, '*', upload_pic_id, '*', upload_pic_url, '*', width, '*', height) SEPARATOR ';') from pic where upload_pic_id = c.id) as pics_str, 
	(select GROUP_CONCAT(label SEPARATOR ',') from upload_pic_labels where upload_pic_id = c.id) as upload_pic_label, 
	(select count(*) from upload_pic_like where upload_pic_id = c.id and like_user_id = 3) as has_liked, 
	(select count(*) from upload_pic_collect where upload_pic_id = c.id and collect_user_id = 3) as has_collected, 
	(select count(*) from upload_pic_thumbup where upload_pic_id = c.id) as thumb_up_num, 
	(select (abs(c.upload_pic_longitude - 114.418008) + abs(c.upload_pic_latitude - 30.491923)) ) as distance, 
	(select count(*) from upload_pic_thumbup where upload_pic_id = c.id and thumbup_user_id = 3) as has_thubm_up 
	from (select distinct a.id as temp_id, a.* from upload_pic as a 
	inner join upload_pic_labels as b on a.id = b.upload_pic_id and a.upload_pic_city = '武汉市' and upload_pic_type = 2) as c 
	inner join users as d on c.user_id = d.id) as e order by e.distance, e.id desc limit 0, 8;









