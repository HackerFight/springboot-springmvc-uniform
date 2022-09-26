package com.qiuguan.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author qiuguan
 *
 * 　{@link InitBinder}用于在控制器(Controller)中标注于方法上，表示为当前控制器注册一个属性编辑器，
 *   只对当前的Controller有效。@InitBinder标注的方法必须有一个参数WebDataBinder。webDataBinder是用于表单到方法的数据绑定的。
 *   所谓的属性编辑器可以理解就是帮助我们完成参数绑定。
 *
 * 　{@link InitBinder}只在{@link Controller} 中注解方法来为这个控制器注册一个绑定器初始化方法，方法只对本控制器有效。
 */
@Controller
public class BaseController {

    /**
     * 请看 {@link com.qiuguan.boot.bean.Student#setBirthDate(Date)}
     *
     * 完成前端字符串形式的日期与后端的Date类型映射的方式：
     *  1.使用 {@link org.springframework.format.annotation.DateTimeFormat}
     *  2.使用 {@link InitBinder}
     *  3.可以实现 {@link org.springframework.web.method.support.HandlerMethodArgumentResolver} 接口自己完成转换逻辑
     */
    @InitBinder
    protected void initBinder(WebDataBinder webDataBinder) {
        //webDataBinder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));

        //webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));

        //自定义
        webDataBinder.registerCustomEditor(Date.class, new MyDateEditor());
    }

    /**
     * @see <a href="https://cloud.tencent.com/developer/article/1952807"></a>
     */
    private static class MyDateEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = format.parse(text);
            } catch (ParseException e) {
                format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = format.parse(text);
                } catch (ParseException e1) {
                }
            }
            setValue(date);
        }
    }
}
