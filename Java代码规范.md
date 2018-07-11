> **文档密级:** 公司内部A

# 芜湖技术中心Java代码规范

  

  

  

| 版本  | 文档时间   | 变更说明 | 变更人员 |
| ----- | ---------- | -------- | -------- |
| 1.0.0 | 2018-06-05 | 初稿     | 徐韦男   |
|       |            |          |          |
|       |            |          |          |



- - -

[TOC]

- - -

  

  

## 说明
> <font color=red>__强制__</font> ---> __*最高优先级的准则*__，任何情况下都不应违反，一般是最基础的规范，亦或是违反后会产生严重隐患
>
> <font color=orange>__推荐__</font> ---> __*必须遵循的普适性准则*__，违反可能会导致性能问题或其他非阻断性问题。代码检查将对违反该类准则的代码产生警告。此类原则应该普遍遵循，除非与准则有冲突的特别需求
>
> <font color=blue>__建议__</font> ---> __*一般场景下的最佳实践*__，非特殊场景，应当遵循的准则
>
> <font color=green>__参考__</font> ---> __*一般场景下的参考项*__，适当的情况下结合实际选择性遵循

- - -

## 代码格式
1. 【<font color=red>__强制__</font>】__大括号的使用约定__
   > 如果是大括号内为空，则简洁地写成{}即可，不需要换行； 如果 是非空代码块则：
   >
   > - 左大括号前不换行。
   > - 左大括号后换行。
   > - 右大括号前换行。
   > - 右大括号后还有 else 等代码则不换行； 表示终止的右大括号后必须换行。

2. 【<font color=red>__强制__</font>】 __左小括号和字符之间不出现空格； 同样，右小括号和字符之间也不出现空格__
   > 详见 第 5 条下方正例提示。 反例： if (空格 a == b 空格) 

3. 【<font color=red>__强制__</font>】 __if/for/while/switch/do 等保留字与括号之间都必须加空格__

4. 【<font color=red>__强制__</font>】__任何二目、 三目运算符的左右两边都需要加一个空格__
   > 说明： 运算符包括赋值运算符=、逻辑运算符&&、加减乘除符号等。

5. 【<font color=red>__强制__</font>】 __采用 4 个空格缩进，禁止使用 tab 字符__
   > 说明： 如果使用 tab 缩进，必须设置 1 个 tab 为 4 个空格。 IDEA 设置 tab 为 4 个空格时， 请勿勾选 Use tab character；而在 eclipse 中，必须勾选 insert spaces for tabs。
   >
   > <font color=green>正例</font>：（涉及 1-5 点）
   >
   > ```java
   > public static void main(String[] args) {
   >     // 缩进 4 个空格
   >     String say = "hello";
   >     // 运算符的左右必须有一个空格
   >     int flag = 0;
   >     // 关键词 if 与括号之间必须有一个空格，括号内的 f 与左括号， 0 与右括号不需要空格
   >     if (flag == 0) {
   >         System.out.println(say);
   >     }
   >     // 左大括号前加空格且不换行；左大括号后换行
   >     if (flag == 1) {
   >         System.out.println("world");
   >         // 右大括号前换行，右大括号后有 else，不用换行
   >     } else {
   >         System.out.println("ok"); // 在右大括号后直接结束，则必须换行 }
   >     }
   > }
   > ```

6. 【<font color=red>__强制__</font>】__注释的双斜线与注释内容之间有且仅有一个空格__
   > <font color=green>正例</font>：
   >
   > ```java
   > // 这是示例注释，请注意在双斜线之后有一个空格
   > String ygb = new String(); 
   > ```

7. 【<font color=red>__强制__</font>】__单行字符数限制不超过 120 个，超出需要换行__
   > 换行时遵循如下原则：
   >
   > - 第二行相对第一行缩进 4 个空格，从第三行开始，不再继续缩进，参考示例。
   >
   > - 运算符与下文一起换行。
   >
   > - 方法调用的点符号与下文一起换行。
   >
   > - 方法调用时，多个参数， 需要换行时， 在逗号后进行。
   >
   > - 在括号前不要换行，见反例。
   >
   > <font color=green>正例</font>：
   >
   > ```java
   >  StringBuffer sb = new StringBuffer();
   > // 超过 120 个字符的情况下，换行缩进 4 个空格， 点号和方法名称一起换行
   > sb.append("zi").append("xin")...
   >     .append("huang")...
   >     .append("huang")...
   >     .append("huang");
   > ```
   > <font color=red>反例</font>
   >
   > ```java
   > StringBuffer sb = new StringBuffer();
   > // 超过 120 个字符的情况下，不要在括号前换行
   > sb.append("zi").append("xin")...append
   >     ("huang");
   > // 参数很多的方法调用可能超过 120 个字符， 不要在逗号前换行
   > method(args1, args2, args3, ...
   >     , argsX); 
   > ```
   >
   > 

8. 【<font color=red>__强制__</font>】__方法参数在定义和传入时，多个参数逗号后边必须加空格__
   > <font color=green>正例</font>： 下例中实参的"a",后边必须要有一个空格。
   >
   > `method("a", "b", "c");` 

9. 【<font color=red>__强制__</font>】 __IDE 的 text file encoding 设置为 UTF-8; IDE 中文件的换行符使用 Unix 格式， 不要使用 Windows 格式__

10. 【<font color=orange>__推荐__</font>】__没有必要增加若干空格来使某一行的字符与上一行对应位置的字符对齐__
    > ```java
    > // 正例
    > int a = 3;
    > long b = 4L;
    > float c = 5F;
    > StringBuffer sb = new StringBuffer(); 
    > 
    > // 反例
    > int          a  = 3;
    > long         b  = 4L;
    > float        c  = 5F;
    > StringBuffer sb = new StringBuffer(); 
    > ```
    >
    > 说明： 增加 sb 这个变量，如果需要对齐，则给 a、 b、 c 都要增加几个空格，在变量比较多的 情况下，是非常累赘的事情。

11. 【<font color=orange>__推荐__</font>】 __不同逻辑、不同语义、不同业务的代码之间插入一个空行分隔开来以提升可读性__
    > 说明： 没有必要插入多个空行进行隔开。    

- - -

## 注释
### 1.版权信息
```
Copyright (c) $today.year IFlyTek. All Rights Reserved.
Project Name: $project.name
File Name: $file.fileName
Date: $today.format("yyyy-MM-dd")
```
> 版权信息需要加在每个源文件的头部。

