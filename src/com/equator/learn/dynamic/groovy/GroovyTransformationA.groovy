package com.equator.learn.dynamic.groovy

/**
 * @author libinkai* @date 2020/11/25 12:28 上午
 */
class GroovyTransformationA {
    private redis.clients.jedis.Jedis jedis;

    GroovyTransformationA() {
        this.jedis = com.equator.learn.dynamic.base.MessageQueue.getMQ().getClient();
    }

    private String getSourceData(String topic) {
        String data = jedis.rpop(topic);
        printf "get data %s from %s %n", data, topic
        return data;
    }

    private void setTargetData(String topic, String data) {
        printf "set data %s to %s %n", data, topic
        jedis.lpush(topic, data);
    }

    def transform() {
        String source = "Topic1";
        String sourceDataStr = getSourceData(source);
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(sourceDataStr)) {
            com.equator.learn.dynamic.base.LogData logData = com.equator.learn.dynamic.base.GsonUtils.fromJson(sourceDataStr, com.equator.learn.dynamic.base.LogData.class);
            if ((logData.getLogTime() & 1) == 0) {
                setTargetData("Topic3", sourceDataStr);
            }
            if ((logData.getLogTime() & 1) == 1) {
                setTargetData("Topic4", sourceDataStr);
            }
        }
    }
}
