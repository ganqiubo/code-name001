create table recomd_user(
	id bigint not null primary key auto_increment COMMENT 'id', 
	from_user_name varchar(40) not null COMMENT '来源用户名', 
	to_user_name varchar(40) not null COMMENT '推荐给用户名', 
	notificated int(4) not null default 0 COMMENT '是否已通知对方 0:未通知; 1: 已通知', 
	has_read int(4) not null default 0  COMMENT '对方是否已读 0:未读取; 1: 已读取'
);


select c.*, d.user_name, d.id, d.nick_name, d.photo, d.age, d.sex, d.hobby, d.occupation from 
	(select b.longitude, b.latitude, b.filter, b.update_time, a.from_user_name from 
	(select distinct(from_user_name) from recomd_user where to_user_name = 'Lucy' and has_read = 0) as a 
	inner join nearby_people as b on a.from_user_name = b.user_name) as c 
	inner join users as d on c.from_user_name = d.user_name;