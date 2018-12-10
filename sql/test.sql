create table test(
	id bigint not null primary key auto_increment COMMENT 'id',
	bol boolean not null default false COMMENT '测试'
);

insert into test(bol) values('true');

delete from community_message_req;
update login_status set login_status = 0;