### 2.类注释
```java
/**
 * [TITLE]
 * <p>[DESCRIPTION]</p>
 * <p>Created by ${USER} on ${DATE}</p>
 *
 * @author ${USER} 
 */
```
> 所有类必须按照该格式来注释。

### 3.注释规约
1. 【<font color=red>__强制__</font>】__类、类属性、类方法的注释必须使用 Javadoc 规范，使用`/**内容*/`格式，不得使用`// xxx `方式__
   > 说明： 在 IDE 编辑窗口中， Javadoc 方式会提示相关注释，生成 Javadoc 可以正确输出相应注释； 在 IDE 中，工程调用方法时，不进入方法即可悬浮提示方法、参数、返回值的意义，提高阅读效率。

2. 【<font color=red>__强制__</font>】__所有的抽象方法（包括接口中的方法） 必须要用 Javadoc 注释、除了返回值、参数、异常说明外，还必须指出该方法做什么事情，实现什么功能__

   > 说明： 对子类的实现要求，或者调用注意事项，请一并说明。
3. 【<font color=red>__强制__</font>】__所有的类都必须添加创建者和创建日期。__

4. 【<font color=red>__强制__</font>】__方法内部单行注释，在被注释语句上方另起一行，使用//注释。方法内部多行注释使用/* */注释，注意与代码对齐__

5. 【<font color=red>__强制__</font>】__代码修改的同时，注释也要进行相应的修改，尤其是参数、返回值、异常、核心逻辑等的修改__
   >说明： 代码与注释更新不同步，就像路网与导航软件更新不同步一样，如果导航软件严重滞后，就失去了导航的意义。

6. 【<font color=orange>__推荐__</font>】__好的命名、代码结构是自解释的，注释力求精简准确、表达到位__
   > 说明：*避免出现注释的一个极端：过多过滥的注释*，代码的逻辑一旦修改，修改注释是相当大的负担。
   > 反例：
   > ```java
   > // put elephant into fridge
   > put(elephant, fridge);
   > ```
   > 方法名 put，加上两个有意义的变量名 elephant 和 fridge，已经说明了这是在干什么，__语义清晰的代码不需要额外的注释__。

- - -

## 编程规约
### 1.命名风格
1. 【<font color=red>__强制__</font>】__代码中的命名均不能以下划线或美元符号开始，也不能以下划线或美元符号结束__
    > <font color=red>反例</font>：_name / \__name /  \$name / name\_ / name\$ / name\_\_

2. 【<font color=red>__强制__</font>】__类名使用UpperCamelCase风格，但以下情形例外：DO / BO / DTO / VO / AO / PO等__
    > <font color=green>正例</font>：MarcoPolo / UserDO / XmlService / TcpUdpDeal / TaPromotion
    > 
    > <font color=red>反例</font>：macroPolo / UserDo / XMLService / TCPUDPDeal / TAPromotion

3. 【<font color=red>__强制__</font>】 __代码中的命名严禁使用拼音与英文混合的方式，更不允许直接使用中文的方式__
    > 说明： 正确的英文拼写和语法可以让阅读者易于理解，避免歧义。
    > <font color=green>正例</font>： alibaba / taobao / youku / hangzhou 等国际通用的名称， 可视同英文。
    > <font color=red>反例</font>： DaZhePromotion [打折] / getPingfenByName() [评分] / int 某变量 = 3

4. 【<font color=red>__强制__</font>】__方法名、参数名、成员变量、局部变量都统一使用lowerCamelCase风格，必须遵从驼峰形式__
    > <font color=green>正例</font>：localValue / getHttpMessage() / inputUserId

5. 【<font color=red>__强制__</font>】__常量命名全部大写，单词间用下划线隔开，力求语义表达完整清楚，不要嫌名字长__
    > <font color=green>正例</font>：MAX_STOCK_COUNT  
    > <font color=red>反例</font>：MAX_COUNT

6. 【<font color=red>__强制__</font>】__抽象类命名使用*Abstract*开头；异常类命名使用 *Exception* 结尾；测试类命名以它要测试的类名开始，以 *Test* 结尾__  

7. 【<font color=red>__强制__</font>】__类型与中括号紧挨相连来定义数组__
    > <font color=green>正例</font>：定义整形数组`int[] arrayDemo`;  
    > <font color=red>反例</font>：在main参数中，使用`String args[]`来定义。

8. 【<font color=red>__强制__</font>】__POJO类中布尔类型的变量，都不要加is前缀，否则部分框架解析会引起序列化错误__
    > <font color=red>反例</font>：定义为基本数据类型`Boolean isDeleted`；的属性，它的方法也是`isDeleted()`，RPC框架在反向解析的时候，误以为对应的属性名称是`deleted`，导致属性获取不到，进而抛出异常。

9. 【<font color=red>__强制__</font>】__包名统一使用小写，点分隔符之间有且仅有一个自然语义的英语单词。包名统一使用单数形式，但是类名如果有复数含义，类名可以使用复数形式__
    > <font color=green>正例</font>：应用工具类包名为`com.alibaba.ai.util`、类名为`MessageUtils`（此规则参考spring的框架结构）

10. 【<font color=blue>__建议__</font>】__为了达到代码自解释的目标，任何自定义编程元素在命名时，使用尽量完整的单词组合来表达其意__
    > <font color=green>正例</font>：从远程仓库拉取代码的类命名为`PullCodeFromRemoteRepository`。  
    > <font color=red>反例</font>：变量`int a;`的随意命名方式。

11. 【<font color=orange>__推荐__</font>】__如果模块、接口、类、方法使用了设计模式，在命名时体现出具体模式__
    > 说明：将设计模式体现在名字中，有利于阅读者快速理解架构设计理念。
    > <font color=green>正例</font>：
    > ```java
    > public class OrderFactory;
    > public class LoginProxy;
    > public class ResourceObserver;
    > ```

12. 【<font color=orange>__推荐__</font>】__接口类中的方法和属性不要加任何修饰符号（public也不要加），保持代码的简洁性，并加上有效的Javadoc注释。尽量不要在接口里定义变量，如果一定要定义变量，肯定是与接口方法相关，并且是整个应用的基础常量__
    > <font color=green>正例</font>：接口方法签名 `void f();`  
    > 接口基础常量 `String COMPANY = "alibaba"`;
    > 
    > <font color=red>反例</font>：接口方法定义 `public abstract void f()`;  

    > 说明：JDK8中接口允许有默认实现，那么这个default方法，是对所有实现类都有价值的默认实现。

