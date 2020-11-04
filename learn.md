# 概述
## 安装
- brew install groovy
- groovy -v
# 数据类型
## 范围
- 定义一个数组
```
def range = 5..10;
println(range); 
println(range.get(2)); 
```
# 方法
## 方法定义
- Groovy 中的方法是使用返回类型或使用 def 关键字定义的。
- 可以添加修饰符，如 public，private 和 protected。默认情况下，如果未提供可见性修饰符，则该方法为 public。
## 方法参数
- 方法可以接收任意数量的参数。定义参数时，不必显式定义类型
## 默认参数
- 如果没有值传递给参数的方法，则使用缺省值。 如果使用非默认和默认参数，则必须注意，默认参数应在参数列表的末尾定义。
```
def someMethod(parameter1, parameter2 = 0, parameter3 = 0) { 
   // Method code goes here 
} 
```
# IO
