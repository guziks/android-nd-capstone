package ua.com.elius.familycart.data.person;

import ua.com.elius.familycart.data.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Family member or friend.
 */
public interface PersonModel extends BaseModel {

    /**
     * Google ID. Corresponds to gms...Person.getId().
     * Can be {@code null}.
     */
    @Nullable
    String getGid();

    /**
     * Member name. Corresponds to gms...Person.getDisplayName().
     * Can be {@code null}.
     */
    @Nullable
    String getDisplayName();

    /**
     * Photo, avatar, etc. Corresponds to gms...Person.getImage().
     * Can be {@code null}.
     */
    @Nullable
    String getImageUrl();

    /**
     * Is this person selected for sharing.
     */
    boolean getSharingToAllowed();

    /**
     * Does this person selected me for sharing
     */
    boolean getSharingFromAllowed();

    /**
     * Last modification timestamp.
     * Can be {@code null}.
     */
    @Nullable
    Long getTimeModified();
}
