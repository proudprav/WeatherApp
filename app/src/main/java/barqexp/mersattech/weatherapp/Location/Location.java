package barqexp.mersattech.weatherapp.Location;

import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;


/**
 * Created by praveendewangan on 03/12/17.
 * Location services
 */

public class Location implements
        LocationListener ,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = Location.class.getSimpleName();
    public static Location location;
    private static Context context1;
    public  LatLng mCurrentLocation;
    private GoogleApiClient mGoogleApiClient;
        private LocationRequest mLocationRequest;
    private long interval;
    private String actionReceiver;
    boolean statusOfGPS;

    private Boolean gps;

    private Boolean netWork;

    LocationManager locationManager;
    private MutableLiveData<LatLng> locationLiveData;

    public Location() {
        locationLiveData = new MutableLiveData<>();
        locationLiveData.setValue(null);
    }

    public static Location getInstance(Context context) {
        if (location == null) {
            synchronized (context) {
                if (location == null) {
                    location = new Location();
                    context1 = context;

                }
            }

        }


        return location;
    }

    public void getLocation() {


        buildGoogleApiClient();

        mGoogleApiClient.connect();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }


        if (ActivityCompat.checkSelfPermission(context1, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context1, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        locationManager = (LocationManager) context1.getSystemService(context1.LOCATION_SERVICE);

//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

    }


    private synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(context1)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(interval);
        mLocationRequest.setFastestInterval(this.interval / 2);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setSmallestDisplacement(10);

    }

    private void startLocationUpdates() {
        try {
            if (mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        } catch (SecurityException ex) {
        }
    }


    protected void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }


    @Override
    public void onConnected(Bundle connectionHint) throws SecurityException {
        Log.i(TAG, "Connected to GoogleApiClient");
        LocationManager manager = (LocationManager) context1.getSystemService(Context.LOCATION_SERVICE);
        assert manager != null;
        statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (statusOfGPS) {
        if (mCurrentLocation != null) {
            if (mGoogleApiClient != null) {
                if(LocationServices.FusedLocationApi.getLastLocation
                        (mGoogleApiClient) != null){
                mCurrentLocation = new LatLng(LocationServices.FusedLocationApi.getLastLocation
                        (mGoogleApiClient).getLatitude(), LocationServices.FusedLocationApi.
                        getLastLocation(mGoogleApiClient).getLongitude());
              //  DataUtils.getInstance().getDefaultSharedPreferences(context1).edit().putFloat("lat", (float) mCurrentLocation.latitude).apply();
                //DataUtils.getInstance().getDefaultSharedPreferences(context1).edit().putFloat("lng", (float) mCurrentLocation.longitude).apply();
               locationLiveData.setValue(mCurrentLocation);}
            }

        }
        }
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        mCurrentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        locationLiveData.setValue(mCurrentLocation);
    }





    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public LiveData<LatLng> getLocationLiveData() {
        return locationLiveData;
    }
}