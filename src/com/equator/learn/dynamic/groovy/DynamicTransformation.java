package com.equator.learn.dynamic.groovy;

import com.equator.learn.dynamic.base.GsonUtils;
import com.equator.learn.dynamic.base.LogData;
import com.equator.learn.dynamic.base.MessageQueue;
import com.equator.learn.dynamic.ordinary.TransformationA;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author libinkai
 * @date 2020/11/25 12:33 上午
 */
@Slf4j
public class DynamicTransformation implements Runnable{


    private Jedis jedis;

    public DynamicTransformation() {
        this.jedis = MessageQueue.getMQ().getClient();
    }

    private void start() {
        log.info("Dynamic转换启动，有点慢...");
        try {
            Thread.sleep(1000 * 15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Dynamic转换终于启动了！");
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            transform();
        }
    }

    private String getSourceData(String topic) {
        String data = jedis.rpop(topic);
        log.info("Dynamic转换 getSourceData --- topic: {}, data: {}", topic, data);
        return data;
    }

    private void setTargetData(String topic, String data) {
        log.info("Dynamic转换 setTargetData --- topic: {}, data: {}", topic, data);
        jedis.lpush(topic, data);
    }

    public void transform() {
        String source = "Topic1";
        String sourceDataStr = getSourceData(source);
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(sourceDataStr)) {
            LogData logData = GsonUtils.fromJson(sourceDataStr, LogData.class);
            if ((logData.getLogTime() & 1) == 0) {
                setTargetData("Topic3", sourceDataStr);
            }
            if ((logData.getLogTime() & 1) == 1) {
                setTargetData("Topic4", sourceDataStr);
            }
        }
    }

    @Override
    public void run() {
        start();
    }

    public static void main(String[] args) {
        new Thread(new TransformationA()).start();
    }
}
