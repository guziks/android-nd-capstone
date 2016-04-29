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
                .getBoolean(SignInActivity.PREF_USER_SIGNED_IN, false);

        if (userSignedIn) {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            finish();
        }
    }

}
