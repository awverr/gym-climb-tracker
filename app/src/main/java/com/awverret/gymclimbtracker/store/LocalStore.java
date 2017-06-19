package com.awverret.gymclimbtracker.store;

import com.awverret.gymclimbtracker.model.User;
import com.google.common.base.Optional;

/**
 * Created by aubry on 4/11/17.
 */

public interface LocalStore {
    void clearUser();

    Optional<User> getUser();

    void setUser(User user);
}
