package et.com.act.unibillingandroid.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import et.com.act.unibillingandroid.Models.Auth;
import et.com.act.unibillingandroid.Repositories.AuthRepository;


public class SettingsActivityViewModel extends AndroidViewModel {

    private Application application;
    private AuthRepository authRepository;
    private LiveData<Auth> auth;


    public SettingsActivityViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        auth = authRepository.getAuth();
    }

    public void deleteAuth(){
        authRepository.deleteAuth();
    }

    public LiveData<Auth> getAuth(){
        return auth;
    }


}
