package net.busonline.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
public class BusApiService implements IBusApiService {
	@Autowired
	private JedisPool jedisPool;

	@Autowired
	private SignMapper signMapper;

	@Override
	public List<Map<String, Object>> getLine(String city) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = signMapper.getLine(city);
		StringBuilder sb = new StringBuilder("");
		for (Map<String, Object> map : list) {
			sb.append(map.get("lineId")).append(",");
		}
		String lineId = sb.deleteCharAt(sb.length() - 1).toString();
		BusLineDao dao = new BusLineDao();
		List<Map<String, Object>> map = dao.getLineByID(lineId);

		return map;
	}

	/**
	 * 业务8-10秒百度调取一次，这个业务比较频繁。 data: 线路Id "data":"M434|B669|935", "city" :
	 * "beijing"
	 * 
	 */
	@Override
	public String lineCity(String city, String data) {
		// TODO Auto-generated method stub
		String[] strs = data.split("\\|");
		System.out.println(strs);
		List<Object> list = new ArrayList<Object>();
		for (String s : strs) {
			list.add(this.get(0, city + "_" + s));
		}
		return list.toString();
	}

	/**
	 * 7.城市&线路站点查询接口
	 */
	@Override
	public  Map<String, Object>  lineStop(String city, String data) {
		// TODO Auto-generated method stub
		String[] strs = data.split("\\|");
		List<Map<String, Object>> list = signMapper.getLineByCityAndLine(city, strs);
		System.out.println(list);
		StringBuilder sb = new StringBuilder("");
		for (Map<String, Object> map : list) {
			sb.append(map.get("lineId")).append(",");
		}
		String lineId = sb.deleteCharAt(sb.length() - 1).toString();
		BusLineDao dao = new BusLineDao();
		List<Map<String, Object>> list2 = dao.getLineByID(lineId,list);
		for(int i = 0 ;i<list.size();i++){
			list.get(i).get("");
		}
		List<Map<String, Object>> list3  = signMapper.lineStop(strs);
		Map<String, Object> map3 = new HashMap<String,Object>();
		map3.put("line", list2);
		map3.put("stop", list3);
	 
		return map3;
	}

	public String get(int dbIndex, String key) {
		String value = null;
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.select(dbIndex);
			List<String> d = jedis.hvals(key);
			System.out.println(d);
//			value = jedis.get(key);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	public String set(int dbIndex, String key, String value) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.select(dbIndex);
			jedis.set(key, value);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return value;
	}
}
