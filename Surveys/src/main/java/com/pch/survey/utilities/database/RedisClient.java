package com.pch.survey.utilities.database;
  
import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
	
public class RedisClient {
 

    public static String getResultAsString(String key) {
  		pauseExecution(2);
  	    try (
        	Jedis jedis = RedisConnection.getJedisPool().getResource()) {
        	return jedis.get(key);
        }
    }

 
    public static Map<String,String> getResultAsMap(String key) {
    	 pauseExecution(2);
    	  try (Jedis jedis = RedisConnection.getJedisPool().getResource()) {
    	    return jedis.hgetAll(key);
    	  }
    	}
    
    
    public static void insertData(String key, Map<String,String> data) {
      	try (Jedis jedis = RedisConnection.getJedisPool().getResource()) {
      	System.out.println("Redis Key: " + key);	
   	     jedis.hset(key,data);
   	  }
   	}
   
    
    
    
    private static void pauseExecution(int secs) {
		try {
			Thread.sleep(secs*1000);
		} catch (InterruptedException e1) {
				e1.printStackTrace();
		}
	}
	
	
    
    
}