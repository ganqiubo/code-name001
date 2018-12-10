package com.pojul.fastIM.dao;

import com.pojul.fastIM.entity.Report;
import com.pojul.fastIM.utils.DaoUtil;

public class ReportDao {
	
	public long insertReport(Report report) {
		String sql = "insert into report(report_message_uid, report_reason, detail, reporter, be_reporter) values ("
				+ "'" + report.getReportMessageUid() + "', " 
				+ "'" + report.getReportReason() + "', " 
				+ "'" + report.getDetail() + "', " 
				+ "'" + report.getReporter() + "', " 
				+ "'" + report.getBeReporter() + "'" + 
				")";
		return DaoUtil.executeUpdate(sql, false);
	}

}
