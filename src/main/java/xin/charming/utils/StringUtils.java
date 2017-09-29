package xin.charming.utils;

public class StringUtils {

    public static final boolean isEmptyAndNull(String str) {
        if (str == null || str.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
