package com.pojul.fastIM.utils;

import java.util.ArrayList;
import java.util.List;

import com.pojul.fastIM.entity.TagMessLabels;

import jdk.internal.dynalink.beans.StaticClass;

public class ArrayUtil {
	
	public static String toCommaSplitSqlStr(List<String> labels) {
		StringBuilder sb = new StringBuilder();
        sb.append("");
        if(labels == null || labels.size() <= 0) {
        	return "";
        }
        for(int i = 0; i < labels.size(); i++){
            if(i > 0){
                sb.append(",");
            }
            sb.append( ( "'" + labels.get(i) + "'") );
        }
        return sb.toString();
	}
	
	public static List<String> toCommaSplitList(String str){
        List<String> arrays = new ArrayList<>();
        if(str == null){
            return arrays;
        }
        String[] strs = str.split(",");
        for (int i = 0; i < strs.length; i++) {
            if(strs[i] == null || "".equals(strs[i])){
                continue;
            }
            arrays.add(strs[i]);
        }
        return arrays;
    }
	
	public static List<String> getLabels(List<TagMessLabels> rawLabels){
		if(rawLabels == null) {
			return null;
		}
		List<String> labels = new ArrayList<>();
		for (int i = 0; i < rawLabels.size(); i++) {
			TagMessLabels tagMessLabels = rawLabels.get(i);
			if(tagMessLabels != null && tagMessLabels.getLabels() != null 
					&& !tagMessLabels.getLabels().isEmpty()) {
				labels.add(tagMessLabels.getLabels());
			}
		}
		return labels;
	}

}
