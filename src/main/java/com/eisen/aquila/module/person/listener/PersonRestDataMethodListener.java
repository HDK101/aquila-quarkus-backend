package com.eisen.aquila.module.person.listener;

import com.eisen.aquila.module.person.model.Person;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.rest.data.panache.RestDataResourceMethodListener;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonRestDataMethodListener implements RestDataResourceMethodListener<Person> {

    @Override
    public void onBeforeAdd(Person entity) {
        entity.passwordHash = BcryptUtil.bcryptHash(entity.password, 8);
    }

    @Override
    public void onBeforeUpdate(Person entity) {
        if (entity.password != null) {
            entity.passwordHash = BcryptUtil.bcryptHash(entity.password, 8);
        }
    }
    
}
