package com.godfunc.paopao.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Godfunc
 * @date 2020/2/9 10:14
 */
@Data
public class GroupCreateParam implements Serializable {

    private String name;

    private String email;
}