13. 【<font color=green>__参考__</font>】__枚举类名建议带上*Enum*后缀，枚举成员名称需要全大写，单词间用下划线隔开__
    > 说明： 枚举其实就是特殊的常量类，且构造方法被默认强制是私有。
    > 
    > <font color=green>正例</font>： 枚举名字为 `ProcessStatusEnum` 的成员名称： SUCCESS / UNKNOWN_REASON。

14. 【<font color=green>__参考__</font>】__各层命名规约：__
    1.  Service/DAO 层方法命名规约
        * 获取单个对象的方法用 get 作前缀。
        * 获取多个对象的方法用 list 作前缀。
        * 获取统计值的方法用 count 作前缀。
        * 插入的方法用 save/insert 作前缀。
        * 删除的方法用 remove/delete 作前缀。
        * 修改的方法用 update 作前缀。
    2.  领域模型命名规约
        * 数据对象： xxxDO， xxx 即为数据表名。
        * 数据传输对象： xxxDTO， xxx 为业务领域相关的名称。
        * 展示对象： xxxVO， xxx 一般为网页名称。
        * POJO 是 DO/DTO/BO/VO 的统称，禁止命名成 xxxPOJO。

### 2.常量定义
1. 【<font color=red>__强制__</font>】__不允许任何魔法值（即未经预先定义的常量） 直接出现在代码中__
    > <font color=red>反例</font>： `String key = "Id#taobao_" + tradeId;`
    > `cache.put(key, value);`

2. 【<font color=red>__强制__</font>】__`long`或者`Long`初始赋值时，使用大写的L，不能是小写的l，小写容易跟数字1 混淆，造成误解__
    > 说明： `Long a = 2l;`写的是数字的21，还是`Long`型的 2?

3. 【<font color=orange>__推荐__</font>】__不要使用一个常量类维护所有常量， 按常量功能进行归类，分开维护__
    > 说明： 大而全的常量类，非得使用查找功能才能定位到修改的常量，不利于理解和维护。
    > <font color=green>正例</font>： 缓存相关常量放在类 CacheConsts 下； 系统配置相关常量放在类 ConfigConsts 下。

4. 【<font color=orange>__推荐__</font>】__常量的复用层次有五层：跨应用共享常量、应用内共享常量、子工程内共享常量、包内共享常量、类内共享常量__
    1. 跨应用共享常量：放置在二方库中，通常是 client.jar 中的 constant 目录下。
    2. 应用内共享常量：放置在一方库中， 通常是子模块中的 constant 目录下。
    3. 子工程内部共享常量：即在当前子工程的 constant 目录下。
    4. 包内共享常量：即在当前包下单独的 constant 目录下。
    5. 类内共享常量：直接在类内部 `private static final` 定义。
    >
    > ```java
    > // 反例： 易懂变量也要统一定义成应用内共享常量，两位攻城师在两个类中分别定义了表示“是”的变量：
    > // 类 A 中：
    > public static final String YES = "yes";
    >
    > // 类 B 中：
    > public static final String YES = "y";
    > // A.YES.equals(B.YES)，预期是 true，但实际返回为 false，导致线上问题。
    > ```

5. 【<font color=orange>__推荐__</font>】 __如果变量值仅在一个固定范围内变化用 enum 来定义__
    > 说明： 如果存在名称之外的延伸属性使用 enum 类型，下面正例中的数字就是延伸信息，表示一年中的第几个季节。
    > <font color=green>正例</font>：
    > ```java
    > public enum SeasonEnum {
    >     SPRING(1), SUMMER(2), AUTUMN(3), WINTER(4);
    >     int seq;
    >     SeasonEnum(int seq){
    >         this.seq = seq;
    >     }
    > }
    > ```

### 3.控制语句
1. 【<font color=red>__强制__</font>】__在一个 `switch` 块内，都必须包含一个 `default` 语句并且放在最后，即使空代码__

2. 【<font color=red>__强制__</font>】__在 if/else/for/while/do 语句中必须使用大括号。 即使只有一行代码，避免采用单行的编码方式：__
    `if (condition) statements;`

3. 【<font color=orange>__推荐__</font>】__循环体中的语句要考量性能，以下操作尽量移至循环体外处理，如定义对象、变量、获取数据库连接，进行不必要的 try-catch 操作（这个 try-catch 是否可以移至循环体外）__

### 4.OOP规约
1. 【<font color=red>__强制__</font>】__避免通过一个类的对象引用访问此类的静态变量或静态方法，无谓增加编译器解析成本，直接用类名来访问即可__

2. 【<font color=red>__强制__</font>】__所有的覆写方法，必须加`@Override` 注解__
    > 说明： `getObject()`与 `get0bject()`的问题。一个是字母的 O，一个是数字的 0，加`@Override`可以准确判断是否覆盖成功。另外，如果在抽象类中对方法签名进行修改，其实现类会马上编译报错。

3. 【<font color=red>__强制__</font>】__外部正在调用或者二方库依赖的接口，不允许修改方法签名， 避免对接口调用方产生影响。接口过时必须加`@Deprecated` 注解，并清晰地说明采用的新接口或者新服务是什么__

4. 【<font color=red>__强制__</font>】 __`Object` 的 `equals` 方法容易抛空指针异常，应使用常量或确定有值的对象来调用`equals`__
    > ```java
    > // 正例：
    > "test".equals(object);
    > 
    > // 反例：
    > object.equals("test");
    > // 说明： 推荐使用 java.util.Objects#equals（JDK7 引入的工具类）
    > ```

5. 【<font color=red>__强制__</font>】__所有的相同类型的包装类对象之间值的比较，全部使用 `equals` 方法比较__
    > 说明： 对于 `Integer var = ?` 在-128 至 127 范围内的赋值， `Integer` 对象是在`IntegerCache.cache` 产生，会复用已有对象，这个区间内的 `Integer` 值可以直接使用==进行判断，但是这个区间之外的所有数据，都会在堆上产生，并不会复用已有对象，这是一个大坑，推荐使用 `equals` 方法进行判断。

