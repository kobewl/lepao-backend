package com.wangliang.lepao.once;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class UserInfo {

    /**
     * planetCode:成员编号
     */
    @ExcelProperty("成员编号")
    private String planetCode;

    /**
     * username:成员昵称
     */
    @ExcelProperty("成员昵称")
    private String username;

}