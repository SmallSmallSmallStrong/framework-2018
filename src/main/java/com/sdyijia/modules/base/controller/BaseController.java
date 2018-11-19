package com.sdyijia.modules.base.controller;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.lang.reflect.Field;
import java.util.Map;

public abstract class BaseController {

    public static final String REDIRECT = "redirect:";
    public static final String FORWARD = "forward:";

    public ModelAndView redirectUrl(Model m, String url) {
        Map<String, Object> map = m.asMap();
        ModelAndView mv = new ModelAndView();
        RedirectView redirectView = new RedirectView(url);
        mv.setView(redirectView);
        mv.addAllObjects(map);
        return mv;
    }

    public ModelAndView redirectUrl(String url) {
        ModelAndView mv = new ModelAndView();
        RedirectView redirectView = new RedirectView(url);
        mv.setView(redirectView);
        return mv;
    }

    /**
     * 利用java反射机制将子类定义的常量放到model中
     *
     * @param c 子类的class
     * @param m Model参数
     */
    public void addUrl(Class<? extends BaseController> c, Model m) {
        Field[] fields = c.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Class<?> type = fields[i].getType();
            if (type.equals(String.class)) {
                String name = fields[i].getName();
                if (!("POJO_NAME".equals(name) || "PERMISSION_PREFIX".equals(name) || "PERMISSION_URL_PREFIX".equals(name))) {
                    System.out.println(fields[i].getName());
                    try {
                        m.addAttribute(fields[i].getName(), fields[i].get(c));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
