create table message_user_filter(
	id bigint not null primary key auto_increment COMMENT 'id',
	message_uid varchar(40) not null COMMENT '社区消息uid',
	is_filter_enabled boolean not null default false COMMENT '过滤器是否可用',
	is_white_list_enabled boolean not null default false COMMENT '是否白名单过滤',
	is_black_list_enabled boolean not null default false COMMENT '是否黑名单过滤',
	is_age_enabled boolean not null default false COMMENT '是否启用年龄过滤',
	min_age int(4) not null default 0 COMMENT '最小年龄',
	max_age int(4) not null default 120 COMMENT '最大年龄',
	is_sex_enabled boolean not null default false COMMENT '是否启用性别过滤',
	sex int(4) not null default 0 COMMENT '性别',
	is_certificat_enabled boolean not null default false COMMENT '是否启用实名认证过滤',
	is_credit_enabled boolean not null default false COMMENT '是否启诚信指数过滤',
	credit int(4) not null default 100 COMMENT '诚信指数'
);

/*请求社区消息*/
select a.* from users as a inner join community_message_req as b on a.user_name = b.user_uid and b.community_uid = '洪山区_丽岛漫城';

/*白名单*/S
select c.* from (select a.* from users as a inner join community_message_req as b on a.user_name = b.user_uid and b.community_uid = '洪山区_丽岛漫城') as c inner join 
 message_user_filter_white as d on c.user_name = d.user_name and d.filter_id = 3;
 
 /*黑名单*/
select c.* from (select a.* from users as a inner join community_message_req as b on a.user_name = b.user_uid and b.community_uid = '洪山区_丽岛漫城') as c left join 
	message_user_filter_black as d on c.user_name = d.user_name and d.filter_id = 3 
	where d.user_name is null;
	
/*条件查询*/
select e.* from (select c.* from (select a.* from users as a inner join community_message_req as b on a.user_name = b.user_uid and b.community_uid = '洪山区_丽岛漫城') as c left join 
	message_user_filter_black as d on c.user_name = d.user_name and d.filter_id = 3 
	where d.user_name is null) as e where age >= 20 and age <= 50;
 
insert into 