package pl.lodz.p.it.ssbd2020.ssbd04.validation;

/**
 * Klasa agregująca wyrażenia regularne w całej aplikacji
 */
public class Regex {
    public static final String FIRST_NAME = "(?ui)^\\p{L}+([ -]\\p{L}*'?\\p{L}+)*$";
    public static final String LAST_NAME = "(?ui)^\\p{L}*'?\\p{L}+([ -]\\p{L}*'?\\p{L}+)*$";
    public static final String LOGIN = "^[a-zA-Z0-9]+([-._][a-zA-Z0-9])*$";
    public static final String PHONE = "^\\+?[0-9]+$";
    public static final String UUID = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

    public static final String SEAT_CLASS_NAME = "(?ui)^\\p{L}*'?\\p{L}+([ -]\\p{L}*'?\\p{L}+)*$";
    public static final String BENEFIT_NAME = "(?ui)^\\p{L}*'?\\p{L}+([ -]\\p{L}*'?\\p{L}+)*$";

    public static final String AIRPORT_CODE = "[a-zA-Z]{3}";
    public static final String AIRPORT_NAME = "(?ui)^\\p{L}*'?\\p{L}+([ -]\\p{L}*'?\\p{L}+)*$";
    public static final String AIRPORT_CITY = "(?ui)^[\\p{L}]+(?:[\\s-][\\p{L}]+)*$";
    public static final String AIRPORT_COUNTRY = "^[A-Z]{2}$";

    public static final String FLIGHT_CODE = "^[A-Za-z0-9]{5}$";
    public static final String AIRPLANE_SCHEMA_NAME = "^[\\p{L}0-9-](\\s?[\\p{L}0-9-])*$";
    public static final String AIRPLANE_SCHEMA_ROW_COL = "^((\\d\\d?)+(,\\d\\d?)*)?$";
}
