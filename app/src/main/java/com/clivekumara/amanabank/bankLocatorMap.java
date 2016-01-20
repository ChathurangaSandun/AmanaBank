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

            getNearestBranch();

        }
    };


    private void getLocations(){



        List<Branch> allBranches = dBhelper.getAllBranches();

        for (Branch b: allBranches){
            Log.d("Clivekumara", b.getBranchName());

            mMap.addMarker(new MarkerOptions().position(new LatLng(b.getLatitude(), b.getLongitude())).title(b.getBranchName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.bank)));



        }


    }


    private void getNearestBranch(){
        double mindistance = 1000000000;
        int branchCode = -1;

        List<Branch> allBranches = dBhelper.getAllBranches();

        for (Branch b: allBranches){
            Log.d("Clivekumara", b.getBranchName());



            double v = distanceFrom(myLocationLat, myLocationLong, b.getLatitude(), b.getLongitude());
            Log.d("min dis",String.valueOf(myLocationLat));

            if(mindistance>v){
                mindistance =v;
                branchCode = b.getBranchCode();
            }

        }
        Log.d("min dis",String.valueOf(mindistance));
        Log.d("min dis",String.valueOf(branchCode));

    }



    public double distanceFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;
        int meterConversion = 1609;
        return new Double(dist * meterConversion).floatValue();    // this will return distance
    }



}
