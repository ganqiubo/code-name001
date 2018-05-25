create table message(
	message_uid varchar(40) not null COMMENT 'uid',
	to_user  varchar(20) not null COMMENT '�ʹ��û�',
	foreign key (to_user) references users(user_name),
	from_user  varchar(20) not null COMMENT '�����û�',
	foreign key (from_user) references users(user_name),
	chat_room_uid  varchar(20) not null COMMENT '������UID',
	foreign key (chat_room_uid) references chat_room(chat_room_uid),
	is_read int(4) not null default 0 COMMENT '��Ϣ�Ƿ��ѱ���ȡ,0: δ����ȡ; 1: �ѱ���ȡ',
	is_send int(4) not null default 0 COMMENT '��Ϣ�Ƿ��ѱ�����,0: δ������; 1: �ѱ�����',
	message_class varchar(200) not null COMMENT '��Ϣ������',
	message_content varchar(2000) not null COMMENT '��Ϣ����',
	send_time char(20) not null COMMENT '��Ϣ��������'
);