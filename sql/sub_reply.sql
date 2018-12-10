create table sub_reply(
	id bigint not null primary key auto_increment COMMENT 'id',
	reply_message_uid varchar(40) not null COMMENT '社区回复消息uid',
	reply_tag_mess_uid varchar(40) not null COMMENT '社区标签消息uid',
	user_name varchar(20) not null COMMENT '回复用户名',
	nick_name varchar(20) not null COMMENT '回复用户名',
	sub_reply_uid varchar(40) not null COMMENT '回复消息uid',
	is_space_travel int not null default 0 COMMENT '0: 穿越; 1: 非穿越',
	text varchar(300) not null COMMENT '回复内容',
	time_milli bigint not null COMMENT '消息创建日期毫秒',
	send_time char(20) not null COMMENT '消息创建日期'
);

alter table sub_reply add column reply_tag_mess_uid varchar(40) not null COMMENT '社区标签消息uid';

select a.*, b.photo, b.sex, b.certificate, 
	(select GROUP_CONCAT(
	concat(reply_message_uid, ',', user_name, ',', nick_name, ',', sub_reply_uid , ',',is_space_travel, ',', text, ',', time_milli) 
	order by id desc SEPARATOR ';') 
	from sub_reply where reply_message_uid = a.reply_uid) as sub_replys,
	(select count(*) from sub_reply where reply_message_uid = a.reply_uid) as sub_replys_num 
	from (select * from reply where reply_message_uid = '1534167617420_root_7' and time_milli < 
	1534354496223 order by id desc limit 10)
	as a inner join users as b on a.user_name = b.user_name;
	
select 
	(select GROUP_CONCAT(
	concat(reply_message_uid, ',', user_name, ',', nick_name, sub_reply_uid , ',',is_space_travel, ',', text, ',', time_milli, ',', reply_tag_mess_uid) 
	order by id desc SEPARATOR ';') 
	from sub_reply where reply_message_uid = a.reply_uid) as sub_replys
	from (select * from reply where reply_message_uid = '1534167617420_root_7' and time_milli < 
	1534354496223 order by id desc limit 10)
	as a inner join users as b on a.user_name = b.user_name;
	
	
select a.text, a.time_milli, b.photo, b.user_name, b.nick_name, b.certificate, b.sex from (select * from sub_reply where 
	reply_message_uid = '1534354249849_root_15' and time_milli < 1534415443226 order by id desc limit 4) as a 
	left join users as b on a.user_name = b.user_name;




















	

