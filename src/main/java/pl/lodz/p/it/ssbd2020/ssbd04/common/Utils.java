package pl.lodz.p.it.ssbd2020.ssbd04.common;

/**
 * Klasa z metodami użytkowymi.
 */
public class Utils {

    /**
     * Zwraca informację o tym, czy ciąg znaków jest pusty lub null.
     *
     * @param string ciąg znaków.
     * @return true, jeżeli znaków jest pusty lub null.
     */
    public static Boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
