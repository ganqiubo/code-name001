create table community_room_follows(
	id bigint not null primary key auto_increment COMMENT '关注id',
	manager varchar(40) not null COMMENT '管理员用户名',
	manager_nickname varchar(40) not null COMMENT '管理员昵称', 
	community_uid varchar(40) not null COMMENT '社区uid',
	user_name varchar(40) not null COMMENT '用户名',
	mess_uid varchar(40) not null COMMENT '通知消息uid', 
	message_title varchar(40) not null COMMENT '通知消息标题', 
	notify_level int(4) not null default 3 comment '接受通知公告级别'
);

select a.*, 
	(select count(*) from community_room_follows where community_uid = a.community_uid) as follows 
	from community_room as a where a.community_uid = '';
	
insert into community_room_follows(community_uid, user_name) values('洪山区_保利花园', 'Lucy');

select count(*) as num from community_room_follows where community_uid = '' and user_name = '';

delete from community_room_follows where community_uid = '' and user_name = '';

alter table community_room_follows modify community_uid varchar(40) not null COMMENT '社区uid';

update community_room_follows set notify_level = 1 where community_uid = '' and user_name = '';

select user_name from community_room_follows where community_uid = '' and notify_level >= 1;