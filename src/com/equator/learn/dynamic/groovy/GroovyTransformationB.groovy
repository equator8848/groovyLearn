package com.equator.learn.dynamic.groovy

/**
 * @author libinkai* @date 2020/11/25 12:28 上午
 */
class GroovyTransformationB {
    private redis.clients.jedis.Jedis jedis;

    GroovyTransformationB() {
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
        String source = "Topic2";
        String sourceDataStr = getSourceData(source);
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(sourceDataStr)) {
            com.google.gson.JsonObject logData = com.equator.learn.dynamic.base.GsonUtils.parseString(sourceDataStr).getAsJsonObject();
            if ((logData.get("logTime").getAsInt() & 1) == 0) {
                logData.addProperty("value", new java.lang.Integer(logData.get("value").getAsInt() + 666));
                setTargetData("Topic4", logData.toString());
            }
            if ((logData.get("logTime").getAsInt() & 1) == 1) {
                setTargetData("Topic3", logData.toString());
            }
        }
    }
}
