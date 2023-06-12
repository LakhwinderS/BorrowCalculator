package au.com.bank.common.utility;

public class StringUtils {

    public static boolean isNullorEmpty(Integer value) {
        return (value == null);
    }
    public static boolean isNullorEmpty(String value) {
        return !(value != null && !"".equals(value));
    }
    public static boolean isNullorEmpty(String[] value) {
        return !(value != null && value.length != 0);
    }
    public static String format(String locatorString, String valueToReplace) {
        if (null != valueToReplace) {
            return locatorString.replace(Constants.LOCATOR_REPLACE.getStringValue(), valueToReplace);
        }
        return null;
    }
    public static String removeBlankReference(String valueToReplace) {
        return valueToReplace.replace(Constants.BLANK_VALUE.getStringValue(), "");
    }
}
