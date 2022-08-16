package et.com.act.unibillingandroid.Activities;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import et.com.act.unibillingandroid.Dto.Loading;
import et.com.act.unibillingandroid.Dto.State;
import et.com.act.unibillingandroid.Fragments.AddMeterFragment;
import et.com.act.unibillingandroid.Fragments.LoadingFragment;
import et.com.act.unibillingandroid.Fragments.MeterDetailFragment;
import et.com.act.unibillingandroid.Fragments.MeterReadingFragment;
import et.com.act.unibillingandroid.Fragments.ReadingHistoryFragment;
import et.com.act.unibillingandroid.Interfaces.MeterSearchLister;
import et.com.act.unibillingandroid.Interfaces.NetworkRequestListener;
import et.com.act.unibillingandroid.Map.MapActivity;
import et.com.act.unibillingandroid.Models.Auth;
import et.com.act.unibillingandroid.Models.Meter;
import et.com.act.unibillingandroid.Models.ReadingInfo;
import et.com.act.unibillingandroid.Network.Dto.FetchMeterListResponseDto;
import et.com.act.unibillingandroid.Network.Dto.MeterReadingDto;
import et.com.act.unibillingandroid.Network.RequestHandler;
import et.com.act.unibillingandroid.Network.Url;
import et.com.act.unibillingandroid.R;
import et.com.act.unibillingandroid.Util.Constants;
import et.com.act.unibillingandroid.Util.FilesHelper;
import et.com.act.unibillingandroid.Util.Helper;
import et.com.act.unibillingandroid.Util.Mapper;
import et.com.act.unibillingandroid.ViewModels.Factory.MainActivityViewModelFactory;
import et.com.act.unibillingandroid.ViewModels.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements NetworkRequestListener,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    public static final String FINE_LOCATION = ACCESS_FINE_LOCATION;
    public static final String COARSE_LOCATION = ACCESS_COARSE_LOCATION;

    private final ArrayList<String> permissions = new ArrayList<>();
    private final String[] storagePermissions = { READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE };
    private ArrayList<String> permissionRequestResult = new ArrayList<>();

    private final boolean isLocationPermissionGranted = false;
    private final boolean readPermissionGranted = false;

    private final boolean writePermissionGranted = false;
    private final ArrayList<String> permissionsRejected = new ArrayList<>();

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;



    private TextView navHeaderUserNameTv,
            navHeaderUserInitialTv,
            navHeaderUserFullName;


    private MainActivityViewModel mainActivityViewModel;
    private Auth auth;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Location readerCurrentLocation;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initPermissions();
        runPermissionLocationNetworkRequests();
        checkGoogleServices();
        initLocationRequest();
        initLiveData();

        if(getIntent() != null && getIntent().hasExtra(Constants.CONTRACT_NO)){
            String contractNo = getIntent().getStringExtra(Constants.CONTRACT_NO);
            mainActivityViewModel.setIsFromMap(true);
            mainActivityViewModel.searchMetersByContractNo(contractNo);
            Helper.openFragment(getSupportFragmentManager(), R.id.fl_main_activity, new MeterDetailFragment());
        }else{
            Helper.openFragment(getSupportFragmentManager(), R.id.fl_main_activity, new MeterReadingFragment());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.menu_export_to_remote){
            exportToRemote();
        }else if(id == R.id.menu_import_from_remote){
            importFromRemote();
        }else if(id ==R.id.menu_export_to_excel){
            exportToExcel();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews(){
        toolbar = findViewById(R.id.tb_main_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.getHeaderView(0);
        navHeaderUserFullName = navHeaderView.findViewById(R.id.nav_header_name);
        navHeaderUserInitialTv = navHeaderView.findViewById(R.id.nav_header_user_initial);
        navHeaderUserNameTv = navHeaderView.findViewById(R.id.nav_header_username);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void initLiveData(){
        mainActivityViewModel = new ViewModelProvider(this, new MainActivityViewModelFactory(getApplication()))
                .get(MainActivityViewModel.class);
        mainActivityViewModel.getAuth().observe(this, new Observer<Auth>() {
            @Override
            public void onChanged(Auth auth) {
                if(auth != null){
                    updateCurrentAuth(auth);
                    updateNavHeader(auth);
                }else{
                    Intent intent = new Intent(MainActivity.this, StarterActivity.class);
                    startActivity(intent);
                }
            }
        });

        mainActivityViewModel.getMeterSearchResult().observe(this, new Observer<List<Meter>>() {
            @Override
            public void onChanged(List<Meter> meters) {
                if(meters != null && meters.size() > 0){
                    mainActivityViewModel.setMeter(meters.get(0));
                }
            }
        });

        mainActivityViewModel.getMeters().observe(this, new Observer<List<Meter>>() {
            @Override
            public void onChanged(List<Meter> meters) {
                if(meters != null && meters.size() > 0){
                    Log.d(TAG, "saved: " + meters.size());
                    for(Meter meter : meters){
                        Log.d(TAG, "Contract Nos: ------> " + meter.getContractNumber());
                    }
                }
            }
        });
    }

    public void updateCurrentAuth(Auth auth){
        this.auth = auth;
    }


    public void updateNavHeader(Auth auth){
        navHeaderUserFullName.setText(auth.getName());
        navHeaderUserInitialTv.setText(Helper.createUserInitial(auth.getName()));
        navHeaderUserNameTv.setText(Helper.generateUserName(auth.getName()));
    }

    private void importFromRemote(){
        showLoadingScreen("Importing Meters", "Importing your assigned meters",0);
        RequestHandler.importMeters(MainActivity.this, auth,this);
    }

    private void exportToRemote(){
       mainActivityViewModel.getMetersByReadingStatus(Constants.TRUE_STRING, new MeterSearchLister() {
           @Override
           public void onMeterSearchResult(List<Meter> meters) {
               if(meters != null){
                    List<MeterReadingDto> requestDtoList = Mapper.mapToReadingRequestDto(meters);
                    //FilesHelper.exportToExcel(MainActivity.this, requestDtoList);
                    RequestHandler.exportReading(auth, requestDtoList, MainActivity.this);

               }
           }
       });
    }

    private void showLoadingScreen(String title, String message, int image){
        Helper.openFragment(getSupportFragmentManager(), R.id.fl_main_activity, new LoadingFragment());
        updateLoadingScreen(title, message,image);
    }

    private void exportToExcel(){
        Toast.makeText(this, "This feature is currently not available", Toast.LENGTH_LONG).show();
//        showLoadingScreen("Exporting to file", "Your reading are being exported to an excel file. You can find the file in your downloads folder",0);
//        mainActivityViewModel.getMetersByReadingStatus(Constants.TRUE_STRING, meters -> {
//            if(meters != null){
//                Log.d(TAG, "exportToExcel: ---->" + meters.size());
//                if(checkStoragePermission()){
//                    Log.d(TAG, "exportToExcel: ---->" + "permission");
//                    List<MeterReadingDto> requestDtoList = Mapper.mapToReadingRequestDto(meters);
//                    FilesHelper.exportToExcel(MainActivity.this, requestDtoList);
//                    Helper.openFragment(getSupportFragmentManager(), R.id.fl_main_activity, new MeterReadingFragment());
//                }else{
//                    Log.d(TAG, "exportToExcel: ---->" + "permission");
//                    requestStoragePermission();
//                }
//            }else{
//                Toast.makeText(this, "No meters to export.", Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    @Override
    public void OnNetworkRequestCompleteListener(State state) {
       String responseName = state.getName();
       switch (responseName){
           case Url.FETCH_METERS:
               handleMeterFetchResult(state);
               break;
           case Url.SUBMIT_METER_READINGS:
               handleReadingSubmissionResult(state);
               break;
       }

    }

    private void runPermissionLocationNetworkRequests(){
        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);
        permissions.add(ACCESS_NETWORK_STATE);
        permissions.add(INTERNET);

        permissionRequestResult = findUnAskedPermissions(permissions);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(permissionRequestResult.size() > 0){
                requestPermissions(permissionRequestResult.toArray(new String[permissionRequestResult.size()]), Constants.PERMISSION_RESPONSE_CODE);
            }
        }

    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<>();
        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }


    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }else{
                Log.d(TAG, "hasPermission: Permission denied");
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_map){
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_home){
            Helper.openFragment(getSupportFragmentManager(), R.id.fl_main_activity, new MeterReadingFragment());
        }else if(id == R.id.nav_add_meter) {
            Helper.openFragment(getSupportFragmentManager(), R.id.fl_main_activity, new AddMeterFragment());
        }else if(id ==R.id.nav_history){
            Helper.openFragment(getSupportFragmentManager(), R.id.fl_main_activity, new ReadingHistoryFragment());
        }else if(id == R.id.nav_settings){
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        closeDrawer();
        return true;
    }


    private void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void handleMeterFetchResult(State state){
        if(state.getRequestState()){
            FetchMeterListResponseDto responseDto = (FetchMeterListResponseDto) state.getResult();
            List<Meter> meters = Mapper.mapToMeter(responseDto.getMeterLocationDtos());

            Log.d(TAG, "handleMeterFetchResult: converted " + meters.size());
            List<ReadingInfo> readingInfoList = Mapper.mapToReadingInfo(responseDto.getReaderInfos());

            Log.d(TAG, "handleMeterFetchResult: reading info " + readingInfoList.size());

            mainActivityViewModel.delete();
            mainActivityViewModel.insertReadingInfo(readingInfoList);
            mainActivityViewModel.insertMeter(meters);
        }else{
            Toast.makeText(this, R.string.generic_network_request_failed, Toast.LENGTH_SHORT).show();
        }
        Helper.openFragment(getSupportFragmentManager(), R.id.fl_main_activity, new MeterReadingFragment());
    }

    private void handleReadingSubmissionResult(State state){
        if(state.getRequestState()){
            List<MeterReadingDto> meterReadings = (List<MeterReadingDto>) state.getResult();
            if(meterReadings != null){
                removeReadMeters(meterReadings);
            }else{
                Log.d(TAG, "handleReadingSubmissionResult: submission successful but unable to fetch result");
            }
            Toast.makeText(this, R.string.reading_submission_sucess, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, R.string.generic_network_request_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private void removeReadMeters(List<MeterReadingDto> meterReadings){
        for(MeterReadingDto meterReading : meterReadings){
            mainActivityViewModel.delete((int) meterReading.getMeterId());
        }
    }

    private boolean checkGoogleServices(){
        Log.d(TAG, "checkGoogleServices: checking");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if(available == ConnectionResult.SUCCESS){
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "checkGoogleServices: services installed but fixable error occured");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, Constants.ERROR_DIALOG_REQUEST_CODE);
            assert dialog != null;
            dialog.show();
        }else{
            Log.d(TAG, "checkGoogleServices: All Failed");
            finish();
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    private void initLocationRequest(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setWaitForAccurateLocation(true);
        locationRequest.setSmallestDisplacement(2);
        initLocationCallBack();
    }


    private void initLocationCallBack(){
        //gets called whenever the update interval is met
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                readerCurrentLocation = locationResult.getLastLocation();
                Log.d(TAG, "onLocationResult: " + "Lat " + readerCurrentLocation.getLongitude() +
                        ": Lon "  + readerCurrentLocation.getLatitude());
                mainActivityViewModel.setReaderCurrentLocation(locationResult.getLastLocation());
            }
        };
    }

    private void initPermissions () {

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK){
                    if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.R){
                        if(Environment.isExternalStorageManager()){
                            Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
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

    private boolean checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int readcheck = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
            int writecheck = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
            return readcheck == PackageManager.PERMISSION_GRANTED && writecheck == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestStoragePermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            Log.d(TAG, "requestStoragePermission: sdk >");
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                activityResultLauncher.launch(intent);
            }catch (Exception e){
                Log.d(TAG, "requestPermission: "+ e.getMessage());
            }
        }else{
            ActivityCompat.requestPermissions(MainActivity.this, storagePermissions, 4321);
        }
    }

    public  void updateLoadingScreen(String title, String message, int image){
        Loading loading = new Loading();
        loading.setLoadingTitle(title);
        loading.setLoadingMessage(message);
        loading.setLoadingImage(image);
        mainActivityViewModel.setLoading(loading);
    }
}