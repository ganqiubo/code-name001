create table report(
	id bigint not null primary key auto_increment COMMENT 'id',
	report_message_uid varchar(40) not null COMMENT '消息uid',
	report_reason varchar(20) not null COMMENT '举报原因',
	detail varchar(300) COMMENT '详情',
	reporter char(20) not null COMMENT '举报者用户名',
	be_reporter char(20) not null COMMENT '被举报者用户名'
);