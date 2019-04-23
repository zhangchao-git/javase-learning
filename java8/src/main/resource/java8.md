# Java8������

### �ص�
- �ٶȸ���
- ������٣��������µ��﷨Lambda ���ʽ��
- ǿ���Stream API
- ���ڲ���
- ��󻯼��ٿ�ָ���쳣Optional

## Lambda ���ʽ
- Lambda ��һ���������������ǿ��԰�Lambda ���ʽ���Ϊ��һ�ο��Դ��ݵĴ��루������������һ�����д��ݣ�
```java
//�����ڲ���
TreeSet<String> ts2 = new TreeSet<>(new Comparator<String>(){
    @Override
    public int compare(String o1, String o2) {
        return Integer.compare(o1.length(), o2.length());
    }
});
//Lambda���ʽ
Comparator<String> com = (x, y) -> Integer.compare(x.length(), y.length());

TreeSet<String> ts = new TreeSet<>(com);
```
- Java8��������һ���µĲ����� "->" �ò�������Ϊ��ͷ�������� Lambda ����������ͷ�������� Lambda ���ʽ��ֳ������֣� 
��ָࣺ����Lambda ���ʽ��Ҫ�����в��� 
�Ҳָࣺ����Lambda �壬��Lambda ���ʽҪִ�еĹ��ܡ�
```java
/**
* ������������һ����ʡ
* ����������ƶ�����ʡ
* ��������ʡ��ʡ
*/
//�﷨��ʽһ���޲������޷���ֵ
() -> System.out.println("Hello Lambda!");

//�﷨��ʽ������һ�������������޷���ֵ
(x) -> System.out.println(x)

//�﷨��ʽ������ֻ��һ��������С���ſ���ʡ�Բ�д
x -> System.out.println(x)

//�﷨��ʽ�ģ����������ϵĲ������з���ֵ������ Lambda �����ж������
Comparator<Integer> com = (x, y) -> {
    System.out.println("����ʽ�ӿ�");
    return Integer.compare(x, y);
};

//�﷨��ʽ�壺�� Lambda ����ֻ��һ����䣬 return �� �����Ŷ�����ʡ�Բ�д
Comparator<Integer> com = (x, y) -> Integer.compare(x, y);

//�﷨��ʽ����Lambda ���ʽ�Ĳ����б���������Ϳ���ʡ�Բ�д����ΪJVM������ͨ���������ƶϳ����������ͣ����������ƶϡ�
(Integer x, Integer y) -> Integer.compare(x, y);
```

## ����ʽ�ӿ�
- Lambda ���ʽ��Ҫ������ʽ�ӿڡ���֧�� 
  ����ʽ�ӿڣ��ӿ���ֻ��һ�����󷽷��Ľӿڣ���Ϊ����ʽ�ӿڡ� ����ʹ��ע�� @FunctionalInterface ���� 
  ���Լ���Ƿ��Ǻ���ʽ�ӿ�

## ���������빹��������
### �������ã��� Lambda ���еĹ��ܣ��Ѿ��з����ṩ��ʵ�֣�����ʹ�÷�������  
    �����Խ������������Ϊ Lambda ���ʽ������һ�ֱ�����ʽ��

- ��������� :: ʵ��������
```java
PrintStream ps = System.out;
Consumer<String> con = (str) -> ps.println(str);
con.accept("Hello World��");

Consumer<String> con2 = ps::println;
con2.accept("Hello Java8��");
```

- ���� :: ��̬������
```java
Comparator<Integer> com = (x, y) -> Integer.compare(x, y);

Comparator<Integer> com2 = Integer::compare;
```
- ���� :: ʵ��������
```java
Function<Employee, String> fun = (e) -> e.show();
System.out.println(fun.apply(new Employee()));

Function<Employee, String> fun2 = Employee::show;
System.out.println(fun2.apply(new Employee()));
```
  ע�⣺  
  	 �ٷ������������õķ����Ĳ����б��뷵��ֵ���ͣ���Ҫ�뺯��ʽ�ӿ��г��󷽷��Ĳ����б�ͷ���ֵ���ͱ���һ�£�  
  	 ����Lambda �Ĳ����б�ĵ�һ����������ʵ�������ĵ����ߣ��ڶ�������(���޲�)��ʵ�������Ĳ���ʱ����ʽ�� ClassName::MethodName

### ���������� :�������Ĳ����б���Ҫ�뺯��ʽ�ӿ��в����б���һ�£�
- ���� :: new
```java
Supplier<Employee> sup = () -> new Employee();
System.out.println(sup.get());

Supplier<Employee> sup2 = Employee::new;
System.out.println(sup2.get());
```
### ��������
- ����[] :: new;
```java
Function<Integer, String[]> fun = (args) -> new String[args];
String[] strs = fun.apply(10);
System.out.println(strs.length);

Function<Integer, Employee[]> fun2 = Employee[] :: new;
Employee[] emps = fun2.apply(20);
System.out.println(emps.length);
```
## Stream API
- Stream API �ṩ��һ�ָ�Ч������ʹ�õĴ��������ݵķ�ʽ,ʹ��Stream API �Լ������ݽ��в�������������ʹ��SQL ִ�е����ݿ��ѯ.
- ���Ͻ��������ݣ��������Ǽ���
    - Stream �Լ�����洢Ԫ�ء�
    - Stream ����ı�Դ�����෴�����ǻ᷵��һ�����н������Stream��
    - Stream �������ӳ�ִ�еġ�����ζ�����ǻ�ȵ���Ҫ�����ʱ���ִ�С�
