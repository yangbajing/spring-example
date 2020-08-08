package me.yangbajing.springreactive.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CheckUtils {
    private static Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static Pattern PHONE_REGEX = Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[0-9])|(18[0-9|]))\\d{8}$");

    public static boolean isEmail(String value) {
        return check(value, EMAIL_REGEX);
    }

    public static boolean isPhone(String value) {
        return check(value, PHONE_REGEX);
    }

    public static boolean check(String str, Pattern pattern) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        try {
            Matcher matcher = pattern.matcher(str);
            return matcher.matches();
        } catch (Exception e) {
            log.error("check({}, {})失败", str, pattern.pattern(), e);
            return false;
        }
    }

}
