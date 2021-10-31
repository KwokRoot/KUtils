package com.kwok.util.commons;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description: Redis多数据源
 * @date: 2020年5月25日
 * @author Kwok
 */
public class JedisFactoryPlus {

	private final static Logger logger = LoggerFactory.getLogger(JedisFactoryPlus.class);
	
	private static Map<Integer, JedisPool> num_jedisPool_map = new ConcurrentHashMap<Integer, JedisPool>();
	private static Map<String, Integer> name_num_map = new HashMap<String, Integer>();
	
	static {
		init();
	}

	private static void init() {
		if (num_jedisPool_map.isEmpty() || name_num_map.isEmpty()) {
			try {
				Map<Integer, JedisConfigPlus> num_config_map = JedisConfigPlus.num_config_map;
				if(num_config_map.isEmpty()){
					JedisConfigPlus.init();
				}
				for (Entry<Integer, JedisConfigPlus> entity : num_config_map.entrySet()) {
					Integer num = entity.getKey();
					JedisConfigPlus jedisConfigPlus = entity.getValue();
					JedisPoolConfig poolConfig = new JedisPoolConfig();
					poolConfig.setMaxTotal(jedisConfigPlus.MAX_TOTAL);
					poolConfig.setMaxIdle(jedisConfigPlus.MAX_IDLE);
					poolConfig.setMaxWaitMillis(jedisConfigPlus.MAX_WAIT);
					poolConfig.setTestOnBorrow(jedisConfigPlus.TEST_ON_BORROW);
					JedisPool jedisPool = new JedisPool(poolConfig, jedisConfigPlus.HOST, jedisConfigPlus.PORT,
							jedisConfigPlus.TIMEOUT, jedisConfigPlus.AUTH, jedisConfigPlus.DB_INDEX);

					name_num_map.put(jedisConfigPlus.NAME, num);
					num_jedisPool_map.put(num, jedisPool);

					logger.info("初始化Redis连接池：{}", jedisConfigPlus.NAME);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * @methods: getJedis
	 * @param name : Redis 实例名
	 */
	public static Jedis getJedis(String name) {
		init();
		if(num_jedisPool_map.get(name_num_map.get(name)) != null){
			return	num_jedisPool_map.get(name_num_map.get(name)).getResource();
		}
		return null;
	}
	
	/**
	 * @methods: getJedis
	 * @param num : Redis 实例编号
	 */
	public static Jedis getJedis(Integer num) {
		init();
		if(num_jedisPool_map.get(num) != null){
			return	num_jedisPool_map.get(num).getResource();
		}
		return null;
	}

	/**
	 * @methods: getJedis
	 * @param name : Redis 实例名
	 * @param dbIndex : 数据库编号
	 */
	public static Jedis getJedis(String name, int dbIndex) {
		init();
		Jedis jedis = getJedis(name);
		if (jedis != null) {
			jedis.select(dbIndex);
		}
		return jedis;
	}
	
	/**
	 * @methods: getJedis
	 * @param num : Redis 实例编号
	 * @param dbIndex : 数据库编号
	 */
	public static Jedis getJedis(Integer num, int dbIndex) {
		init();
		Jedis jedis = getJedis(num);
		if (jedis != null) {
			jedis.select(dbIndex);
		}
		return jedis;
	}
	
	
	public static void returnJedis(final Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}
	
	public static void closeJedisPool() {
		if(!num_jedisPool_map.isEmpty()){
			for (Entry<Integer, JedisPool> entry : num_jedisPool_map.entrySet()) {
				entry.getValue().close();
				num_jedisPool_map.remove(entry.getKey());
			}
		}
	}
	
	
	/**
	 * Jedis 配置信息
	 * @author Kwok
	 */
	public static class JedisConfigPlus {
		
		public static Map<Integer, JedisConfigPlus> num_config_map = new HashMap<Integer, JedisConfigPlus>();
		
		/**
		 * 实例标识
		 */
		public String NAME = "";
		
		/**
		 * Redis服务器地址
		 */
		public String HOST = "localhost";
		/**
		 * Redis的端口号
		 */
		public Integer PORT = 6379;
		/**
		 * 访问密码
		 */
		public String AUTH = null;
		/**
		 * 连接Redis服务器超时时间
		 */
		public Integer TIMEOUT = 5000;
		/**
		 * 数据库编号
		 */
		public Integer DB_INDEX = 0;
		/**
		 * 控制一个pool最多有多少个jedis实例，默认值是8。
		 */
		public Integer MAX_TOTAL = 8;
		/**
		 * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
		 */
		public Integer MAX_IDLE = 8;
		/**
		 * 从 pool
		 * 中获取可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException。
		 */
		public Long MAX_WAIT = -1L;
		/**
		 * 在borrow(获取)一个jedis实例时，是否提前进行validate操作。如果为true，则得到的jedis实例均是可用的。
		 */
		public Boolean TEST_ON_BORROW = true;
		
		@Override
		public String toString() {
			return "JedisConfigPlus [NAME=" + NAME + ", HOST=" + HOST + ", PORT=" + PORT + ", AUTH=" + AUTH
					+ ", TIMEOUT=" + TIMEOUT + ", DB_INDEX=" + DB_INDEX + ", MAX_TOTAL=" + MAX_TOTAL + ", MAX_IDLE="
					+ MAX_IDLE + ", MAX_WAIT=" + MAX_WAIT + ", TEST_ON_BORROW=" + TEST_ON_BORROW + "]";
		}
		
		static {
			init();
		}
		
		public static void init(){
			
			Properties props = new Properties();
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("redis-plus.properties");
			try{
				if (is != null) {
					props.load(is);
					is.close();
					
					Set<Integer> numSet = new HashSet<Integer>();
					for (Entry<Object, Object> entry : props.entrySet()) {
						String key = entry.getKey().toString().trim();
						if(key.endsWith(".redis.name")){
							numSet.add(Integer.valueOf(key.substring(0, key.indexOf(".redis.name"))));
						}
					}
					
					for (Integer num : numSet) {
						
						JedisConfigPlus jedisConfigPlus = new JedisConfigPlus();
						
						jedisConfigPlus.NAME = props.getProperty(num + ".redis.name", jedisConfigPlus.NAME);
						jedisConfigPlus.HOST = props.getProperty(num + ".redis.host", jedisConfigPlus.HOST);
						jedisConfigPlus.PORT = Integer.valueOf(props.getProperty(num + ".redis.port", jedisConfigPlus.PORT.toString()));
						if (!"".equals(props.getProperty(num + ".redis.auth"))) {
							jedisConfigPlus.AUTH = props.getProperty(num + ".redis.auth");
						}
						jedisConfigPlus.TIMEOUT = Integer.valueOf(props.getProperty(num + ".redis.timeout", jedisConfigPlus.TIMEOUT.toString()));
						jedisConfigPlus.DB_INDEX = Integer.valueOf(props.getProperty(num + ".redis.db", jedisConfigPlus.DB_INDEX.toString()));
						
						jedisConfigPlus.MAX_TOTAL = Integer.valueOf(props.getProperty(num + ".redis.pool.maxTotal", jedisConfigPlus.MAX_TOTAL.toString()));
						jedisConfigPlus.MAX_IDLE = Integer.valueOf(props.getProperty(num + ".redis.pool.maxIdle", jedisConfigPlus.MAX_IDLE.toString()));
						jedisConfigPlus.MAX_WAIT = Long.valueOf(props.getProperty(num + ".redis.pool.maxWait", jedisConfigPlus.MAX_WAIT.toString()));
						jedisConfigPlus.TEST_ON_BORROW = Boolean.valueOf(props.getProperty(num + ".redis.pool.testOnBorrow", jedisConfigPlus.TEST_ON_BORROW.toString()));
						num_config_map.put(num, jedisConfigPlus);

						logger.info("加载 Redis实例 {}，配置信息 {}", jedisConfigPlus.NAME, jedisConfigPlus);
					}
					
				}else{
					logger.info("classpath 下未发现'redis-plus.properties'配置文件，加载默认配置...");
				}
			}catch (Exception e) {
				logger.error("加载redis配置文件信息失败 {}", e.getMessage(), e);
			}
			
		}
	}

	public static void main(String[] args) {

		Jedis jedis = JedisFactoryPlus.getJedis(1);
		jedis.set("key666", "hello redis!");
		System.out.println(jedis.get("key666"));
		JedisFactoryPlus.returnJedis(jedis);
		
		
		Jedis jedis2 = JedisFactoryPlus.getJedis(2, 8);
		jedis2.set("key888", "hello redis!");
		System.out.println(jedis.get("key888"));
		JedisFactoryPlus.returnJedis(jedis2);
	
		JedisFactoryPlus.closeJedisPool();
	}
	
}
