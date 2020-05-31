package pl.lodz.p.it.ssbd2020.ssbd04.validation;

public class Regex {
    public static final String FIRST_NAME = "(?ui)^\\p{L}+([ -]\\p{L}*'?\\p{L}+)*$";
    public static final String LAST_NAME = "(?ui)^\\p{L}*'?\\p{L}+([ -]\\p{L}*'?\\p{L}+)*$";
    public static final String LOGIN = "^[a-zA-Z0-9]+([-._][a-zA-Z0-9])*$";
    public static final String PHONE = "^\\+?[0-9]+$";
    public static final String UUID = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";
}