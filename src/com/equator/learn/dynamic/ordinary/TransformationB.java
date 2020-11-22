package com.equator.learn.dynamic.ordinary;

import com.equator.learn.dynamic.base.MessageQueue;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Slf4j
public class TransformationB implements Runnable {
    private Jedis jedis;

    public TransformationB() {
        this.jedis = MessageQueue.getMQ().getClient();
    }

    private void start() {
        log.info("转换B启动，有点慢...");
        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("转换B终于启动了！");
        while (true) {
            try {
                Thread.sleep(1000 * 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            transform();
        }
    }

    private String getSourceData(String topic) {
        String data = jedis.rpop(topic);
        log.info("TransformA getSourceData --- topic: {}, data: {}", topic, data);
        return data;
    }

    private void setTargetData(String topic, String data) {
        log.info("TransformA setTargetData --- topic: {}, data: {}", topic, data);
        jedis.lpush(topic, data);
    }

    private void transform() {
        String source = "Topic2";
        log.info("转换数据源：{}", source);
        String sourceDataStr = getSourceData(source);
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(sourceDataStr)) {
            com.google.gson.JsonObject logData = com.equator.learn.dynamic.base.GsonUtils.parseString(sourceDataStr).getAsJsonObject();
            log.info("data: {}", logData);
            if ((logData.get("logTime").getAsInt() & 1) == 0) {
                logData.addProperty("value", new java.lang.Integer(logData.get("value").getAsInt() + 666));
                setTargetData("Topic4", logData.toString());
            }
            if ((logData.get("logTime").getAsInt() & 1) == 1) {
                setTargetData("Topic3", logData.toString());
            }
        }
    }

    public void testSetGet() {
        com.equator.learn.dynamic.base.LogData logData = new com.equator.learn.dynamic.base.LogData("Topic2", 1604884788672L, "something", 666);
        log.info("time {}", logData.getLogTime());
        log.info("mock {}", logData);
    }

    public void test() {
        String source = "Topic2";
        log.info("转换数据源：{}", source);
        String sourceDataStr = getSourceData(source);
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(sourceDataStr)) {
            com.equator.learn.dynamic.base.LogData logData = com.equator.learn.dynamic.base.GsonUtils.fromJson(sourceDataStr, com.equator.learn.dynamic.base.LogData.class);
            logData.setData(new java.lang.String("666"));
            System.out.println(logData);
        }
    }
    // setTargetData("Topic4", com.equator.learn.dynamic.base.GsonUtils.toJson(logData));

    @Override
    public void run() {
        start();
    }

    public static void main(String[] args) {
        new Thread(new TransformationB()).start();
    }
}
