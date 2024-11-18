package com.pch.survey.testingexamples;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class RedisKeyTester {

	
    private static String host = "redis-16397.redis01.stg.pch.com";
	private static int port=16397;
	private static String pw = "offers-qa";
    private static JedisPool jedisPool;
   
    public static JedisPool createJedisPool() {
    	if (null == jedisPool) {
    		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    		int timeout = Protocol.DEFAULT_TIMEOUT;
    		jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, pw);
       	}
    	return jedisPool;
    }
	
    public static Map<String,String> redisMap(String key) {
  	  try (Jedis jedis = createJedisPool().getResource()) {
  	    return jedis.hgetAll(key);
  	  }
  	}
	
    public static String redisString(String key) {
    	  try (Jedis jedis = createJedisPool().getResource()) {
    	    return jedis.get(key);
    	  }
    	}
	
	
	 public static void main( String[] args )
	    {
	        Map<String,String>  questions = redisMap("s:dp:18b50bda-0a16-4ceb-b322-b97349f47e27:q");
	        System.out.println("Number of dp questions answered: = "+questions.size() +redisMap("s:dp:18b50bda-0a16-4ceb-b322-b97349f47e27:q"));
	        System.out.println(redisString("o:pfl:basic:18b50bda-0a16-4ceb-b322-b97349f47e27:s"));
	    
	    
	    }
	
	
	
}
