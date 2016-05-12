package ua.com.elius.familycart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class SignActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = "SignActivity";

    public static final String PREF_USER_NAME = "userName";
    public static final String PREF_USER_EMAIL = "userEmail";
    public static final String PREF_USER_PHOTO_URL = "userPhotoUrl";
    public static final String PREF_USER_ID = "userId";
    public static final String PREF_USER_ID_TOKEN = "userIdToken";
    public static final String PREF_USER_SIGNED_IN = "userSignedIn";

    public static String ACTION_SIGN_OUT = "intent.action.SIGN_OUT";

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
                .addConnectionCallbacks(this)
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (getIntent().getAction() != null && getIntent().getAction().equals(ACTION_SIGN_OUT)) {
            signOut();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.getStatusCode() == CommonStatusCodes.SUCCESS) {
                            Log.i(TAG, "Sign out status code: " + status.getStatusCode());
                            PreferenceManager.getDefaultSharedPreferences(SignActivity.this).edit()
                                    .putBoolean(PREF_USER_SIGNED_IN, false)
                                    .commit();
                        }
                    }
                });
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
        Log.d(TAG, "Sign in status code: " + result.getStatus().getStatusCode());
        if (result.isSuccess()) {
            Log.i(TAG, "Sign in successful");
            saveUserInfo(result);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Log.i(TAG, "Sign in unsuccessful");
            Toast.makeText(this, "Sign in unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserInfo(GoogleSignInResult result) {
        GoogleSignInAccount account = result.getSignInAccount();
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        //noinspection ConstantConditions
        editor.putString(PREF_USER_NAME, account.getDisplayName());
        editor.putString(PREF_USER_EMAIL, account.getEmail());
        editor.putString(PREF_USER_ID, account.getId());
        editor.putString(PREF_USER_ID_TOKEN, account.getIdToken());
        editor.putBoolean(PREF_USER_SIGNED_IN, true);
        if (account.getPhotoUrl() != null) {
            editor.putString(PREF_USER_PHOTO_URL, account.getPhotoUrl().toString()); // null check because of toString
        }
        editor.commit();
    }
}