6. __关于基本数据类型与包装数据类型的使用标准如下：__
    * 【<font color=blue>__建议__</font>】 所有的 POJO 类属性必须使用包装数据类型。
    * 【<font color=red>__强制__</font>】 RPC 方法的返回值和参数必须使用包装数据类型。
    * 【<font color=orange>__推荐__</font>】 所有的局部变量使用基本数据类型。
    > 说明： POJO 类属性没有初值是提醒使用者在需要使用时，必须自己显式地进行赋值，任何NPE 问题，或者入库检查，都由使用者来保证。
    > 
    > <font color=green>正例</font>： 数据库的查询结果可能是 `null`，因为自动拆箱，用基本数据类型接收有 NPE 风险。  
    > 
    > <font color=red>反例</font>： 比如显示成交总额涨跌情况，即正负 x%， x 为基本数据类型，调用的 RPC 服务，调用不成功时，返回的是默认值，页面显示为 0%，这是不合理的，应该显示成中划线。所以包装数据类型的 `null` 值，能够表示额外的信息，如：远程调用失败，异常退出。

7. 【<font color=red>__强制__</font>】__构造方法里面禁止加入任何业务逻辑，如果有初始化逻辑，请放在 init 方法中__

8. 【<font color=blue>__建议__</font>】 __POJO 类必须写 `toString` 方法__
    > 使用 IDE 中的工具： source> generate toString时，如果继承了另一个 POJO 类，注意在前面加一下 `super.toString`。
    >
    > 说明： 在方法执行抛出异常时，可以直接调用 POJO 的 `toString()`方法打印其属性值，便于排查问题。

9. 【<font color=orange>__推荐__</font>】 __类内方法定义的顺序依次是：公有方法或保护方法 > 私有方法 > getter/setter方法__
    > 说明： 公有方法是类的调用者和维护者最关心的方法，首屏展示最好； 保护方法虽然只是子类关心，也可能是“模板设计模式”下的核心方法； 而私有方法外部一般不需要特别关心，是一个黑盒实现； 因为承载的信息价值较低，所有 Service 和 DAO 的 getter/setter 方法放在类体最后。

10. 【<font color=orange>__推荐__</font>】__循环体内，字符串的连接方式，使用 `StringBuilder` 的 `append` 方法进行扩展__
    > 说明： 反编译出的字节码文件显示每次循环都会 `new` 出一个 `StringBuilder` 对象，然后进行`append` 操作，最后通过 `toString` 方法返回 `String` 对象，造成内存资源浪费。
    > 
    > <font color=red>反例</font>：
    > ```java
    > String str = "start";
    > for (int i = 0; i < 100; i++) {
    >     str = str + "hello";
    > }
    > ```

11. 【<font color=orange>__推荐__</font>】__类成员与方法访问控制从严：__
    1. 如果不允许外部直接通过 `new` 来创建对象，那么构造方法必须是 `private`。
    2. 工具类不允许有 `public` 或 `default` 构造方法。
    3. 类非 static 成员变量并且与子类共享，必须是 `protected`。
    4. 类非 static 成员变量并且仅在本类使用，必须是 `private`。
    5. 类 static 成员变量如果仅在本类使用，必须是 `private`。
    6. 若是 static 成员变量，必须考虑是否为 `final`。
    7. 类成员方法只供类内部调用，必须是 `private`。
    8. 类成员方法只对继承类公开，那么限制为 `protected`。
    > 说明： 任何类、方法、参数、变量，严控访问范围。过于宽泛的访问范围，不利于模块解耦。
    > 
    > 思考：如果是一个 `private` 的方法，想删除就删除，可是一个 `public` 的 `service` 成员方法或成员变量，删除一下，不得手心冒点汗吗？变量像自己的小孩，尽量在自己的视线内，变量作用域太大， 无限制的到处跑，那么你会担心的。

12. 【<font color=orange>__推荐__</font>】 __在枚举中定义的字段必须私有，且仅能通过getter访问__
    > 说明：enum通常用来定义常量，如果枚举中的字段为public或者拥有public setter，此时该字段可以被外部代码随意修改，第一，不符合枚举的初衷，第二，期望的值很容易被其他代码破坏导致逻辑错误，第三，造成理解上的困难。
    > <font color=red>反例</font>：
    > ```java
    > public enum Continent {
    > 
    >   NORTH_AMERICA (23, 24709000),
    >   EUROPE (50, 39310000);
    > 
    >   public int countryCount;  // Noncompliant
    >   private int landMass;
    > 
    >   Continent(int countryCount, int landMass) {
    >       // ...
    >   }
    > 
    >   public void setLandMass(int landMass) {
    >     this.landMass = landMass;
    >   }
    > ```
    >
    > <font color=green>正例</font>：
    > ```java
    > public enum Continent {
    > 
    >   NORTH_AMERICA (23, 24709000),
    >   // ...
    >   EUROPE (50, 39310000);
    > 
    >   private int countryCount;
    >   private int landMass;
    > 
    >   Continent(int countryCount, int landMass) {
    >       // ...
    >   }
    > ```

13. 【<font color=orange>__推荐__</font>】 __final类不允许有`protected`成员__

    > 说明：final类不允许被继承，定义protected成员没有意义，只会造成代码理解上的障碍，其访问级别一定可以降低到默认级别或private。

14. 【<font color=blue>__建议__</font>】 __"getClass"的结果不应该用于同步代码块加锁__
    > 说明：`getClass`不应该被用于synchronized代码块中作为锁定对象，因为子类对象与父类对象将会锁定不同的类型，但很容易让人以为他们锁定的是同一个类，造成多个线程进入了同步代码块。
    >
    > <font color=red>反例</font>：
    >
    > ```java
    > public class MyClass {
    >     public void doSomethingSynchronized() {
    >         synchronized (this.getClass()) {
    >             // ...
    >         }
    >     }
    > }
    > ```
    >
    > <font color=green>正例</font>：
    >
    > ```java
    > public class MyClass {
    >     public void doSomethingSynchronized(){
    >         synchronized (MyClass.class) {
    >             // ...
    >         }
    >     }
    > }
    > ```

15. 【<font color=blue>__建议__</font>】 __不需要访问实例数据的私有方法必须为静态的__
    > 说明：`private`方法如果没有访问任何具体实例的数据，那么应该将它作为静态方法来增加可读性。

16. 【<font color=orange>__推荐__</font>】 __子类不应该隐藏父类字段__
    > 说明：对于两个没有任何关系的类来说，含有相同名称的字段并无大碍，但是如果父类与子类同时含有相同名称的字段，子类将会隐藏父类的同名字段，这种行为在多态中会增加理解的难度，降低代码可读性，且这种情况通常都有更好的解决方案，所以不推荐。

