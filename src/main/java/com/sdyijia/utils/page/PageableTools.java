package com.sdyijia.utils.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public class PageableTools {
    //默认分页
    public static final Integer PAGE_SIZE = 20;


    /**
     * 获取基础分页对象
     * - 默认以id降序排序
     *
     * @param page 获取第几页
     * @return
     */
    public static Pageable basicPage(Integer page) {
        return basicPage(page, 0, null);
    }

    /**
     * 获取基础分页对象
     *
     * @param page 获取第几页
     * @param dtos 排序对象数组
     * @return
     */
    public static Pageable basicPage(Integer page, SortDto... dtos) {
        return basicPage(page, 0, dtos);
    }

    /**
     * 获取基础分页对象
     *
     * @param page 获取第几页
     * @param size 每页条数
     * @param dtos 排序对象数组
     * @return
     */
    public static Pageable basicPage(Integer page, Integer size, SortDto... dtos) {
        Sort sort = SortTools.basicSort(dtos);
        page = (page == null || page < 0) ? 0 : page;
        size = (size == null || size <= 0) ? PAGE_SIZE : size;
        return PageRequest.of(page, size, sort);
    }


}
