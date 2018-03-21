package pl.filipmarkiewicz.asystentkierowcy;

/**
 * Created by Filip on 2018-03-19.
 */

public class SimpleRecord {
    private String code;
    private String key;
    private String value;

    public SimpleRecord(String code, String key, String value) {
        this.code = code;
        this.key = key;
        this.value = value;
    }

    public String getCode() {
        return this.code;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public SimpleRecord setValue(String newValue) {
        this.value = newValue;

        return this;
    }
}
