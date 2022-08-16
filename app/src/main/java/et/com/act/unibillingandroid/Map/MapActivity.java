package et.com.act.unibillingandroid.Map;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import et.com.act.unibillingandroid.Activities.MainActivity;
import et.com.act.unibillingandroid.Interfaces.MeterSearchLister;
import et.com.act.unibillingandroid.Models.Meter;
import et.com.act.unibillingandroid.R;
import et.com.act.unibillingandroid.Repositories.MeterRepository;
import et.com.act.unibillingandroid.Util.Constants;
import et.com.act.unibillingandroid.Util.MapHelper;
import et.com.act.unibillingandroid.ViewModels.Factory.MainActivityViewModelFactory;
import et.com.act.unibillingandroid.ViewModels.Factory.MapActivityViewModelFactory;
import et.com.act.unibillingandroid.ViewModels.MapActivityViewModel;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MapActivity";

    public static final String FINE_LOCATION = ACCESS_FINE_LOCATION;
    public static final String COARSE_LOCATION = ACCESS_COARSE_LOCATION;

    private GoogleMap mMap;


    private boolean isLocationPermissionGranted = false;


    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Location readerCurrentLocation;

    private MapActivityViewModel mapActivityViewModel;
    private List<Marker> meterMarkers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

            initViewModels();
            checkGoogleServices();
            getLocationPermission();
            initMap();
            initLocationRequest();
            updateGps();
    }


    private void initViewModels(){
        mapActivityViewModel = new ViewModelProvider(this,
                new MapActivityViewModelFactory(getApplication())).get(MapActivityViewModel.class);
    }


    private void initLocationRequest(){
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setWaitForAccurateLocation(true);
        locationRequest.setSmallestDisplacement(2);
        initLocationCallBack();
    }
    

    private void drawMetersOnMap(List<Meter> meters, Location location){
        if(location != null) {

             List<Meter> sampledMeters = MapHelper.sampleMeters(meters);
             Log.d(TAG, "drawMetersOnMap: uncleaned meters size " + sampledMeters.size());
             List<Meter> cleanedMeters = MapHelper.cleanMeterData(sampledMeters);
             Log.d(TAG, "drawMetersOnMap: cleaned meters size " + cleanedMeters.size());

             List<Meter> nearByMeters = MapHelper.getNearByMeters(cleanedMeters, location);
             Log.d(TAG, "drawMetersOnMap: ");

             if(nearByMeters != null && nearByMeters.size() > 0){
                 Log.d(TAG, "drawMetersOnMap: cleaned and Nearby meters " + nearByMeters.size());

                 if(meterMarkers != null){
                     for(Marker marker : meterMarkers){
                         this.runOnUiThread(marker::remove);
                     }
                     meterMarkers.clear();
                 }

                 for (Meter meter : nearByMeters) {
                     Log.d(TAG, "drawMetersOnMap: Nearby Meter " + meter.getCustomerMeter());
                     double latitude = MapHelper.cleanCo(meter.getMeterLat());
                     double longitude = MapHelper.cleanCo(meter.getMeterLong());
                     this.runOnUiThread(() -> {
                         final  LatLng meterLatLng = new LatLng(latitude, longitude);
                         Log.d(TAG, "drawMetersOnMap: Lat: " + latitude + " Lng : " + longitude);
                         Marker marker = mMap.addMarker(
                                 new MarkerOptions().position(meterLatLng).title(meter.getContractNumber()));
                         meterMarkers.add(marker);
                     });

                 }
             }else{
                 Log.d(TAG, "drawMetersOnMap: There are no meters near by you");
                 //Todo: show no meters dialog
             }

        }else{
            Log.d(TAG, "drawMetersOnMap: reader current location is null || no meters in database");
        }

    }

    private void initLocationCallBack(){
        //gets called whenever the update interval is met
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                readerCurrentLocation = locationResult.getLastLocation();
                Log.d(TAG, "onLocationResult: " + "Lat " + readerCurrentLocation.getLongitude() +
                        ": Lon "  + readerCurrentLocation.getLatitude());

                mapActivityViewModel.getUnreadMeters(meters -> {
                    if(meters != null && meters.size() > 0){
                        Log.d(TAG, "onMeterSearchResult: " + meters.size());
                        drawMetersOnMap(meters, readerCurrentLocation);
                    }else{
                        Log.d(TAG, "onMeterSearchResult: 0 meters returned from database");
                    }
                });

            }
        };
    }

    private void initMap(){
        meterMarkers = new ArrayList<>();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getLocationPermission(){
        String[] permissions = {ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_DENIED){
                isLocationPermissionGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this, permissions, Constants.PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this, permissions, Constants.PERMISSION_REQUEST_CODE);
        }

    }


    @SuppressLint("MissingPermission")
    private void updateGps(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> moveCamera(location, Constants.DEFAULT_CAMERA_ZOOM));
        }else{
           if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
               String[] permissions = { FINE_LOCATION, COARSE_LOCATION };
               requestPermissions(permissions, Constants.PERMISSION_REQUEST_CODE);
           }
        }
    }

    private boolean checkGoogleServices(){
        Log.d(TAG, "checkGoogleServices: checking");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapActivity.this);
        if(available == ConnectionResult.SUCCESS){
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "checkGoogleServices: services installed but fixable error occured");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MapActivity.this, available, Constants.ERROR_DIALOG_REQUEST_CODE);
            assert dialog != null;
            dialog.show();
        }else{
            Log.d(TAG, "checkGoogleServices: All Failed");
            finish();
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if(isLocationPermissionGranted){
            updateGps();

            if(ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    String contractNo = marker.getTitle();
                    Intent intent = new Intent(MapActivity.this, MainActivity.class);
                    intent.putExtra(Constants.CONTRACT_NO, contractNo);
                    startActivity(intent);
                    return false;
                }
            });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        isLocationPermissionGranted = false;

        switch (requestCode){
            case Constants.PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            return;
                        }
                    }
                    isLocationPermissionGranted = true;
                    updateGps();
                    initMap();
                }else{
                    //Toast.makeText(this, "These feature requires GPS access!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onRequestPermissionsResult:gps not turned on");
                    finish();
                }
            }
        }
    }

    public void moveCamera(Location location, float zoom){
        if(location != null){
        Log.d(TAG, "moveCamera: moving the camera to: lat :" + location.getLatitude() + " lng: " + location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), zoom));
        }else{
            Log.d(TAG, "moveCamera: location is null");
        }
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates(){
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void stopLocationUpdates(){
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }



    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    private void BuildAlertDialog(int message, int positive, int negative){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getResources().getString(message))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })

                .setNegativeButton(getResources().getString(negative), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                }).show();
    }


    private void drawCircle(LatLng readerLatLng){

    }

}