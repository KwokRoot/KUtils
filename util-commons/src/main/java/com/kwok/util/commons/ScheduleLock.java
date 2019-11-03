package com.kwok.util.commons;

import redis.clients.jedis.Jedis;

public class ScheduleLock {

	private static final String keyPrefix = "lock:schedule:";
	
	public static Boolean lock(String scheduleKey, Integer expireSecond){
		Jedis jedis = null;
		try{
			jedis = JedisFactory.getJedis(15);
			jedis.select(15);
			if(jedis.set(keyPrefix + scheduleKey, "lock:" + String.valueOf(System.currentTimeMillis()), "NX", "EX", expireSecond) == null){
				return false;
			}else{
				return true;
			}
		}catch (Exception e) {
			return false;
		}finally {
			JedisFactory.returnJedis(jedis);
		}
	}
	
	public static Boolean unlock(String scheduleKey){
		Jedis jedis = null;
		try{
			jedis = JedisFactory.getJedis(15);
			jedis.del(keyPrefix + scheduleKey);
			return true;
		}catch (Exception e) {
			return false;
		}finally {
			JedisFactory.returnJedis(jedis);
		}
	}
	
}
