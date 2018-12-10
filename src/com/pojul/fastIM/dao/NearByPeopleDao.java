package com.pojul.fastIM.dao;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pojul.fastIM.entity.Count;
import com.pojul.fastIM.entity.LatLonRange;
import com.pojul.fastIM.entity.LocUser;
import com.pojul.fastIM.entity.NearByPeople;
import com.pojul.fastIM.entity.NearbyUserFilter;
import com.pojul.fastIM.entity.UserFilter;
import com.pojul.fastIM.entity.UserSelectFilter;
import com.pojul.fastIM.utils.DaoUtil;
import com.pojul.fastIM.utils.DateUtil;
import com.sun.media.sound.SoftTuning;

public class NearByPeopleDao {

	public long updateLoc(String from, NearByPeople nearByPeople) {
		String sql = "";
		if(isUserLocExit(from)) {
			sql = "update nearby_people set "
					+ "country = '" + nearByPeople.getCountry() + "', " 
					+ "province = '" + nearByPeople.getProvince() + "', "
					+ "city = '" + nearByPeople.getCity() + "', "
					+ "district = '" + nearByPeople.getDistrict() + "', "
					+ "addr = '" + nearByPeople.getAddr() + "', "
					+ "longitude = " + nearByPeople.getLongitude() + ", "
					+ "latitude = " + nearByPeople.getLatitude() + ", " 
					+ "altitude = " + nearByPeople.getAltitude() + ", "
					+ "update_time = '" + DateUtil.getFormatDate() + "', "
					+ "update_milli = " + System.currentTimeMillis() + " where user_name = '" + from + "'";
		}else {
			UserFilter userFilter = new UserFilter();
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			String filterStr = gson.toJson(userFilter);
			sql = "insert into nearby_people(user_name, country, province, city, district, addr, "
					+ "longitude, latitude, altitude, update_time, update_milli, enable, filter) values (" + 
					"'" + from + "', " + 
					"'" + nearByPeople.getCountry() + "', " + 
					"'" + nearByPeople.getProvince() + "', " + 
					"'" + nearByPeople.getCity() + "', " + 
					"'" + nearByPeople.getDistrict() + "', " + 
					"'" + nearByPeople.getAddr() + "', " + 
					"" + nearByPeople.getLongitude() + ", " + 
					"" + nearByPeople.getLatitude() + ", " + 
					"" + nearByPeople.getAltitude() + ", " + 
					"'" + DateUtil.getFormatDate() + "', " + 
					"" + System.currentTimeMillis() + ", " + 
					"" + 1 + ", " + 
					"'" + filterStr + "'" + 
					")";
		}
		return DaoUtil.executeUpdate(sql, false);
	}
	
	
	public boolean isUserLocExit(String from) {
		String sql = "select count(*) as num from nearby_people where user_name = '" + from + "'";
		List<Count> counts = DaoUtil.executeQuery(sql, Count.class);
		if(counts == null || counts.size() <= 0) {
			return false;
		}
		if(counts.get(0).getNum() > 0) {
			return true;
		}
		return false;
	}
	
	public List<LocUser> getNearByPeople(UserSelectFilter filter, int num, long startNum, LatLonRange latLonRange){
		String frontSql = "select a.longitude, a.latitude, a.filter, b.user_name, b.id, b.nick_name, b.photo, b.age, b.sex, b.hobby, b.occupation, a.update_time " + 
				"from (select *, (select (abs(longitude - " + latLonRange.getRawLon() + ") + abs(latitude - " + latLonRange.getRawLat() + ")) ) as distance " + 
				"from nearby_people where enable = 1 and longitude >= " + latLonRange.getMinLon() + " and  longitude <= " + latLonRange.getMaxLon() + " and " + 
				"latitude >= " + latLonRange.getMinLat() + " and latitude <= " + latLonRange.getMaxLat() + ") as a " + 
				"inner join users as b on a.user_name = b.user_name ";
		long timilli = (System.currentTimeMillis() - 2 * 60 * 60 * 1000);
		String whereSql = "and update_milli > " + timilli + " and b.age >= " + filter.getMinAge() + " and b.age <= " + filter.getMaxAge() + " and b.credit >= " + filter.getMinCredit() + " ";
		if(filter.getSex() != -1) {
			whereSql = whereSql + "and b.sex = " + filter.getSex() + " ";
		}
		String endSql = "order by distance limit " + startNum +", " + num;
		String sql = frontSql + whereSql + endSql;
		return DaoUtil.executeQuery(sql, LocUser.class);
	}	
	
	public NearbyUserFilter getNearbyUserFilter(String from) {
		String sql = "select enable, filter from nearby_people where user_name = '" + from + "'";
		List<NearbyUserFilter> filters = DaoUtil.executeQuery(sql, NearbyUserFilter.class);
		if(filters == null) {
			return null;
		}
		if(filters.size() <= 0) {
			return new NearbyUserFilter();
		}
		return filters.get(0);
	}
	
	
	public long updateNearbyUserFilter(NearbyUserFilter filter, String from) {
		String sql = "";
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String filterStr = gson.toJson(filter.getUserFilter());
		if(!isUserLocExit(from)) {
			sql = "insert into nearby_people(user_name, country, province, city, district, addr, "
					+ "longitude, latitude, altitude, update_time, update_milli, enable, filter) values (" + 
					"'" + from + "', " + 
					"'', " + 
					"'', " + 
					"', " + 
					"'', " + 
					"'', " + 
					"" + 0 + ", " + 
					"" + 0 + ", " + 
					"" + 0 + ", " + 
					"'" + DateUtil.getFormatDate() + "', " + 
					"" + System.currentTimeMillis() + ", " + 
					"" + filter.getEnable() + ", " + 
					"'" + filterStr + "'" + 
					")";
		}else {
			sql = "update nearby_people set enable = " + filter.getEnable() + ", filter = '" +  filterStr + "' where user_name = '" + from + "'";
		}
		return DaoUtil.executeUpdate(sql, false);
	}

}
