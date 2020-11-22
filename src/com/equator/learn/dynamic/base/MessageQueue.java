package com.equator.learn.dynamic.base;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 利用redis模拟消息队列
 */
public class MessageQueue {
    private final JedisPool pool;

    private MessageQueue() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        this.pool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379);
    }

    public Jedis getClient() {
        return pool.getResource();
    }

    public static MessageQueue getMQ() {
        return MQEnum.MQ.getInstance();
    }

    private enum MQEnum {
        // 消息队列
        MQ;
        private final MessageQueue mq;

        MQEnum() {
            mq = new MessageQueue();
        }

        public MessageQueue getInstance() {
            return mq;
        }
    }
}
