package com.equator.learn

/**
 * @author libinkai* @date 2020/11/2 9:07 下午
 */
def addList(Closure closure) {
    list = []
    closure.delegate = list
    closure()
    list
}

list = addList(){
    add(1)
    add(2)
    add(3)
    size()
}

println list