17. 【<font color=red>__强制__</font>】 __构造方法中不应该调用可被重写的实例方法__
    > 说明：在构造方法中调用可被重写的实例方法，在实例化子类时（子类重写了该方法）可能会导致运行时异常或者逻辑错误。
    >
    > 发生过程:
    > - 子类构造方法开始执行，调用父类构造方法
    > - 父类构造方法调用那个被子类重写的方法
    > - 此时，如果子类重写的方法依赖了尚未被初始化的成员变量，可能会抛出异常(例如`NullPointerException`)
    >
    > <font color=red>反例</font>：
    > ```java
    > public class Parent {
    > 
    >     public Parent () {
    >         doSomething();
    >     }
    > 
    >     public void doSomething () {  // 非final，可以被重写
    >         ...
    >     }
    > }
    > 
    > public class Child extends Parent {
    > 
    >     private String foo;
    > 
    >     public Child(String foo) {
    >         // 调用父类构造方法，将会调用到子类的doSomething()方法，这个过程子类可能并不知道，此时该方法如果访问foo变量，将会导致运行时异常
    >         super();
    >         this.foo = foo;
    >     }
    > 
    >     @Override
    >     public void doSomething () {
    >         System.out.println(this.foo.length());
    >     }
    > }
    > ```

18. 【<font color=red>__强制__</font>】 __AOP切入的方法不能为private__
    > 说明：AOP以运行时织入方式工作时，通常有两种实现，一种是jdk的动态代理，一种是cglib，不论哪种方式，都要访问原方法，__如果是cglib，切点切入的方法还必须能够被重写__。

19. 【<font color=red>__强制__</font>】 __不允许使用的Bigdecimal(double)构造方法__
    > 说明：java的double与float表示小数时，可能会产生精度损失，如果通过`BigDecimal(double)`方法来构造，会把double的精度损失传递到BigDecimal中，此时BigDecimal失去了其意义。
    >
    > <font color=red>反例</font>：
    > ```java
    > double d = 1.1;
    > BigDecimal bd1 = new BigDecimal(d);
    > BigDecimal bd2 = new BigDecimal(1.1);
    > ```
    >
    > <font color=green>正例</font>：
    > ```java
    > double d = 1.1;
    > BigDecimal bd1 = BigDecimal.valueOf(d);
    > BigDecimal bd2 = new BigDecimal("1.1");
    > ```

20. 【<font color=orange>__推荐__</font>】 __`instanceof`操作时，省去不必要的非空判断__
    > 说明：`null`不是任何类型的实例，所以通常情况下，在进行`instanceof`操作时，如果期望`null`作为否定的结果，不需要进行任何非空判断。
    > <font color=red>反例</font>：
    >
    > ```java
    > if (x != null && x instanceof MyClass) { ... }
    > if (x == null || ! x instanceof MyClass) { ... }
    > ```
    >
    > <font color=green>正例</font>：
    >
    > ```java
    > if (x instanceof MyClass) { ... }
    > if (! x instanceof MyClass) { ... }
    > if (null == x || x instanceof MyClass) { ... }
    > ```

### 5.并发处理
1. 【<font color=red>__强制__</font> 】 __获取单例对象需要保证线程安全，其中的方法也要保证线程安全__
    > 说明： 资源驱动类、工具类、单例工厂类都需要注意。

2. 【<font color=red>__强制__</font> 】__创建线程或线程池时请指定有意义的线程名称，方便出错时回溯__
    > ```java
    >   // 正例：
    >   public class TimerTaskThread extends Thread {
    >    public TimerTaskThread() {
    >    super.setName("TimerTaskThread");
    >    // ...
    >   }
    > ```

3. 【<font color=red>__强制__</font> 】__线程资源必须通过线程池提供，不允许在应用中自行显式创建线程__
    > 说明： 使用线程池的好处是减少在创建和销毁线程上所花的时间以及系统资源的开销，解决资源不足的问题。如果不使用线程池，有可能造成系统创建大量同类线程而导致消耗完内存或者“过度切换”的问题。

4. 【<font color=orange>__推荐__</font>】 __`SimpleDateFormat` 是 *线程不安全* 的类，一般不要定义为 static 变量__
    > ```java
    >   // 正例： 注意线程安全，推荐如下处理：
    > private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
    >     @Override
    >     protected DateFormat initialValue() {
    >         return new SimpleDateFormat("yyyy-MM-dd");
    >     }
    >};
    > ```
    > 
    > 说明： 如果是 JDK8 的应用，可以使用 `Instant` 代替 `Date`， `LocalDateTime` 代替 `Calendar`，`DateTimeFormatter` 代替 `SimpleDateFormat`，官方给出的解释： simple beautiful strong immutable thread-safe。

5. 【<font color=red>__强制__</font>】__线程池不允许使用 `Executors`去创建，而是通过 `ThreadPoolExecutor` 的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险__
    > 说明： `Executors` 返回的线程池对象的弊端如下：
    > 1.  `FixedThreadPool` 和 `SingleThreadPool`:
    >     允许的请求队列长度为 `Integer.MAX_VALUE`，可能会堆积大量的请求，从而导致 OOM。
    > 2. `CachedThreadPool` 和 `ScheduledThreadPool`:
    >    允许的创建线程数量为 `Integer.MAX_VALUE`， 可能会创建大量的线程，从而导致 OOM。

6. 【<font color=red>__强制__</font>】__高并发时，同步调用应该去考量锁的性能损耗。能用无锁数据结构，就不要用锁； 能锁区块，就不要锁整个方法体； 能用对象锁，就不要用类锁__
    > 说明： 尽可能使加锁的代码块工作量尽可能的小，避免在锁代码块中调用 RPC 方法。

7. 【<font color=red>__强制__</font>】__对多个资源、数据库表、对象同时加锁时，需要保持一致的加锁顺序，否则可能会造成死锁__
    > 说明： 线程一需要对表 A、 B、 C 依次全部加锁后才可以进行更新操作，那么线程二的加锁顺序也必须是 A、 B、 C，否则可能出现死锁。

8. 【<font color=red>__强制__</font>】__并发修改同一记录时，避免更新丢失， 需要加锁。 要么在应用层加锁，要么在缓存加锁，要么在数据库层使用乐观锁，使用 version 作为更新依据__
    > 说明： 如果每次访问冲突概率小于 20%，推荐使用乐观锁，否则使用悲观锁。乐观锁的重试次数不得小于 3 次。

