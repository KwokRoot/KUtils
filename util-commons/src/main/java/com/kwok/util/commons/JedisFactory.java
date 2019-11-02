package com.kwok.util.commons;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 通过 JedisPool 获取 Jedis。
 * 注：使用后调用 returnJedis(jedis) 方法手动关闭 jedis，否则超过最大连接数(maxTotal)会抛异常。
 * @author Kwok
 */
public class JedisFactory {

	private final static Logger logger = LoggerFactory.getLogger(JedisFactory.class);
	
	private static JedisPool jedisPool;

	static{
		init();
	}
	
	private static void init() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(JedisConfig.MAX_TOTAL);
		poolConfig.setMaxIdle(JedisConfig.MAX_IDLE);
		poolConfig.setMaxWaitMillis(JedisConfig.MAX_WAIT);
		poolConfig.setTestOnBorrow(JedisConfig.TEST_ON_BORROW);
		jedisPool = new JedisPool(poolConfig, JedisConfig.HOST, JedisConfig.PORT, JedisConfig.TIMEOUT, JedisConfig.AUTH, JedisConfig.DB_INDEX);
		logger.info("初始化Redis连接池 {}", jedisPool);
	}

	public static Jedis getJedis() {
		return jedisPool.getResource();
	}

	public static Jedis getJedis(int dbIndex) {
		Jedis jedis = getJedis();
		jedis.select(dbIndex);
		return jedis;
	}

	public static void returnJedis(final Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}
	
	
	/**
	 * Jedis 配置信息
	 * @author Kwok
	 */
	public static class JedisConfig {

		private final static Logger logger = LoggerFactory.getLogger(JedisConfig.class);
		
		/**
		 * Redis服务器地址
		 */
		public static String HOST = "localhost";
		/**
		 * Redis的端口号
		 */
		public static int PORT = 6379;
		/**
		 * 访问密码
		 */
		public static String AUTH = null;
		/**
		 * 连接Redis服务器超时时间
		 */
		public static int TIMEOUT = 5000;
		/**
		 * 数据库编号
		 */
		public static int DB_INDEX = 0;
		/**
		 * 控制一个pool最多有多少个jedis实例，默认值是8。
		 */
		public static int MAX_TOTAL = 8;
		/**
		 * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
		 */
		public static int MAX_IDLE = 8;
		/**
		 * 从 pool 中获取可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException。
		 */
		public static long MAX_WAIT = -1L;
		/**
		 * 在borrow(获取)一个jedis实例时，是否提前进行validate操作。如果为true，则得到的jedis实例均是可用的。
		 */
		public static boolean TEST_ON_BORROW = true;

		static {
			init();
		}
		
		public static void init(){
			
			try {
				Properties props = new Properties();
				InputStream is = JedisConfig.class.getClassLoader().getResourceAsStream("redis.properties");
				if(is != null) {
					props.load(is);
					is.close();
					HOST = props.getProperty("redis.host", HOST);
					PORT = Integer.parseInt(props.getProperty("redis.port", Integer.valueOf(PORT).toString()));
					if(!"".equals(props.getProperty("redis.auth"))){
						AUTH = props.getProperty("redis.auth", null);
					}
					TIMEOUT = Integer.parseInt(props.getProperty("redis.timeout", Integer.valueOf(TIMEOUT).toString()));
					DB_INDEX = Integer.parseInt(props.getProperty("redis.db", Integer.valueOf(DB_INDEX).toString()));
					MAX_TOTAL = Integer.parseInt(props.getProperty("redis.pool.maxTotal", Integer.valueOf(MAX_TOTAL).toString()));
					MAX_IDLE = Integer.parseInt(props.getProperty("redis.pool.maxIdle", Integer.valueOf(MAX_IDLE).toString()));
					MAX_WAIT = Long.parseLong(props.getProperty("redis.pool.maxWait", Long.valueOf(MAX_WAIT).toString()));
					TEST_ON_BORROW = Boolean.parseBoolean(props.getProperty("redis.pool.testOnBorrow", Boolean.valueOf(TEST_ON_BORROW).toString()));
				}else{
					logger.info("classpath下未发现'redis.properties'配置文件，加载默认配置...");
				}
				
				logger.info(
						"加载redis配置信息：[HOST={}, PORT={}, AUTH={}, DB_INDEX={}, MAX_TOTAL={}, MAX_IDLE={}, MAX_WAIT={}, TEST_ON_BORROW={}]",
						new Object[] { HOST, PORT, AUTH, DB_INDEX, MAX_TOTAL, MAX_IDLE, MAX_WAIT, TEST_ON_BORROW });
			} catch (IOException e) {
				logger.error("加载redis配置文件失败 {}", e.getMessage(), e);
			}
		}
		
	}
	
	
	public static void main(String[] args) {
		
		Jedis jedis = JedisFactory.getJedis();
		jedis.set("key", "hello redis!");
		System.out.println(jedis.get("key"));
		JedisFactory.returnJedis(jedis);
		
	}
	
}
