package com.qiuguan.boot.controller;

import com.qiuguan.boot.group.Book;
import com.qiuguan.boot.group.ValidGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiuguan
 * @date 2022/09/18 14:59:09  星期日
 */
@Slf4j
@RestController
public class BookGroupController {

    @PostMapping("/bookAdd")
    public Book add(@Validated(ValidGroup.Operate.Add.class) Book book) {
        log.info("add book is {}", book);
        return book;
    }

    @PostMapping("/bookUpdate")
    public boolean update(@Validated(ValidGroup.Operate.Update.class) Book book) {
        log.info("book is {}", book);
        return true;
    }
}
