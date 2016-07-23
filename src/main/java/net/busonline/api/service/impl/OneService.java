package net.busonline.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.busonline.api.dao.OneMapper;
import net.busonline.api.service.IOneService;
import net.busonline.core.redis.RedisServiceImpl;
import net.busonline.core.util.NoticeHttpClient;

@Service
public class OneService implements IOneService {
	@Autowired
	NoticeHttpClient httpClient;
	@Autowired
	private OneMapper oneMapper;
	@Autowired
	private RedisServiceImpl redisService;

	@Override
	public List<Map<String, Object>> select() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = oneMapper.getOneById("1");
		// openApiHttpClient.doPassSendArr(methodName, map)
		Map<String,Object>map = new HashMap<String,Object>();
		map.put("tel", "15850781443");
//		https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=15850781443
		String str = httpClient.GettoOpen("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm", map);
		System.out.println(str);
		redisService.put("beijing", "001", map);
		Map<String,Object> map1 = redisService.get("beijing", "001", Map.class);
		System.out.println(map1.get("tel"));
		//this.redisService.put("getMemberInfoCollection", memberId, Map.class);
		return list;
	}
}
