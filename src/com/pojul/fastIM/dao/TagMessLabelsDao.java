package com.pojul.fastIM.dao;

import java.util.List;

import com.pojul.fastIM.entity.TagMessLabels;
import com.pojul.fastIM.utils.DaoUtil;

public class TagMessLabelsDao {

	public String getLabels() {
		String sql = "select GROUP_CONCAT(label SEPARATOR ',') as labels from tag_mess_labels";
		List<TagMessLabels> tagMessLabels = DaoUtil.executeQuery(sql, TagMessLabels.class);
		if(tagMessLabels == null || tagMessLabels.size() <= 0 || "".equals(tagMessLabels.get(0).getLabels())) {
			return null;
		}
		return tagMessLabels.get(0).getLabels();
	}
	
}