9. 【<font color=red>__强制__</font>】__多线程并行处理定时任务时， `Timer` 运行多个 `TimeTask` 时，只要其中之一没有捕获抛出的异常，其它任务便会自动终止运行，使用 `ScheduledExecutorService` 则没有这个问题__

10. 【<font color=green>__参考__</font>】 __`HashMap` 在容量不够进行 `resize` 时由于高并发可能出现死链，导致 CPU 飙升，在开发过程中可以使用其它数据结构或加锁来规避此风险__

11. 【<font color=orange>__推荐__</font>】 __同步代码块的锁定类的字段，需要是private final的__
    > 说明：同步代码块锁定一个字段，并不是锁定字段本身，而是锁定它指向的对象。如果该字段不是final的，可能会导致锁定的对象被修改，此时其他线程在获得新对象的锁后，将会进入同步代码块导致同步机制失效。
    >
    > <font color=red>反例</font>：
    >
    > ```java
    > private String color = "red";
    > 
    > private void doSomething(){
    >     synchronized(color) {
    >         //...
    >         color = "green"; // other threads now allowed into this block
    >         // ...
    >     }
    >     synchronized(new Object()) { // Noncompliant this is a no-op.
    >          // ...
    >     }
    > }
    > ```

13. 【<font color=red>__强制__</font>】 __"Lock"对象不允许用于synchronized中__
    > 说明：`java.util.concurrent.locks.Lock` 本身提供了比 `synchronized` 更强大、完善的同步控制能力，所以使用同步代码块来锁定一个`Lock`对象是没有意义的，对于`Lock`对象，应该使用他的`tryLock()` 与 `unlock()`方法来获得、释放锁。
    >
    > <font color=red>反例</font>：
    >
    > ```java
    > Lock lock = new MyLockImpl();
    > synchronized(lock) {
    >     //...
    > }
    > ```
    >
    > <font color=green>正例</font>：
    >
    > ```java
    > Lock lock = new MyLockImpl();
    > lock.tryLock();
    > //...
    > ```

### 6.序列化
1. 【<font color=red>__强制__</font>】__实现了Serializable接口的类必须指定SerializationUID __

2. 【<font color=red>__强制__</font>】__序列化类新增属性时，请不要修改 `serialVersionUID` 字段，避免反序列失败； 如果完全不兼容升级，避免反序列化混乱，那么请修改`serialVersionUID` 值__
    > 说明： 注意 `serialVersionUID` 不一致会抛出序列化运行时异常。

3. 【<font color=red>__强制__</font>】 __必须注意序列化类中的字段，对于不可序列化字段，必须标记为transient__
    > 说明：如果序列化类中包含不可序列化的字段，且该字段未标记为transient，会抛出序列化运行时异常

4. 【<font color=orange>__推荐__</font>】 __不能序列化的对象不能放到HttpSession中__
    > Session有时可能会需要序列化（如分布式session），所以不加选择的将不可序列化的对象放到session中不是一个好习惯。

5. 【<font color=red>__强制__</font>】  __实现了Serializable接口的内部类应该尽可能是静态的__
    > 序列化成员内部类时，同时会序列化外部类，如果外部类不可序列化，将会导致序列化异常；所以*外部类不可序列化，则内部类必须标记为静态*。如果外部类可以序列化，序列化可以成功。但必须要考虑序列化外部类带来的成本，所以此时如果内部类可以是静态的，尽可能标记为静态。

### 7.集合处理
1. 【<font color=orange>__推荐__</font>】__集合初始化时， 应该指定集合初始值大小__
    > 说明： `HashMap` 使用 `HashMap(int initialCapacity)` 初始化，
    > <font color=green>正例</font>：initialCapacity = (需要存储的元素个数 / 负载因子) + 1。注意负载因子（即 loaderFactor） 默认为 0.75。
    >
    > <font color=red>反例</font>： `HashMap` 需要放置 1024 个元素， 由于没有设置容量初始大小，随着元素不断增加，容量 7 次被迫扩大， `resize` 需要重建 hash 表，严重影响性能。

2. 【<font color=orange>__推荐__</font>】__高度注意 Map 类集合 K/V 能不能存储 null 值的情况，如下表格：__
    > <font color=red>反例</font>： 由于 `HashMap` 的干扰，很多人认为 `ConcurrentHashMap` 是可以置入 `null` 值，而事实上，存储 `null` 值时会抛出 NPE 异常。
| 集合类            | Key           | Value         | Super       | 说明                   |
| ----------------- | ------------- | ------------- | ----------- | ---------------------- |
| Hashtable         | 不允许为 null | 不允许为 null | Dictionary  | 线程安全               |
| ConcurrentHashMap | 不允许为 null | 不允许为 null | AbstractMap | 锁分段技术（JDK8:CAS） |
| TreeMap           | 不允许为 null | 允许为 null   | AbstractMap | 线程不安全             |
| HashMap           | 允许为 null   | 允许为 null   | AbstractMap | 线程不安全             |

3. 【<font color=red>__强制__</font>】 __ArrayList的subList结果不可强转成ArrayList，否则会抛出 ClassCastException异常， 即 java.util.RandomAccessSubList cannot be cast to java.util.ArrayList.__
    > 说明： subList 返回的是 ArrayList 的内部类 SubList，并不是 ArrayList ，而是ArrayList 的一个视图，对于 SubList 子列表的所有操作最终会反映到原列表上。

4. 【<font color=red>__强制__</font>】__在 subList 场景中， 高度注意对原集合元素个数的修改，会导致子列表的遍历、增加、删除均会产生 ConcurrentModificationException 异常__

5. 【<font color=red>__强制__</font>】__使用工具类 Arrays.asList()把数组转换成集合时，不能使用其修改集合相关的方法，它的 add/remove/clear 方法会抛出 UnsupportedOperationException 异常__
    > 说明： asList 的返回对象是一个 Arrays 内部类，并没有实现集合的修改方法。 Arrays.asList体现的是适配器模式，只是转换接口，后台的数据仍是数组。
    > `String[] str = new String[] { "you", "wu" };`
    > `List list = Arrays.asList(str);`
    > 第一种情况： `list.add("yangguanbao");` 运行时异常。
    > 第二种情况： `str[0] = "gujin";` 那么`list.get(0)`也会随之修改。

