package com.sdyijia.config.aop.manager;

import com.alibaba.fastjson.JSON;
import com.sdyijia.config.aop.bean.LogAdmModel;
import org.springframework.stereotype.Service;

/**
 * ILogManager实现类，将日志入库。
 */
@Service
public class DBLogManager implements ILogManager {

    @Override
    public void dealLog(LogAdmModel paramLogAdmBean) {
        System.out.println("将日志存入数据库,日志内容如下: " + JSON.toJSONString(paramLogAdmBean));
    }
}
