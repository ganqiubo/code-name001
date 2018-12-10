create table reply(
	id bigint not null primary key auto_increment COMMENT 'id',
	reply_message_uid varchar(40) not null COMMENT '社区标签消息uid',
	user_name varchar(20) not null COMMENT '回复用户名',
	nick_name varchar(20) not null COMMENT '回复用户昵称',
	reply_uid varchar(40) not null COMMENT '回复消息uid',
	is_space_travel int not null default 0 COMMENT '0: 穿越; 1: 非穿越',
	text varchar(300) not null COMMENT '回复内容',
	time_milli bigint not null COMMENT '消息创建日期毫秒',
	send_time char(20) not null COMMENT '消息创建日期'
);

alter table reply add index index_reply_message_uid(reply_message_uid);

alter table reply add column nick_name varchar(20) not null COMMENT '回复用户昵称';

select end_tab.message_uid, end_tab.user_name, end_tab.time_mill, end_tab.send_time, 
	(select count(*) from community_mess_thumbup where message_uid = end_tab.message_uid) as thumb_ups, 
	(select count(*) from community_mess_thumbup where message_uid = end_tab.message_uid and user_name = 'root') as has_thumb_up,
	(select count(*) from report where report_message_uid = end_tab.message_uid and reporter = 'root') as has_report,
	(select count(*) from reply where reply_message_uid = end_tab.message_uid) as reply_num,
	(select concat(text, ',', user_name, ',', nick_name) from reply where reply_message_uid = end_tab.message_uid order by id desc limit 1) as last_reply
	from(
	select c.* from (
	select a.*, b.certificate, b.photo, b.sex, b.nick_name from (select * from community_message where community_uid = '洪山区_丽岛漫城' and time_mill < 1534233130671) 
	as a inner join users as b on a.user_name = b.user_name 
	) as c order by c.time_mill desc limit 5) as end_tab order by end_tab.time_mill asc;
	
	
	
select end_tab.*, 
	(select count(*) from community_mess_thumbup where message_uid = end_tab.message_uid) as thumb_ups, 
	(select count(*) from reply where reply_message_uid = end_tab.message_uid) as reply_num
	from (select a.*, b.certificate, b.photo, b.sex, b.nick_name from (select * from community_message where message_uid = '1534750366319_root_16') 
	as a inner join users as b on a.user_name = b.user_name 
	) as end_tab;

	
	

	
