package com.equator.learn.dynamic.base;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

@Slf4j
public class Consumer implements Runnable {
    private final int MAX_TRY = 5;
    private String topic;
    private Jedis jedis;

    public Consumer(String topic) {
        this.topic = topic;
        jedis = MessageQueue.getMQ().getClient();
    }

    private boolean consume() {
        String logStr = jedis.rpop(topic);
        if (StringUtils.isEmpty(logStr)) {
            return false;
        }
        log.info("消费数据 {}", logStr);
        return true;
    }

    @Override
    public void run() {
        log.info("{} topic 的消费者启动...", topic);
        while (true) {
            if (!consume()) {
                boolean hungry = true;
                for (int retryTimes = 1; retryTimes < MAX_TRY; retryTimes++) {
                    if (consume()) {
                        hungry = false;
                        break;
                    }
                    try {
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (hungry) {
                    log.error("饿饿QAQ");
                    break;
                }
            }
        }
        log.info("{} topic 的消费者停止...", topic);
    }

    public static void main(String[] args) {
        // 下游消费数据
        new Thread(new Consumer("Topic3")).start();
    }
}
