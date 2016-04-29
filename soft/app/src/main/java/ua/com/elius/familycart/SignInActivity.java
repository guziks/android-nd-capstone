package ua.com.elius.familycart;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String TAG = "SignInActivity";

    public static final String PREF_USER_NAME = "userName";
    public static final String PREF_USER_EMAIL = "userEmail";
    public static final String PREF_USER_PHOTO_URL = "userPhotoUrl";
    public static final String PREF_USER_ID = "userId";
    public static final String PREF_USER_ID_TOKEN = "userIdToken";
    public static final String PREF_USER_SIGNED_IN = "userSignedIn";

    private static final int RC_SIGN_IN = 0;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_services_oauth_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        //noinspection ConstantConditions
        signInButton.setOnClickListener(this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, connectionResult.getErrorMessage());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "Sign in result status code: " + result.getStatus().getStatusCode());
        if (result.isSuccess()) {
            Log.d(TAG, "Sign in successful");
            GoogleSignInAccount account = result.getSignInAccount();
            //noinspection ConstantConditions
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putString(PREF_USER_NAME, account.getDisplayName())
                    .putString(PREF_USER_EMAIL, account.getEmail())
                    .putString(PREF_USER_PHOTO_URL, account.getPhotoUrl().toString())
                    .putString(PREF_USER_ID, account.getId())
                    .putString(PREF_USER_ID_TOKEN, account.getIdToken())
                    .putBoolean(PREF_USER_SIGNED_IN, true)
                    .commit();
            //noinspection ConstantConditions
//            Toast.makeText(this,
//                    "Name " + result.getSignInAccount().getDisplayName(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(this,
//                    "Email " + result.getSignInAccount().getEmail(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(this,
//                    "ID " + result.getSignInAccount().getId(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(this,
//                    "TokenID " + result.getSignInAccount().getIdToken(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(this,
//                    "Photo URL " + result.getSignInAccount().getPhotoUrl(), Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "Sign in failed");
        }
    }

}
