create table community_message(
	id bigint not null primary key auto_increment COMMENT 'id',
	message_uid varchar(40) not null unique COMMENT 'uid',
	user_name varchar(40) not null COMMENT '用户名',
	community_name varchar(20) not null COMMENT '社区名',
	community_uid varchar(40) not null COMMENT '社区uid',
	is_effective int not null COMMENT '0: 未失效; 1: 已失效',
	message_class varchar(200) not null COMMENT '消息类名称',
	message_content varchar(6000) not null COMMENT '消息内容',
	time_mill bigint not null COMMENT '消息创建日期毫秒',
	message_type varchar(40) not null default 0 COMMENT '消息类型 0: normal; 1: tag',
	message_private varchar(40) not null default 0 COMMENT '消息回复类型 0: 公开回复;1: 私密回复',
	longitude double not null COMMENT '经度',
	latitude double not null COMMENT '纬度',
	send_time char(20) not null COMMENT '消息创建日期'
);

ALTER TABLE community_message ADD INDEX index_message_uid(message_uid);
ALTER TABLE community_message ADD INDEX index_community_uid(community_uid);

ALTER TABLE community_message ADD message_type varchar(40) not null default 0 COMMENT '消息类型 0: normal; 1: tag';
ALTER TABLE community_message ADD message_private varchar(40) not null default 0 COMMENT '消息回复类型 0: 公开回复;1: 私密回复';

ALTER TABLE community_message ADD longitude double not null COMMENT '经度';
ALTER TABLE community_message ADD latitude double not null COMMENT '纬度';


select message_uid, time_mill, send_time, user_name from community_message;
	
select * from community_message_label where label in ('运动', '旅游', '打球');

	

	
select end_tab.message_uid, end_tab.user_name, end_tab.time_mill, end_tab.send_time from(
	select c.* from (
	select a.*, b.certificate, b.photo, b.sex, b.nick_name from (select * from community_message where community_uid = '洪山区_丽岛漫城' and time_mill < 1533922250142) 
	as a inner join users as b on a.user_name = b.user_name and sex = 0
	) as c inner join (select distinct message_uid from community_message_label where label in ('运动','旅游','打球')) as d on c.message_uid = d.message_uid
	order by c.time_mill desc limit 5) as end_tab order by end_tab.time_mill asc;
	
	
select end_tab.message_uid, end_tab.user_name, end_tab.time_mill, end_tab.send_time, 
	(select count(*) from community_mess_thumbup where message_uid = end_tab.message_uid) as thumb_ups, 
	(select count(*) from community_mess_thumbup where message_uid = end_tab.message_uid and user_name = 'root') as has_thumb_up
	from(
	select c.* from (
	select a.*, b.certificate, b.photo, b.sex, b.nick_name from (select * from community_message where community_uid = '洪山区_丽岛漫城' and time_mill < 1533922250142) 
	as a inner join users as b on a.user_name = b.user_name and sex = 0
	) as c inner join (select distinct message_uid from community_message_label where label in ('运动','旅游','打球')) as d on c.message_uid = d.message_uid
	order by c.time_mill desc limit 5) as end_tab order by end_tab.time_mill asc;
	
select a.user_name, a.message_uid, a.id, a.message_class, a.send_time, 
	(select count(*) from community_mess_thumbup where message_uid = a.message_uid) as thumb_ups, 
	(select count(*) from community_mess_thumbup where message_uid = a.message_uid and user_name = 'root') as has_thumb_up, 
	(select count(*) from report where report_message_uid = a.message_uid and reporter = 'root') as has_report, 
	(select count(*) from reply where reply_message_uid = a.message_uid) as reply_num, 
	(select concat(text, ',', user_name, ',', nick_name) from reply where reply_message_uid = a.message_uid order by id desc limit 1) as last_reply 
	from (select * from community_message where user_name = 'root' and message_type = 1 and id < 100 order by id desc limit 8) as a;

	

	
select longitude, latitude, message_uid, send_time, (select) from community_message where longitude >= 114.418055 and  longitude <= 114.419059 and
	latitude >= 30.492811 and latitude <= 30.492815 and message_type = 1;
	
	
	
	
	
	
	






