package ua.com.elius.familycart.data.person;

import android.net.Uri;
import android.provider.BaseColumns;

import ua.com.elius.familycart.data.ListProvider;
import ua.com.elius.familycart.data.item.ItemColumns;
import ua.com.elius.familycart.data.person.PersonColumns;

/**
 * Family member or friend.
 */
public class PersonColumns implements BaseColumns {
    public static final String TABLE_NAME = "person";
    public static final Uri CONTENT_URI = Uri.parse(ListProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Google ID. Corresponds to gms...Person.getId().
     */
    public static final String GID = "gid";

    /**
     * Member name. Corresponds to gms...Person.getDisplayName().
     */
    public static final String DISPLAY_NAME = "display_name";

    /**
     * Photo, avatar, etc. Corresponds to gms...Person.getImage().
     */
    public static final String IMAGE_URL = "image_url";

    /**
     * Is this person selected for sharing.
     */
    public static final String SHARING_TO_ALLOWED = "sharing_to_allowed";

    /**
     * Does this person selected me for sharing
     */
    public static final String SHARING_FROM_ALLOWED = "sharing_from_allowed";

    /**
     * Last modification timestamp.
     */
    public static final String TIME_MODIFIED = "time_modified";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            GID,
            DISPLAY_NAME,
            IMAGE_URL,
            SHARING_TO_ALLOWED,
            SHARING_FROM_ALLOWED,
            TIME_MODIFIED
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(GID) || c.contains("." + GID)) return true;
            if (c.equals(DISPLAY_NAME) || c.contains("." + DISPLAY_NAME)) return true;
            if (c.equals(IMAGE_URL) || c.contains("." + IMAGE_URL)) return true;
            if (c.equals(SHARING_TO_ALLOWED) || c.contains("." + SHARING_TO_ALLOWED)) return true;
            if (c.equals(SHARING_FROM_ALLOWED) || c.contains("." + SHARING_FROM_ALLOWED)) return true;
            if (c.equals(TIME_MODIFIED) || c.contains("." + TIME_MODIFIED)) return true;
        }
        return false;
    }

}
