create table report_reasons(
	id int not null primary key auto_increment COMMENT 'id',
	reason varchar(20) not null unique COMMENT '举报原因'
);

insert into report_reasons(reason) values ('欺诈'),('色情'),('诱导行为'),('不实信息')
	,('违法犯罪'),('骚扰'),('未经授权的内容'),('侵权'),('垃圾广告'),('政治敏感'),('辱骂、歧视、挑衅等');