6. 【<font color=orange>__推荐__</font>】 __使用`entrySet`遍历`Map`类集合 KV，而不是`keySet`方式进行遍历__
    > 说明：`keySet`其实是遍历了 2 次，一次是转为`Iterator`对象，另一次是从`HashMap`中取出`key` 所对应的`value`。而`entrySet`只是遍历了一次就把`key`和`value`都放到了`entry`中，效率更高。如果是 JDK8，使用`Map.foreach`方法。
    > <font color=green>正例</font>：
    > `values()`返回的是 V 值集合，是一个 list 集合对象； `keySet()`返回的是 K 值集合，是一个`Set`集合对象； `entrySet()`返回的是 K-V 值组合集合。

### 8.异常处理
1. 【<font color=red>__强制__</font> 】 __Java 类库中定义的可以通过预检查方式规避的 `RuntimeException` 异常不应该通过catch 的方式来处理__
    > 比如： `NullPointerException`，`IndexOutOfBoundsException` 等等。
    >
    > 说明： 无法通过预检查的异常除外，比如，在解析字符串形式的数字时，不得不通过 `catch NumberFormatException` 来实现。
    > 
    > <font color=green>正例</font>：
    > ```java
    > if (obj != null) {
    > ...
    > }
    > ```
    > 
    > <font color=red>反例</font>：
    > ```java
    > try {
    >     obj.method()
    > } catch (NullPointerException e) {
    >     ...
    > }
    > ```
    >

2. 【<font color=red>__强制__</font> 】__异常不要用来做流程控制，条件控制__
    > 说明： 异常设计的初衷是解决程序运行中的各种意外情况，且异常的处理效率比条件判断方式要低很多。

3. 【<font color=red>__强制__</font> 】 __catch 时请分清稳定代码和非稳定代码，稳定代码指的是无论如何不会出错的代码。对于非稳定代码的 catch 尽可能进行区分异常类型，再做对应的异常处理__
    > 说明： 对大段代码进行 try-catch，使程序无法根据不同的异常做出正确的应激反应，也不利于定位问题，这是一种不负责任的表现。
    > 
    > <font color=green>正例</font>： 用户注册的场景中，如果用户输入非法字符， 或用户名称已存在， 或用户输入密码过于简单，在程序上作出分门别类的判断，并提示给用户。

4. 【<font color=red>__强制__</font> 】__捕获异常是为了处理它，不要捕获了却什么都不处理而抛弃之，如果不想处理它，请将该异常抛给它的调用者。最外层的业务使用者，必须处理异常，将其转化为用户可以理解的内容__

5. 【<font color=red>__强制__</font>】  __finally中不允许再抛出异常__
    > 说明：在finally中抛出异常，将会掩盖之前try或catch中抛出的任何异常，并且这些异常中的消息与堆栈信息都将会丢失。
    >
    > <font color=red>反例</font>：
    >
    > ```java
    > public class TryCatchFinally {
    > 
    >     public static final String test() {
    >         String t = "";
    > 
    >         try {
    >             t = "try";
    >             Integer.parseInt(null);
    >             return t;
    >         } catch (Exception e) {
    >             t = "catch";
    >             return t;
    >         } finally {
    >             t = "finally";
    >             String.valueOf(null);
    >             return t;
    >         }
    >     }
    > 
    >     public static void main(String[] args) {
    >         // 方法最终将抛出NPE，try中的NumberFormatException将被掩盖，catch中的操作结果也将被掩盖
    >         System.out.print(TryCatchFinally.test());
    >     }
    > }
    > ```
    >
    > 

6. 【<font color=orange>__推荐__</font>】 __不应该使用标准输出流来打印日志__
    > 说明：记录日志时，需要满足一些关键性需求：
    > - 日志必须很容易被获取
    > - 日志格式应当统一以便于阅读
    > - 应当很容易的通过全局来控制是否记录日志
    >
    > 很明显，标准输出流不能满足所有的需求。

### 9.日志规约
1. 【<font color=red>__强制__</font>】__应用中不可直接使用日志系统（Log4j、 Logback） 中的 API，而应依赖使用日志框架SLF4J 中的 API，使用门面模式的日志框架，有利于维护和各个类的日志处理方式统一__
   > ```java
   > import org.slf4j.Logger;
   > import org.slf4j.LoggerFactory;
   > private static final Logger logger = LoggerFactory.getLogger(Abc.class);
   > ```

  

2. 【<font color=red>__强制__</font>】__日志文件推荐至少保存 15 天，因为有些异常具备以“周”为频次发生的特点__

3. 【<font color=red>__强制__</font>】__对 trace/debug/info 级别的日志输出，必须使用条件输出形式或者使用占位符的方式__
   > 说明：
   >
   > `logger.debug("Processing trade with id: " + id + " and symbol: " + symbol);`
   > 如果日志级别是 warn，上述日志不会打印，但是会执行字符串拼接操作，如果 symbol 是对象，
   > 会执行 `toString()`方法，浪费了系统资源，执行了上述操作，最终日志却没有打印。
   >
   > ```java
   > // 正例： （条件）
   > if (logger.isDebugEnabled()) {
   >     logger.debug("Processing trade with id: " + id + " and symbol: " + symbol);
   > }
   > 
   > // 正例： （占位符）
   > logger.debug("Processing trade with id: {} and symbol : {} ", id, symbol);
   > ```

4. 【<font color=red>__强制__</font>】__避免重复打印日志，浪费磁盘空间，务必在 log4j.xml 中设置 additivity=false__
   > 正例：` <logger name="com.taobao.dubbo.config" additivity="false">`

5. 【<font color=red>__强制__</font>】__异常信息应该包括两类信息：案发现场信息和异常堆栈信息。如果不处理，那么通过关键字 throws 往上抛出__
   > 正例： `logger.error(各类参数或者对象 toString + "_" + e.getMessage(), e);`

6. 【推荐】__谨慎地记录日志__
   > 生产环境禁止输出 debug 日志； 有选择地输出 info 日志； 如果使用 warn 来记录刚上线时的业务行为信息，一定要注意日志输出量的问题，避免把服务器磁盘撑爆，并记得及时删除这些观察日志。
   >
   > 说明： 大量地输出无效日志，不利于系统性能提升，也不利于快速定位错误点。
   >
   > 记录日志时请思考：这些日志真的有人看吗？看到这条日志你能做什么？能不能给问题排查带来好处？

