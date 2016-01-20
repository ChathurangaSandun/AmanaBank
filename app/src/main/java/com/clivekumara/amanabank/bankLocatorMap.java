package com.clivekumara.amanabank;

import android.annotation.TargetApi;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class bankLocatorMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double myLocationLat, myLocationLong;
    Marker myMarker;
    DBhelper dBhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_locator_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        dBhelper = new DBhelper(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);
        getLocations();

    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            myLocationLat = location.getLatitude();
            myLocationLong = location.getLongitude();

            if(myMarker != null){
                myMarker.remove();
            }

            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());


            myMarker = mMap.addMarker(new MarkerOptions()
                    .position(loc)
                    .snippet(
                            "Lat:" + location.getLatitude() + "Lng:"
                                    + location.getLongitude())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_man))
                    .title("My Locaiton"));
            /*if(mMap != null){
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
            }*/
        }
    };


    private void getLocations(){
        List<Branch> allBranches = dBhelper.getAllBranches();

        for (Branch b: allBranches){
            Log.d("Clivekumara", b.getBranchName());

            mMap.addMarker(new MarkerOptions().position(new LatLng(b.getLatitude(),b.getLongitude())).title(b.getBranchName() + "\n" + b.getAddress()).icon(BitmapDescriptorFactory.fromResource(R.drawable.bank)));
        }

    }
}
