package net.busonline.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.busonline.api.dao.BusLineDao;
import net.busonline.api.dao.SignMapper;
import net.busonline.api.service.IBusApiService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Service
public class BusApiService implements IBusApiService{
	@Autowired
	private JedisPool jedisPool; 
	
	@Autowired
	private SignMapper signMapper;
	@Override
	public  List<Map<String, Object>> city(String city) {
		// TODO Auto-generated method stub
		List<Map<String,Object>>list = signMapper.getLine(city);
		StringBuilder sb = new StringBuilder("");
		for(Map<String,Object>map:list){
			sb.append(map.get("lineId")).append(",");
		}
		String lineId = sb.deleteCharAt(sb.length()-1).toString();
		BusLineDao dao = new BusLineDao();
		List<Map<String,Object>>map = dao.getLineByID(lineId);
 
		return map;
	}
/**
 * 业务8-10秒百度调取一次，这个业务比较频繁。
 * data: 线路Id
 * "data":"M434|B669|935",
    "city" : "beijing"
 * 
 */
	@Override
	public String lineCity(String city, String data) {
		// TODO Auto-generated method stub
		String[]strs = data.split("|");
		List<Object>list = new ArrayList<Object>();
		for(String s:strs){
			list.add(this.get(1, city+"_"+s));
		}
		return list.toString();
	}

	@Override
	public List<Map<String, Object>> lineStop(String city, String data) {
		// TODO Auto-generated method stub
		return null;
	}
	public String get(int dbIndex, String key) {
		String value = null;
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.select(dbIndex);
			value = jedis.get(key);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return value;
	}
}
