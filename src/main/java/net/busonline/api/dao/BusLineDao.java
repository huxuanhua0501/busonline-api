package net.busonline.api.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.busonline.api.controller.BusApiController;
import net.busonline.core.datasource.JDBCUtils;

public class BusLineDao extends JDBCUtils{
	public static   Logger logger = LoggerFactory.getLogger(BusLineDao.class);
	/**
	 * 运用第一个city接口，通过lineid调取基础库的线路名字和线路id
	 * @param lineid
	 * @author xuanhua.hu
	 * @return
	 */
	public   List<Map<String,Object>> getLineByID(String lineid){
		  ConcurrentHashMap<String,Object> map= new ConcurrentHashMap<String,Object>();
		  List<Map<String,Object>>list = new ArrayList<Map<String,Object>>();
		try {
			con = this.getConnection();
			String sql = "SELECT id,name FROM bus_line where id in ("+lineid+")";
			logger.info("sql===="+BusLineDao.class+list);
			pre = con.prepareStatement(sql);
			res = pre.executeQuery();
			res.beforeFirst();
			String sendTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			String status = "0";
	 
			while (res.next()) {
				map= new ConcurrentHashMap<String,Object>();
				map.put("dplLine", res.getInt("id"));
				map.put("name", res.getString("name"));
				list.add(map);
			}
			//map= new ConcurrentHashMap<String,Object>(); 
		//	map.put("data", list);
			//map.put("status", status);
			//map.put("sendTime", sendTime);
 
		} catch (Exception e) {
			logger.debug("异常开始===="+BusLineDao.class, e);
		}finally{
			
			this.close();
		}
		return list;
	 
		 
	}
	
	/**
	 * 运用第3个linestop接口，通过lineid调取基础库的线路名字和线路id,
	 * 然后将数据合并baiduku中查询的线路。整合正一个有上下行的完整线路数据
	 * @param lineid
	 * @author xuanhua.hu
	 * @return
	 */
	public   List<Map<String,Object>> getLineByID(String lineid,List<Map<String,Object>>list1){
		  ConcurrentHashMap<String,Object> map= new ConcurrentHashMap<String,Object>();
		  List<Map<String,Object>>list = new ArrayList<Map<String,Object>>();
		try {
			con = this.getConnection();
			String sql = "SELECT id,name FROM bus_line where id in ("+lineid+")";
			logger.info("sql===="+BusLineDao.class+list);
			pre = con.prepareStatement(sql);
			res = pre.executeQuery();
			res.beforeFirst();
			while (res.next()) {
				map= new ConcurrentHashMap<String,Object>();
//				map.put("dplLine", res.getInt("id"));
				map.put("name", res.getString("name"));
//				System.out.println(res.getInt("id")+res.getString("name"));
				for(int i = 0;i<list1.size() ;i++){
					list1.get(i).get("lineid").equals(res.getInt("id"));
					list1.get(i).putAll(map);
					logger.info("合并基础库和baidu库的线路数据===="+BusLineDao.class, list1);
				}
//				list.add(map);
			}
			
			
		 

		} catch (Exception e) {
			logger.debug("异常开始===="+BusLineDao.class, e);
		}finally{
			
			this.close();
		}
		return list1;
	 
		 
	}
	
	
	public static void main(String[] args) {
//		Map<String,Object>map =new BusLineDao().getLineByID(null);
//		System.out.println(map.size());
//		System.out.println(map.get("data"));
	}
}
