package pl.lodz.p.it.ssbd2020.ssbd04.security;

/**
 * Interferjs udostępniający metody dodawania wartości ETag do nagłówka
 */
public interface Signable {
    String createMessage();
}
