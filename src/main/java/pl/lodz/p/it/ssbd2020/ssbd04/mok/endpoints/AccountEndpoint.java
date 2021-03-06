package pl.lodz.p.it.ssbd2020.ssbd04.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.TransactionStarter;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.dto.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2020.ssbd04.security.Role.*;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas Account i AccountAccessLevel.
 */
@Local
public interface AccountEndpoint extends TransactionStarter {
    /**
     * Rejestruje nowe konto.
     *
     * @param accountRegisterDto obiekt zawierający login, hasło i wymagane dane konta.
      gdy login lub e-mail już istnieje.
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @PermitAll
    void register(AccountRegisterDto accountRegisterDto) throws AppBaseException;

    /**
     * Potwierdza konto na podstawie unikalnego identyfkatoru tokena wysłanego na adres e-mail.
     *
     * @param fromString unikalny identyfikator tokenu potwierdzające konto.
      gdy potwierdzenie konta się nie powiedzie.
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @PermitAll
    void confirm(UUID fromString) throws AppBaseException;

    /**
     * Zmienia poziomy dostępu dla danego konta
     *
     * @param login                 login jednoznacznie identyfikujący konto.
     * @param accountAccessLevelDto zawiera listę poziomów dostępów, które mają zostać przypisane użytkownikowi.
     * @return listę poziomów dostępów, które zostały przypisane do konta.
      gdy modyfikacja nie powiedzie się.
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @RolesAllowed(EditAccountAccessLevel)
    AccountAccessLevelDto editAccountAccessLevel(String login, AccountAccessLevelDto accountAccessLevelDto) throws AppBaseException;

    /**
     * Pobiera listę poziomów dostępów przypisanych do konta.
     *
     * @param login login jednoznacznie identyfikujący konto.
     * @return listę poziomów dostępów przypisanych do konta.
      gdy pobieranie nie powiedzie się.
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @RolesAllowed(GetAccessLevels)
    AccountAccessLevelDto getAccessLevels(String login) throws AppBaseException;

    /**
     * Zwraca dane konta inicjującego żądanie.
     *
     * @return konto inicjujące żądanie.
      gdy nie udało się pobrać danych konta.
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @RolesAllowed(RetrieveOwnAccountDetails)
    AccountDto retrieveOwnAccountDetails() throws AppBaseException;

    /**
     * Zwraca dane konta o wybranym loginie.
     *
     * @param login login konta, którego dane zostaną zwrócone.
     * @return konto wybranego użytkownika.
      gdy nie udało się pobrać danych konta.
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @RolesAllowed(RetrieveOtherAccountDetails)
    AccountDto retrieveOtherAccountDetails(String login) throws AppBaseException;

    /**
     * Modyfikuje dane szczegółowe konta inicjującego żądanie.
     *
     * @param accountEditDto nowe dane konta w których skład wchodzi jedynie imie, nazwisko oraz numer telefonu.
     * @return konto inicjujące żądanie z uwzględnionymi zmianami danych.
      gdy zapisanie zmodyfikowanego konta nie powiodło się.
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @RolesAllowed(EditOwnAccountDetails)
    AccountDto editOwnAccountDetails(AccountEditDto accountEditDto) throws AppBaseException;

    /**
     * Modyfikuje dane szczegółowe wybranego konta.
     *
     * @param login          login konta, którego dane zostaną zmodyfikowane.
     * @param accountEditDto nowe dane konta w których skład wchodzi jedynie imie, nazwisko oraz numer telefonu.
     * @return wybrane konto z uwzględnionymi zmianami danych.
      gdy zapisanie zmodyfikowanego konta nie powiodło się.
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @RolesAllowed(EditOtherAccountDetails)
    AccountDto editOtherAccountDetails(String login, AccountEditDto accountEditDto) throws AppBaseException;

    /**
     * Wysyła token resetujący hasło użytkownika o podanym emailu.
     * <p>
     * Użytkownik musi być aktywny, a jego rejestracja potwierdzona.
     *
     * @param email e-mail użytkownika
     * @throws AppBaseException gdy operacja się nie powiodła
     */
    @PermitAll
    void sendResetPasswordToken(String email) throws AppBaseException;

