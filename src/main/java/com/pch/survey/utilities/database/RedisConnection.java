package com.pch.survey.utilities.database;

import com.pch.survey.utilities.ConfigurationReader;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
 
public class RedisConnection {
    private static JedisPool jedisPool;

    private static String host = ConfigurationReader.getInstance().getOffersHost();
	private static int port    = Integer.parseInt(ConfigurationReader.getInstance().getOffersPort());
	private static String pw   = ConfigurationReader.getInstance().getOffersPW();
  
   
    protected static JedisPool getJedisPool() {

        if (null == jedisPool) {
            createJedisPool();

        }
        return jedisPool;

    }

    /**
     * Create a new pool of Jedis connections.
     */
    private static void createJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        int timeout = Protocol.DEFAULT_TIMEOUT;
        jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, pw);
    }
}

 
