create table friend_req(
	id bigint not null primary key auto_increment COMMENT '请求id', 
	req_user_name varchar(40) not null COMMENT '请求用户名', 
	reqed_user_name varchar(40) not null COMMENT '被请求用户名', 
	req_type int(4) not null default 0 COMMENT '请求类型: 1: 添加好友请求;2: 打招呼请求', 
	req_text varchar(100) COMMENT '请求内容',
	req_notified int(4) not null default 0 COMMENT '请求用户是否被通知: 0: 未通知;1: 已通知', 
	reqed_notified int(4) not null default 0 COMMENT '被请求用户是否被通知: 0: 未通知;1: 已通知', 
	req_status int(4) not null default 0 COMMENT '请求状态: 0: 未回应;1: 已同意', 
	req_time varchar(20) not null COMMENT '请求时间'
);

select * from (select * from friend_req where reqed_user_name = '韩信' and reqed_notified = 0 and req_status = 0) as a 
	inner join users as b on a.req_user_name = b.user_name;
	
select * from (select * from friend_req where reqed_user_name = '韩信' order by req_time desc limit 1, 2) as a 
	inner join users as b on a.req_user_name = b.user_name;

	
select count(*) from friend where (frienda_user_name = 'Tony' and friendb_user_name = 'Jack') or (friendb_user_name = 'Tony' and frienda_user_name = 'Jack')

select * from (select req_user_name, reqed_user_name, req_type from friend_req where req_user_name = '韩信' and req_notified = 0 and req_status = 0) as a 
	inner join users as b on a.reqed_user_name = b.user_name;

	
select req_status as num from friend_req where req_type = 2 and 
	((req_user_name = '韩信' and reqed_user_name ='Lucy') or (reqed_user_name = '韩信' and req_user_name = 'Lucy'));
	
	
select req_user_name as owner, reqed_user_name as friend from friend_req where 
	req_status = -1 and reqed_notified = 1 and (req_user_name = 'Lucy' or reqed_user_name = 'Lucy');
	
update friend_req set req_status = -1 where 
	(req_user_name = 'Tony' and reqed_user_name = 'Jack') or 
	(reqed_user_name = 'Tony' and req_user_name = 'Jack');
