create table friend(
	friend_uid varchar(20) not null primary key COMMENT '好友uid',
	frienda_user_name char(20) not null COMMENT '好友a用户id',
	friendb_user_name char(20) not null COMMENT '好友b用户id'
);

#insert into friend(friend_uid, frienda_user_name, friendb_user_name) values('root_Lucy', 'root', 'Lucy');
#insert into friend(friend_uid, frienda_user_name, friendb_user_name) values('韩信_root', '韩信', 'root');
#insert into friend(friend_uid, frienda_user_name, friendb_user_name) values('张良_root', '张良', 'root');
#insert into friend(friend_uid, frienda_user_name, friendb_user_name) values('root_Tony', 'root', 'Tony');
#insert into friend(friend_uid, frienda_user_name, friendb_user_name) values('root_Jack', 'root', 'Jack'); 
#insert into friend(friend_uid, frienda_user_name, friendb_user_name) values('萧何_root', '萧何', 'root');
#insert into friend(friend_uid, frienda_user_name, friendb_user_name) values('陈平_root', '陈平', 'root');
#insert into friend(friend_uid, frienda_user_name, friendb_user_name) values('Tony_Lucy', 'Tony', 'Lucy');


#select a.* from users as a INNER JOIN friend as b on ((a.user_name = b.frienda_user_name and b.friendb_user_name = 'root') or  (a.user_name = b.friendb_user_name and b.frienda_user_name = 'root'));
#select a.*, count(c.user_message_uid) as unsend_message from ( select a.* from users as a INNER JOIN friend as b on ((a.user_name = b.frienda_user_name and b.friendb_user_name = 'root') or  (a.user_name = b.friendb_user_name and b.frienda_user_name = 'root')) ) as a LEFT JOIN user_message as c on (a.user_name = c.from_user and c.to_user = 'root' and c.is_send = '0') group by a.user_name;