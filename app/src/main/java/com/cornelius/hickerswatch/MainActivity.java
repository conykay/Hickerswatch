package com.cornelius.hickerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager ;
    LocationListener locationListener;

    TextView textView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                LatLng userlocation = new LatLng(location.getLatitude(), location.getLongitude());

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {

                    List<Address> listadresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                    if (listadresses != null && listadresses.size() > 0) {

                        String adresses = "";

                        if (String.valueOf(listadresses.get(0).getLongitude()) != null) {

                            adresses += "Longitude:  " + listadresses.get(0).getLongitude() + "\n \n";

                        }
                        if (String.valueOf(listadresses.get(0).getLatitude()) != null) {

                            adresses += "Latitude:  " + listadresses.get(0).getLatitude() + "\n \n";

                        }


                        if (listadresses.get(0).getSubThoroughfare() != null) {

                            adresses += "Adress:  \n" + listadresses.get(0).getSubThoroughfare() + "\n \n";

                        }
                        if (listadresses.get(0).getThoroughfare() != null) {

                            adresses += listadresses.get(0).getThoroughfare() + " \n";

                        }
                        if (listadresses.get(0).getPostalCode() != null) {

                            adresses += listadresses.get(0).getPostalCode() + "\n";

                        }
                        if (listadresses.get(0).getCountryName() != null) {

                            adresses += listadresses.get(0).getCountryName() + "\n ";

                        }

                        textView.setText(adresses);

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (Build.VERSION.SDK_INT < 23) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        } else {


            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                //as for permission

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastknownlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            }
        }
    }

        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (grantResults.length > 0 && grantResults[0] == getPackageManager().PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                }

            }

        }

}
