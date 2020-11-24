package com.equator.learn.dynamic.groovy
/**
 * @author libinkai* @date 2020/11/25 12:28 上午
 */
class GroovyTransformationB {
    def transform() {
        String source = "Topic2";
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
}
