package net.busonline.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.busonline.api.service.IOneService;
import net.busonline.core.base.BaseController;

@RestController
@RequestMapping("/oneController")
public class OneController extends BaseController{

	@Autowired
	private IOneService oneService;

	@RequestMapping(value = "find",method = { RequestMethod.POST,
			RequestMethod.GET })
	public String select() {
//	   List<Map<String,Object>> list = oneService.select();
	   
//		for(Map<String,Object> map:list){
//			System.out.println(map.get("name"));
//		}
		return this.jsonSuccess(oneService.select());
	}
	
	@RequestMapping(value = "go",method = { RequestMethod.POST,
			RequestMethod.GET })
	public String go() {
	//   List<Map<String,Object>> list = oneService.select();
	   
//		for(Map<String,Object> map:list){
//			System.out.println(map.get("name"));
//		}
		return this.jsonSuccess(oneService.select());
	}
}
