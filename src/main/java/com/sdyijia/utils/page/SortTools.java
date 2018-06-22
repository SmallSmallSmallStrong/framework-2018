package com.sdyijia.utils.page;

import org.springframework.data.domain.Sort;


public class SortTools {
    /**
     * @return 根据id正序
     */
    public static Sort basicSort() {
        return basicSort("id", "ASC");
    }

    /**
     * 根据orderField字段orderType排列
     *
     * @param orderField 排序字段
     * @param orderType  排列方式
     * @return
     */
    public static Sort basicSort(String orderField, String orderType) {
        Sort sort = new Sort(Sort.Direction.fromString(orderType), orderField);
        return sort;
    }

    /**
     * 多个字段排序
     *
     * @param dtos 创建排序类SortDto，最少一个
     * @return 排序规则
     */
    public static Sort basicSort(SortDto... dtos) {
        Sort result = Sort.unsorted();//这个是框架默认
        if (dtos == null) {
            return result;
        }
        for (int i = 0; i < dtos.length; i++) {
            SortDto dto = dtos[i];
            if (result == null) {
                result = new Sort(Sort.Direction.fromString(dto.getOrderType()), dto.getOrderField());
            } else {
                result = result.and(new Sort(Sort.Direction.fromString(dto.getOrderType()), dto.getOrderField()));
            }
        }
        return result;
    }
}
