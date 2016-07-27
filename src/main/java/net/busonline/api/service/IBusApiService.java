package net.busonline.api.service;


public interface IBusApiService {
	public String city(String city,String sign);
	public String linecity(String city,String data,String sign) ;
	public String linestop(String city,String data,String sign);
}
