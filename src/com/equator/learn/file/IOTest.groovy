package com.equator.learn.file

class IOTest {
    static void main(String[] args) {
        new File("").eachFileRecurse() {
            file -> println file.getAbsolutePath()
        }
    }
}
