package com.wangliang.lepao.once;

import com.alibaba.excel.EasyExcel;

import java.util.List;

public class ImportExcel {

    public static void main(String[] args) {

        String fileName = "D:\\Java File\\lepao\\lepao-backend\\src\\main\\resources\\testExcel.xlsx";
        //readByListener(fileName);
        synchronousRead(fileName);

    }

    private static void readByListener(String fileName) {

        EasyExcel.read(fileName, UserInfo.class, new UserInfoListener()).sheet().doRead();
    }
    private static void synchronousRead(String fileName) {

        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
        List<UserInfo> list = EasyExcel.read(fileName).head(UserInfo.class).sheet().doReadSync();
        for (UserInfo data : list) {
            System.out.println("读取到数据:" + data);
        }
    }
}
