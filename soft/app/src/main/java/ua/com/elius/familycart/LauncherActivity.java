package ua.com.elius.familycart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean userSignedIn = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(SignActivity.PREF_USER_SIGNED_IN, false);

        Intent intent;
        if (userSignedIn) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, SignActivity.class);
        }
        this.startActivity(intent);
    }

}
