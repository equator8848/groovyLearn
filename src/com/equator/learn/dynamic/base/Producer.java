package com.equator.learn.dynamic.base;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Slf4j
public class Producer implements Runnable {
    private String topic;
    private Jedis jedis;

    public Producer(String topic) {
        this.topic = topic;
        jedis = MessageQueue.getMQ().getClient();
    }

    private void produce() {
        LogData logData = new LogData();
        logData.setSource(topic);
        logData.setLogTime(System.currentTimeMillis());
        logData.setData(String.format("一些数据 %d", System.currentTimeMillis()));
        logData.setValue(0);
        Gson gson = new Gson();
        String logStr = gson.toJson(logData);
        jedis.lpush(topic, logStr);
        log.info("生产数据 {}", logStr);
    }

    @Override
    public void run() {
        log.info("{} topic 的生产者启动...", topic);
        while (true) {
            produce();
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Producer("Topic1")).start();
        new Thread(new Producer("Topic2")).start();
    }
}
