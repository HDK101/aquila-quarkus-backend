INSERT INTO persons(id, birth, email, login, name, password_hash, phone)
VALUES (
        NEXTVAL(persons_SEQ),
        "2000-01-01",
        "admin@admin.com.br",
        "admin@admin.com.br",
        "Admin",
        "$2y$08$cBpzoTm0rgu3hC/g8mk6SeKGZTognWcpbmM/dLxAyhNirbIHvLw36",
        "18999999999"
    );

INSERT INTO person_role(
    person_id,
    role_id
) VALUES (
    LASTVAL(persons_SEQ),
    (SELECT id FROM roles r WHERE r.name_id = "super_admin")
);

INSERT INTO person_role(
    person_id,
    role_id
) VALUES (
    LASTVAL(persons_SEQ),
    (SELECT id FROM roles r WHERE r.name_id = "admin")
);

INSERT INTO person_role(
    person_id,
    role_id
) VALUES (
    LASTVAL(persons_SEQ),
    (SELECT id FROM roles r WHERE r.name_id = "client")
);

INSERT INTO person_role(
    person_id,
    role_id
) VALUES (
    LASTVAL(persons_SEQ),
    (SELECT id FROM roles r WHERE r.name_id = "user")
);