package pl.lodz.p.it.ssbd2020.ssbd04.common;

import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import java.util.Map;

public class Group {
    public static final String ADMIN = "admin";
    public static final String MANAGER = "manager";
    public static final String CUSTOMER_SERVICE = "customer_service";
    public static final String CLIENT = "client";

    public static final Map<String, String> GroupRoleMapper = Map.of(
            Group.CLIENT, Role.CLIENT,
            Group.CUSTOMER_SERVICE, Role.CUSTOMER_SERVICE,
            Group.MANAGER, Role.MANAGER,
            Group.ADMIN, Role.ADMIN
    );
}
