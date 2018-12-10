create table message_user_filter_white(
	filter_id bigint not null COMMENT 'filter_id',
	user_name char(20) not null COMMENT '用户名'
);

insert into message_user_filter_white values('1', 'Lucy');
insert into message_user_filter_white values('2', 'Lucy');
insert into message_user_filter_white values('2', 'root');
insert into message_user_filter_white values('3', 'root');