- Stream �Ĳ�������
    - ���� Stream

    - �м����
        - ����м�����������������γ�һ����ˮ�ߣ�������ˮ���ϴ�����ֹ�����������м��������ִ���κεĴ���������ֹ����ʱһ����ȫ��������Ϊ��������ֵ��

        - ɸѡ����Ƭ  

           ����|����  
           --|:--  
           filter(Predicatep) | ����Lambda ���������ų�ĳЩԪ�ء�  
           distinct() | ɸѡ��ͨ����������Ԫ�ص�hashCode() ��equals() ȥ���ظ�Ԫ��  
           limit(long maxSize) | �ض�����ʹ��Ԫ�ز���������������  
           skip(long n) | ����Ԫ�أ�����һ���ӵ���ǰn ��Ԫ�ص�����������Ԫ�ز���n �����򷵻�һ����������limit(n) ����    

        - ӳ��

           ����|����  
           --|:--  
           map(Functionf) | ����һ��������Ϊ�������ú����ᱻӦ�õ�ÿ��Ԫ���ϣ�������ӳ���һ���µ�Ԫ�ء�
           mapToDouble(ToDoubleFunction f) | ����һ��������Ϊ���밴��2�����ú����ᱻӦ�õ�ÿ��Ԫ���ϣ�����һ���µ�DoubleStream��
           mapToInt(ToIntFunction f) | ����һ��������Ϊ�������ú����ᱻӦ�õ�ÿ��Ԫ���ϣ�����һ���µ�IntStream��
           mapToLong(ToLongFunction f) | ����һ��������Ϊ�������ú����ᱻӦ�õ�ÿ��Ԫ���ϣ�����һ���µ�LongStream��
           flatMap(Function f) | ����һ��������Ϊ�����������е�ÿ��ֵ��������һ������Ȼ������������ӳ�һ����

        - ����

          ����|����
          --|:--
          sorted()|����һ�����������а���Ȼ˳������
          sorted(Comparatorcomp)|����һ�����������а��Ƚ���˳������

    - ��ֹ����

        - ������ƥ��

            ����|����
            --|:--
            allMatch(Predicate p) | ����Ƿ�ƥ������Ԫ��
            anyMatch(Predicate p) | ����Ƿ�����ƥ��һ��Ԫ��
            noneMatch(Predicatep) | ����Ƿ�û��ƥ������Ԫ��
            findFirst() | ���ص�һ��Ԫ���ն˲������������ˮ�����ɽ���������������κβ�������ֵ�����磺List��Integer��������void
            findAny() | ���ص�ǰ���е�����Ԫ�� 
            count() | ��������Ԫ������
            max(Comparatorc) | �����������ֵ
            min(Comparatorc) | ����������Сֵ
            forEach(Consumerc) | �ڲ�����(ʹ��Collection �ӿ���Ҫ�û�ȥ����������Ϊ�ⲿ�������෴��Stream API ʹ���ڲ���������������ѵ�������)

    - ��Լ
        ����|����
        --|:--
        reduce(T iden, BinaryOperator b) | ���Խ�����Ԫ�ط�������������õ�һ��ֵ������T
        reduce(BinaryOperator b) | ���Խ�����Ԫ�ط�������������õ�һ��ֵ������Optional<T>

    - �ռ�
      
      ����|����
      --|:--
      collect(Collector c) | ����ת��Ϊ������ʽ������һ��Collector�ӿڵ�ʵ�֣����ڸ�Stream��Ԫ�������ܵķ���

��ע��map ��reduce ������ͨ����Ϊmap-reduce ģʽ����Google ��������������������������

## �ӿ��е�Ĭ�Ϸ����뾲̬����

- Java 8������ӿ��а������о���ʵ�ֵķ������÷�����Ϊ��Ĭ�Ϸ�������Ĭ�Ϸ���ʹ��default�ؼ�����
- ��һ���ӿ��ж�����һ��Ĭ�Ϸ�����������һ�������ӿ����ֶ�����һ��ͬ���ķ���ʱ
  - ѡ�����еķ��������һ�������ṩ�˾����ʵ�֣���ô�ӿ��о�����ͬ���ƺͲ�����Ĭ�Ϸ����ᱻ���ԡ�
  - �ӿڳ�ͻ�����һ�����ӿ��ṩһ��Ĭ�Ϸ���������һ���ӿ�Ҳ�ṩ��һ��������ͬ���ƺͲ����б�ķ��������ܷ����Ƿ���Ĭ�Ϸ���������ô���븲�Ǹ÷����������ͻ

## ��ʱ������API

- LocalDate��LocalTime��LocalDateTime
- Instant ʱ���
- Duration (ʱ����)��Period(���ڼ��)

## ����������

- Optional