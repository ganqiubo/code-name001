create table notify_reply(
	id bigint not null primary key auto_increment COMMENT 'id',
	mess_from varchar(20) not null COMMENT '回复用户',
	mess_to varchar(20) not null COMMENT '被回复用户',
	send_time varchar(20) not null COMMENT '发送时间',
	message_uid varchar(40) not null COMMENT '消息uid',
	is_send int not null default 0 COMMENT '是否已发送',
	reply_tag_mess_uid varchar(40) not null COMMENT '回复tag消息uid',
	reply_text varchar(300) not null COMMENT '回复内容',
	tag_mess_title varchar(60) not null COMMENT '回复标题',
	reply_nick_name varchar(20) not null COMMENT '回复用户昵称',
	reply_type int not null default 0 COMMENT '回复tag消息类型: 0: 公开; 1: 私密',
	photo varchar(80) not null COMMENT '回复用户头像'
);

select a.*, (select count(*) from notify_reply where mess_to = 'chatId') as un_send_count 
		from notify_reply as a where a.mess_to = 'chatId' order by id desc limit 1;
		
select a.*, (select count(*) from notify_reply where mess_to = 'root' and is_send = 0) as un_send_count  
				from notify_reply as a where a.mess_to = 'root' and is_send = 0 order by id desc limit 1;	


(select reply_tag_mess_uid from notify_reply group by reply_tag_mess_uid)	

select * from (select reply_tag_mess_uid from notify_reply group by reply_tag_mess_uid) as b 
	inner join notify_reply as a on a.reply_tag_mess_uid = b.reply_tag_mess_uid;
	
select b.reply_text, b.mess_from, b.mess_to, b.tag_mess_title from (select max(id) as id, reply_tag_mess_uid from notify_reply group by reply_tag_mess_uid) as a 
	inner join notify_reply as b on a.reply_tag_mess_uid = b.reply_tag_mess_uid and a.id = b.id and b.is_send = 0 and b.mess_to = 'root';
	
	