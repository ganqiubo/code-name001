package com.pojul.fastIM.dao;

import java.util.ArrayList;
import java.util.List;
import com.pojul.fastIM.entity.ReportReason;
import com.pojul.fastIM.utils.DaoUtil;

public class ReportReasonDao {

	public List<String> getReasons(){
		String sql = "select * from report_reasons";
		List<ReportReason> reportReasons = DaoUtil.executeQuery(sql, ReportReason.class);
		return getReasons(reportReasons);
	}
	
	public List<String> getReasons(List<ReportReason> reportReasons){
		if(reportReasons == null) {
			return null;
		}
		List<String> reasons = new ArrayList<>();
		for (int i = 0; i < reportReasons.size(); i++) {
			ReportReason reportReason = reportReasons.get(i);
			if(reportReason == null || reportReason.getReason() == null || "".equals(reportReason.getReason())) {
				continue;
			}
			reasons.add(reportReason.getReason());
		}
		return reasons;
	}
	
}
