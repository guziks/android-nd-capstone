package ua.com.elius.familycart;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private static final String FRAGMENT_TAG_GEOFENCE = "geofence";

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

        addGeofenceFragment();
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

    private void addGeofenceFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(new GeofenceFragment(), FRAGMENT_TAG_GEOFENCE)
                .commit();
    }
}
