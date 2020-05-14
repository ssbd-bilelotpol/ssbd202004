package pl.lodz.p.it.ssbd2020.ssbd04.security;

import java.util.Map;

/**
 * Przechowuje stałe ciągi znakowe odpowiadające za identifykacje ról w kontenerze aplikacyjnym.
 */
public class Role {
    private static final String Admin = "ADMIN";
    private static final String Manager = "MANAGER";
    private static final String CustomerService = "CUSTOMER_SERVICE";
    private static final String Client = "CLIENT";

    public static final Map<String, String> GroupRoleMapper = Map.of(
            "client", Role.Client,
            "customer_service", Role.CustomerService,
            "manager", Role.Manager,
            "admin", Role.Admin
    );


    public static final String EditAccountAccessLevel = "editAccountAccessLevel";
    public static final String GetAccessLevels = "getAccessLevels";
    public static final String RetrieveOwnAccountDetails = "retrieveOwnAccountDetails";
    public static final String EditOwnAccountDetails = "editOwnAccountDetails";
    public static final String EditOtherAccountDetails = "editOtherAccountDetails";
    public static final String GetAllAccounts = "getAllAccounts";
    public static final String GetAllAccountsAuthInfo = "getAllAccountsAuthInfo";
    public static final String GetAccountAuthInfo = "getAccountAuthInfo";
    public static final String ChangeOwnAccountPassword = "changeOwnAccountPassword";
    public static final String ChangeOtherAccountPassword = "changeOtherAccountPassword";
    public static final String ChangeAccountActiveStatus = "changeAccountActiveStatus";
    public static final String RetrieveOtherAccountDetails = "retrieveOtherAccountDetails";
    public static final String ChangeRole = "changeRole";
    public static final String FindAccountsByName = "findAccountsByName";
}
