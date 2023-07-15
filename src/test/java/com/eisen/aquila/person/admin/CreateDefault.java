package com.eisen.aquila.person.admin;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import com.eisen.aquila.module.person.model.Person;
import com.eisen.aquila.module.person.model.Role;

import jakarta.transaction.Transactional;

public class CreateDefault {
    public static final String ADMIN_EMAIL = "admin@admin.com";
    public static final String ADMIN_PASSWORD = "admin";

    @Transactional
    public static void create() {
        List<Role> roles = createRoles();

        Person person = new Person();
        person
                .name("Admin")
                .login(ADMIN_EMAIL)
                .email(ADMIN_EMAIL)
                .password(ADMIN_PASSWORD)
                .birth(LocalDate.now())
                .phone("18999999999")
                .roles(new HashSet<Role>(roles))
                .persist();
    }

    @Transactional
    private static List<Role> createRoles() {
        Role userRole = new Role()
                .nameDisplay("User")
                .nameId("user");

        Role clientRole = new Role()
                .nameDisplay("Client")
                .nameId("client");

        Role adminRole = new Role()
                .nameDisplay("Admin")
                .nameId("admin");

        Role superAdminRole = new Role()
                .nameDisplay("Super-Admin")
                .nameId("super_admin");

        userRole.persist();
        clientRole.persist();
        adminRole.persist();
        superAdminRole.persist();

        return List.of(userRole, clientRole, adminRole, superAdminRole);
    }
}
