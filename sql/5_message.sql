create table message(
	id int not null primary key auto_increment COMMENT 'id',
	message_uid varchar(40) unique not null COMMENT 'uid',
	message_class varchar(200) not null COMMENT '��Ϣ������',
	message_content varchar(2000) not null COMMENT '��Ϣ����'
);

select * from user_message INNER JOIN message on (message.message_uid = user_message.user_message_uid and user_message.to_user = 'root' and user_message.is_send = 0);