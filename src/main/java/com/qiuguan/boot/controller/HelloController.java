package com.qiuguan.boot.controller;

import com.qiuguan.boot.bean.Person;
import com.qiuguan.boot.bean.Student;
import com.qiuguan.boot.enums.ApiCodeEnum;
import com.qiuguan.boot.result.ApiResult;
import com.qiuguan.boot.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author qiuguan
 * @date 2022/09/08 18:52:15  星期四
 */
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/hello")
    public ApiResult<?> hello(){
        return ApiResult.ok("666");
    }

    @GetMapping("/test/{id}")
    public String test(@PathVariable Integer id){
        return "7777__" + id;
    }

    @GetMapping("/test2")
    public String testEx(){
        return this.helloService.helloService();
    }

    @GetMapping("/param")
    public ApiResult<?> testParamEx(@RequestParam(name = "id") Integer id){
        if (null == id) {
            return ApiResult.fail(ApiCodeEnum.PARAM_ILLEGAL);
        }
        return ApiResult.ok(id);
    }

    /**
     * GET: http://localhost:8080/bean?id=123&name=fuyuanhui&gender=1&phone=18368116334
     * 参数解析器是：{@link org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor}
     * 它会根据HttpServletRequest对象解析url中携带的参数，将其映射到bean中
     *
     * POST: 必须搭配 {@link RequestBody} 注解
     * 1. 如果使用了{@link RequestBody} 注解，则使用 {@link org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor}
     *    它将读取body中的参数，将其映射到bean中
     * 2. 如果没有使用{@link RequestBody} 注解，则和GET 使用的处理器一样，但是无法完成属性映射，因为他默认是从url中取值
     *    注意：{@link RequestBody} 也可以作用到GET请求上，但是有几点要求：
     *       a).必须要有body体（get请求也可以有）
     *       b).根据请求头的 content-type 选择对应的 HttpMessageConverter 对body中的内容进行转换
     *          -- 只要保证带有body体并且content-type能找到对应的HttpMessageConverter就能解析成功。
     */
    @GetMapping("/bean")
    //@PostMapping("/bean")
    public ApiResult<?> testBean(Person person){
        return ApiResult.ok(person);
    }

    @GetMapping("/get")
    public Person get(){
        return Person.getDefaultBean();
    }

    @PostMapping("/add")
    public Student add(@RequestBody Student student, @RequestHeader HttpHeaders headers) {
        System.out.println(headers);
        String version = headers.get("version").get(0);
        String function = headers.get("function").get(0);

        student.put("version", version);
        student.put("function", function);

        return student;
    }

    @PostMapping("/valid")
    public Student validate(@Validated @RequestBody Student student,
                            @RequestHeader(value = "version", defaultValue = "3.0") String version,
                            @RequestHeader(value = "function") String function) {

        student.put("version", version);
        student.put("function", function);

        return student;
    }
}
