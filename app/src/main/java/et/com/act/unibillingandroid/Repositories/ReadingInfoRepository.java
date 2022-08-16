package et.com.act.unibillingandroid.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import et.com.act.unibillingandroid.Database.AppDao;
import et.com.act.unibillingandroid.Database.AppDatabase;
import et.com.act.unibillingandroid.Models.ReadingInfo;

public class ReadingInfoRepository {
    private AppDao appDao;
    private LiveData<List<ReadingInfo>> readingInfoList;

    public ReadingInfoRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application.getApplicationContext());
        appDao = db.appDao();
        readingInfoList = appDao.getAllReadingInfo();
    }


    public void insert(ReadingInfo readingInfo){
        AppDatabase.databaseExecutor.execute(()-> appDao.insertReadingInfo(readingInfo));
    }

    public LiveData<List<ReadingInfo>> getAll(){
        return readingInfoList;
    }

    public void delete(ReadingInfo readingInfo){
        AppDatabase.databaseExecutor.execute(()->appDao.deleteReadingInfo(readingInfo));
    }

    public void delete(){
        AppDatabase.databaseExecutor.execute(()->appDao.deleteReadingInfo());
    }
}
