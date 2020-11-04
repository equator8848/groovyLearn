package com.equator.learn

/**
 * @author libinkai* @date 2020/10/31 10:15 下午
 */
class VariableTest {
    static void main(String[] args) {
        println 'hello groovy'
        // Groovy 是动态类型语言
        def x = 22
        println(x)
        // 同时，Groovy也是一门弱类型语言
        x = 'equator'
        println(x)
    }
}
