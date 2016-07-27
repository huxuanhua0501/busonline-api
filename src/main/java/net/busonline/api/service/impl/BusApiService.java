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
import net.busonline.core.base.BaseService;
import net.busonline.core.exception.ServiceException;
import net.busonline.core.util.PubMethod;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 业务成，主要处理接口的业务逻辑
 * 
 * @author xuanhua.hu
 *
 */
@Service
public class BusApiService extends BaseService implements IBusApiService {
	public static Logger logger = LoggerFactory.getLogger(BusApiService.class);
	@Autowired
	private JedisPool jedisPool;

	@Autowired
	private SignMapper signMapper;

	/**
	 * 对应city接口，这里主要是city接口的处理业务
	 */
	@Override
	public String city(String city, String sign)  throws ServiceException {
		if (PubMethod.isEmpty(city)) {
			logger.debug("net.busonline.api.service.impl.BusApiService.city.001===城市参数异常");
			throw new ServiceException("net.busonline.api.service.impl.BusApiService.city.001", "城市参数异常");
		}
		if (PubMethod.isEmpty(sign)) {
			logger.debug("net.busonline.api.service.impl.BusApiService.city.002===sign参数异常");
			throw new ServiceException("net.busonline.api.service.impl.BusApiService.city.002", "sign参数异常");
		}
		// 调取redis数据
		// 匹配sign码
		// 匹配通过，走业务
		// 否则返回空

		List<String> redisList = this.hget(2, city, sign);
		List<String> nullArr = new ArrayList<String>();
		nullArr.add(null);
		redisList.removeAll(nullArr);// 移除空null
		if (redisList.size() == 0) {
			return this.jsonFailure();
		}

		// TODO Auto-generated method stub
		List<Map<String, Object>> list = signMapper.getLine(city);
		logger.info("调取baidu库中查询的线路数据" + BusApiService.class + list);
		StringBuilder sb = new StringBuilder("");
		for (Map<String, Object> map : list) {
			sb.append(map.get("lineId")).append(",");
		}
		String lineId = sb.deleteCharAt(sb.length() - 1).toString();
		BusLineDao dao = new BusLineDao();
		List<Map<String, Object>> map = dao.getLineByID(lineId);
		logger.info("带去基础库里调取的线路数据" + BusApiService.class + map);

		return jsonSuccess(map);
	}

	/**
	 * 业务8-10秒百度调取一次，这个业务比较频繁。 data: 线路Id "data":"M434|B669|935", "city" :
	 * "beijing"
	 * 
	 */
	@Override
	public String linecity(String city, String data, String sign)  throws ServiceException {
		if (PubMethod.isEmpty(city)) {
			logger.debug("net.busonline.api.service.impl.BusApiService.linecity.001===城市参数异常");
			throw new ServiceException("net.busonline.api.service.impl.BusApiService.linecity.001", "城市参数异常");
		}
		if (PubMethod.isEmpty(data)) {
			logger.debug("net.busonline.api.service.impl.BusApiService.linecity.002===线路参数异常");
			throw new ServiceException("net.busonline.api.service.impl.BusApiService.linecity.002", "线路参数异常");
		}
		if (PubMethod.isEmpty(sign)) {
			logger.debug("net.busonline.api.service.impl.BusApiService.linecity.003===sign参数异常");
			throw new ServiceException("net.busonline.api.service.impl.BusApiService.linecity.003", "sign参数异常");
		}
		// TODO Auto-generated method stub
		// 调取redis数据
		// 匹配sign码
		// 匹配通过，走业务
		// 否则返回空

		List<String> redisList = this.hget(2, city, sign);
		List<String> nullArr = new ArrayList<String>();
		nullArr.add(null);
		redisList.removeAll(nullArr);// 移除空null
		if (redisList.size() == 0) {
			return this.jsonFailure();
		}
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
	public String linestop(String city, String data, String sign)  throws ServiceException {
		if (PubMethod.isEmpty(city)) {
			logger.debug("net.busonline.api.service.impl.BusApiService.linestop.001===城市参数异常");
			throw new ServiceException("net.busonline.api.service.impl.BusApiService.linestop.001", "城市参数异常");
		}
		if (PubMethod.isEmpty(data)) {
			logger.debug("net.busonline.api.service.impl.BusApiService.linestop.002===线路参数异常");
			throw new ServiceException("net.busonline.api.service.impl.BusApiService.linestop.002", "线路参数异常");
		}
		if (PubMethod.isEmpty(sign)) {
			logger.debug("net.busonline.api.service.impl.BusApiService.linestop.003===sign参数异常");
			throw new ServiceException("net.busonline.api.service.impl.BusApiService.linestop.003", "sign参数异常");
		}
		// TODO Auto-generated method stub
		// 调取redis数据
		// 匹配sign码
		// 匹配通过，走业务
		// 否则返回空
		List<String> redisList = this.hget(2, city, sign);
		List<String> nullArr = new ArrayList<String>();
		nullArr.add(null);
		redisList.removeAll(nullArr);// 移除空null
		if (redisList.size() == 0) {
			return this.jsonFailure();
		}
		String[] strs = data.split("\\|");
		List<Map<String, Object>> list = signMapper.getLineByCityAndLine(city, strs);
		logger.info("调取baidu库中查询的线路数据" + BusApiService.class + list);
		System.out.println(list);
		StringBuilder sb = new StringBuilder("");
		for (Map<String, Object> map : list) {
			sb.append(map.get("lineid")).append(",");
		}
		String lineId = sb.deleteCharAt(sb.length() - 1).toString();
		BusLineDao dao = new BusLineDao();
		List<Map<String, Object>> list2 = dao.getLineByID(lineId, list);
		List<Map<String, Object>> list3 = signMapper.lineStop(strs);
		logger.info("调取baidu库中查询的站点数据" + BusApiService.class + list3);
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("line", list2);
		map3.put("stop", list3);

		return this.jsonSuccess(map3);
	}

	/**
	 * 获取redis中hset中的数据
	 * 
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
			// value = jedis.get(key);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return value;
	}

	public List<String> hget(int dbIndex, String key, String sign) {

		Jedis jedis = jedisPool.getResource();
		List<String> d = null;
		try {
			jedis.select(dbIndex);
			d = jedis.hmget(key, sign);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return d;
	}
	/**
	 * 存放redis中
	 * 
	 * @param dbIndex
	 * @param key
	 * @param value
	 * @return
	 */
	// public String set(int dbIndex, String key, String value) {
	// List<String> vals = null;
	// Jedis jedis = jedisPool.getResource();
	// try {
	// jedis.select(dbIndex);
	// vals = jedis.hmset(key, value);
	//// jedis.hmset("", hash)
	// } finally {
	// jedisPool.returnResource(jedis);
	// }
	// return vals;
	// }
}
