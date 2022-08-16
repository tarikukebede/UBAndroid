package et.com.act.unibillingandroid.ViewModels;

import android.app.Application;
import android.location.Location;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import et.com.act.unibillingandroid.Dto.Loading;
import et.com.act.unibillingandroid.Interfaces.MeterSearchLister;
import et.com.act.unibillingandroid.Models.Auth;
import et.com.act.unibillingandroid.Models.Meter;
import et.com.act.unibillingandroid.Models.ReadingInfo;
import et.com.act.unibillingandroid.Repositories.AuthRepository;
import et.com.act.unibillingandroid.Repositories.MeterRepository;
import et.com.act.unibillingandroid.Repositories.ReadingInfoRepository;
import et.com.act.unibillingandroid.Util.Constants;

public class MainActivityViewModel extends AndroidViewModel {

    private static final String TAG = "MainActivityViewModel";

    private final MeterRepository meterRepository;
    private final AuthRepository authRepository;
    private final ReadingInfoRepository readingInfoRepository;
    private final LiveData<List<Meter>> meters;
    private final LiveData<List<ReadingInfo>> readingInfoList;
    private final LiveData<Auth> auth;

    private final LiveData<Integer> readMeterCount;
    private final LiveData<Integer> unreadMeterCount;

    private final LiveData<List<Meter>> meterSearchResult;

    private List<Meter> unreadMeters;
    private LiveData<List<Meter>> readMeters;

    private final MutableLiveData<Meter> meter = new MutableLiveData<>();

    private final MutableLiveData<Location> readerCurrentLocation = new MutableLiveData<>();

    private final  MutableLiveData<Boolean> isBackPressed = new MutableLiveData<>();
    private final MutableLiveData<Boolean> fromMap = new MutableLiveData<>();


    public MainActivityViewModel(Application application){
        super(application);
        meterRepository = new MeterRepository(application);
        authRepository = new AuthRepository(application);
        readingInfoRepository = new ReadingInfoRepository(application);
        meters = meterRepository.getAll();
        auth = authRepository.getAuth();
        readingInfoList = readingInfoRepository.getAll();
        readMeterCount = meterRepository.getReadMeterCount();
        unreadMeterCount = meterRepository.getUnreadMeterCount();
        meterSearchResult = meterRepository.getMeterSearchResult();
        //unreadMeters = meterRepository.getAllUnreadMeters();
        readMeters = meterRepository.getReadMeters();

    }


    public void setMeter(Meter meter){
        this.meter.setValue(meter);
    }

    public LiveData<Meter> getMeter(){
        return meter;
    }

    public LiveData<Integer> getReadMeterCount(){
        return readMeterCount;
    }

    public LiveData<Integer> getUnreadMeterCount(){
        return unreadMeterCount;
    }

    public LiveData<List<Meter>> getReadMeters(){
        return readMeters;
    }

    public LiveData<List<Meter>> getMeters(){
        return meters;
    }

    public LiveData<List<Meter>> getMeterSearchResult(){
        return meterSearchResult;
    }

    public void searchMetersByContractNo(String contractNo){
        meterRepository.searchMetersByContractNo(contractNo);
    }

    public void insertMeter(Meter meter){
        meterRepository.insert(meter);
    }


    public void updateMeter(Meter meter){
        meterRepository.update(meter);
    }

    public void insertMeter(List<Meter> meters){
        Log.d(TAG, "insertMeter: " + meters.size());
        for(Meter meter : meters){
            insertMeter(meter);
        }
    }

    public void delete(Meter meter){
        meterRepository.delete(meter);
    }

    public void delete(int meterId){
        meterRepository.delete(meterId);
    }

    public void delete(){
        meterRepository.deleteAll();
    }


    //////////////////////////////

    public LiveData<Auth> getAuth(){
        return auth;
    }


    //////////////////////////////

    public LiveData<List<ReadingInfo>> getAllReadingInfo(){
        return readingInfoList;
    }

    public void insertReadingInfo(List<ReadingInfo> readingInfoList){
        for (ReadingInfo readingInfo : readingInfoList){
            readingInfoRepository.insert(readingInfo);
        }
    }

    public void getMetersByReadingStatus(String readingStatus, MeterSearchLister lister){
        meterRepository.getMetersByReadingStatus(readingStatus, lister);
    }

    public void getUnreadMeters(MeterSearchLister lister){
        meterRepository.getAllUnreadMeters(lister);
    }

    public LiveData<Location> getReaderCurrentLocation(){
        return readerCurrentLocation;
    }

    public void setReaderCurrentLocation(Location location){
        readerCurrentLocation.setValue(location);
    }


    public LiveData<Boolean> getIsBackPressed(){
        return isBackPressed;
    }

    public void setIsBackPressed(boolean status){
        isBackPressed.setValue(status);
    }

    public LiveData<Boolean> getIsFromMap(){
        return fromMap;
    }

    public void setIsFromMap(boolean status){
        fromMap.setValue(status);
    }


    /////////////////////

    private final  MutableLiveData<Loading> loading = new MutableLiveData<>();

    public void setLoading(Loading loading){
        this.loading.setValue(loading);
    }

    public LiveData<Loading> getLoading(){
        return loading;
    }
}
