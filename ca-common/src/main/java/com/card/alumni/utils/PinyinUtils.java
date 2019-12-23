package com.card.alumni.utils;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sunxiaodong10 2019/12/23
 * @date 8:47 PM
 */
public class PinyinUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(PinyinUtils.class);

    private final static Pattern IS_CHINESE = Pattern.compile("[\u4e00-\u9fa5]");

    private final static Pattern IS_CONTAINS_NUM = Pattern.compile(".*\\d+.*");
    /**
     * 取得给定汉字串的首字母串
     *
     * @param chinese 给定汉字串
     * @return 所有字符的首字母
     */
    public static String getAllFirstLetter(String chinese) {
        if (chinese == null || chinese.trim().length() == 0) {
            return "";
        }
        try {
            return PinyinHelper.getShortPinyin(chinese);
        } catch (PinyinException e) {
            LOGGER.error("字符串：【{}】转换拼音首字母异常", chinese, e);
            return chinese;
        }
    }

    /**
     * 取得给定汉字的首字母,即声母
     *
     * @param chinese 给定的汉字
     * @return 给定汉字的声母
     */
    public static String getFirstLetter(String chinese) {
        if (chinese == null || chinese.trim().length() == 0) {
            return "";
        }
        try {
            return PinyinHelper.getShortPinyin(String.valueOf(chinese.charAt(0)));
        } catch (PinyinException e) {
            LOGGER.error("字符串：【{}】转换开头字母异常", chinese, e);
            return chinese;
        }
    }

    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Matcher m = IS_CHINESE.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 判断一个字符串是否含有数字
     *
     * @param content
     * @return
     */
    public static boolean isContainDigit(String content) {
        boolean flag = false;
        if (StringUtils.isBlank(content)) {
            return flag;
        }

        Matcher m = IS_CONTAINS_NUM.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 取得给定汉字串的首字母串
     *
     * @param chinese 给定汉字串
     * @return 所有字符的首字母
     */
    public static String getAllFirstLetterRemoveOthers(String chinese) {
        if (chinese == null || chinese.trim().length() == 0) {
            return "";
        }
        try {
            chinese = chinese.replaceAll("[^(\\u4e00-\\u9fa5)]", " ").replaceAll("\\(|\\)", " ").replaceAll(" +", " ");
            return PinyinHelper.getShortPinyin(chinese);
        } catch (PinyinException e) {
            LOGGER.error("字符串：【{}】转换拼音首字母异常", chinese, e);
            return chinese;
        }
    }
}
