package com.topoutlabs.gymclimbtracker.store;

import android.content.Context;

import com.topoutlabs.gymclimbtracker.model.User;
import com.google.common.base.Optional;

/**
 * Created by aubry on 4/11/17.
 */

public class PreferencesLocalStore implements LocalStore {
    public static final String PREF_TAG = "com.aubray.periodically.0";
    public static final String UID = "uid";
    public static final String EMAIL = "email";
    public static final String GIVEN_NAME = "givenName";
    public static final String FAMILY_NAME = "familyName";

    private final Context context;

    public PreferencesLocalStore(Context context) {
        this.context = context;
    }

    @Override
    public void setUser(User user) {
        context.getSharedPreferences(PREF_TAG, 0).edit()
                .putString(UID, user.getUid())
                .putString(EMAIL, user.getEmailAddress())
                .putString(GIVEN_NAME, user.getFirstName())
                .putString(FAMILY_NAME, user.getLastName())
                .apply();
    }

    @Override
    public void clearUser() {
        // Add abstraction
        context.getSharedPreferences(PREF_TAG, 0).edit()
                .remove(UID)
                .remove(EMAIL)
                .remove(GIVEN_NAME)
                .remove(FAMILY_NAME)
                .apply();
    }

    @Override
    public Optional<User> getUser() {
        if (context.getSharedPreferences(PREF_TAG, 0).contains("email")) {
            String uid = context.getSharedPreferences(PREF_TAG, 0).getString(UID, "");
            String email = context.getSharedPreferences(PREF_TAG, 0).getString(EMAIL, "");
            String givenName = context.getSharedPreferences(PREF_TAG, 0).getString(GIVEN_NAME, "");
            String familyName = context.getSharedPreferences(PREF_TAG, 0).getString(FAMILY_NAME, "");

            User user = new User(uid, email, givenName, familyName);
//            if (!photo.isEmpty()) {
//                user.setProfileImageURL(photo);
//            }

            return Optional.of(user);
        }

        return Optional.absent();
    }
}
