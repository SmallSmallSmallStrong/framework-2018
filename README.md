# sdyijia-framework-2018

2018框架

2018.11.28
添加vuejs到 footer<br>
如果想使用vue,idea最好添加vuejs插件

2018.11.19更新<br/>
添加方法 : <BR>
    ToolStr.captureNametoUp:首字母大写<BR>
    ToolStr.captureNametoLow:首字母小写<BR>
更新数据库位置到112<BR>
添加redis版本2.0.7,配置在application-dev.properties,
配置文件在:com.sdyijia.config.redis.RedisConfig.java
    
2018.11.09更新<br/>
&nbsp;&nbsp;&nbsp;&nbsp;1.在BaseController添加重定向方法，添加url方法<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;调用方式：<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;继承BaseController后，直接调用redirectUrl方法，或者addUrl(this.getClass,Model m)<br/>
    
&nbsp;&nbsp;&nbsp;&nbsp;2.添加Service层；<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用说明：<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果想调用Dao层那么就需要调用BaseSerice中的$()方法即可获取到对应的Dao，
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;BaseService实现了添加creatTime，跟updataTime属性的对应方法。



尚未完成：<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自定义异常拦截机制。<br>
    