7. 【推荐】__可以使用 warn 日志级别来记录用户输入参数错误的情况，避免用户投诉时，无所适从__
   > 如非必要，请不要在此场景打出 error 级别，避免频繁报警。
   >
   > 说明： 注意日志输出的级别， error 级别只记录系统逻辑出错、异常或者重要的错误信息。

### 10.其他
1. 【<font color=red>__强制__</font> 】 __关于 `hashCode` 和 `equals` 的处理，遵循如下规则：__
    1. 只要重写 `equals`，就必须重写 `hashCode`。
    2. 因为 Set 存储的是不重复的对象，依据 `hashCode` 和 `equals` 进行判断，所以 `Set` 存储的对象必须重写这两个方法。
    3. 如果自定义对象作为 Map 的键，那么必须重写 `hashCode` 和 `equals`。
    > 说明： `String` 重写了 `hashCode` 和 `equals` 方法，所以我们可以非常愉快地使用 `String` 对象作为 key 来使用。

2. 【<font color=red>__强制__</font>】__泛型通配符`<? extends T>`来接收返回的数据，此写法的泛型集合不能使用`add`方法， 而`<? super T>`不能使用`get`方法，作为接口调用赋值时易出错__
    > 说明： 扩展说一下*PECS*(Producer Extends Consumer Super)原则： 第一、 频繁往外读取内容的，适合用`<? extends T>`。 第二、 经常往里插入的，适合用`<? super T>`。

3. 【<font color=red>__强制__</font>】 __不要在 foreach 循环里进行元素的 remove/add 操作。 remove 元素请使用 Iterator方式，如果并发操作，需要对 Iterator 对象加锁__
    > ```java
    > // 正例：
    > Iterator<String> iterator = list.iterator();
    > while (iterator.hasNext()) {
    > 	String item = iterator.next();
    > 	if (删除元素的条件) {
    > 		iterator.remove();
    > 	}
    > }
    > 
    > // 反例：
    > List<String> list = new ArrayList<String>();
    > list.add("1");
    > list.add("2");
    > for (String item : list) {
    > 	if ("1".equals(item)) {
    > 		list.remove(item);
    > 	}
    > }
    > ```
    > 
    > 说明： 以上代码的执行结果肯定会出乎大家的意料，那么试一下把“1”换成“2”，会是同样的结果吗？

4. 【<font color=red>__强制__</font>】  __"Atomic"不允许通过`equals`比较__ 
    > 说明：`AtomicInteger`, `AtomicLong`也继承自`Number`类,但他们与`Integer`与`Long`有所不同；他们线程安全并且无需加锁，但`equals`方法比较的是对象本身，而不是他们的值，因此，希望对于值的比较，必须通过`get()`方法获取其值后再行比较。
    >
    > <font color=green>正例</font>：
    >
    > ```java
    > AtomicInteger aInt1 = new AtomicInteger(0);
    > AtomicInteger aInt2 = new AtomicInteger(0);
    > 
    > if (aInt1.get() == aInt2.get()) {
    > 	// do something
    > }
    > ```
    >
    > <font color=red>反例</font>：
    > ```java
    > AtomicInteger aInt1 = new AtomicInteger(0);
    > AtomicInteger aInt2 = new AtomicInteger(0);
    > 
    > if (aInt1.equals(aInt2)) {
    >     // 这里的比较是错误的
    > }
    > ```

5. 【<font color=orange>__推荐__</font>】 __基本类型数组转为流必须使用Arrays.stream__
    > 说明：对于对象数组来说，`Arrays.asList(T ... a).stream()`与`Arrays.stream(array)`是性能相同的做法，但是，对于基本类型而言，使用`Arrays.asList`因为要构造`List`,将会导致每个元素被装箱，而`Arrays.stream`不会，它将会构造出基本类型的流，拥有更高的性能。
    >
    > <font color=red>反例</font>：
    >
    > ```java
    > Arrays.asList(1, 2, 3, 4).stream()
    >     .filter(...)
    >     .forEach(...);
    > ```
    >
    > <font color=green>正例</font>：
    >
    > ```java
    > int[] intArray = new int[]{1, 2, 3, 4};
    > Arrays.stream(intArray)
    >     .filter(...)
    >     .forEach(...);
    > ```

6. 【<font color=orange>__推荐__</font>】  __不应该调用数组实例的hashCode与toString方法__
    > 说明：数组对象也有`hashCode`与`toString`方法，但几乎没有用处。`hashCode`返回的是数组对象本身标识符的hash code，`toString`也是同样的机制。但绝大多数情况下，我们期望得到的是数组中的内容的hash code与string。因此，通常情况下应该使用`Arrays.hashCode`与`Arrays.toString`方法。
    >
    > <font color=red>反例</font>：
    >
    > ```java
    > String argStr = args.toString();
    > int argHash = args.hashCode();
    > ```
    >
    > <font color=green>正例</font>：
    >
    > ```java
    > String argStr = Arrays.toString(args);
    > int argHash = Arrays.hashCode(args);
    > ```

7. 【<font color=orange>__推荐__</font>】 __StringBuilder与StringBuffer不允许通过char来构造__
    > 说明：用char实例化`StringBuilder`或`StringBuffer` 具有很强的误导性，因为大部分人会认为这样是使用这个字符来初始化`StringBuffer`，但实际上这样做是构造了一个初始容量为这个char的ascii编码的空`StringBuffer`。
    >
    > <font color=red>反例</font>：
    >
    > ```java
    > StringBuffer foo = new StringBuffer('x');   //与StringBuffer foo = new StringBuffer(120);等价
    > ```
    >
    > <font color=green>正例</font>：
    >
    > ```java
    > StringBuffer foo = new StringBuffer("x");
    > ```


8. 【<font color=red>__强制__</font>】  __toString与clone方法不允许返回null__
    > 说明：任何非空对象的clone方法与toString方法都不应该返回null

9. 【<font color=red>__强制__</font>】 __不允许对浮点类型（float，double）做==判断__
    > 说明：IEEE794标准下的浮点类型表示会产生精度损失，导致本该相等的数值实际判断结果不相等，所以不允许对浮点值做==判断。
    > 
    > <font color=red>反例</font>：
    > ```java
    > float myNumber = 3.146;
    > if ( myNumber == 3.146f ) { // false
    >   // ...
    > }
    > if ( myNumber != 3.146f ) { // true
    >   // ...
    > }
    > ```

10. 【<font color=blue>__建议__</font>】 __方法不应该有太多参数__
    > 说明：方法不应该有太多参数，如果参数很多，应该考虑重构，将参数包装。