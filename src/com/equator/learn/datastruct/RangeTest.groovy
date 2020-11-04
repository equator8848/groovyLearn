package com.equator.learn.datastruct

class RangeTest {
    static void test1() {
        def r1 = 1..10;
        println r1[1];
        def r2 = 1..<10;
        println r2[1];
    }

    static test2() {
        return 3;
    }

    static void main(String[] args) {
        test1();
    }
}
