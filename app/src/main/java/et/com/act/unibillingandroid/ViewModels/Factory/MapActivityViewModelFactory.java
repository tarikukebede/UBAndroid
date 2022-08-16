package et.com.act.unibillingandroid.ViewModels.Factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import et.com.act.unibillingandroid.ViewModels.MapActivityViewModel;

public class MapActivityViewModelFactory implements ViewModelProvider.Factory {
    private Application application;

    public MapActivityViewModelFactory(Application application){
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        return (T) new MapActivityViewModel(application);
    }
}
