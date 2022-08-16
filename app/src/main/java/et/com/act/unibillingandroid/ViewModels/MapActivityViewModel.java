package et.com.act.unibillingandroid.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import et.com.act.unibillingandroid.Interfaces.MeterSearchLister;
import et.com.act.unibillingandroid.Models.Meter;
import et.com.act.unibillingandroid.Repositories.MeterRepository;

public class MapActivityViewModel extends AndroidViewModel {
    private MeterRepository meterRepository;


    public MapActivityViewModel(@NonNull Application application) {
        super(application);
        meterRepository = new MeterRepository(application);

    }

    public  void getUnreadMeters(MeterSearchLister lister){
        meterRepository.getAllUnreadMeters(lister);
    }
}
