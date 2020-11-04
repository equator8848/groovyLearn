package com.equator.learn;

/**
 * @author libinkai
 * @date 2020/11/4 9:04 上午
 */
public class JClass {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void intro() {
        System.out.println("I'm " + name);
    }
}
