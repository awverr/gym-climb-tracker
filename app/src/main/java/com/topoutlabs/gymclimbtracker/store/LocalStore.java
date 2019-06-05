package com.topoutlabs.gymclimbtracker.store;

import com.topoutlabs.gymclimbtracker.model.Gym;
import com.topoutlabs.gymclimbtracker.model.User;
import com.google.common.base.Optional;

/**
 * Created by aubry on 4/11/17.
 */

public interface LocalStore {
    void clearUser();

    Optional<User> getUser();

    void setUser(User user);

    void setGym(Gym gym);

    Gym getGym();
}
