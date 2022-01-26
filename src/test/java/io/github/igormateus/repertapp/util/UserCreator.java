package io.github.igormateus.repertapp.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import io.github.igormateus.repertapp.model.AppUser;
import io.github.igormateus.repertapp.model.AppUserRole;

public class UserCreator {

    public static AppUser createToBeSaved() {
        AppUser newUser = new AppUser();

        // Long id;
        // Date createdAt;
        // Date updatedAt;
        newUser.setUsername("username_test");
        newUser.setPassword("password_test");
        newUser.setEmail("test@email.com");
        newUser.setName("name_test");
        newUser.setBio("Bio test");
        newUser.setAppUserRoles(new ArrayList<AppUserRole>(new ArrayList<AppUserRole>(Arrays.asList(AppUserRole.ROLE_CLIENT))));

        return newUser;
    }

    public static AppUser createValid() {
        AppUser user = createToBeSaved();

        user.setId(1L);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        
        return user;
    }
}
