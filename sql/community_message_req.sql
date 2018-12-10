create table community_message_req(
	user_uid varchar(20) not null COMMENT '用户uid',
	community_uid varchar(40) not null COMMENT '社区uid',
	filter_uid varchar(40) not null default '' COMMENT '消息过滤器id'
);

select * from users as a inner join community_message_req as b on a.user_name = b.user_uid and b.community_uid = '洪山区_丽岛漫城';