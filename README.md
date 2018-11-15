# ChinesePinyinSortAndStrokeSort
Android中文拼音排序以及中文笔划排序和英文排序，实现类似微信登录选择国家地区列表的功能

# 一、需求描述
最近要做一个类似微信的，在登录界面选择国家地区的功能，微信有中文汉字笔画排序以及中文拼音排序等几种方式，如下所示：

+ 微信：简体中文、拼音排序
![在这里插入图片描述](https://img-blog.csdnimg.cn/2018111519263423.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxNDQ2MjgyNDEy,size_16,color_FFFFFF,t_70)

+ 微信：繁体中文、笔画排序
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181115192753392.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxNDQ2MjgyNDEy,size_16,color_FFFFFF,t_70)

+ 微信 英文 字母排序
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181115192846477.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxNDQ2MjgyNDEy,size_16,color_FFFFFF,t_70)

# 二、实现效果
下面看看我实现的效果
+ 简体中文、拼音排序
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181115193235646.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxNDQ2MjgyNDEy,size_16,color_FFFFFF,t_70)

+ 繁体中文、笔画排序
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181115193308573.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxNDQ2MjgyNDEy,size_16,color_FFFFFF,t_70)

+ 英文 字母排序
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181115193346272.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxNDQ2MjgyNDEy,size_16,color_FFFFFF,t_70)

+ 其他语言，显示其他语言，排序按照对应的英文名来排序
![在这里插入图片描述](https://img-blog.csdnimg.cn/201811151936113.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxNDQ2MjgyNDEy,size_16,color_FFFFFF,t_70)

# 三、实现过程

## 3.1 拿到汉字笔画数据库
为了实现这个中文汉字排序，我在前面的博客中

+ [【我的Java开发学习之旅】如何实现中文汉字进行笔划(笔画)排序？](https://blog.csdn.net/ouyang_peng/article/details/83863693)
找了大量的资料，最后才找到一个较完整的数据库，里面包含汉字、汉字笔画数、汉字对应的编码。
+ 数据库 https://github.com/ouyangpeng/ChineseStrokeSorting/blob/master/jar包源代码以及demo下载/ChinessStroke.db
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181114163830847.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxNDQ2MjgyNDEy,size_16,color_FFFFFF,t_70)

内容如下所示，总共有20902个汉字，满足了日常的需求了。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181114163944500.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxNDQ2MjgyNDEy,size_16,color_FFFFFF,t_70)

+ 文本内容 https://github.com/ouyangpeng/ChineseStrokeSorting/blob/master/jar包源代码以及demo下载/ChinessStroke.dat
通过 博客
+  [【我的Android进阶之旅】如果使用.output命令将SQLite数据库中的数据导出为文本？](https://blog.csdn.net/qq446282412/article/details/84070920)
中介绍的命令将db文件的内容导出为 ChinessStroke.dat文本，内容如下

![在这里插入图片描述](https://img-blog.csdnimg.cn/20181114173000119.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxNDQ2MjgyNDEy,size_16,color_FFFFFF,t_70)

## 3.2 实现排序

### 3.2.1 项目结构
现在将这部分代码抽取出来，做了一个demo。项目结构如下所示：采用策略模式，分别有EnglishSortStrategy、PinyinSortStrategy、StrokeSortStrategy三种策略，分别表示英文排序策略、拼音排序策略、汉字笔画排序策略。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181115194237291.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxNDQ2MjgyNDEy,size_16,color_FFFFFF,t_70)


### 3.2.2 运行效果
+ 简体中文：拼音排序
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181115194733537.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxNDQ2MjgyNDEy,size_16,color_FFFFFF,t_70)

+ 繁体中文：笔画排序
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181115194630515.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxNDQ2MjgyNDEy,size_16,color_FFFFFF,t_70)


+ 英文 字母排序
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181115194816613.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxNDQ2MjgyNDEy,size_16,color_FFFFFF,t_70)

+ 其他语言，显示其他语言，排序按照对应的英文名来排序
就不展示了，和刚刚我做的APP一样，现在没有可以切换其他语言的手机在身边。
![在这里插入图片描述](https://img-blog.csdnimg.cn/201811151936113.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxNDQ2MjgyNDEy,size_16,color_FFFFFF,t_70)

### 3.2.3 源代码
至于代码我就不想贴了，代码直接传送到了github上。有兴趣的小伙伴可以给个星星！

+ [github地址](https://github.com/ouyangpeng/ChinesePinyinSortAndStrokeSort) 
https://github.com/ouyangpeng/ChinesePinyinSortAndStrokeSort

# 四、参考链接

+ [【我的Java开发学习之旅】如何实现中文汉字进行笔划(笔画)排序？](https://blog.csdn.net/ouyang_peng/article/details/83863693)

+  https://github.com/ouyangpeng/ChineseStrokeSorting/blob/master/jar包源代码以及demo下载/ChinessStroke.db

+  [【我的Android进阶之旅】如果使用.output命令将SQLite数据库中的数据导出为文本？](https://blog.csdn.net/qq446282412/article/details/84070920)

+  https://github.com/ouyangpeng/ChineseStrokeSorting/blob/master/jar包源代码以及demo下载/ChinessStroke.dat

+ https://github.com/ouyangpeng/ChinesePinyinSortAndStrokeSort

------

![在这里插入图片描述](https://img-blog.csdn.net/20150708201910089)

>作者：欧阳鹏 欢迎转载，与人分享是进步的源泉！
转载请保留原文地址：https://blog.csdn.net/qq446282412/article/details/84109727
如果本文对您有所帮助，欢迎您扫码下图所示的支付宝和微信支付二维码对本文进行打赏。

![](https://img-blog.csdn.net/20170413233715262?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvb3V5YW5nX3Blbmc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