    /**
     * Resetuje hasło za pomocą tokenu resetującego.
     *
     * @param passwordResetDto token resetujący oraz nowe hasło.
      w przypadku niepowodzenia operacji.
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @PermitAll
    void resetPassword(PasswordResetDto passwordResetDto) throws AppBaseException;

    /**
     * Aktualizuje dane o ostatnim poprawnym uwierzytelnieniu użytkownika.
     *
     * @param login         login użytkownika.
     * @param lastIpAddress adres ip.
     * @param currentAuth   data logowania.
      gdy operacja się nie powiedzie
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @PermitAll
    void updateAuthInfo(String login, String lastIpAddress, LocalDateTime currentAuth) throws AppBaseException;

    /**
     * Aktualizuje dane o ostatnim niepoprawnym uwierzytelnieniu użytkownika.
     *
     * @param username          login użytkownika.
     * @param lastIncorrectAuth data logowania.
     * @throws AppBaseException gdy operacja się nie powiodła
     */
    @PermitAll
    void updateAuthInfo(String username, LocalDateTime lastIncorrectAuth) throws AppBaseException;

    /**
     * Zwraca zbiór wszystkich kont wraz z ich danymi ostatniego uwierzytelniania.
     *
     * @return dane uwierzytelniania.
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @RolesAllowed(GetAllAccountsAuthInfo)
    List<AccountAuthInfoDto> getAllAccountsAuthInfo() throws AppBaseException;

    /**
     * Zwraca dane z ostatniego uwierzytelniania dla konta.
     *
     * @return obiekt reprezentujący dane uwierzytelniania
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @RolesAllowed(GetAccountAuthInfo)
    AccountAuthInfoDto getAccountAuthInfo() throws AppBaseException;

    /**
     * Zmienia hasło dla aktualnego użytkownika.
     *
     * @param accountPasswordDto obiekt który przechowuje nowe i stare hasła podane przez użytkownika.
      jeśli Etag się nie zgadza, lub podane stare hasło nie jest zgodne z tym z bazy danych.
     @throws AppBaseException gdy operacja się nie powiodła
     */
    @RolesAllowed(ChangeOwnAccountPassword)
    void changeOwnAccountPassword(AccountPasswordDto accountPasswordDto) throws AppBaseException;

    /**
     * Zmienia hasło dla podanego użytkonika.
     *
     * @param login    login konta, dla którego zmieniane jest hasło.
     * @param password nowe hasło.
      jeśli Etag się nie zgadza.
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @RolesAllowed(ChangeOtherAccountPassword)
    void changeOtherAccountPassword(String login, String password) throws AppBaseException;

    /**
     * Zwraca listę wszystkich kont wraz z ich danymi szczegółowymi, dla których imię i nazwisko jest zgodne z podaną frazą.
     *
     * @param name fraza, której poszukujemy. Jeśli name jest pustym ciągiem znaków lub null, to metoda zwraca wszystkie konta.
     * @return lista kont wraz z danymi szczegółowymi.
      gdy nie udało się znaleźć żadnego konta zgodnego z podaną frazą.
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @RolesAllowed(FindAccountsByName)
    List<AccountDto> findByName(String name) throws AppBaseException;

    /**
     * Zmienia status aktywności dla konta o podanym loginie.
     *
     * @param login  login konta, dla którego zmieniamy status aktywności.
     * @param active wartość statusu aktywności konta, która ma zostać ustawiona.
      gdy nie udało się zmienić statusu aktywności konta.
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @RolesAllowed(ChangeAccountActiveStatus)
    void changeAccountActiveStatus(String login, Boolean active) throws AppBaseException;

    /**
     * Powiadamia użytkownika o roli administratora o logowaniu na jego konto.
     *
     * @param login    login konta, dla którego chcemy wysłać e-mail.
     * @param remoteIP adres IP, z którego nastąpiło logowanie
      w przypadku gdy nie udało się wysłać maila.
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @PermitAll
    void notifyAboutAdminLogin(String login, String remoteIP) throws AppBaseException;
}
