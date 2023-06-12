package au.com.bank.common.utility;

public enum Constants {
    TOTAL_CLICK_COUNTER(1),
    ANYVALUE_REPLACEMENT("AnyValue"),
    LOCATOR_REPLACE("(.?)"),
    BLANK_VALUE("#blank"),
    TOTAL_LOOP_COUNT(10);
    Constants(final int value) {
        this.intValue = value;
    }
    Constants(final String value) {
        this.stringValue = value;
    }

    private String stringValue;

    private int intValue;

    public int getIntValue() {
        return intValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
