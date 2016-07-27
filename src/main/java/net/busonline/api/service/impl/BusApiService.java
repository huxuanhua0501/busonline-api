package net.busonline.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.busonline.api.dao.BusLineDao;
import net.busonline.api.dao.SignMapper;
import net.busonline.api.service.IBusApiService;
import net.busonline.core.cache.EhcacheListener;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 * 业务成，主要处理接口的业务逻辑
 * @author xuanhua.hu
 *
 */
@Service
public class BusApiService implements IBusApiService {
	public static   Logger logger = LoggerFactory.getLogger(BusApiService.class);
	@Autowired
	private JedisPool jedisPool;

	@Autowired
	private SignMapper signMapper;

	/**
	 * 对应city接口，这里主要是city接口的处理业务
	 */
	@Override
	public List<Map<String, Object>> getLine(String city) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = signMapper.getLine(city);
		logger.info("调取baidu库中查询的线路数据"+BusApiService.class+list);
		StringBuilder sb = new StringBuilder("");
		for (Map<String, Object> map : list) {
			sb.append(map.get("lineId")).append(",");
		}
		String lineId = sb.deleteCharAt(sb.length() - 1).toString();
		BusLineDao dao = new BusLineDao();
		List<Map<String, Object>> map = dao.getLineByID(lineId);
		logger.info("带去基础库里调取的线路数据"+BusApiService.class+map);

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
			String str = get(0, city + "_" + s);
			list.add(str);
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
		logger.info("调取baidu库中查询的线路数据"+BusApiService.class+list);
		System.out.println(list);
		StringBuilder sb = new StringBuilder("");
		for (Map<String, Object> map : list) {
			sb.append(map.get("lineId")).append(",");
		}
		String lineId = sb.deleteCharAt(sb.length() - 1).toString();
		BusLineDao dao = new BusLineDao();
		List<Map<String, Object>> list2 = dao.getLineByID(lineId,list);
		List<Map<String, Object>> list3  = signMapper.lineStop(strs);
		logger.info("调取baidu库中查询的站点数据"+BusApiService.class+list3);
		Map<String, Object> map3 = new HashMap<String,Object>();
		map3.put("line", list2);
		map3.put("stop", list3);
	 
		return map3;
	}
/**
 * 获取redis中hset中的数据
 * @param dbIndex
 * @param key
 * @return
 */
	public String get(int dbIndex, String key) {
		String value = null;
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.select(dbIndex);
			List<String> d = jedis.hvals(key);
			value = d.toString();
//			value = jedis.get(key);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	/**
	 * 存放redis中
	 * @param dbIndex
	 * @param key
	 * @param value
	 * @return
	 */
//	public String set(int dbIndex, String key, String value) {
//		List<String> vals = null;
//		Jedis jedis = jedisPool.getResource();
//		try {
//			jedis.select(dbIndex);
//			vals = jedis.hmset(key, value);
////			jedis.hmset("", hash)
//		} finally {
//			jedisPool.returnResource(jedis);
//		}
//		return vals;
//	}
}
