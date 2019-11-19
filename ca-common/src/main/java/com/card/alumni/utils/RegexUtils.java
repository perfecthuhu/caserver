package com.card.alumni.utils;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @author liumingyu
 * @date 2019-11-19 10:32 PM
 */
public class RegexUtils {

    /**
     * 验证Email
     *
     * @param email email地址
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

    /**
     * 验证手机号码
     *
     * @param mobile 手机号码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        String regex = "(\\+\\d+)?1[34578]\\d{9}$";
        return Pattern.matches(regex, mobile);
    }

    /**
     * 验证整数（正整数和负整数）
     *
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDigit(String digit) {
        String regex = "^-?[0-9]+";
        return Pattern.matches(regex, digit);
    }

    public static void main(String[] args) {
        boolean isChinese = checkDigit("231a212");
        System.out.println(isChinese);
        boolean isPhone = checkMobile("15604619791");
        System.out.println(isPhone);
    }
}
