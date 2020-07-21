package day10_redis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTest {

	@Test
	public void testJedis(){
		//设置ip地址和端口号,redis的端口号默认是6379
				Jedis jedis = new Jedis("127.0.0.1", 6379);
				//设置数据
				jedis.set("name", "张悦");
				//获取数据
				String name = jedis.get("name");
				System.out.println(name);
				//释放资源
				jedis.close();
	}
	@Test
	public void testJedisPool(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(10);
		jedisPoolConfig.setMaxIdle(5);
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379);
		Jedis jedis = jedisPool.getResource();
		jedis.set("name", "星河");
		
		jedis.close();
		jedisPool.close();
	}
}
