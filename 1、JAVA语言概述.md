####如何编写并运行第一个java程序
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
   注意点：
   - Java源文件以“java”为扩展名。源文件的基本组成部分是类（class），如本类中的HelloWorld类。
   一个源文件中最多只能有一个public类。其它类的个数不限，如果源文件包含一个public类，则文件名必须按该类名命名。
   - Java应用程序的执行入口是main()方法。它有固定的书写格式：public static void main(String[] args)  {...}
   - Java语言严格区分大小写。
   - Java方法由一条条语句构成，每个语句以“;”结束。
   - 大括号都是成对出现的，缺一不可。
2. 编译： 在源文件所在的目录下，执行javac.exe 源文件名.java;生成诸多个.class结尾的字节码文件
3. 运行：生成的字节码文件通过java.exe解释执行
####注释
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