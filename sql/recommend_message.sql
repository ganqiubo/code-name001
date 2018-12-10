create table recommend_message(
	id bigint not null primary key auto_increment COMMENT 'id',
	message_uid varchar(40) not null COMMENT 'uid', 
	from_user_name varchar(40) not null COMMENT '发布者用户名', 
	to_user_name varchar(40) not null COMMENT '推荐者用户名', 
	notificated int(4) not null default 0 COMMENT '是否已通知对方 0:未通知; 1: 已通知', 
	has_read int(4) not null default 0  COMMENT '对方是否已读 0:未读取; 1: 已读取'
);

update recommend_message set notificated = 1 where message_uid = '' and from_user_name = '' and to_user_name = '';

select from_user_name, to_user_name, message_uid as uid, 1 as type from recommend_message where to_user_name = '' and notificated = 0;

select end_tab.*, 
	(select count(*) from community_mess_thumbup where message_uid = end_tab.message_uid) as thumb_ups, 
	(select count(*) from community_mess_thumbup where message_uid = end_tab.message_uid and user_name = '韩信') as has_thumb_up, 
	(select count(*) from report where report_message_uid = end_tab.message_uid and reporter = '韩信') as has_report, 
	(select count(*) from reply where reply_message_uid = end_tab.message_uid) as reply_num, 
	(select concat(text, ',', user_name, ',', nick_name) from reply where reply_message_uid = end_tab.message_uid order by id desc limit 1) as last_reply 
	from (select c.*, d.certificate, d.age, d.photo, d.sex, d.nick_name 
	from (select b.* from (select * from recommend_message where to_user_name = '韩信' and has_read = 0) as a 
	inner join community_message as b on a.message_uid = b.message_uid) as c inner join users as d on c.user_name = d.user_name) as end_tab;
	
update recommend_message set has_read = 1 where to_user_name = '韩信';