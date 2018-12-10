package com.pojul.fastIM.dao;

import java.util.List;

import com.pojul.fastIM.entity.Pic;
import com.pojul.fastIM.utils.DaoUtil;

public class PicDao {

	public Long insertPic(List<Pic> pics, long uploadPicId) {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into pic(upload_pic_id, upload_pic_url, width, height, is_delete) values ");
		for (int i = 0; i < pics.size(); i++) {
			if(i > 0) {
				sb.append(",");
			}
			Pic pic = pics.get(i);
			String value = "("
					+ "'" + uploadPicId + "', " 
					+ "'" + pic.getUploadPicUrl().getFilePath() + "', "
					+ "'" + pic.getWidth() + "', "
					+ "'" + pic.getHeight() + "', "
					+ "'" + pic.getIsDelete() + "'"
					+ ")";
			sb.append(value);
		}
		String sql = sb.toString();
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public List<Pic> queryRecords(long uploadPicId){
		String sql = "select * from pic where upload_pic_id = " + uploadPicId + " and is_delete = 1";
		List<Pic> pics = DaoUtil.executeQuery(sql, Pic.class);
		return pics;
	}
	
}
