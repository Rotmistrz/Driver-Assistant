package pl.filipmarkiewicz.asystentkierowcy;

/**
 * Created by Filip on 2018-04-01.
 */

public enum FuelType {
    UNDEFINED (0),
    PETROL_PLAIN(1),
    DIESEL_PLAIN (2),
    DIESEL_VERVA (3);

    private final int id;

    FuelType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static FuelType parseType(String id) {
        return parseType(Integer.parseInt(id));
    }

    public static FuelType parseType(int id) {
        if (id == FuelType.PETROL_PLAIN.getId()) {
            return FuelType.PETROL_PLAIN;
        } else if (id == FuelType.DIESEL_PLAIN.getId()) {
            return FuelType.DIESEL_PLAIN;
        } else if (id == FuelType.DIESEL_VERVA.getId()) {
            return FuelType.DIESEL_VERVA;
        } else {
            return FuelType.UNDEFINED;
        }
    }
}
