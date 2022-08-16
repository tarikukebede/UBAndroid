package et.com.act.unibillingandroid.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import et.com.act.unibillingandroid.Models.Auth;
import et.com.act.unibillingandroid.Repositories.AuthRepository;

public class StarterActivityViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final LiveData<Auth> auth;

    public StarterActivityViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        auth = authRepository.getAuth();
    }


    public LiveData<Auth> getAuth(){
        return auth;
    }

    public void insert(Auth auth){
        authRepository.insert(auth);
    }

    public void deleteAuth(){
        authRepository.deleteAuth();
    }

}
