package com.sdyijia.utils.tool;

import java.math.BigDecimal;

public class ToolMoney {
    
    public static final int TEN_THOUSAND = 10000;
    public static final int BILLION = 100000000;
    
    /**
     * 判断是否小于1万元，如果小于直接返回“金额（元）”，否则返回“金额（万元）”
     * 
     * @param money
     * @return
     */
    public static String tenThousandFormat(double money){
        String result = null;
        if(money < TEN_THOUSAND){
            result = money + "元";
        }else{
            result = keep2Decimal(money / TEN_THOUSAND) + "万元";
        }
        return result;
    }
    
    /**
     * 判断是否小于1亿元，如果小于根据tenThousandFormat方法返回，否则返回“金额（亿元）”
     * 
     * @param money
     * @return
     */
    public static String billionFormat(double money){
        String result = null;
        if(money < BILLION){
            result = tenThousandFormat(money);
        }else{
            result = keep2Decimal(money / BILLION) + "亿元";
        }
        return result;
    }
    
    /**
     * 保留两位小数
     * @return
     */
    public static double keep2Decimal(double num){
        double resultNum = 0d;
        try {
            BigDecimal bigDecimal = new BigDecimal(num);
            resultNum = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Exception e) {
        }
        return resultNum;
    }

}
