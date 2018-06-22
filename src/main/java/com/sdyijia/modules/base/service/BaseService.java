package com.sdyijia.modules.base.service;


import com.sdyijia.modules.base.bean.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
import java.util.Date;

public abstract class BaseService<D extends CrudRepository<T, Long>, T extends Base> {

    /** 上传文件路径 */
    public static final String UPLOADURL = "/file";


    @Autowired
    protected D repository;

    @Transactional
    public T save(T t) {
        try {
            Date now = new Date();
            t.setCreatedTime(now);
            t.setUpdataTime(now);
            return repository.save(t);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Transactional
    public T update(T t) {
        try {
            Date now = new Date();
            t.setUpdataTime(now);
            return repository.save(t);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    public D $() {
        return repository;
    }



}