package ua.com.elius.familycart;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {

    private static final String TAG = "MainActivity";

    private static final int RC_LOCATION_PERMISSIONS = 1;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private ArrayList<Geofence> mGeofenceList;
    private boolean mGeofencesAdded;
    private PendingIntent mGeofencePendingIntent;

    public MainActivity() {
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeaderInit(navigationView);

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_to_buy);
            switchFragment(ToBuyListFragment.newInstance());
            getSupportActionBar().setTitle("To buy");
        }

        buildGoogleApiClient();
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void navHeaderInit(final NavigationView navigationView) {
        View navHeader = navigationView.getHeaderView(0);
        TextView userNameView = (TextView) navHeader.findViewById(R.id.userName);
        TextView userEmailView = (TextView) navHeader.findViewById(R.id.userEmail);
        final ImageView userPhotoView = (ImageView) navHeader.findViewById(R.id.userPhoto);

        final ToggleButton accountDropButton = (ToggleButton) navHeader.findViewById(R.id.accountDropButton);
        accountDropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationView.getMenu().clear();
                if (accountDropButton.isChecked()) {
                    navigationView.inflateMenu(R.menu.activity_main_drawer_account);
                } else {
                    navigationView.inflateMenu(R.menu.activity_main_drawer);
                }
            }
        });

        String name = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(SignActivity.PREF_USER_NAME, "");
        String email = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(SignActivity.PREF_USER_EMAIL, "");
        String photoUrl = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(SignActivity.PREF_USER_PHOTO_URL, "");

        userNameView.setText(name);
        userEmailView.setText(email);
        Glide.with(this).load(photoUrl).asBitmap().centerCrop().into(new BitmapImageViewTarget(userPhotoView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(MainActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                userPhotoView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public void signOut(MenuItem item) {
        Intent intent = new Intent(this, SignActivity.class);
        intent.setAction(SignActivity.ACTION_SIGN_OUT);
        startActivity(intent);
        finish();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings({"StatementWithEmptyBody", "ConstantConditions"})
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_to_buy) {
            getSupportActionBar().setTitle("To buy");
            switchFragment(ToBuyListFragment.newInstance());
        } else if (id == R.id.nav_wont_buy) {
            getSupportActionBar().setTitle("Wont buy");
            switchFragment(WontBuyListFragment.newInstance());
        } else if (id == R.id.nav_bought) {
            getSupportActionBar().setTitle("Bought");
            switchFragment(BoughtListFragment.newInstance());
        } else if (id == R.id.nav_family) {
            getSupportActionBar().setTitle("Family");
            switchFragment(FamilyFragment.newInstance());
        } else if (id == R.id.nav_settings) {
            getSupportActionBar().setTitle("Settings");
            switchFragment(SettingsFragment.newInstance());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchFragment(Fragment switchToFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, switchToFragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected");
        getLocation();
        createGeofance();
        addGeofances();
    }

    private void getLocation() {
        Log.d(TAG, "getLocation");
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    RC_LOCATION_PERMISSIONS);

            return;
        }
        Log.d(TAG, "Actually getLocation");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            Toast.makeText(this, "Lat: " + String.valueOf(mLastLocation.getLatitude()), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Long: " + String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RC_LOCATION_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    // permission denied
                }
                return;
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Google API connection failed");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "Google API connection suspended");
    }

    public void createGeofance() {
        if (mLastLocation == null) {
            return;
        }
        if (mGeofenceList == null) {
            mGeofenceList = new ArrayList<>();
        }
        mGeofenceList.add(new Geofence.Builder()
                // Set the request ID of the geofence. This is a string to identify this
                // geofence.
                .setRequestId("home")

                .setCircularRegion(
                        mLastLocation.getLatitude(),
                        mLastLocation.getLongitude(),
                        100
                )
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build());
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        // The INITIAL_TRIGGER_ENTER flag indicates that geofencing service should trigger a
        // GEOFENCE_TRANSITION_ENTER notification when the geofence is added and if the device
        // is already inside that geofence.
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);

        // Add the geofences to be monitored by geofencing service.
        builder.addGeofences(mGeofenceList);

        // Return a GeofencingRequest.
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void addGeofances() {
        if (!mGoogleApiClient.isConnected()) {
            return;
        }
        try {
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    // The GeofenceRequest object.
                    getGeofencingRequest(),
                    // A pending intent that that is reused when calling removeGeofences(). This
                    // pending intent is used to generate an intent when a matched geofence
                    // transition is observed.
                    getGeofencePendingIntent()
            ).setResultCallback(this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            Log.e(TAG, "Invalid location permission. " +
                    "You need to use ACCESS_FINE_LOCATION with geofences", securityException);
        }
    }

    @Override
    public void onResult(@NonNull Status status) {

    }
}
