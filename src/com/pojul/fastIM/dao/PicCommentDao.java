package com.pojul.fastIM.dao;

import java.util.List;

import com.pojul.fastIM.entity.Count;
import com.pojul.fastIM.entity.PicComment;
import com.pojul.fastIM.utils.DaoUtil;

public class PicCommentDao {

	public long picComment(String from, String uploadPicId, String text, int level, long oneLevelId, String gallery) {
		String sql = "insert into pic_comment(upload_pic_id, comment_user_name, comment_text, comment_level, "
				+ "one_level_id, gallery, time_milli) values("
				+ "'" + uploadPicId + "', "
				+ "'" + from + "', "
				+ "'" + text + "', "
				+ "" + level + ", "
				+ "" + oneLevelId + ", "
				+ "'" + gallery + "', "
				+ "" + System.currentTimeMillis() + "" + 
				")";
		return DaoUtil.executeUpdate(sql, true);
	}
	
	public List<PicComment> getComments(int page, String picId, int num, String from){
		String sql = "select b.*, c.nick_name, c.photo, c.sex from (select a.*, " + 
				"(select count(*) from pic_comment where upload_pic_id = '" + picId + "') as total_comments, " + 
				"(select count(*) from pic_comment_thumpup where comment_id = a.id) as thumpups, " + 
				"(select count(*) from pic_comment where one_level_id = a.id) as sub_comments_num, " + 
				"(select count(*) from pic_comment_thumpup where comment_id = a.id and thumpup_user_name = '" + from + "') as has_thumb_up " + 
				"from pic_comment as a) as b inner join users as c where b.comment_user_name = c.user_name and b.upload_pic_id = '" + picId + "' "
				+ "and b.comment_level = 0 order by thumpups desc, id desc limit " + (page*num) + ", " + num;
		return DaoUtil.executeQuery(sql, PicComment.class);
	}
	
	public List<PicComment> getTopSubComment(long oneLevelId) {
		String sql = "select b.*, c.nick_name from pic_comment as b inner join users as c "
				+ "where b.comment_user_name = c.user_name and "
				+ "b.one_level_id = " + oneLevelId + " and b.comment_level = 1 "
				+ "order by id desc limit 5";
		return DaoUtil.executeQuery(sql, PicComment.class);
	}
	
	public long getCommentNum(String picId) {
		String sql = "select count(*) as num from pic_comment where comment_level = 0 and upload_pic_id = '" + picId + "'";
		List<Count> counts = DaoUtil.executeQuery(sql, Count.class);
		if(counts == null || counts.size() <= 0) {
			return 0;
		}
		return counts.get(0).getNum();
	}
	
	public List<PicComment> getTop3Comments(String picId, String from){
		String sql = "select b.*, c.nick_name, c.photo, c.sex from (select a.*, " + 
				"(select count(*) from pic_comment_thumpup where comment_id = a.id) as thumpups, " + 
				"(select count(*) from pic_comment_thumpup where comment_id = a.id and thumpup_user_name = '" + from + "') as has_thumb_up " + 
				"from pic_comment as a) as b inner join users as c where b.comment_user_name = c.user_name and "
				+ "b.upload_pic_id = '" + picId + "' and b.comment_level = 0 order by thumpups desc limit 3";
		return DaoUtil.executeQuery(sql, PicComment.class);
	}

	public long thumbUpComment(String commentId, String from) {
		// TODO Auto-generated method stub
		String sql = "insert into pic_comment_thumpup(comment_id, thumpup_user_name) values("
				+ "'" + commentId + "', "
				
				+ "'" + from + "'" + 
				")";
		return DaoUtil.executeUpdate(sql, false);
	}
}
