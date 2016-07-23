package net.busonline.api.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.busonline.api.dao.SignMapper;
import net.busonline.api.service.InitCacheService;

@Service("initCacheService")
public class InitCacheServiceImpl implements InitCacheService {
	@Autowired
	private SignMapper signMapper;
	 

	@Override
	public List<Map<String, Object>> getAllSign() {
		// TODO Auto-generated method stub
		return signMapper.getAllSign();
	}

}
