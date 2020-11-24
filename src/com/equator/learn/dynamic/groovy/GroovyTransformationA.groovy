package com.equator.learn.dynamic.groovy

import com.equator.learn.dynamic.base.GsonUtils
import com.equator.learn.dynamic.base.LogData

/**
 * @author libinkai* @date 2020/11/25 12:28 上午
 */
class GroovyTransformationA {
    def transform() {
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
}
