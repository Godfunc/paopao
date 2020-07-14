package com.godfunc.paopao.result;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Godfunc
 * @date 2020/2/11 13:27
 */
@Data
@Accessors(chain = true)
public class SendInfoResult implements Serializable {

    private String status;

    private String msg;
}
