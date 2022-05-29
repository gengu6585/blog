/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80017
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2021-06-20 11:43:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for articles
-- ----------------------------
DROP TABLE IF EXISTS `articles`;
CREATE TABLE `articles` (
  `cid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `slug` varchar(200) DEFAULT NULL,
  `created` int(10) unsigned DEFAULT '0',
  `modified` int(10) unsigned DEFAULT '0',
  `words` int(11) DEFAULT NULL,
  `music` varchar(255) DEFAULT NULL,
  `content` text COMMENT '内容文字',
  `author_id` int(10) unsigned DEFAULT '0',
  `type` varchar(16) DEFAULT 'post',
  `status` varchar(16) DEFAULT 'publish',
  `tags` varchar(200) DEFAULT NULL,
  `categories` varchar(200) DEFAULT NULL,
  `hits` int(10) unsigned DEFAULT '0',
  `comments_num` int(10) unsigned DEFAULT '0',
  `allow_comment` tinyint(1) DEFAULT '1',
  `allow_ping` tinyint(1) DEFAULT '1',
  `allow_feed` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`cid`),
  UNIQUE KEY `slug` (`slug`) USING BTREE,
  KEY `created` (`created`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of articles
-- ----------------------------
INSERT INTO `articles` VALUES ('1', 'about my blog', 'about', '1487853610', '1621618032', null, null, '### Hello World\r\n\r\nabout me\r\n\r\n### ...\r\n\r\n...', '1', 'page', 'publish', null, null, '5', '0', '1', '1', '1');
INSERT INTO `articles` VALUES ('9', 'git学习', null, '1617022312', '1617022312', null, null, '# 1.入坑即踩坑\r\n\r\n## 1.1 git默认开了代理导致下载失败\r\n\r\n> git config --global --unset http.proxy	默认开了全局代理，卡了一个月。。。\r\n\r\n## 1.2 url不能含有空格\r\n\r\n在windows电脑中，文件名常常含有空格，但在网络url中不能含有空格，负责会解码成%20造成路径错误\r\n\r\n# 2.hexo常用命令\r\n\r\n> npm install hexo-cli -g 安装hexo\r\n>\r\n> hexo init blog 初始化博客\r\n>\r\n> cd blog 进入博客目录\r\n>\r\n> hexo clean 清楚生成的public静态文件\r\n>\r\n> hexo g 生成public静态文件\r\n>\r\n> hexo s 启动hexo服务器\r\n>\r\n> hexo d 部署\r\n>\r\n> hexo new articlename 新建一篇文章\r\n\r\n# 3.git常用命令', '1', 'post', 'publish', 'git', '开发工具', '42', '1', '1', '1', '1');
INSERT INTO `articles` VALUES ('10', '算法基础', null, '1617022404', '1617022404', null, '22814470', '# 0.Java常用集合工具使用\r\n\r\n## 0.1java中堆栈和队列的使用：直接使用双端队列——ArrayDeque\r\n\r\n> ArrayDeque、ArrayList等之间可以使用构造方法互转。\r\n>\r\n> 原理就是数组拷贝。\r\n\r\n同时具有栈、队列、双端队列全部接口，分别如下：\r\n\r\n- Stack\r\n\r\n| Modifier and Type | Method and Description                                       |\r\n| ----------------- | ------------------------------------------------------------ |\r\n| `boolean`         | `empty()`<br/>测试栈是否为空                                 |\r\n| `E`               | `peek()` 在不将其从栈中移除的情况下，查看该栈顶部的对象。在栈为空的时候会报EmptyStackException |\r\n| `E`               | `pop()`删除此栈顶部的对象，并将该对象作为此函数的值返回。在栈为空的时候会报EmptyStackException |\r\n| `E`               | `push(E item)`<br/>将一个项目推送到此堆栈的顶部。            |\r\n| `int`             | `push(E item)`<br/>将一个项目推送到此堆栈的顶部。            |\r\n\r\n- Queue\r\n\r\n| Modifier and Type | Method and Description                                       |\r\n| ----------------- | ------------------------------------------------------------ |\r\n| `boolean`         | `add(E e)`<br/>如果可以立即将指定的元素插入此队列，而不会违反容量限制，则在成功时返回true，如果当前没有可用空间，则抛出IllegalStateException。 |\r\n| `E`               | `element()`<br/>检索但不删除此队列的头。                     |\r\n| `boolean`         | `offer(E e)`<br/>如果可以在不违反容量限制的情况下立即将指定的元素插入此队列。 |\r\n| E                 | `peek()`<br/>检索但不删除此队列的头部，如果此队列为空，则返回null。 |\r\n| `E`               | `poll()`<br/>检索并删除此队列的头部，如果此队列为空，则返回null。 |\r\n| `E`               | `remove()`<br/>检索并删除此队列的头。                        |\r\n\r\n- Deque\r\n\r\n  **First Element (Head)**：\r\n\r\n  |             | *Throws exception*                                           | *Returns special value*                                      |\r\n  | ----------- | ------------------------------------------------------------ | ------------------------------------------------------------ |\r\n  | **Insert**  | [`addFirst(e)`](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html#addFirst-E-) | [`offerFirst(e)`](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html#offerFirst-E-) |\r\n  | **Remove**  | [`addFirst(e)`](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html#addFirst-E-) | [`pollFirst()`](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html#pollFirst--) |\r\n  | **Examine** | [`getFirst()`](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html#getFirst--) | [`peekFirst()`](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html#peekFirst--) |\r\n\r\n  **Last Element (Tail)**\r\n\r\n  |             | *Throws exception*                                           | *Returns special value*                                      |\r\n  | ----------- | ------------------------------------------------------------ | ------------------------------------------------------------ |\r\n  | **Insert**  | [`addLast(e)`](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html#addLast-E-) | [`offerLast(e)`](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html#offerLast-E-) |\r\n  | **Remove**  | [`removeLast()`](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html#addFirst-E-) | [`pollLast()`](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html#pollFirst--) |\r\n  | **Examine** | [`getLast()`](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html#getFirst--) | [`peekLast()`](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html#peekLast--) |\r\n\r\n- PriorityQueue：优先队列即堆\r\n\r\n  > 往优先队列加入元素时，会自动建堆\r\n\r\n## 0.2关于使用HashSet对Object去重\r\n\r\n> 当两个对象使用 `==`连接时，它会先调用两个对象的hashCode方法比较两个对象的hashCode是否相等（除了自带的Integer、String等已经重写过Object的hashCode方法的对象之外，默认都是不相等），再调用equals方法，当两者都满足时，才视为相等。所以当对自定义Object方法去重时，需要同时重写这两个方法。\r\n\r\n## 0.3java中final修饰对象的传递问题\r\n\r\n> java中方法参数中，对于final修饰的对象，传递不是原对象指针，而是其copy的指针。\r\n>\r\n> 这就导致Integer、String等对象，在传递时方法内部的修改无法对方法外部产生影响。\r\n\r\n## 0.4Lambda表达式的使用\r\n\r\n> Lambda表达式的使用必须要用到接口，能够简写的秘密在于这个接口里面的方法很少，通过参数类型或者参数个数能锁定到那个方法，lambda表达式实质为实现了接口中的方法。\r\n>\r\n> Lambda表达式便捷在于，接口无需指定，当方法的参数约束为某个接口时，会自动匹配到那个接口。\r\n\r\n`::`的使用\r\n\r\n使用::可以指定引用方法，是当lambda表达式中恰好只需调用这个方法的简写。例如：\r\n\r\n```java\r\npublic class Example {\r\n\r\n	@Test\r\n	public void test() {\r\n		InterfaceExample com =  Example::new;\r\n        //等效于\r\n        InterfaceExample com = (i) -> new Integer(i);\r\n		Example bean = com.create();	\r\n		System.out.println(bean);\r\n	}\r\n}\r\n\r\ninterface InterfaceExample{\r\n	Example create();\r\n}\r\n```\r\n\r\n```java\r\n//用法一\r\nint[] ints = Arrays.stream(integers).mapToInt(Integer::valueOf).toArray();\r\n//用法二\r\nInteger[] integers = Stream.iterate(1, (a) -> a + 1).limit(50).toArray(Integer[]::new);\r\n\r\n```\r\n\r\n## 0.5妖魔鬼怪\r\n\r\n使用克隆的栈，在递归退栈的时候竟然给我清空了。\r\n\r\n![image-20210305134605347](算法基础/image-20210305134605347.png)\r\n\r\n> 问题解决：java中没有指针，引用类型传参是传递永远是对象或数组的地址，而不是指针。即传递的是箭头的终点，而不是箭头的始端。\r\n\r\n## 0.6数组初始化\r\n\r\n```java\r\nint[]dp=new int[amount]\r\nArrays.fill(dp, -1);\r\n```\r\n\r\n# 1.贪心\r\n\r\n## 定义\r\n\r\n> 遵循某种规律，不断**贪心**地选取**当前最优**策略的算法设计方法\r\n\r\n## 例题\r\n\r\n- ### 糖果分配：给定一个糖果大小序列和一个需求序列，求最大满足的需求数\r\n\r\n  ```java\r\n  package com.zwj;\r\n  import java.util.*;\r\n  /**\r\n   * @Author:zengwenjie\r\n   * @Date:2021/2/28 16:32\r\n   */\r\n  public class Sweet_allocation {\r\n      public static void main(String[] args) {\r\n          List<Integer> sweet = Arrays.asList(20,8,3,1,6);\r\n          List<Integer> need = Arrays.asList(15,5,9,2,10,9);\r\n          long start = System.currentTimeMillis();\r\n          System.out.println(\"糖果大小分别为\"+\"-----\"+sweet);\r\n          System.out.println(\"需求因子分别为\"+\"-----\"+need);\r\n          int num = new Sweet_allocation().sweet_allocation(sweet, need);\r\n          System.out.println(\"最后能满足的个数\"+\"-----\"+num);\r\n          long last = System.currentTimeMillis();\r\n          System.out.println(\"程序执行时间为\"+(last-start)+\"ms\");\r\n      }\r\n      public int sweet_allocation(List<Integer> sweet, List<Integer> need) {\r\n          Comparator comparator = new Comparator() {\r\n              @Override\r\n              public int compare(Object o1, Object o2) {\r\n                  return (int) o1 - (int) o2;\r\n              }\r\n          };\r\n          sweet.sort(comparator);\r\n          need.sort(comparator);\r\n          int s_size = sweet.size();\r\n          int n_size = need.size();\r\n          int s = 0,n =0;\r\n          while (s < s_size && n < n_size) {\r\n              if (((int) need.get(n)) <= ((int) sweet.get(s))) {\r\n                  n++;\r\n              }\r\n                  s++;\r\n          }\r\n          return n;\r\n      }\r\n  }\r\n  ```\r\n\r\n  ![image-20210228223952633](C:\\Users\\Administrator\\AppData\\Roaming\\Typora\\typora-user-images\\image-20210228223952633.png)\r\n\r\n  \r\n\r\n- ### 摆动数组：给定一个数组，求最长摆动子序列\r\n\r\n  \r\n\r\n```java\r\npackage com.zwj;\r\nimport java.util.ArrayList;\r\nimport java.util.Arrays;\r\nimport java.util.List;\r\n/**\r\n * @Author:zengwenjie\r\n * @Date:2021/2/28 18:53\r\n */\r\npublic class WiggleMaxLength {\r\n    static final int STATIC = 0;\r\n    static final int UP = 1;\r\n    static final int DOWN = 2;\r\n    public static void main(String[] args) {\r\n        long start = System.currentTimeMillis();\r\n        int[] array = {1,17,5,10,13,15,10,5,16,8};\r\n        ArrayList<Integer> list = new ArrayList<>();\r\n        System.out.println(\"输入的序列为：\"+\"-----\"+ Arrays.toString(array));\r\n        int result = new WiggleMaxLength().solution(array,list);\r\n        System.out.println(\"最长摆动子序列长度为\" +list.size());\r\n        System.out.println(\"最长摆动子序列为：\"+\"-----\"+list.toString());\r\n        long end = System.currentTimeMillis();\r\n        System.out.println(\"程序运行时间\" +\r\n                \"-----\"+(start-end)+\"ms\");\r\n\r\n\r\n    }\r\n\r\n    public int solution(int [] array, List<Integer> list) {\r\n        //开始状态设置设为 不变\r\n        int state = STATIC;\r\n        int maxLength = 1;\r\n        list.add(array[0]);\r\n//      从第二位元素开始遍历，比较与前一个元素的大小修改状态\r\n        for (int i = 1; i < array.length; i++) {\r\n            switch (state) {\r\n                case STATIC:\r\n                    if (array[i - 1] < array[i]) {\r\n                        state = UP;\r\n                        maxLength++;\r\n                        list.add(array[i]);\r\n                    } else if (array[i - 1] > array[i]) {\r\n                        state = DOWN;\r\n                        maxLength++;\r\n                        list.add(array[i]);\r\n                    }\r\n                    break;\r\n\r\n                case UP:\r\n                    if (array[i - 1] < array[i]) {\r\n                        //如果一直增加，则移除上次加入list的数，再加入当前这个更大的数，\r\n                        //保证最后加入摆动子序列是一直递增得到最大的那个数\r\n                        list.remove(list.size() - 1);\r\n                        list.add(array[i]);\r\n                    } else if (array[i - 1] > array[i]) {\r\n                        state = DOWN;\r\n                        maxLength++;\r\n                        list.add(array[i]);\r\n                    }\r\n                    break;\r\n                case DOWN:\r\n                    if (array[i - 1] < array[i]) {\r\n                        state = UP;\r\n                        maxLength++;\r\n                        list.add(array[i]);\r\n                    } else if (array[i - 1] > array[i]) {\r\n                        //如果一直减少，则移除上次加入list的数，再加入当前这个更小的数，\r\n                        //保证最后加入摆动子序列是一直递减得到最小的那个数\r\n                        list.remove(list.size() - 1);\r\n                        list.add(array[i]);\r\n                    }\r\n                    break;\r\n            }\r\n        }\r\n        return maxLength;\r\n    }\r\n```\r\n\r\n![image-20210228223933213](C:\\Users\\Administrator\\AppData\\Roaming\\Typora\\typora-user-images\\image-20210228223933213.png)\r\n\r\n# 2.递归\r\n\r\n> 把一个大型复杂的问题层层转化为一个与原问题相似的规模较小的问题来求解的思想\r\n\r\n# 3.回溯\r\n\r\n> 回溯法又称为试探法，但当探索到第一步时，发现原先的选择达不到目标，就退回一步重新选择，这种走不通就退回再走的思想成为回溯法。\r\n\r\n# 4.二叉树\r\n\r\n> 层序遍历记录高度的两种方法：\r\n>\r\n> 1. 根节点的高度为0，每一次循环（弹出结点，加入该结点的结点）在加入结点的同时，把当前结点高度+1绑定给新加入节点。*使用键值对把结点和高度绑定*\r\n>\r\n> 2. 第一次循环时，队列只含有一个结点，执行一次循环后得到恰好是第二层的所有结点。\r\n>\r\n>    如果在循环之前记录队列的个数size，就知道在执行size次循环后恰好是下一层的所有结点。\r\n>\r\n>    由此类推。\r\n\r\n# 5.图\r\n\r\n### 5.1环的判断\r\n\r\n1. 使用深搜，但是对visited数组修改为三种状态，深搜第一步先把当前结点的状态设置为`正在遍历`，每次对临接结点深搜之前，如果该结点的状态是`正在遍历`说明遇到了未搜索完退栈的结点，即出现环路，返回false。\r\n\r\n2. 使用广搜，邻接表进行拓扑排序是不方便对图的入度进行判断，一般会先准备好入度数组，从入读为0的结点开始进行广搜。若最后结点存在未访问结点，即有环。\r\n\r\n   > 广搜也可用三种状态标记，因为可能会出现一个结点被多次添加到队列的情况。\r\n\r\n## 5.2使用广搜得到单源最短路径（距离相同要求返回多条结果）\r\n\r\n> 遇到最短路径问题是时，往往需要准备一个数组（初始值为-1）记录搜索路径下所有结点的距离始点的距离，当临接点的距离大于所记录的距离不对该临界点进行搜索。\r\n>\r\n> 这样处理既避免了环路的问题。\r\n\r\n# 6.动态规划\r\n\r\n## 6.1动态规划基础\r\n\r\n- 动态规划是运筹学的一个分支，是求解决策过程最优化的数学方法。它是20世界50年代初美国数学家R.E.Bellman等人提出的最优化原理，它利用各阶段之间的关系，逐个求解，最终求得全局最优解。在设计动态规划算法时，需要确认原问题和子问题，动态规划状态，边界状态值，状态转移方程等关键因素。\r\n\r\n> 动态规划的状态转移方程不一定是连续的，方程的左边也可能出现很多个状态。\r\n>\r\n> 一个状态也可能需要保存多个值。	\r\n\r\n## 例题：\r\n\r\n1. 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。\r\n\r\n   给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。\r\n\r\n   > 输入：[1,2,3,1]\r\n   > 输出：4\r\n   > 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。\r\n   >   偷窃到的最高金额 = 1 + 3 = 4 。\r\n\r\n   dp[i]=Max{dp[i-1]),dp[i-2]+nums[i]}\r\n\r\n2. 假设你正在爬楼梯。需要 *n* 阶你才能到达楼顶。\r\n\r\n   每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？\r\n\r\n   **注意：**给定 *n* 是一个正整数。\r\n\r\n   > 输入： 2\r\n   > 输出： 2\r\n   > 解释： 有两种方法可以爬到楼顶。\r\n   >\r\n   > 1.  1 阶 + 1 阶\r\n   > 2.  2 阶\r\n\r\n   状态方程：\r\n\r\n   dp[i]=`dp[i-1]`+`dp[i-2]`\r\n\r\n3. 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。\r\n\r\n   你可以认为每种硬币的数量是无限的\r\n\r\n   > 输入：coins = [1, 2, 5], amount = 11\r\n   > 输出：3 \r\n   > 解释：11 = 5 + 5 + 1\r\n\r\n   状态方程：\r\n\r\n   设金额数组，int dp [amount]\r\n\r\n   dp[i]=Min{`dp[i-coins[0]]`,`dp[i-coins[1]]`,`dp[i-coins[2]]`...}+1', '1', 'post', 'publish', '', '算法', '105', '9', '1', '1', '1');
INSERT INTO `articles` VALUES ('11', 'IDEA使用与快捷键', null, '1617022490', '1617022490', null, null, '# IDEA常用快捷键\r\n\r\n1. `ctrl+shift+E`   显示最近的编辑位置\r\n2. `alt+D` 禁用/启用断点（自定义）\r\n\r\n# IDEA使用心得\r\n\r\n## 1.web使用中的热部署VS重新部署\r\n\r\n> 在debug模式中，点击更新类和资源可以直接热部署，不需要重新部署工件\r\n\r\n![image-20200908172543212](./IDEA使用快捷键.assets/image-20200908172543212.png)\r\n\r\n## 2.断点使用技巧\r\n\r\n### 2.1点击按钮可以一键禁用所有断点\r\n\r\n![image-20200908171949890](IDEA使用快捷键.assets/image-20200908171949890.png)\r\n\r\n## 2.2 断点的不可追踪\r\n\r\n- 由于java代码中都会常见地使用动态代理技术，动态代理相当于用代码在内存中生成了一个新的.class文件作为代理对象使用，代理有同原对象一样的方法，但调用前后会定义写操作对原对象进行增强。\r\n\r\n- 当断点执行到代理对象时断点不可追踪，下一步则直接跳出。但值得注意的是，代理对象终究是代理，它紧接着执行原对象的方法。\r\n\r\n- spring容器中所有的对象都是动态代理增强过的对象，当容器中代理对象执行方法是通常会被拦截，而导致执行到这种方法时点击下一步idea会自动跳到代理对象调用invokesuper( )后的。此时栈中已经多出很多帧，**期间断点不可追踪**，只能看到过程。\r\n\r\n  ![image-20210128175250125](IDEA使用与快捷键/image-20210128175250125.png) \r\n\r\n## 2.3 断点突遇天外\"帧\"来如何处理\r\n\r\n> 1.找到上一步所处帧位置的下一行，打断点并运行至此。\r\n>\r\n> 2.点击\"跳出\"，逐帧跳出。每次点击\"跳出\"为弹出一帧。\r\n\r\n## 2.4IDEA中的各种断点\r\n\r\n- 行断点\r\n\r\n  直接点击所在行标注即为行断点\r\n\r\n- 详细断点\r\n\r\n  按住shift点击所在行会弹出断点配置的高级配置\r\n\r\n- 方法断点\r\n\r\n  可以打在任意方法上包括接口中方法，会自动停在方法的第一行和方法的最后一行。\r\n\r\n- 异常断点\r\n\r\n  点击查看所有断点可以添加异常断点，如添加一个空指针断点，所有会发生空指针错误的前一行执行时会停住。\r\n\r\n- 字段断点\r\n\r\n  断点标注在类的字段上，即监控该字段，每次修改时会停住。\r\n\r\n## 3.捕获的错误处理\r\n\r\n捕获的错误如果不输出调用栈信息则不会显示任何错误。\r\n\r\n在有些使用日志记录错误信息的程序，在运行过程中如果不添上调用栈打印的语句就很难排错\r\n\r\n![image-20200910160149221](IDEA使用与快捷键.assets/image-20200910160149221.png)\r\n\r\n捕获源码抛出的错误，再自己抛出一个对排错更友好的错误\r\n\r\n![image-20200910160431449](IDEA使用与快捷键.assets/image-20200910160431449.png)\r\n\r\n## 4.从github上下载的项目的启动\r\n\r\n从github上下载的项目转移到自己电脑的时往往会出现一些错误，常见的有：\r\n\r\n- 编码错误——编码错误在idea右下角设置项目编码可以迅速解决\r\n\r\n- 文件路径错误\r\n\r\n  因为当项目导入idea中时，idea只会默认地标注自己能识别的原码的目录，如java、src等等\r\n\r\n  而大多数情况下原码作者可能标注了多个源码目录，需要对运行的错误信息适当的标注源码目录。\r\n\r\n![image-20200910161612894](IDEA使用与快捷键.assets/image-20200910161612894.png)\r\n\r\n> ide中\"结构目录\"最终都不是最终的输出结构，只有标注了源码或资源的目录下的文件才会加入到真正的类路径。如上四个文件夹若要正常使用于代码中，除了lib之外全部都要标位源码或资源目录。（添加为库的lib在编译时不会输出到类路径，但运行时会处于同一类路径）最终所有标为源码或资源目录下的所有文件都会输出到一个叫classes的文件下。故实际所有标为源码或资源目录以及web目录处于同一层。\r\n\r\n\r\n\r\n![image-20200910161452217](IDEA使用与快捷键.assets/image-20200910161452217.png)\r\n\r\n- 库文件错误\r\n\r\n  - ​	检查项目结构的库文件，如果没有添加到库则新建项目库\r\n\r\n    ![image-20200910162056631](IDEA使用与快捷键.assets/image-20200910162056631.png)\r\n\r\n  - ​    选择添加lib文件\r\n\r\n    ![image-20200910162245552](IDEA使用与快捷键.assets/image-20200910162245552.png)\r\n\r\n\r\n\r\n## 5.双击打开首页\r\n\r\n![image-20210131194538491](IDEA使用与快捷键/image-20210131194538491.png)\r\n\r\n## 6.工件的部署\r\n\r\nweb项目不同于常规项目，最大的不同是web项目不是由我们启动，而是写好代码部署在指定的环境，让服务器来执行我们写的代码。这类项目部署的文件在idea中叫构件“artifacts”\r\n\r\n![image-20210131194917853](IDEA使用与快捷键/image-20210131194917853.png)\r\n\r\nweb项目运行前有两步，一是正常编译java代码，二是输出构件“artifacts”\r\n\r\n​	![image-20210131195218483](IDEA使用与快捷键/image-20210131195218483.png)\r\n\r\n编译好的java代码和web项目所用到的库都会统一拷贝到WEB-IF目录下，java代码从类路径打包成classes，项目库放到lib，下图target为编译生成的java代码，artifacts为输出的构件\r\n\r\n> 在最终输出的构件里，WEB-INF下放lib和classes是Tomcat约定的，只有这么放才能让Tomcat正确调用。\r\n\r\n![image-20210131195550194](IDEA使用与快捷键/image-20210131195550194.png)\r\n\r\n因此，web项目构件输出的配置尤为重要，很多时候web项目无法启动的原因就是web构件配置错误\r\n\r\n![image-20210131200448223](IDEA使用与快捷键/image-20210131200448223.png)\r\n\r\n> 构件输出分为两个部分，其一WEB-INF包括java代码和库文件，另一个是webapp下的静态资源，\r\n>\r\n> 可以选择哪些库文件最终输出到构件，这与编译无关\r\n\r\n# IDEA项目结构\r\n\r\n## 1.项目\r\n\r\n需要配置JDK\r\n\r\n![image-20200914151054391](IDEA使用与快捷键/image-20200914151054391.png)\r\n\r\n## 2.模块\r\n\r\n配置源码目录，添加依赖等\r\n\r\n![image-20200914151711865](IDEA使用与快捷键/image-20200914151711865.png)\r\n\r\n手动为模块添加tomcat依赖\r\n\r\n![image-20200914170333397](IDEA使用与快捷键/image-20200914170333397.png)\r\n\r\n为导入的web项目手动指定web.xml文件\r\n\r\n![image-20200914151507681](IDEA使用与快捷键/image-20200914151507681.png)\r\n\r\n## 3.库文件\r\n\r\n管理项目库文件，分为全局库和模块库\r\n\r\n![image-20200914151903313](IDEA使用与快捷键/image-20200914151903313.png)\r\n\r\n## 4.facet\r\n\r\n配置和查看项目所有的框架\r\n\r\n![image-20200914152053874](IDEA使用与快捷键/image-20200914152053874.png)\r\n\r\n## 5.工件部署\r\n\r\n服务于所有需要服务器部署的模块和主项目\r\n\r\n![image-20200914152204019](IDEA使用与快捷键/image-20200914152204019.png)\r\n\r\n## 6.输出\r\n\r\n> idea输出文件包括\r\n> artifacts  部署的工件\r\n>\r\n> production 模块编译后的原码\r\n>\r\n> target  项目默认创建的src输出，在和.out同目录下target目录\r\n\r\n<span style=\"color:red\">检查一个web项目是否配置成功只需要查看artifacts目录下有没有相应的工件部署</span>\r\n\r\n![image-20200914153613297](IDEA使用与快捷键/image-20200914153613297.png)\r\n\r\n![image-20200914154725853](IDEA使用与快捷键/image-20200914154725853.png)\r\n\r\n# IDEA常见错误\r\n\r\n## 1.编码问题\r\n\r\n![image-20201103110604953](IDEA使用与快捷键/image-20201103110604953.png)\r\n\r\n右下角文件编码应为GBK，否则会出现编码问题\r\n\r\nwindows倘若配置成其他编码，代码中写的任何中文，直接在内存中就出现乱码\r\n\r\n![image-20201103110852382](IDEA使用与快捷键/image-20201103110852382.png)', '1', 'post', 'publish', 'idea', '开发工具', '24', '0', '1', '1', '1');
INSERT INTO `articles` VALUES ('12', 'mysql数据库学习笔记', null, '1617022561', '1617022561', null, null, '# 1.maven简介\r\n\r\n- maven是Apache下管理项目的工具\r\n\r\n- pom是project object model（项目对象模型）的简称，为maven的配置文件，描述项目种种。\r\n\r\n- maven有统一的目录结构，在eclipse和idea达到统一\r\n\r\n  > {base}/src/main/java\r\n  >\r\n  > {base}/src/main/resource\r\n  >\r\n  > {base}/src/test/java\r\n  >\r\n  > {base}/src/test/resource\r\n  >\r\n  > {base}/pom.xml\r\n\r\n- maven能自动完成程序的构建\r\n\r\n  构建包括：\r\n\r\n  - 清理：将以前编译的字节码文件删除，为下一次编译做准备\r\n  - 编译\r\n  - 测试：自动测试，自动调用Junit程序\r\n  - 报告\r\n  - 打包\r\n  - 安装：maven特定的概念——将打包得到的文件复制到“仓库”中进行统一管理，使用坐标引用。\r\n  - 部署\r\n\r\n  \r\n\r\n# 2.maven安装和配置\r\n\r\n## 2.1安装\r\n\r\n下载镜像配置好环境变量即可\r\n\r\n![image-20210124173421437](C:\\Users\\Administrator\\AppData\\Roaming\\Typora\\typora-user-images\\image-20210124173421437.png)\r\n\r\n![image-20210124173514993](C:\\Users\\Administrator\\AppData\\Roaming\\Typora\\typora-user-images\\image-20210124173514993.png)\r\n\r\n## 2.2远程镜像地址和本地仓库路径修改\r\n\r\n- 配置文件 位置\r\n\r\n  ![image-20210124173726011](C:\\Users\\Administrator\\AppData\\Roaming\\Typora\\typora-user-images\\image-20210124173726011.png)\r\n\r\n  配置阿里镜像地址取代原镜像地址\r\n\r\n  ```xml\r\n  <mirror>\r\n      <id>nexus-aliyun</id>\r\n      <mirrorOf>*</mirrorOf>\r\n      <name>Nexus aliyun</name>\r\n      <url>http://maven.aliyun.com/nexus/content/groups/public</url>\r\n  </mirror> \r\n  ```\r\n\r\n  本地仓库路径修改\r\n\r\n  ```xml\r\n  <localRepository>F:/develop-software/m2/repository</localRepository\r\n  ```\r\n\r\n  \r\n\r\n# 3.常用命令\r\n\r\n命令格式： mvn [plugin-name]:[goal-name]\r\n\r\n| 命令                                                         | 描述 |\r\n| ------------------------------------------------------------ | ---- |\r\n| mvn -version                                                 |      |\r\n| mvn clean                                                    |      |\r\n| mvn compile                                                  |      |\r\n| mvn test                                                     |      |\r\n| mvn install                                                  |      |\r\n| mvn deploy                                                   |      |\r\n| mvn compile 编译                                             |      |\r\n| mvn exec:java -Dexec.mainClass=<br/>`\"com.zwj.mvnstudy.HelloWorld\"` <br/>指定路径 报名+类名mvn site |      |\r\n| mvn eclipse:eclipse                                          |      |\r\n| mvn dependency:tree                                          |      |\r\n| mvn archetype:generate                                       |      |\r\n| mvn tomcat7:run                                              |      |\r\n| mvn jetty:run                                                |      |\r\n\r\n> 手动运行时需要cmd窗口切到pom目录\r\n\r\n# 4.maven初步\r\n\r\n## 4.1 坐标\r\n\r\n> ```xml\r\n> <groupId>com.zwj</groupId>\r\n> <artifactId>Web_maven</artifactId>\r\n> <version>1.0-SNAPSHOT</version>  //快照指的是当前版本并不稳定，迭代速度很快，是每一个瞬间的版本\r\n> 而release指的是正式发布的版本\r\n> ```\r\n\r\n即使在依赖中指明了坐标，但是在该模块完成install之前，本地仓库是没有这个包的\r\n\r\n三者构成一个资源坐标，引入插件，配置依赖都需要用到\r\n\r\n## 4.2 build配置\r\n\r\n```xml\r\n<build>\r\n  <finalName>Web_maven</finalName>\r\n  <plugins>\r\n      <plugin>\r\n      </plugin>\r\n  </plugins>\r\n```\r\n\r\nweb开发常用两个build工具\r\n\r\n- tomcat plugin\r\n\r\n  ```xml\r\n  <plugin>\r\n    <groupId>org.apache.tomcat.maven</groupId>\r\n    <artifactId>tomcat7-maven-plugin</artifactId>\r\n    <version>2.1</version>\r\n    <configuration>\r\n      <hostName>localhost</hostName>        <!--   Default: localhost -->\r\n      <port>8081</port>                     <!-- 启动端口 Default:8080 -->\r\n      <path>/test</path>   <!-- 访问应用路径  Default: /${project.artifactId}-->\r\n      <uriEncoding>UTF-8</uriEncoding>      <!-- uri编码 Default: ISO-8859-1 -->\r\n      <server>tomcat7</server>\r\n    </configuration>\r\n  </plugin>\r\n  ```\r\n\r\n- jetty plugin\r\n\r\n  ```xml\r\n  <plugin>\r\n    <groupId>org.mortbay.jetty</groupId>\r\n    <artifactId>maven-jetty-plugin</artifactId>\r\n    <version>6.1.25</version>\r\n    <configuration>\r\n      <scanIntervalSeconds>10</scanIntervalSeconds>\r\n        <contextPath>/Web-maven</contextPath><!--添加为项目名称即可-->\r\n        <connectors>\r\n          <connector implementation=\"org.mortbay.jetty.nio.SelectChannelConnector\">\r\n            <port>9090</port>\r\n          </connector>\r\n        </connectors>\r\n  \r\n    </configuration>\r\n  </plugin>\r\n  ```\r\n\r\n> 引入build插件后，使用新的命令进行编译和启动，以上两个插件会自动进行热部署，以下为idea启动配置\r\n\r\n![image-20210124172841934](C:\\Users\\Administrator\\AppData\\Roaming\\Typora\\typora-user-images\\image-20210124172841934.png)\r\n\r\n![image-20210124172907571](C:\\Users\\Administrator\\AppData\\Roaming\\Typora\\typora-user-images\\image-20210124172907571.png)\r\n\r\n# 5.maven仓库概念\r\n\r\n- maven仓库分为远程仓库和本地仓库，中央仓库是默认的远程仓库，（默认地址声明在maven-model-builder-xxx.jar中的一个超级POM中）上述镜像地址的修改即仓库地址的修改。\r\n\r\n- 另外还有一种远程仓库——私服，是架设在局域网的仓库服务，一般为公司内部自己搭建的远程仓库，供局域网的公司用户使用。\r\n\r\n- 第三种远程仓库即第三方公共库，如上述配置的阿里仓库。\r\n\r\n- 所有需要的依赖都可以访问中央仓库和第三方公共仓库官网获得依赖的“坐标”\r\n\r\n  > 仓库中的内容：\r\n  >\r\n  > maven自身需要的插件\r\n  >\r\n  > 第三方框架或工具的jar包\r\n  >\r\n  > 我们自己开发的maven工程 （故而依赖引用自己的包也需要安装才能使用）\r\n\r\n# 6.依赖dependency\r\n\r\n- 通过坐标引用\r\n- 依赖范围属性：\r\n  - compile范围依赖\r\n    - 对主程序是否有效：有效\r\n    - 对测试程序是否有效：有效\r\n    - 是否参与打包：参与\r\n  - test\r\n    - 对主程序是否有效：无效\r\n    - 对测试程序是否有效：有效\r\n    - 是否参与打包：不参与\r\n    - 典型例子：Junit\r\n  - provided\r\n    - 对主程序是否有效：有效\r\n    - 对测试程序是否有效：有效\r\n    - 是否参与打包：不参与\r\n    - 是否参与部署：不参与\r\n    - 典型例子：servlet-api\r\n\r\n# 7.maven多模块构建\r\n\r\n- 创建maven-parent及其子模块\r\n\r\n  ![image-20210124203429211](C:\\Users\\Administrator\\AppData\\Roaming\\Typora\\typora-user-images\\image-20210124203429211.png)\r\n\r\n- 配置pom依赖关系，controller依赖service，service依赖dao\r\n\r\n  在controller的pom依赖中填入service的坐标，其他类同\r\n\r\n  ```xml\r\n  <dependency>\r\n              <groupId>com.zwj</groupId>\r\n              <artifactId>maven-service</artifactId>\r\n              <version>1.0</version>\r\n  </dependency>\r\n  ```\r\n\r\n- 在controller中引入servlet依赖并配置tomcat启动插件\r\n\r\n  - 引入依赖\r\n\r\n    ```xml\r\n    <dependency>\r\n        <groupId>javax.servlet</groupId>\r\n        <artifactId>javax.servlet-api</artifactId>\r\n        <version>3.1.0</version>\r\n        <scope>provided</scope>\r\n    </dependency>\r\n    ```\r\n\r\n    > `<scope>provided</scope>`必要，因为tomcat jar是本地的，依赖本地的才有用。\r\n\r\n  - tomcat插件\r\n\r\n    ```xml\r\n    <plugin>\r\n        <groupId>org.apache.tomcat.maven</groupId>\r\n        <artifactId>tomcat7-maven-plugin</artifactId>\r\n        <version>2.1</version>\r\n        <configuration>\r\n            <hostName>localhost</hostName>        <!--   Default: localhost -->\r\n            <port>8081</port>                     <!-- 启动端口 Default:8080 -->\r\n            <path>/test</path>   <!-- 访问应用路径  Default: /${project.artifactId}-->\r\n            <uriEncoding>UTF-8</uriEncoding>      <!-- uri编码 Default: ISO-8859-1 -->\r\n            <server>tomcat7</server>\r\n        </configuration>\r\n    </plugin>\r\n    ```', '1', 'post', 'publish', 'mysql', '开发工具', '24', '2', '1', '1', '1');
INSERT INTO `articles` VALUES ('13', '布局案例', null, '1617024828', '1621620501', null, '', '---\r\ntitle:css布局基础\r\ndate: 2021-09-20 17:20:03\r\ntags:前端\r\n\r\n---\r\n\r\n# 1.元素分类\r\n\r\n## 1.1块元素\r\n\r\n- 块元素独占一行，默认宽度百分之百，高度自适应\r\n- 父子元素上边界平齐时，子元素外边距会传递给父元素\r\n- 相邻块元素之间上下外边距会重合，如上面的元素设置下边距，下面的元素设置上边距，最终两元素上下相距其中最大值。\r\n\r\n## 1.2行内元素\r\n\r\n- 行内元素无法设置宽高\r\n- 行内元素可以设置内边距、边框、外边距，但是垂直方向不影响页面布局。\r\n\r\n## 1.3行内块元素\r\n\r\n- 可以设置宽高，且宽度默认不会占父元素的百分之百\r\n- 其他属性同块元素\r\n\r\n# 2.BFC\r\n\r\n## 2.1BFC 概念\r\n\r\nFormatting context(格式化上下文) 是 W3C CSS2.1 规范中的一个概念。它是页面中的一块渲染区域，并且有一套渲染规则，它决定了其子元素将如何定位，以及和其他元素的关系和相互作用。\r\n\r\n那么 BFC 是什么呢？\r\n\r\nBFC 即 Block Formatting Contexts (块级格式化上下文)，它属于上述定位方案的普通流。\r\n\r\n**具有 BFC 特性的元素可以看作是隔离了的独立容器，容器里面的元素不会在布局上影响到外面的元素，并且 BFC 具有普通容器所没有的一些特性。\r\n\r\n**通俗一点来讲，可以把 BFC 理解为一个封闭的大箱子，箱子内部的元素无论如何翻江倒海，都不会影响到外部。\r\n\r\n## 2.2BFC触发条件\r\n\r\n- html 根元素就是一个BFC\r\n- 浮动元素：float 除 none 以外的值\r\n- 绝对定位元素：position (absolute、fixed)\r\n- display 为 inline-block、table-cells、flex\r\n- overflow 除了 visible 以外的值 (hidden、auto、scroll)\r\n\r\n> inline-block不适合作为容器，因为宽度丢失了。\r\n\r\n## 2.3BFC特性\r\n\r\n- ### 同一个BFC下外边距会发生折叠\r\n\r\n```html\r\n<head>\r\ndiv{\r\n    width: 100px;\r\n    height: 100px;\r\n    background: lightblue;\r\n    margin: 100px;\r\n}\r\n</head>\r\n<body>\r\n    <div></div>\r\n    <div></div>\r\n</body>\r\n```\r\n\r\n![img](布局案例/v2-0a9ca8952c83141250a2d9002e6d2047_720w.png)\r\n\r\n从效果上看，因为两个 div 元素都处于同一个 BFC 容器下 (这里指 body 元素) 所以第一个 div 的下边距和第二个 div 的上边距发生了重叠，所以两个盒子之间距离只有 100px，而不是 200px。\r\n\r\n首先这不是 CSS 的 bug，我们可以理解为一种规范，**如果想要避免外边距的重叠，可以将其放在不同的 BFC 容器中。**\r\n\r\n​	\r\n\r\n```html\r\n<div class=\"container\">\r\n    <p></p>\r\n</div>\r\n<div class=\"container\">\r\n    <p></p>\r\n</div>\r\n.container {\r\n    overflow: hidden;\r\n}\r\np {\r\n    width: 100px;\r\n    height: 100px;\r\n    background: lightblue;\r\n    margin: 100px;\r\n}\r\n```\r\n\r\n这时候，两个盒子边距就变成了 200px\r\n\r\n![preview](布局案例/v2-5b8d6e8b2b507352900c1ece00018855_r.jpg)\r\n\r\n-  **BFC 可以包含浮动的元素（清除浮动）**\r\n\r\n我们都知道，浮动的元素会脱离普通文档流，来看下下面一个例子\r\n\r\n```html\r\n<div style=\"border: 1px solid #000;\">\r\n    <div style=\"width: 100px;height: 100px;background: #eee;float: left;\"></div>\r\n</div>\r\n```\r\n\r\n![img](布局案例/v2-371eb702274af831df909b2c55d6a14b_720w.png)\r\n\r\n由于容器内元素浮动，脱离了文档流，所以容器只剩下 2px 的边距高度。如果使触发容器的 BFC，那么容器将会包裹着浮动元素。\r\n\r\n```html\r\n<div style=\"border: 1px solid #000;overflow: hidden\">\r\n    <div style=\"width: 100px;height: 100px;background: #eee;float: left;\"></div>\r\n</div>\r\n```\r\n\r\n![img](https://pic4.zhimg.com/80/v2-cc8365db5c9cc5ca003ce9afe88592e7_720w.png)\r\n\r\n- **BFC 可以阻止元素被浮动元素覆盖**\r\n\r\n先来看一个文字环绕效果：\r\n\r\n```html\r\n<div style=\"height: 100px;width: 100px;float: left;background: lightblue\">我是一个左浮动的元素</div>\r\n<div style=\"width: 200px; height: 200px;background: #eee\">我是一个没有设置浮动, \r\n也没有触发 BFC 元素, width: 200px; height:200px; background: #eee;</div>\r\n```\r\n\r\n![img](布局案例/v2-dd3e636d73682140bf4a781bcd6f576b_720w.png)\r\n\r\n这时候其实第二个元素有部分被浮动元素所覆盖，(但是文本信息不会被浮动元素所覆盖) 如果想避免元素被覆盖，可触第二个元素的 BFC 特性，在第二个元素中加入 **overflow: hidden**，就会变成：\r\n\r\n![img](布局案例/v2-5ebd48f09fac875f0bd25823c76ba7fa_720w.png)\r\n\r\n这个方法可以用来实现两列自适应布局，效果不错，这时候左边的宽度固定，右边的内容自适应宽度(去掉上面右边内容的宽度)。\r\n\r\n# 3.其他细节\r\n\r\n- 有些网页会设置html和body的高度为：100%，这意味着html的高度为浏览器窗口高度，body自然也为浏览器窗口高度。这通常用来实现当内容高度不足窗口高度时页脚的固定（如下图）。默认html和body的高度为0，有body的内容撑开。所以无需纠结body内部元素的高度加起来不为body的高度，因为在body没有设置overflow：hidden的情况下，没有任何影响。\r\n\r\n  ![image-20210202194045517](布局案例/image-20210202194045517.png)\r\n\r\n- 开启绝对定位会脱离文档流，独立于定位参考的所有的元素，但在没有设置偏移量时，位置仍处于原来在文档流中的位置。\r\n\r\n- margin为百分比时，相对的父元素的宽度\r\n\r\n[常用布局]: https://sweet-kk.github.io/css-layout/', '1', 'post', 'publish', '前端,博客', '默认分类', '24', '2', '1', '1', '1');
INSERT INTO `articles` VALUES ('14', 'maven入门', null, '1617024884', '1621600845', null, null, '# 1.maven简介\r\n\r\n- maven是Apache下管理项目的工具\r\n\r\n- pom是project object model（项目对象模型）的简称，为maven的配置文件，描述项目种种。\r\n\r\n- maven有统一的目录结构，在eclipse和idea达到统一\r\n\r\n  > {base}/src/main/java\r\n  >\r\n  > {base}/src/main/resource\r\n  >\r\n  > {base}/src/test/java\r\n  >\r\n  > {base}/src/test/resource\r\n  >\r\n  > {base}/pom.xml\r\n\r\n- maven能自动完成程序的构建\r\n\r\n  构建包括：\r\n\r\n  - 清理：将以前编译的字节码文件删除，为下一次编译做准备\r\n  - 编译\r\n  - 测试：自动测试，自动调用Junit程序\r\n  - 报告\r\n  - 打包\r\n  - 安装：maven特定的概念——将打包得到的文件复制到“仓库”中进行统一管理，使用坐标引用。\r\n  - 部署\r\n\r\n  \r\n\r\n# 2.maven安装和配置\r\n\r\n## 2.1安装\r\n\r\n下载镜像配置好环境变量即可\r\n\r\n![image-20210124173421437](C:\\Users\\Administrator\\AppData\\Roaming\\Typora\\typora-user-images\\image-20210124173421437.png)\r\n\r\n![image-20210124173514993](C:\\Users\\Administrator\\AppData\\Roaming\\Typora\\typora-user-images\\image-20210124173514993.png)\r\n\r\n## 2.2远程镜像地址和本地仓库路径修改\r\n\r\n- 配置文件 位置\r\n\r\n  ![image-20210124173726011](C:\\Users\\Administrator\\AppData\\Roaming\\Typora\\typora-user-images\\image-20210124173726011.png)\r\n\r\n  配置阿里镜像地址取代原镜像地址\r\n\r\n  ```xml\r\n  <mirror>\r\n      <id>nexus-aliyun</id>\r\n      <mirrorOf>*</mirrorOf>\r\n      <name>Nexus aliyun</name>\r\n      <url>http://maven.aliyun.com/nexus/content/groups/public</url>\r\n  </mirror> \r\n  ```\r\n\r\n  本地仓库路径修改\r\n\r\n  ```xml\r\n  <localRepository>F:/develop-software/m2/repository</localRepository\r\n  ```\r\n\r\n  \r\n\r\n# 3.常用命令\r\n\r\n命令格式： mvn [plugin-name]:[goal-name]\r\n\r\n| 命令                                                         | 描述 |\r\n| ------------------------------------------------------------ | ---- |\r\n| mvn -version                                                 |      |\r\n| mvn clean                                                    |      |\r\n| mvn compile                                                  |      |\r\n| mvn test                                                     |      |\r\n| mvn install                                                  |      |\r\n| mvn deploy                                                   |      |\r\n| mvn compile 编译                                             |      |\r\n| mvn exec:java -Dexec.mainClass=<br/>`\"com.zwj.mvnstudy.HelloWorld\"` <br/>指定路径 报名+类名mvn site |      |\r\n| mvn eclipse:eclipse                                          |      |\r\n| mvn dependency:tree                                          |      |\r\n| mvn archetype:generate                                       |      |\r\n| mvn tomcat7:run                                              |      |\r\n| mvn jetty:run                                                |      |\r\n\r\n> 手动运行时需要cmd窗口切到pom目录\r\n\r\n# 4.maven初步\r\n\r\n## 4.1 坐标\r\n\r\n> ```xml\r\n> <groupId>com.zwj</groupId>\r\n> <artifactId>Web_maven</artifactId>\r\n> <version>1.0-SNAPSHOT</version>  //快照指的是当前版本并不稳定，迭代速度很快，是每一个瞬间的版本\r\n> 而release指的是正式发布的版本\r\n> ```\r\n\r\n即使在依赖中指明了坐标，但是在该模块完成install之前，本地仓库是没有这个包的\r\n\r\n三者构成一个资源坐标，引入插件，配置依赖都需要用到\r\n\r\n## 4.2 build配置\r\n\r\n```xml\r\n<build>\r\n  <finalName>Web_maven</finalName>\r\n  <plugins>\r\n      <plugin>\r\n      </plugin>\r\n  </plugins>\r\n```\r\n\r\nweb开发常用两个build工具\r\n\r\n- tomcat plugin\r\n\r\n  ```xml\r\n  <plugin>\r\n    <groupId>org.apache.tomcat.maven</groupId>\r\n    <artifactId>tomcat7-maven-plugin</artifactId>\r\n    <version>2.1</version>\r\n    <configuration>\r\n      <hostName>localhost</hostName>        <!--   Default: localhost -->\r\n      <port>8081</port>                     <!-- 启动端口 Default:8080 -->\r\n      <path>/test</path>   <!-- 访问应用路径  Default: /${project.artifactId}-->\r\n      <uriEncoding>UTF-8</uriEncoding>      <!-- uri编码 Default: ISO-8859-1 -->\r\n      <server>tomcat7</server>\r\n    </configuration>\r\n  </plugin>\r\n  ```\r\n\r\n- jetty plugin\r\n\r\n  ```xml\r\n  <plugin>\r\n    <groupId>org.mortbay.jetty</groupId>\r\n    <artifactId>maven-jetty-plugin</artifactId>\r\n    <version>6.1.25</version>\r\n    <configuration>\r\n      <scanIntervalSeconds>10</scanIntervalSeconds>\r\n        <contextPath>/Web-maven</contextPath><!--添加为项目名称即可-->\r\n        <connectors>\r\n          <connector implementation=\"org.mortbay.jetty.nio.SelectChannelConnector\">\r\n            <port>9090</port>\r\n          </connector>\r\n        </connectors>\r\n  \r\n    </configuration>\r\n  </plugin>\r\n  ```\r\n\r\n> 引入build插件后，使用新的命令进行编译和启动，以上两个插件会自动进行热部署，以下为idea启动配置\r\n\r\n![image-20210124172841934](C:\\Users\\Administrator\\AppData\\Roaming\\Typora\\typora-user-images\\image-20210124172841934.png)\r\n\r\n![image-20210124172907571](C:\\Users\\Administrator\\AppData\\Roaming\\Typora\\typora-user-images\\image-20210124172907571.png)\r\n\r\n# 5.maven仓库概念\r\n\r\n- maven仓库分为远程仓库和本地仓库，中央仓库是默认的远程仓库，（默认地址声明在maven-model-builder-xxx.jar中的一个超级POM中）上述镜像地址的修改即仓库地址的修改。\r\n\r\n- 另外还有一种远程仓库——私服，是架设在局域网的仓库服务，一般为公司内部自己搭建的远程仓库，供局域网的公司用户使用。\r\n\r\n- 第三种远程仓库即第三方公共库，如上述配置的阿里仓库。\r\n\r\n- 所有需要的依赖都可以访问中央仓库和第三方公共仓库官网获得依赖的“坐标”\r\n\r\n  > 仓库中的内容：\r\n  >\r\n  > maven自身需要的插件\r\n  >\r\n  > 第三方框架或工具的jar包\r\n  >\r\n  > 我们自己开发的maven工程 （故而依赖引用自己的包也需要安装才能使用）\r\n\r\n# 6.依赖dependency\r\n\r\n- 通过坐标引用\r\n- 依赖范围属性：\r\n  - compile范围依赖\r\n    - 对主程序是否有效：有效\r\n    - 对测试程序是否有效：有效\r\n    - 是否参与打包：参与\r\n  - test\r\n    - 对主程序是否有效：无效\r\n    - 对测试程序是否有效：有效\r\n    - 是否参与打包：不参与\r\n    - 典型例子：Junit\r\n  - provided\r\n    - 对主程序是否有效：有效\r\n    - 对测试程序是否有效：有效\r\n    - 是否参与打包：不参与\r\n    - 是否参与部署：不参与\r\n    - 典型例子：servlet-api\r\n\r\n# 7.maven多模块构建\r\n\r\n- 创建maven-parent及其子模块\r\n\r\n  ![image-20210124203429211](C:\\Users\\Administrator\\AppData\\Roaming\\Typora\\typora-user-images\\image-20210124203429211.png)\r\n\r\n- 配置pom依赖关系，controller依赖service，service依赖dao\r\n\r\n  在controller的pom依赖中填入service的坐标，其他类同\r\n\r\n  ```xml\r\n  <dependency>\r\n              <groupId>com.zwj</groupId>\r\n              <artifactId>maven-service</artifactId>\r\n              <version>1.0</version>\r\n  </dependency>\r\n  ```\r\n\r\n- 在controller中引入servlet依赖并配置tomcat启动插件\r\n\r\n  - 引入依赖\r\n\r\n    ```xml\r\n    <dependency>\r\n        <groupId>javax.servlet</groupId>\r\n        <artifactId>javax.servlet-api</artifactId>\r\n        <version>3.1.0</version>\r\n        <scope>provided</scope>\r\n    </dependency>\r\n    ```\r\n\r\n    > `<scope>provided</scope>`必要，因为tomcat jar是本地的，依赖本地的才有用。\r\n\r\n  - tomcat插件\r\n\r\n    ```xml\r\n    <plugin>\r\n        <groupId>org.apache.tomcat.maven</groupId>\r\n        <artifactId>tomcat7-maven-plugin</artifactId>\r\n        <version>2.1</version>\r\n        <configuration>\r\n            <hostName>localhost</hostName>        <!--   Default: localhost -->\r\n            <port>8081</port>                     <!-- 启动端口 Default:8080 -->\r\n            <path>/test</path>   <!-- 访问应用路径  Default: /${project.artifactId}-->\r\n            <uriEncoding>UTF-8</uriEncoding>      <!-- uri编码 Default: ISO-8859-1 -->\r\n            <server>tomcat7</server>\r\n        </configuration>\r\n    </plugin>\r\n    ```', '1', 'post', 'publish', 'maven', '开发工具', '3', '0', '1', '1', '1');
INSERT INTO `articles` VALUES ('99', 'Jsp和Jstl表达式', null, '1621617709', '1621620693', null, '', '# 1.JSTL配置\r\n\r\n## 1.1下载所需库文件\r\n\r\n- 下载 **jakarta-taglibs-standard-1.1.2.zip** 包并解压，将 **jakarta-taglibs-standard-1.1.2/lib/** 下的两个 jar 文件：**standard.jar** 和 **jstl.jar** 文件拷贝到 **/WEB-INF/lib/** 下。\r\n\r\n  将 tld 下的需要引入的 tld 文件复制到 WEB-INF 目录下。\r\n\r\n  接下来我们在 web.xml 文件中添加以下配置：\r\n\r\n  ```xml\r\n  <?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n  <web-app version=\"2.4\" \r\n      xmlns=\"http://java.sun.com/xml/ns/j2ee\" \r\n      xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n      xsi:schemaLocation=\"http://java.sun.com/xml/ns/j2ee \r\n          http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd\">\r\n      <jsp-config>\r\n      <taglib>\r\n      <taglib-uri>http://java.sun.com/jsp/jstl/fmt</taglib-uri>\r\n      <taglib-location>/WEB-INF/fmt.tld</taglib-location>\r\n      </taglib>\r\n      <taglib>\r\n      <taglib-uri>http://java.sun.com/jsp/jstl/fmt-rt</taglib-uri>\r\n      <taglib-location>/WEB-INF/fmt-rt.tld</taglib-location>\r\n      </taglib>\r\n      <taglib>\r\n      <taglib-uri>http://java.sun.com/jsp/jstl/core</taglib-uri>\r\n      <taglib-location>/WEB-INF/c.tld</taglib-location>\r\n      </taglib>\r\n      <taglib>\r\n      <taglib-uri>http://java.sun.com/jsp/jstl/core-rt</taglib-uri>\r\n      <taglib-location>/WEB-INF/c-rt.tld</taglib-location>\r\n      </taglib>\r\n      <taglib>\r\n      <taglib-uri>http://java.sun.com/jsp/jstl/sql</taglib-uri>\r\n      <taglib-location>/WEB-INF/sql.tld</taglib-location>\r\n      </taglib>\r\n      <taglib>\r\n      <taglib-uri>http://java.sun.com/jsp/jstl/sql-rt</taglib-uri>\r\n      <taglib-location>/WEB-INF/sql-rt.tld</taglib-location>\r\n      </taglib>\r\n      <taglib>\r\n      <taglib-uri>http://java.sun.com/jsp/jstl/x</taglib-uri>\r\n      <taglib-location>/WEB-INF/x.tld</taglib-location>\r\n      </taglib>\r\n      <taglib>\r\n      <taglib-uri>http://java.sun.com/jsp/jstl/x-rt</taglib-uri>\r\n      <taglib-location>/WEB-INF/x-rt.tld</taglib-location>\r\n      </taglib>\r\n      </jsp-config>\r\n  </web-app>\r\n  ```\r\n\r\n  \r\n\r\n> 注意：Web项目中需要用的库文件`一定要放在WEB-INFO目录`下，否则JSP页面是无法使用到该类库的\r\n>\r\n> 通常会在WEB-INF下创建lib文件夹，存放web项目需要用到库\r\n\r\n![image-20200901095054750](Jsp和JSTL表达式.assets/image-20200901095054750.png)\r\n\r\n## 1.2使用JSTL\r\n\r\n在jsp引入核心标签库\r\n\r\n```java\r\n<%@ page contentType=\"text/html;charset=UTF-8\" language=\"java\" %>\r\n<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\" %>\r\n<html>\r\n<head>\r\n    <title>Title</title>\r\n</head>\r\n<body>\r\n<c:set var=\"name\" value=\"zhangsan\" scope=\"session\"></c:set>    ${sessionScope.name }\r\n</body>\r\n</html>\r\n```\r\n\r\n```jsp\r\n<c:if test=\"true\"></c:if>\r\n<c:when test=\"true\"></c:when>\r\n<c:otherwise></c:otherwise>\r\n<c:set var=\"begin\" value=\"${}\"></c:set>\r\n<c:foreach></c:foreach>\r\n```', '1', 'post', 'publish', 'jsp', '框架', '3', '0', '1', '1', '1');
INSERT INTO `articles` VALUES ('100', 'js高级语法', null, '1621617757', '1621617757', null, null, '# 1.对象使用\r\n\r\n## 1.1 对象声明与构造\r\n\r\n## 1.2 继承\r\n\r\n- 使用对象冒充：直接调用父构造器向全局注入所有父类变量。\r\n\r\n  ```js\r\n  function ClassA(sColor) {\r\n      this.color = sColor;\r\n      this.sayColor = function () {\r\n          alert(this.color);\r\n      };\r\n  }\r\n  function ClassB(sColor) {\r\n      this.newMethod = ClassA;\r\n      this.newMethod(sColor);\r\n      delete this.newMethod;\r\n  }\r\n  ```\r\n\r\n- 使用call或apply方法替代\r\n\r\n  ```js\r\n  function ClassB(sColor, sName) {\r\n      //this.newMethod = ClassA;\r\n      //this.newMethod(color);\r\n      //delete this.newMethod;\r\n      ClassA.call(this, sColor);\r\n  \r\n      this.name = sName;\r\n      this.sayName = function () {\r\n          alert(this.name);\r\n      };\r\n  }\r\n  ```\r\n\r\n  ```js\r\n  function ClassB(sColor, sName) {\r\n      //this.newMethod = ClassA;\r\n      //this.newMethod(color);\r\n      //delete this.newMethod;\r\n      ClassA.apply(this, new Array(sColor));\r\n  \r\n      this.name = sName;\r\n      this.sayName = function () {\r\n          alert(this.name);\r\n      };\r\n  }\r\n  ```\r\n\r\n- 原型链\r\n\r\n  ```js\r\n  function ClassA() {\r\n  }\r\n  \r\n  ClassA.prototype.color = \"blue\";\r\n  ClassA.prototype.sayColor = function () {\r\n      alert(this.color);\r\n  };\r\n  \r\n  function ClassB() {\r\n  }\r\n  \r\n  ClassB.prototype = new ClassA();\r\n  ```\r\n\r\n- 混合使用以上方法\r\n\r\n  ```js\r\n  function ClassA(sColor) {\r\n      this.color = sColor;\r\n  }\r\n  \r\n  ClassA.prototype.sayColor = function () {\r\n      alert(this.color);\r\n  };\r\n  \r\n  function ClassB(sColor, sName) {\r\n      ClassA.call(this, sColor);\r\n      this.name = sName;\r\n  }\r\n  \r\n  ClassB.prototype = new ClassA();\r\n  \r\n  ClassB.prototype.sayName = function () {\r\n      alert(this.name);\r\n  };\r\n  ```\r\n\r\n  \r\n\r\n# 2.let、var、const区别\r\n\r\n> let：块级变量声明\r\n>\r\n> var，const：函数级变量声明\r\n\r\n# 3.闭包\r\n\r\n> js中特殊用法，因为js中可以灵活的返回方法对象， 故外层的调用可以返回内层的某个方法， 这时如果用外层的变量接收这个方法，便形成了“外层可以访问内层方法变量”的假象。\r\n\r\n```js\r\nvar name = \"The Window\";\r\n\r\n　　var object = {\r\n　　　　name : \"My Object\",\r\n　　　　getNameFunc : function(){\r\n　　　　　　return function(){\r\n　　　　　　　　return this.name;\r\n　　　　　　};\r\n　　　　}\r\n　　};\r\n　　alert(object.getNameFunc()());\r\n//输出“The Window”\r\n```\r\n\r\n```js\r\n　　var name = \"The Window\";\r\n　　var object = {\r\n　　　　name : \"My Object\",\r\n　　　　getNameFunc : function(){\r\n　　　　　　var that = this;\r\n　　　　　　return function(){\r\n　　　　　　　　return that.name;\r\n　　　　　　};\r\n　　　　}\r\n　　};\r\n　　alert(object.getNameFunc()());\r\n//输出“My Object”\r\n```\r\n\r\n> 使用闭包返回的方法会暴露在外层，此时调用对象会变为外层的对象。如果在window域中使用闭包，调用对象（闭包方法this关键字）指向window\r\n\r\n# 4.JSON\r\n\r\n## 4.1有效的数据类型\r\n\r\n在 JSON 中，值必须是以下数据类型之一：\r\n\r\n- 字符串\r\n- 数字\r\n- 对象（JSON 对象）\r\n- 数组\r\n- 布尔\r\n- Null\r\n\r\nJSON 的值*不可以*是以下数据类型之一：\r\n\r\n- 函数\r\n- 日期\r\n- undefined\r\n\r\n## 4.2 js对象转json字符串\r\n\r\n```js\r\nvar obj = { name:\"Bill Gates\", age:62, city:\"Seattle\"};\r\nvar myJSON =  JSON.stringify(obj);\r\ndocument.getElementById(\"demo\").innerHTML = myJSON;\r\n```\r\n\r\n## 4.3 解析json字符串为js对象\r\n\r\n```js\r\nvar obj = JSON.parse(\'{ \"name\":\"Bill Gates\", \"age\":62, \"city\":\"Seattle\"}\');\r\n```', '1', 'post', 'publish', 'js', '默认分类', '4', '0', '1', '1', '1');
INSERT INTO `articles` VALUES ('101', '指令系统', null, '1621617801', '1621620454', null, '', '# 4.指令系统\r\n\r\n## 	4.1指令分类	\r\n\r\n指令：指令是计算机执行某种操作的命令，是计算机运行的最小功能单位。\r\n\r\n`*计算机只能执行自己系统中的指令，无法执行其它系统的指令。`\r\n\r\n​	指令通常包括操作码和地址码两部分，通常有如下分类：\r\n\r\n- 零地址指令\r\n  1. 不需要操作数\r\n  2. 堆栈计算机，例如后缀表达式计算中操作数隐含放在栈顶和次栈顶。\r\n\r\n- 一地址指令\r\n\r\n  ![image-20200831102023474](C:\\Users\\Administrator\\AppData\\Roaming\\Typora\\typora-user-images\\image-20200831102023474.png)\r\n\r\n  1. 只需要单操作数，如加1减1，取反，求补等\r\n  2. 需要两个操作数，但其中一个操作数隐含在某个寄存器中。例如乘法操作\r\n     (ACC)OP（A₁）→ACC\r\n\r\n- 二地址指令\r\n\r\n  ![image-20200831102046264](C:\\Users\\Administrator\\AppData\\Roaming\\Typora\\typora-user-images\\image-20200831102046264.png)\r\n\r\n  1. 需要两个操作数\r\n\r\n- 三地址指令\r\n\r\n  ![image-20200831102121520](C:\\Users\\Administrator\\AppData\\Roaming\\Typora\\typora-user-images\\image-20200831102121520.png)\r\n\r\n  \r\n\r\n- 四地址指令\r\n\r\n  ![image-20200831102219861](C:\\Users\\Administrator\\AppData\\Roaming\\Typora\\typora-user-images\\image-20200831102219861.png)\r\n\r\n> 指令分类：\r\n>\r\n> 1. 变长指令字结构\r\n> 2. 定长指令字结构\r\n>\r\n> 按操作码长度分类：\r\n>\r\n> 1. 定长操作码\r\n> 2. 可变长操作码\r\n>\r\n> 按操作类型分类：\r\n>\r\n> 1. 数据传送\r\n> 2. 算数逻辑操作\r\n> 3. 移位操作\r\n> 4. 转移操作\r\n>    - 无条件转移\r\n>    - 条件转移\r\n>    - 调用和返回\r\n>    - 陷阱指令\r\n> 5. 输入输出操作\r\n\r\n![image-20200912002138190](4.指令系统.assets/image-20200912002138190.png)\r\n\r\n## 	4.2扩展操作码\r\n\r\n`定长指令字结构+可变长操作码`\r\n\r\n![image-20200831155504571](4.指令系统.assets/image-20200831155504571.png)\r\n\r\n## 	4.3指令寻址\r\n\r\n`指令始终由程序计数器PC给出`\r\n\r\n- 顺序寻址\r\n\r\n  变长指令：程序计数器识别指令前缀判断指令类型后，PC+“1”（根据指令类型，加一个指令长度）\r\n\r\n- 跳跃寻址\r\n\r\n  利用在指令之间穿插转移指令（类似goto），指明下一条指令执行的位置\r\n\r\n  ## 4.4数据寻址\r\n\r\n`确定本条指令的地址码指明的真实地址`\r\n\r\n![image-20200831161419382](4.指令系统.assets/image-20200831161419382.png)\r\n\r\n### 变指寻址\r\n\r\n![image-20200912012019039](4.指令系统.assets/image-20200912012019039.png)\r\n\r\n![image-20200912013022761](4.指令系统.assets/image-20200912013022761.png)\r\n\r\n## 4.5CISC和RISC\r\n\r\n\r\n\r\n**CISC**：Complex Instruction Set Computer\r\n\r\n设计思路：一条指令完成一个复杂的基本功能\r\n\r\n代表：X86架构，主要用于笔记本、台式机等。\r\n\r\n**RISC**：Reduced Instruction Set Computer\r\n\r\n设计思路：一个指令完成一个基本“动作“，多条指令组合完成一个复杂的基本功能\r\n\r\n代表：ARM架构，主要用于手机、平板。\r\n\r\n![image-20200831170940626](4.指令系统.assets/image-20200831170940626.png)\r\n\r\n![image-20200831170901707](4.指令系统.assets/image-20200831170901707.png)\r\n\r\n\r\n\r\n# 5.CPU\r\n\r\n## 5.1cpu基本结构\r\n\r\n![image-20200901204633762](4.指令系统.assets/image-20200901204633762.png)', '1', 'post', 'publish', '操作系统', '基础知识', '0', '0', '1', '1', '1');
INSERT INTO `articles` VALUES ('102', 'Hibernate', null, '1621617847', '1621620704', null, '', '# 1.Hibernate配置\r\n\r\n## 1.1 下载与导包\r\n\r\n## 1.2 案例\r\n\r\n```java\r\n@Test\r\n   public void test1() {\r\n      // 1. 加载配置文件\r\n      Configuration configuration = new Configuration().configure();\r\n      // 2.构建 SessionFactory \r\n      SessionFactory sessionFactory = configuration.buildSessionFactory();\r\n      // 3. 通过 SessionFactory 构建 Session 对象\r\n      Session session = sessionFactory.openSession();\r\n      // 4.使用 Session 对象开启事务\r\n      Transaction transaction = session.beginTransaction();\r\n      // 5. 执行相关操作\r\n      Customer customer = new Customer();\r\n      customer.setCust_name(\"小绿绿\");\r\n      \r\n      session.save(customer);\r\n      \r\n      // 6.提交事务\r\n      transaction.commit();\r\n      // 7.关闭释放资源\r\n      session.close();\r\n//    sessionFactory.close();\r\n   }\r\n```\r\n\r\n# 2.概念\r\n\r\n## 2.1 Hibernate功能\r\n\r\n> Hibernate通过建立ORM对象关系映射，让程序员不再需要自己建表，对外提供一个session对象概统持久化和查询等数据库操作服务\r\n\r\n## 2.2 实体类与持久化类\r\n\r\n| 持久化类 | Java实体类加上hibernate解析的map配置文件就是持久类，对象里还有id，与session有关联 |\r\n| -------- | ------------------------------------------------------------ |\r\n| 瞬时类   | 没有和hibernate关联的类，直接new出来的类和hibernate删除返回的类（添加时） |\r\n| 托管类   | 对象有id值，但与session无关联                                |\r\n\r\n> 在实例类创建中，需要全部使用包装类，因为非包装类在对象中会有默认值。\r\n\r\n# 3. 配置文件\r\n\r\n## 3.1 基本配置\r\n\r\n- hibernate基本配置文件：\r\n\r\n> 1. 位于src下，命名为hibernate.cfg.xml\r\n> 2. 配置内容分别为：\r\n>    - 数据库驱动配置\r\n>    - 数据库连接池(可选)\r\n>    - 实体类配置文件映射\r\n>    - 可选配置\r\n\r\n```xml\r\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n\r\n<!DOCTYPE hibernate-configuration PUBLIC\r\n   \"-//Hibernate/Hibernate Configuration DTD 3.0//EN\"\r\n   \"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd\">\r\n\r\n<hibernate-configuration>\r\n   <session-factory>\r\n      <!-- ===================Hibernate 必要的配置=================== -->\r\n\r\n      <property name=\"hibernate.connection.driver_class\">com.mysql.cj.jdbc.Driver</property>\r\n      <property name=\"hibernate.connection.url\">jdbc:mysql://LOCALHOST:3306/hibernate?zeroDateTimeBehavior=convertToNull&serverTimezone=GMT%2b8\r\n      </property>\r\n      <property name=\"hibernate.connection.username\">root</property>\r\n      <property name=\"hibernate.connection.password\">123456</property>\r\n      \r\n      <!-- 配置 Hibernate 生成SQL语句的方言 -->\r\n      <property name=\"hibernate.dialect\">org.hibernate.dialect.MySQLDialect</property>\r\n      \r\n      \r\n      <!-- 配置C3P0连接池 -->\r\n      <property name=\"hibernate.connection.provider_class\">org.hibernate.c3p0.internal.C3P0ConnectionProvider</property>\r\n      <!--在连接池中可用的数据库连接的最少数目 -->\r\n      <property name=\"c3p0.min_size\">5</property>\r\n      <!--在连接池中所有数据库连接的最大数目  -->\r\n      <property name=\"c3p0.max_size\">20</property>\r\n      <!--设定数据库连接的过期时间,以秒为单位,\r\n      如果连接池中的某个数据库连接处于空闲状态的时间超过了timeout时间,就会从连接池中清除 -->\r\n      <property name=\"c3p0.timeout\">120</property>\r\n       <!--每3000秒检查所有连接池中的空闲连接 以秒为单位-->\r\n      <property name=\"c3p0.idle_test_period\">3000</property>\r\n      \r\n      \r\n      <!-- ===================Hibernate 可选的配置=================== -->\r\n      <!-- Hibernate 显示 SQL 语句 -->\r\n      <property name=\"hibernate.show_sql\">true</property>\r\n      <!-- Hibernate 格式化 SQL 语句 -->\r\n      <property name=\"hibernate.format_sql\">true</property>\r\n      <!-- 使用 Hibernate 创建表 -->\r\n      <property name=\"hibernate.hbm2ddl.auto\">update</property>\r\n      <!-- ===================引入 Hibernate 映射文件=================== -->\r\n      <mapping resource=\"com/qidi/pojo/Customer.hbm.xml\"/>\r\n   </session-factory>\r\n</hibernate-configuration>\r\n```\r\n\r\n- 实现对象关系映射的实体类配置文件\r\n\r\n> 1. 通常放置在实体类同目录下，命名为Classname.hbm.xml\r\n> 2. 包含几个重要属性:\r\n>    - id，hibernate要求每个需要建立映射的实体类包含的唯一属性，对应数据库表中的唯一id\r\n>    - 各属性名称和表中字段的映射\r\n>\r\n> \r\n\r\n```xml\r\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE hibernate-mapping PUBLIC \r\n    \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\"\r\n    \"http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd\">\r\n\r\n<hibernate-mapping>\r\n   <!-- 建立类和表的映射关系 -->\r\n   <class name=\"com.qidi.pojo.Customer\" table=\"cst_customer\">\r\n      <!-- 建立类中的属性与表中的主键的映射关系 -->\r\n      <id name=\"cust_id\" column=\"cust_id\">\r\n         <generator class=\"native\"/>\r\n      </id>\r\n      \r\n      <!-- 建立类中的普通属性和表中的字段的映射关系 -->\r\n      <property name=\"cust_name\" column=\"cust_name\" />\r\n      <property name=\"cust_source\" column=\"cust_source\" />\r\n      <property name=\"cust_industry\" column=\"cust_industry\" />\r\n      <property name=\"cust_level\" column=\"cust_level\" />\r\n      <property name=\"cust_phone\" column=\"cust_phone\" />\r\n      <property name=\"cust_mobile\" column=\"cust_mobile\" />\r\n   </class>\r\n</hibernate-mapping>    \r\n```\r\n\r\n## 3.2 主键自增策略\r\n\r\n```xml\r\n  <id name=\"cust_id\" column=\"cust_id\">\r\n     <generator class=\"native\"/>\r\n  </id>\r\n```\r\n| 属性     | 作用                                                         |\r\n| -------- | ------------------------------------------------------------ |\r\n| native   | 根据底层数据库对自动生成表示符的能力来选择identity、sequence、hilo三种生成器的一种。即自动使用数据库自己的自增策略。实体类通常使用长整型Long |\r\n| uuid     | Hibernate内置，使用128位UUID算法生成的标识符。字符串类型主键比整数类型逐渐占用更多的数据库空间。适用于代理主键。实体类使用String |\r\n| assigned | 需要程序员自己生成标识符，不指定id元素generator属性默认使用此主键策略 |\r\n\r\n\r\n\r\n## 3.3 表关联与级联操作\r\n\r\n# 4.session\r\n\r\n| 方法           | 作用                      |\r\n| -------------- | ------------------------- |\r\n| save()         | 保存一个对象,存在时会修改 |\r\n| update()       | 修改                      |\r\n| saveOrUpdate() | 修改或保存                |\r\n| delete()       | 删除                      |\r\n| get()          | 根据id查询                |\r\n\r\n- 删除操作\r\n\r\n  1. 根据id查到对象再进行删除\r\n\r\n     ```java\r\n     User user=session.get(User.class,2L);\r\n     session.delete(user);\r\n     ```\r\n\r\n  2. new出一个对象设置id，直接删除\r\n\r\n     ```java\r\n     User user=new User();\r\n     user.setUid(3);\r\n     session.delete(user);\r\n     ```', '1', 'post', 'publish', 'hibernate', '框架', '5', '0', '1', '1', '1');
INSERT INTO `articles` VALUES ('103', 'windows常用', null, '1621617880', '1621617880', null, null, '# 1.根据文件句柄找到PID\r\n\r\n![image-20200910155252373](windows常用.assets/image-20200910155252373.png)\r\n\r\n![image-20200910155503142](windows常用.assets/image-20200910155503142.png)\r\n\r\n# 2.通过PID查杀进程\r\n\r\n> tasklist | findstr 14396  根据PID查询进程名\r\n>\r\n> taskkill /pid 14396 -t -f   强制终止进程\r\n\r\n# 3.根据网络端口查询进程\r\n\r\n> netstat -aon 查看所有使用的端口\r\n>\r\n> netstat -aon | findstr \"1080\"  查找占用端口的PID', '1', 'post', 'publish', 'windows', '默认分类', '2', '0', '1', '1', '1');
INSERT INTO `articles` VALUES ('104', '你不知道的JavaScript笔记', null, '1621617932', '1621617932', null, null, '---\r\ntitle:操作系统（进程管理）\r\ndate: 2021-07-20 17:20:03\r\ntags:计算机基础知识\r\n---\r\n\r\n# 进程管理\r\n\r\n## 	生产者消费者问题\r\n\r\n![image-20200903211957229](操作系统.assets/image-20200903211957229.png)\r\n\r\n![image-20200903212020134](操作系统.assets/image-20200903212020134.png)\r\n\r\n![image-20200903212101673](操作系统.assets/image-20200903212101673.png)\r\n\r\n## 	多生产者多消费者问题（多产品）\r\n\r\n![image-20200903212138042](操作系统.assets/image-20200903212138042.png)\r\n\r\n![image-20200903212228091](操作系统.assets/image-20200903212228091.png)\r\n\r\n## 	哲学家问题\r\n\r\n# 死锁避免\r\n\r\n## 	银行家算法\r\n\r\n![image-20200904203726552](操作系统.assets/image-20200904203726552.png)\r\n\r\n# 内存管理\r\n\r\n## 	交换技术\r\n\r\n![image-20200904230618679](操作系统.assets/image-20200904230618679.png)', '1', 'post', 'publish', 'js', '默认分类', '7', '0', '1', '1', '1');
INSERT INTO `articles` VALUES ('106', 'music testing', null, '1621637630', '1621637630', null, '1472480890', 'music testing', '1', 'post', 'publish', '', '', '2', '0', '1', '1', '1');

-- ----------------------------
-- Table structure for t_attach
-- ----------------------------
DROP TABLE IF EXISTS `t_attach`;
CREATE TABLE `t_attach` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `fname` varchar(100) NOT NULL DEFAULT '',
  `ftype` varchar(50) DEFAULT '',
  `fkey` varchar(100) NOT NULL DEFAULT '',
  `author_id` int(10) DEFAULT NULL,
  `created` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_attach
-- ----------------------------
INSERT INTO `t_attach` VALUES ('22', 'image-20200914151507681.png', 'image', '/upload/2021/03/v0fsm1usqaiv9qneq9ar6kvrci.png', '1', '1617025767');
INSERT INTO `t_attach` VALUES ('23', 'image-20200914151711865.png', 'image', '/upload/2021/03/qqhb23lqn2it3pkn7p2eet5897.png', '1', '1617025767');
INSERT INTO `t_attach` VALUES ('24', 'image-20200914151903313.png', 'image', '/upload/2021/03/60dahb84uuhb8rhfpr47ue8v1e.png', '1', '1617025769');
INSERT INTO `t_attach` VALUES ('25', 'image-20200914152053874.png', 'image', '/upload/2021/03/r9ltqluiogg8sps0msh6kluv9s.png', '1', '1617025769');
INSERT INTO `t_attach` VALUES ('26', 'image-20200914151054391.png', 'image', '/upload/2021/03/q8od4fmu0ciiaqvf28cjvk44a6.png', '1', '1617025784');
INSERT INTO `t_attach` VALUES ('27', 'image-20200914151052679.png', 'image', '/upload/2021/03/vmorf98gv6i42qon91ov2rfg08.png', '1', '1617025784');
INSERT INTO `t_attach` VALUES ('28', 'image-20200914151711865.png', 'image', '/upload/2021/03/3ro98jbgmkjanrb7qqpscp76ir.png', '1', '1617025784');
INSERT INTO `t_attach` VALUES ('29', 'image-20200914151507681.png', 'image', '/upload/2021/03/rp46j7girkjabplt2nh0fjah6e.png', '1', '1617025784');
INSERT INTO `t_attach` VALUES ('30', 'image-20200914152053874.png', 'image', '/upload/2021/03/vrfsp4nd6qi63rv2rsc7i5a4fh.png', '1', '1617025784');
INSERT INTO `t_attach` VALUES ('31', 'image-20200914151903313.png', 'image', '/upload/2021/03/vob6nu4bokhpko9gbtvb6mfehp.png', '1', '1617025784');
INSERT INTO `t_attach` VALUES ('32', 'image-20210131194511368.png', 'image', '/upload/2021/03/4hifvhsm98gmcqurp2015mlkra.png', '1', '1617025789');
INSERT INTO `t_attach` VALUES ('33', 'image-20210131194538491.png', 'image', '/upload/2021/03/ufn6ptp16cgi3orehj58do3psl.png', '1', '1617025789');
INSERT INTO `t_attach` VALUES ('34', 'image-20210131194917853.png', 'image', '/upload/2021/03/5601o921hgijcq8epst9cjpb20.png', '1', '1617025789');
INSERT INTO `t_attach` VALUES ('35', 'image-20210131195550194.png', 'image', '/upload/2021/03/0b9dftjmkog3vp0ml7f7ufdoph.png', '1', '1617025789');
INSERT INTO `t_attach` VALUES ('36', 'image-20210131195218483.png', 'image', '/upload/2021/03/tllinev74qguaqkgs3i0gu4mp3.png', '1', '1617025789');
INSERT INTO `t_attach` VALUES ('37', 'image-20210131200448223.png', 'image', '/upload/2021/03/6olbgk819mjkfrqm51nq2aefcp.png', '1', '1617025789');
INSERT INTO `t_attach` VALUES ('38', '69b2d659e9bdcc6657bed0d24c93d861001.jpg', 'image', '/upload/2021/05/7514kde6uuh3qr7r0b0av7atmk.jpg', '1', '1621580182');
INSERT INTO `t_attach` VALUES ('39', '69432531_p0.jpg', 'image', '/upload/2021/05/unl5568hhqhp9r5gpenpn50mog.jpg', '1', '1621580182');
INSERT INTO `t_attach` VALUES ('40', 'image-20200914170333397.png', 'image', '/upload/2021/05/1s1pghkstcgsjrhfiilcdshljk.png', '1', '1621580208');
INSERT INTO `t_attach` VALUES ('41', 'image-20200914151711865.png', 'image', '/upload/2021/05/uok2shkedaj70ph74lcd8ksjf0.png', '1', '1621585119');
INSERT INTO `t_attach` VALUES ('42', 'image-20200914151507681.png', 'image', '/upload/2021/05/sht2p924luieppmib29vdvbjck.png', '1', '1621585119');
INSERT INTO `t_attach` VALUES ('43', 'image-20200914152053874.png', 'image', '/upload/2021/05/5iivu0hm00g6sr89ktcrong51r.png', '1', '1621585119');
INSERT INTO `t_attach` VALUES ('44', 'image-20200914151903313.png', 'image', '/upload/2021/05/vtjo1i9lcki1tpjgmopc0a82r6.png', '1', '1621585119');
INSERT INTO `t_attach` VALUES ('45', 'image-20201103110604953.png', 'image', '/upload/2021/05/6k4gngeedmg38qs2mmqpbprmc3.png', '1', '1621585392');

-- ----------------------------
-- Table structure for t_comments
-- ----------------------------
DROP TABLE IF EXISTS `t_comments`;
CREATE TABLE `t_comments` (
  `coid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cid` int(10) unsigned DEFAULT '0',
  `created` int(10) unsigned DEFAULT '0',
  `author` varchar(200) DEFAULT NULL,
  `author_id` int(10) unsigned DEFAULT '0',
  `owner_id` int(10) unsigned DEFAULT '0',
  `mail` varchar(200) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `ip` varchar(64) DEFAULT NULL,
  `agent` varchar(200) DEFAULT NULL,
  `content` text,
  `type` varchar(16) DEFAULT 'comment',
  `status` varchar(16) DEFAULT 'approved',
  `parent` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`coid`),
  KEY `cid` (`cid`),
  KEY `created` (`created`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_comments
-- ----------------------------
INSERT INTO `t_comments` VALUES ('19', '105', '1621619639', '复试发三顺丰', '0', '1', 'fsfsfs@qdgdq.com', '', '0:0:0:0:0:0:0:1', null, 'fafa ', 'comment', 'approved', '0');
INSERT INTO `t_comments` VALUES ('20', '105', '1621620219', '复试发三顺丰', '0', '1', 'fsfsfs@qdgdq.com', '', '0:0:0:0:0:0:0:1', null, 'fafafafaf', 'comment', 'approved', '0');
INSERT INTO `t_comments` VALUES ('21', '105', '1621620231', '复试发三顺丰', '0', '1', 'fsfsfs@qdgdq.com', '', '0:0:0:0:0:0:0:1', null, 'fsafafa', 'comment', 'approved', '0');

-- ----------------------------
-- Table structure for t_logs
-- ----------------------------
DROP TABLE IF EXISTS `t_logs`;
CREATE TABLE `t_logs` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `action` varchar(100) DEFAULT NULL,
  `data` varchar(2000) DEFAULT NULL,
  `author_id` int(10) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `created` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_logs
-- ----------------------------
INSERT INTO `t_logs` VALUES ('1', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1611750045');
INSERT INTO `t_logs` VALUES ('2', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1611750278');
INSERT INTO `t_logs` VALUES ('3', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1611750309');
INSERT INTO `t_logs` VALUES ('4', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1611750377');
INSERT INTO `t_logs` VALUES ('5', '登录后台', null, '1', '127.0.0.1', '1611750442');
INSERT INTO `t_logs` VALUES ('6', '登录后台', null, '1', '127.0.0.1', '1611750619');
INSERT INTO `t_logs` VALUES ('7', '登录后台', null, '1', '127.0.0.1', '1611750704');
INSERT INTO `t_logs` VALUES ('8', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1611750962');
INSERT INTO `t_logs` VALUES ('9', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1611750962');
INSERT INTO `t_logs` VALUES ('10', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1611751003');
INSERT INTO `t_logs` VALUES ('11', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1611751091');
INSERT INTO `t_logs` VALUES ('12', '保存系统设置', '{\"site_record\":\"\",\"site_description\":\"亘古的个人小站\",\"site_title\":\"My Blog\",\"site_theme\":\"default\",\"allow_install\":\"\"}', '1', '0:0:0:0:0:0:0:1', '1611751178');
INSERT INTO `t_logs` VALUES ('13', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1611753346');
INSERT INTO `t_logs` VALUES ('14', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1611832895');
INSERT INTO `t_logs` VALUES ('15', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1611833234');
INSERT INTO `t_logs` VALUES ('16', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1612229241');
INSERT INTO `t_logs` VALUES ('17', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1612229246');
INSERT INTO `t_logs` VALUES ('18', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1612229256');
INSERT INTO `t_logs` VALUES ('19', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1612229259');
INSERT INTO `t_logs` VALUES ('20', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1612229262');
INSERT INTO `t_logs` VALUES ('21', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1612229290');
INSERT INTO `t_logs` VALUES ('22', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1612229315');
INSERT INTO `t_logs` VALUES ('23', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1612232242');
INSERT INTO `t_logs` VALUES ('24', '登录后台', null, '1', '127.0.0.1', '1612238749');
INSERT INTO `t_logs` VALUES ('25', '登录后台', null, '1', '127.0.0.1', '1612339282');
INSERT INTO `t_logs` VALUES ('26', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1616057174');
INSERT INTO `t_logs` VALUES ('27', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1616057185');
INSERT INTO `t_logs` VALUES ('28', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1616057214');
INSERT INTO `t_logs` VALUES ('29', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1616057374');
INSERT INTO `t_logs` VALUES ('30', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1616057378');
INSERT INTO `t_logs` VALUES ('31', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1616057437');
INSERT INTO `t_logs` VALUES ('32', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1616059798');
INSERT INTO `t_logs` VALUES ('33', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1616078659');
INSERT INTO `t_logs` VALUES ('34', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1616142274');
INSERT INTO `t_logs` VALUES ('35', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1616161370');
INSERT INTO `t_logs` VALUES ('36', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1616581688');
INSERT INTO `t_logs` VALUES ('37', '删除文章', '/upload/2021/03/ocq7lb4792hcvois9lu2hlh7pp.jpg', '1', '0:0:0:0:0:0:0:1', '1616581867');
INSERT INTO `t_logs` VALUES ('38', '删除文章', '/upload/2021/03/saphvcur96ga1qq1h25gech49s.jpg', '1', '0:0:0:0:0:0:0:1', '1616581871');
INSERT INTO `t_logs` VALUES ('39', '删除文章', '/upload/2021/03/vi13eouhq0hriq6vkdcofue09l.md', '1', '0:0:0:0:0:0:0:1', '1616582063');
INSERT INTO `t_logs` VALUES ('40', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1616582222');
INSERT INTO `t_logs` VALUES ('41', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1616582224');
INSERT INTO `t_logs` VALUES ('42', '删除文章', '/upload/2021/03/5od12omfboi85rv32nvbjiebfu.md', '1', '0:0:0:0:0:0:0:1', '1616582297');
INSERT INTO `t_logs` VALUES ('43', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1616587736');
INSERT INTO `t_logs` VALUES ('44', '登录后台', null, '1', '127.0.0.1', '1616739270');
INSERT INTO `t_logs` VALUES ('45', '删除文章', '/upload/2021/03/ql0apipih0jpsp5csara6a3g0q.md', '1', '127.0.0.1', '1616743862');
INSERT INTO `t_logs` VALUES ('46', '登录后台', null, '1', '127.0.0.1', '1616744113');
INSERT INTO `t_logs` VALUES ('47', '登录后台', null, '1', '127.0.0.1', '1616745222');
INSERT INTO `t_logs` VALUES ('48', '登录后台', null, '1', '127.0.0.1', '1616747099');
INSERT INTO `t_logs` VALUES ('49', '登录后台', null, '1', '127.0.0.1', '1616747369');
INSERT INTO `t_logs` VALUES ('50', '删除文章', '/upload/2021/03/uptukcfv30hj9o077nkl2pj374.jpg', '1', '127.0.0.1', '1616747426');
INSERT INTO `t_logs` VALUES ('51', '删除文章', '/upload/2021/03/voj1hdntrsggdq8gp3gm7h6l8q.jfif', '1', '127.0.0.1', '1616747432');
INSERT INTO `t_logs` VALUES ('52', '删除文章', '/upload/2021/03/07o2mh5llcgq7qra56abuob0qu.jfif', '1', '127.0.0.1', '1616747436');
INSERT INTO `t_logs` VALUES ('53', '删除文章', '/upload/2021/03/596qc7vti0hfmrbiournto294b.png', '1', '127.0.0.1', '1616747439');
INSERT INTO `t_logs` VALUES ('54', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1617021773');
INSERT INTO `t_logs` VALUES ('55', '删除文章', '7', '1', '0:0:0:0:0:0:0:1', '1617022321');
INSERT INTO `t_logs` VALUES ('56', '删除文章', '8', '1', '0:0:0:0:0:0:0:1', '1617022324');
INSERT INTO `t_logs` VALUES ('57', '删除文章', '6', '1', '0:0:0:0:0:0:0:1', '1617022327');
INSERT INTO `t_logs` VALUES ('58', '删除文章', '5', '1', '0:0:0:0:0:0:0:1', '1617022330');
INSERT INTO `t_logs` VALUES ('59', '删除文章', '4', '1', '0:0:0:0:0:0:0:1', '1617022332');
INSERT INTO `t_logs` VALUES ('60', '删除文章', '3', '1', '0:0:0:0:0:0:0:1', '1617022335');
INSERT INTO `t_logs` VALUES ('61', '删除文章', '2', '1', '0:0:0:0:0:0:0:1', '1617022338');
INSERT INTO `t_logs` VALUES ('62', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1617023581');
INSERT INTO `t_logs` VALUES ('63', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1617023600');
INSERT INTO `t_logs` VALUES ('64', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1617024219');
INSERT INTO `t_logs` VALUES ('65', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1617024430');
INSERT INTO `t_logs` VALUES ('66', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1617024803');
INSERT INTO `t_logs` VALUES ('67', '删除文章', '/upload/2021/03/sh96vv4fv2jklr331ihnd4tg5d.jpg', '1', '0:0:0:0:0:0:0:1', '1617025090');
INSERT INTO `t_logs` VALUES ('68', '删除文章', '/upload/2021/03/tfbh6vm29qjlbpsadq5f5pbgg0.jpg', '1', '0:0:0:0:0:0:0:1', '1617025093');
INSERT INTO `t_logs` VALUES ('69', '删除文章', '/upload/2021/03/vgm5mc9l88h34r2pav6eguetos.png', '1', '0:0:0:0:0:0:0:1', '1617025095');
INSERT INTO `t_logs` VALUES ('70', '删除文章', '/upload/2021/01/6gkp3it38ugolp16ous8f5j3dt.jpg', '1', '0:0:0:0:0:0:0:1', '1617025098');
INSERT INTO `t_logs` VALUES ('71', '删除文章', '/upload/2021/03/4uqk552010il5pgjmoll382ld2.jpg', '1', '0:0:0:0:0:0:0:1', '1617025100');
INSERT INTO `t_logs` VALUES ('72', '删除文章', '/upload/2021/03/6it77nsb2gin8onpd2jioukqab.jpg', '1', '0:0:0:0:0:0:0:1', '1617025104');
INSERT INTO `t_logs` VALUES ('73', '删除文章', '/upload/2021/03/v03c1pkb1ugt5reelcc6blvhv1.jpg', '1', '0:0:0:0:0:0:0:1', '1617025106');
INSERT INTO `t_logs` VALUES ('74', '删除文章', '/upload/2021/03/u2ns4rmq0qgtlrs6rdndn352gb.jpg', '1', '0:0:0:0:0:0:0:1', '1617025109');
INSERT INTO `t_logs` VALUES ('75', '删除文章', '/upload/2021/01/0ghjafvj0khp6qg6dv21tj1elo.jpg', '1', '0:0:0:0:0:0:0:1', '1617025112');
INSERT INTO `t_logs` VALUES ('76', '删除文章', '/upload/2021/01/sal74o68dqgc4rce5s4nrfe1dc.jpg', '1', '0:0:0:0:0:0:0:1', '1617025114');
INSERT INTO `t_logs` VALUES ('77', '删除文章', '/upload/2021/01/72j4jqsl3sgtfopugmf1pebheb.jpg', '1', '0:0:0:0:0:0:0:1', '1617025116');
INSERT INTO `t_logs` VALUES ('78', '删除文章', '/upload/2021/03/7lkoa8t5a2gueru3s3bbalrj5l.jpg', '1', '0:0:0:0:0:0:0:1', '1617025118');
INSERT INTO `t_logs` VALUES ('79', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1617025406');
INSERT INTO `t_logs` VALUES ('80', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1617025529');
INSERT INTO `t_logs` VALUES ('81', '登录后台', null, '1', '127.0.0.1', '1617025633');
INSERT INTO `t_logs` VALUES ('82', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1617025747');
INSERT INTO `t_logs` VALUES ('83', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1620135811');
INSERT INTO `t_logs` VALUES ('84', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1620135811');
INSERT INTO `t_logs` VALUES ('85', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1620236459');
INSERT INTO `t_logs` VALUES ('86', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1620741738');
INSERT INTO `t_logs` VALUES ('87', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1620742232');
INSERT INTO `t_logs` VALUES ('88', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1621255154');
INSERT INTO `t_logs` VALUES ('89', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1621325057');
INSERT INTO `t_logs` VALUES ('90', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1621491481');
INSERT INTO `t_logs` VALUES ('91', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1621491486');
INSERT INTO `t_logs` VALUES ('92', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1621491497');
INSERT INTO `t_logs` VALUES ('93', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1621495802');
INSERT INTO `t_logs` VALUES ('94', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1621495806');
INSERT INTO `t_logs` VALUES ('95', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1621495827');
INSERT INTO `t_logs` VALUES ('96', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1621530331');
INSERT INTO `t_logs` VALUES ('97', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1621580165');
INSERT INTO `t_logs` VALUES ('98', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1621584890');
INSERT INTO `t_logs` VALUES ('99', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1621593250');
INSERT INTO `t_logs` VALUES ('100', '系统备份', null, '1', '0:0:0:0:0:0:0:1', '1621598327');
INSERT INTO `t_logs` VALUES ('101', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1621616923');
INSERT INTO `t_logs` VALUES ('102', '删除文章', '98', '1', '0:0:0:0:0:0:0:1', '1621617674');
INSERT INTO `t_logs` VALUES ('103', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1621621503');
INSERT INTO `t_logs` VALUES ('104', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1621621545');
INSERT INTO `t_logs` VALUES ('105', '修改个人信息', '{\"uid\":1,\"email\":\"2352462017@qq.com\",\"screenName\":\"admin\"}', '1', '0:0:0:0:0:0:0:1', '1621621671');
INSERT INTO `t_logs` VALUES ('106', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1621637397');
INSERT INTO `t_logs` VALUES ('107', '删除文章', '105', '1', '127.0.0.1', '1621637600');
INSERT INTO `t_logs` VALUES ('108', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1621642828');
INSERT INTO `t_logs` VALUES ('109', '删除文章', '107', '1', '0:0:0:0:0:0:0:1', '1621642989');

-- ----------------------------
-- Table structure for t_metas
-- ----------------------------
DROP TABLE IF EXISTS `t_metas`;
CREATE TABLE `t_metas` (
  `mid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `slug` varchar(200) DEFAULT NULL,
  `type` varchar(32) NOT NULL DEFAULT '',
  `description` varchar(200) DEFAULT NULL,
  `sort` int(10) unsigned DEFAULT '0',
  `parent` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`mid`),
  KEY `slug` (`slug`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_metas
-- ----------------------------
INSERT INTO `t_metas` VALUES ('6', 'my github', 'https://github.com/wander-chu', 'link', '', '0', '0');
INSERT INTO `t_metas` VALUES ('8', '算法', null, 'category', null, '0', '0');
INSERT INTO `t_metas` VALUES ('9', 'my github	', 'https://www.baidu.com/s?ie=utf-8&wd=md5%E5%8A%A0%E5%AF%86', 'link', '', '1', '0');
INSERT INTO `t_metas` VALUES ('11', '默认分类', '默认分类', 'category', null, '0', '0');
INSERT INTO `t_metas` VALUES ('13', 'オオカミさんは食べられたい', 'https://github.com/wander-chu', 'link', '', '0', '0');
INSERT INTO `t_metas` VALUES ('14', 'オオカミさんは食べられたい', 'https://github.com/wander-chu', 'link', '', '0', '0');
INSERT INTO `t_metas` VALUES ('15', 'my github', 'https://github.com/wander-chu', 'link', '', '0', '0');
INSERT INTO `t_metas` VALUES ('18', '开发工具', null, 'category', null, '0', '0');
INSERT INTO `t_metas` VALUES ('19', 'git', 'git', 'tag', null, '0', '0');
INSERT INTO `t_metas` VALUES ('21', 'idea', 'idea', 'tag', null, '0', '0');
INSERT INTO `t_metas` VALUES ('22', 'mysql', 'mysql', 'tag', null, '0', '0');
INSERT INTO `t_metas` VALUES ('23', '前端', '前端', 'tag', null, '0', '0');
INSERT INTO `t_metas` VALUES ('24', 'maven', 'maven', 'tag', null, '0', '0');
INSERT INTO `t_metas` VALUES ('32', '博客', '博客', 'tag', null, '0', '0');
INSERT INTO `t_metas` VALUES ('33', '基础知识', null, 'category', null, '0', '0');
INSERT INTO `t_metas` VALUES ('34', '框架', null, 'category', null, '0', '0');
INSERT INTO `t_metas` VALUES ('35', 'jsp', 'jsp', 'tag', null, '0', '0');
INSERT INTO `t_metas` VALUES ('36', 'js', 'js', 'tag', null, '0', '0');
INSERT INTO `t_metas` VALUES ('37', '操作系统', '操作系统', 'tag', null, '0', '0');
INSERT INTO `t_metas` VALUES ('38', 'hibernate', 'hibernate', 'tag', null, '0', '0');
INSERT INTO `t_metas` VALUES ('39', 'windows', 'windows', 'tag', null, '0', '0');
INSERT INTO `t_metas` VALUES ('40', 'fsa', 'fsa', 'tag', null, '0', '0');
INSERT INTO `t_metas` VALUES ('41', 'jf', 'jf', 'tag', null, '0', '0');

-- ----------------------------
-- Table structure for t_options
-- ----------------------------
DROP TABLE IF EXISTS `t_options`;
CREATE TABLE `t_options` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `value` varchar(1000) DEFAULT '',
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_options
-- ----------------------------
INSERT INTO `t_options` VALUES ('allow_install', '', null);
INSERT INTO `t_options` VALUES ('music_list', '', null);
INSERT INTO `t_options` VALUES ('site_description', '亘古的个人小站', null);
INSERT INTO `t_options` VALUES ('site_keywords', 'wander Blog', null);
INSERT INTO `t_options` VALUES ('site_record', '', '备案号');
INSERT INTO `t_options` VALUES ('site_theme', 'default', null);
INSERT INTO `t_options` VALUES ('site_title', 'My Blog', '');
INSERT INTO `t_options` VALUES ('social_github', '', null);
INSERT INTO `t_options` VALUES ('social_twitter', '', null);
INSERT INTO `t_options` VALUES ('social_weibo', '', null);
INSERT INTO `t_options` VALUES ('social_zhihu', '', null);

-- ----------------------------
-- Table structure for t_relationships
-- ----------------------------
DROP TABLE IF EXISTS `t_relationships`;
CREATE TABLE `t_relationships` (
  `cid` int(10) unsigned NOT NULL,
  `mid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`cid`,`mid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_relationships
-- ----------------------------
INSERT INTO `t_relationships` VALUES ('9', '18');
INSERT INTO `t_relationships` VALUES ('9', '19');
INSERT INTO `t_relationships` VALUES ('10', '8');
INSERT INTO `t_relationships` VALUES ('11', '18');
INSERT INTO `t_relationships` VALUES ('11', '21');
INSERT INTO `t_relationships` VALUES ('12', '18');
INSERT INTO `t_relationships` VALUES ('12', '22');
INSERT INTO `t_relationships` VALUES ('13', '11');
INSERT INTO `t_relationships` VALUES ('13', '23');
INSERT INTO `t_relationships` VALUES ('13', '32');
INSERT INTO `t_relationships` VALUES ('14', '18');
INSERT INTO `t_relationships` VALUES ('14', '24');
INSERT INTO `t_relationships` VALUES ('15', '11');
INSERT INTO `t_relationships` VALUES ('99', '34');
INSERT INTO `t_relationships` VALUES ('99', '35');
INSERT INTO `t_relationships` VALUES ('100', '11');
INSERT INTO `t_relationships` VALUES ('100', '36');
INSERT INTO `t_relationships` VALUES ('101', '33');
INSERT INTO `t_relationships` VALUES ('101', '37');
INSERT INTO `t_relationships` VALUES ('102', '34');
INSERT INTO `t_relationships` VALUES ('102', '38');
INSERT INTO `t_relationships` VALUES ('103', '11');
INSERT INTO `t_relationships` VALUES ('103', '39');
INSERT INTO `t_relationships` VALUES ('104', '11');
INSERT INTO `t_relationships` VALUES ('104', '36');

-- ----------------------------
-- Table structure for t_users
-- ----------------------------
DROP TABLE IF EXISTS `t_users`;
CREATE TABLE `t_users` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `home_url` varchar(200) DEFAULT NULL,
  `screen_name` varchar(32) DEFAULT NULL,
  `created` int(10) unsigned DEFAULT '0',
  `activated` int(10) unsigned DEFAULT '0',
  `logged` int(10) unsigned DEFAULT '0',
  `group_name` varchar(16) DEFAULT 'visitor',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `name` (`username`),
  UNIQUE KEY `mail` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_users
-- ----------------------------
INSERT INTO `t_users` VALUES ('1', 'admin', 'a66abb5684c45962d887564f08346e8d', '2352462017@qq.com', null, 'admin', '1490756162', '0', '0', 'visitor');
INSERT INTO `t_users` VALUES ('2', 'gengu', '2e92ec3923851c94', null, null, 'admin', '0', '0', '0', 'visitor');
