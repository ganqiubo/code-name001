create table community_mess_thumbup(
	message_uid varchar(40) not null COMMENT '社区消息uid',
	user_name char(20) not null COMMENT '用户名'
);

insert into community_mess_thumbup values ('1533921956129_Lucy_18', 'Lucy'), ('1533921956129_Lucy_18', 'root'), ('1533922113108_Lucy_16', 'Jack');

select * from (select count(*), message_uid from community_mess_thumbup group by message_uid) as a inner join ;


select community_uid from community_message;




select * from (select a.*, 
	(select count(*) from community_mess_thumbup where message_uid = a.message_uid) as thumb_ups 
	from (select message_uid, time_mill, send_time, community_uid 
	from community_message where community_uid = '洪山区_丽岛漫城' and time_mill > 1534156403777 and is_effective = 0) 
	as a order by thumb_ups desc limit 3) as b;
