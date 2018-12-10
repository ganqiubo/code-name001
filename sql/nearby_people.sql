create table nearby_people(
	id bigint not null primary key auto_increment COMMENT 'id',
	user_name varchar(20) not null unique COMMENT '用户名', 
	country char(15) COMMENT '所在国家',
	province char(15) COMMENT '所在省份',
	city char(15) COMMENT '所在城市',
	district char(15) COMMENT '所在县/区',
	addr char(30) COMMENT '所在详细地址',
	longitude double COMMENT '经度',
	latitude double COMMENT '纬度',
	altitude double COMMENT '海拔',
	update_time char(20) not null COMMENT '位置更新时间',
	update_milli bigint not null COMMENT '位置更新时间毫秒', 
	enable int(4) not null default 0 COMMENT '是否可见: 0: 不可见; 1: 可见',
	filter varchar(1000) not null COMMENT '过滤器'
);

select a.longitude, a.latitude, b.user_name, a.filter, b.id, b.nick_name, b.photo, b.age, b.sex, b.hobby, b.occupation, a.update_time
	from (select *, (select (abs(longitude - 114.418008) + abs(latitude - 30.491923)) ) as distance 
	from nearby_people where longitude >= 114.408055 and  longitude <= 114.429059 and latitude >= 30.482811 and latitude <= 30.499815) as a 
	inner join users as b on a.user_name = b.user_name and update_milli > 1536499223688 order by distance limit 0, 20;
	
select a.longitude, a.latitude, a.filter, b.user_name, b.id, b.nick_name, b.photo, b.age, b.sex, b.hobby, b.occupation, a.update_time from (select *, (select (abs(longitude - 114.418056) + abs(latitude - 30.492812)) ) as distance from nearby_people where longitude >= 114.31368915252715 and  longitude <= 114.52242284747287 and latitude >= 30.40287983940813 and latitude <= 30.582744160591872) as a inner join users as b on a.user_name = b.user_name and b.age >= 0 and b.age <= 100 and b.credit >= 90 order by distance limit 0, 20  
	and a.update_milli > 1536593310162 
	
select enable, filter from nearby_people where user_name = '韩信';

select count(*) as num from nearby_people where user_name = '韩信';

update nearby_people set enable = 1, filter = '' where user_name = '韩信b';