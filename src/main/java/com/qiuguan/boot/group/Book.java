package com.qiuguan.boot.group;

import com.qiuguan.boot.group.ValidGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author qiuguan
 * @date 2022/09/17 00:23:54  星期六
 *
 * 一个VO对象在新增的时候某些字段为必填，在更新的时候又非必填。如Book中 id 和 bookId 属性在新增操作时都是非必填(后端生成)
 * 而在编辑操作时都为必填，bookName在新增操作时为必填，面对这种场景你会怎么处理呢？
 */
@Data
public class Book {

    /**
     * 新增的时候必须为空，修改的时候必须不为空
     */
    @Null(groups = ValidGroup.Operate.Add.class)
    @NotNull(groups = ValidGroup.Operate.Update.class, message = "id must be not null.")
    private Long id;

    /**
     * 新增的时候必须为空，修改的时候必须不为空
     */
    @Null(groups = ValidGroup.Operate.Add.class)
    @NotNull(groups = ValidGroup.Operate.Update.class, message = "bookId must be not null.")
    private Long bookId;

    /**
     * 新增的时候都不可以为空
     */
    @NotBlank(groups = ValidGroup.Operate.Add.class, message = "name must be not null.")
    private String bookName;
}
