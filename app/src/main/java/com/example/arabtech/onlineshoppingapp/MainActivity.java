package com.example.arabtech.onlineshoppingapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;

import static util.Constants.ERROR_DIALOG_REQUEST;
import static util.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static util.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mainNav = null;
    private NavigationView shoppingNav=null;
    private FrameLayout mainFrame = null;
    private Store store;
    private Profile profile;
    private Notifications notifications;
    private ActionBarDrawerToggle drawerToggle=null;
    private DrawerLayout drawerLayout=null;
    private SearchView searchView;
    private Spinner spinner;
    private boolean mLocationPermissionGranted=false;
    private ArrayList<Integer> prices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater factory = LayoutInflater.from(this);
        final View myView = factory.inflate(R.layout.fragment_store, null);
        store = new Store(/*myView*/);
        profile = new Profile();
        notifications = new Notifications();
        mainFrame = (FrameLayout) findViewById(R.id.mainFrame);
        mainNav = (BottomNavigationView) findViewById(R.id.mainNav);
        shoppingNav=(NavigationView) findViewById(R.id.shoppingNav);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawerLayout);
        spinner=(Spinner) findViewById(R.id.spinner);
        searchView=(SearchView) findViewById(R.id.searchView);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        prices=new ArrayList<>();
        drawerToggle.syncState();
        prices.add(50);
        prices.add(100);
        prices.add(200);
        prices.add(500);
        prices.add(1000);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(checkMapServices()){
            if(mLocationPermissionGranted){
                setFragment(store);
            }
            else{
                getLocationPermission();
            }
        }

        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navStore:
                        Stores stores = Stores.getInstance();
                        stores.setCurrentFlag(false);
                        setFragment(store);
                        return true;
                    case R.id.navProfile:
                        setFragment(profile);
                        return true;
                    case R.id.navNotifications:
                        setFragment(notifications);
                        return true;
                    default:
                        return false;
                }
            }
        });
        shoppingNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Stores stores = Stores.getInstance();
                Filters filters;
                if (stores.isCurrentFlag()) {
                    filters = new Filters(stores.getCurrent().getProducts());
                    stores.getCurrent().setFilters(true);
                } else {
                    filters = new Filters(stores.getAllProducts());
                    stores.setFilters(true);
                }
                switch(item.getItemId()) {
                    case R.id.dresses:
                        filters.categoryFilter("Dress");
                        Toast.makeText(getApplicationContext(),"Dresses",Toast.LENGTH_SHORT).show();
                        break;
                        //return true;
                    case R.id.jeans:
                        filters.categoryFilter("Jeans");
                        Toast.makeText(getApplicationContext(),"Jeans",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.shoes:
                        filters.categoryFilter("Shoes");
                        Toast.makeText(getApplicationContext(),"Shoes",Toast.LENGTH_SHORT).show();
                        break;
                        //return true;
                    case R.id.shirts:
                        filters.categoryFilter("Shirt");
                        Toast.makeText(getApplicationContext(),"Shirts",Toast.LENGTH_SHORT).show();
                        break;
                        //return true;
                    case R.id.jackets:
                        filters.categoryFilter("Jacket");
                        Toast.makeText(getApplicationContext(),"Jackets",Toast.LENGTH_SHORT).show();
                        break;
                        //return true;
                    case R.id.shorts:
                        filters.categoryFilter("Short");
                        Toast.makeText(getApplicationContext(),"Shorts",Toast.LENGTH_SHORT).show();
                        break;
                        //return true;
                    case R.id.sweatshirts:
                        filters.categoryFilter("SweatShirt");
                        Toast.makeText(getApplicationContext(),"Sweatshirts",Toast.LENGTH_SHORT).show();
                        break;
                        //return true;
                }
                if (stores.isCurrentFlag()) {
                    stores.getCurrent().setFiltered(filters.getFiltered());
                } else {
                    stores.setFiltered(filters.getFiltered());
                    Toast.makeText(getApplicationContext(), Integer.toString(stores.getFiltered().size()), Toast.LENGTH_LONG).show();
                }
                setFragment(new Store());
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
                drawer.closeDrawer(GravityCompat.START);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        // your code here
                        int price = Integer.parseInt(spinner.getItemAtPosition(position).toString());
                        Stores stores = Stores.getInstance();
                        Filters filters;
                        if (stores.isCurrentFlag()) {
                            filters = new Filters(stores.getCurrent().getProducts());
                            stores.getCurrent().setFilters(true);
                        } else {
                            filters = new Filters(stores.getAllProducts());
                            stores.setFilters(true);
                        }
                        filters.priceFilter(price);
                        if (stores.isCurrentFlag()) {
                            stores.getCurrent().setFiltered(filters.getFiltered());
                        } else {
                            stores.setFiltered(filters.getFiltered());
                            Toast.makeText(getApplicationContext(), Integer.toString(stores.getFiltered().size()), Toast.LENGTH_LONG).show();
                        }
                        setFragment(new Store());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }
                });
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        /*if(!query.equals(null)){
                            Toast.makeText(getApplicationContext(),"hola",Toast.LENGTH_SHORT);
                            return true;
                        }*/
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (TextUtils.isEmpty(newText)) {
                             //code
                        } else {
                            //code
                        }
                        return true;
                    }
                });
                return true;
            }
        });

    }
    //map permissions
    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            setFragment(store);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK(){
        // Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            //  Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            // Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionGranted){
                    setFragment(store);
                }
                else{
                    getLocationPermission();
                }
            }
        }

    }

    private void setFragment(android.support.v4.app.Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopping_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
