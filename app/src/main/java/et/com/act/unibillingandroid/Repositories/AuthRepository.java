package et.com.act.unibillingandroid.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import et.com.act.unibillingandroid.Database.AppDao;
import et.com.act.unibillingandroid.Database.AppDatabase;
import et.com.act.unibillingandroid.Models.Auth;

public class AuthRepository {

    private AppDao appDao;
    private LiveData<Auth> auth;

    public AuthRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        appDao = db.appDao();
        auth = appDao.getAuth();
    }

    public void insert(Auth auth){
        AppDatabase.databaseExecutor.execute(()-> appDao.insertAuth(auth));
    }

    public LiveData<Auth> getAuth(){
        return auth;
    }

    public void deleteAuth(){
        AppDatabase.databaseExecutor.execute(()->appDao.deleteAuth());
    }

}
