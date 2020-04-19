package pl.lodz.p.it.ssbd2020.ssbd04.security;

import java.util.Map;

/**
 * Przechowuje stałe ciągi znakowe odpowiadające za identifykacje ról w kontenerze aplikacyjnym.
 */
public class Role {
    public static final String Admin = "ADMIN";
    public static final String Manager = "MANAGER";
    public static final String CustomerService = "CUSTOMER_SERVICE";
    public static final String Client = "CLIENT";

    public static final Map<String, String> GroupRoleMapper = Map.of(
            "client", Role.Client,
            "customer_service", Role.CustomerService,
            "manager", Role.Manager,
            "admin", Role.Admin
    );
}
