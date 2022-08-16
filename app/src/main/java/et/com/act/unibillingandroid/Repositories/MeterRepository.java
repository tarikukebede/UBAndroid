package et.com.act.unibillingandroid.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import et.com.act.unibillingandroid.Database.AppDao;
import et.com.act.unibillingandroid.Database.AppDatabase;
import et.com.act.unibillingandroid.Interfaces.MeterSearchLister;
import et.com.act.unibillingandroid.Models.Meter;
import et.com.act.unibillingandroid.Util.Constants;

public class MeterRepository {
    private static final String TAG = "Meter Repository";

    private AppDao appDao;
    private LiveData<List<Meter>> meters;
    //private List<Meter> unreadMeters;
    private LiveData<Integer> unreadMeterCount;
    private LiveData<Integer> readMeterCount;
    private LiveData<List<Meter>> readMeters;

    private MutableLiveData<List<Meter>> meterSearchResult = new MutableLiveData<>();


    public MeterRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application.getApplicationContext());
        appDao = db.appDao();
        meters = appDao.getAllMeters();
        unreadMeterCount = appDao.getUnreadMeterCount();
        readMeterCount = appDao.getReadMeterCount();
        ///unreadMeters = appDao.getAllUnreadMeters();
        readMeters = appDao.getAllReadMeters();
    }

    public void insert(Meter meter){
        //AppDatabase.databaseExecutor.execute(()->appDao.insertMeter(meter));
        AppDatabase.databaseExecutor.execute(()->{
            Long column = appDao.insertMeter(meter);
            Log.d(TAG, "insert: " + column);
        });
    }

    public void update(Meter meter){
        AppDatabase.databaseExecutor.execute(()->appDao.updateMeter(meter));
    }

    public LiveData<List<Meter>> getAll(){
        return meters;
    }

    public void getMetersByReadingStatus(String readingStatus, MeterSearchLister meterSearchLister ){
        AppDatabase.databaseExecutor.execute(()->{
             meterSearchLister.onMeterSearchResult(appDao.getMetersByReadingStatus(readingStatus));
        });
    }


    public void searchMetersByContractNo(String contractNo){
        AppDatabase.databaseExecutor.execute(()->{
            List<Meter> meters = appDao.getMeterByContractNo(contractNo, Constants.FALSE_STRING);
            if(meters != null && meters.size() > 0){
                Log.d(TAG, "searchMetersByContractNo: found"+ meters.get(0).getContractNumber());
            }else{
                Log.d(TAG, "searchMetersByContractNo: found 00000");
            }

            setMeterSearchResult(meters);
        });
    }


    public LiveData<List<Meter>> getMeterSearchResult(){
        return meterSearchResult;
    }

    public void setMeterSearchResult(List<Meter> meterList){
        meterSearchResult.postValue(meterList);
    }

    public LiveData<Integer> getUnreadMeterCount(){
        return unreadMeterCount;
    }

    public LiveData<Integer> getReadMeterCount(){
        return readMeterCount;
    }


    public void getAllUnreadMeters(MeterSearchLister listener){
        AppDatabase.databaseExecutor.execute(()->{
            List<Meter> meters = appDao.getAllUnreadMeters();
            listener.onMeterSearchResult(meters);
        });
    }

    public LiveData<List<Meter>> getReadMeters(){
        return readMeters;
    }


    public void delete(Meter meter){
        AppDatabase.databaseExecutor.execute(()->appDao.deleteMeter(meter));
    }

    public void delete(int meterId){
        AppDatabase.databaseExecutor.execute(()->appDao.deleteMeter(meterId));
    }

    public void deleteAll(){
        AppDatabase.databaseExecutor.execute(()->appDao.deleteAllMeters());
    }
}
