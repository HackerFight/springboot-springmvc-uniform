package com.qiuguan.boot.group;

import javax.validation.groups.Default;

/**
 * @author qiuguan
 * @date 2022/09/17 00:27:27  星期六
 */
public interface ValidGroup extends Default {

     interface Operate extends ValidGroup {

        interface Add extends Operate {

        }

        interface Update extends Operate {

        }
    }
}
