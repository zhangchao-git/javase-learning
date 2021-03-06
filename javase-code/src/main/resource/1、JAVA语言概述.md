#### 如何编写并运行第一个java程序
   - 编写>编译>运行
1. 编写：每一个java文件都是.java结尾的，称为源文件【HelloWorld.java】。java程序就存在于源文件中
   ```JAVA
    public class HelloWorld{ 	  
       //程序的主方法，是程序的入口
       public static void main(String args[]){
           //要执行的代码
           System.out.println("HelloWorld");
       }
    }
   ```
   - Java源文件以“java”为扩展名。源文件的基本组成部分是类（class），如本类中的HelloWorld类。
   一个源文件中最多只能有一个public类。其它类的个数不限，如果源文件包含一个public类，则文件名必须按该类名命名。
   - Java应用程序的执行入口是main()方法。它有固定的书写格式：public static void main(String[] args)  {...}
   - Java语言严格区分大小写。
   - Java方法由一条条语句构成，每个语句以“;”结束。
   - 大括号都是成对出现的，缺一不可。
2. 编译： 在源文件所在的目录下，执行javac.exe 源文件名.java;生成诸多个.class结尾的字节码文件
3. 运行：生成的字节码文件通过java.exe解释执行
#### 注释
   - 单行、多行注释 
   ```java
      // 单行注释 
      
      /*  
       * 多行注释不能够嵌套
       */
   ```  
   - 文档注释 
   ```
      /** 
        * javadoc  -d 文件目录名 -author -version 源文件名.java;
        */        
   ```
#### 关键字&标识符
   - 关键字：被Java语言赋予了特殊含义，用做专门用途的字符串（单词）
   - 标识符：凡是自己可以起名字的地方都叫标识符
#### 命名的规则（一定要遵守，不遵守就会报编译的错误）
   - 由26个英文字母大小写，0-9 ，_或 $ 组成  
   - 数字不可以开头
   - 不可以使用关键字和保留字，但能包含关键字和保留字
   - Java中严格区分大小写，长度无限制
   - 标识符不能包含空格
#### Java中的名称命名规范：（不遵守，也不会出现编译的错误）
   - 包名：多单词组成时所有字母都小写：xxxyyyzzz
   - 类名、接口名：多单词组成时，所有单词的首字母大写：XxxYyyZzz
   - 变量名、方法名：多单词组成时，第一个单词首字母小写，第二个单词开始每个单词首字母大写：xxxYyyZzz
   - 常量名：所有字母都大写。多单词时每个单词用下划线连接：XXX_YYY_ZZZ
