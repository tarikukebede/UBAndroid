package et.com.act.unibillingandroid.Fragments;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import et.com.act.unibillingandroid.Models.Meter;
import et.com.act.unibillingandroid.R;
import et.com.act.unibillingandroid.Util.Constants;
import et.com.act.unibillingandroid.Util.Helper;
import et.com.act.unibillingandroid.ViewModels.Factory.MainActivityViewModelFactory;
import et.com.act.unibillingandroid.ViewModels.MainActivityViewModel;

public class ReadingSubmissionFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ReadingSubmission";

    private MainActivityViewModel mainActivityViewModel;

    private TextView submitBtn, cancelBtn, customerNameTv, meterSerialTv;
    private EditText meterReadingEt;

    private Meter meter;
    private Location readerCurrentLocation;

    public ReadingSubmissionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityViewModel = new ViewModelProvider(requireActivity(),
                new MainActivityViewModelFactory(requireActivity().getApplication()))
                .get(MainActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reading_submission, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivityViewModel.getMeter().observe(getViewLifecycleOwner(), this::updateViews);
        mainActivityViewModel.getReaderCurrentLocation().observe(requireActivity(), this::updateReaderCurrentLocation);
    }

    private void initViews(View view){

        customerNameTv = view.findViewById(R.id.tv_submit_reading_customer_name);
        meterSerialTv = view.findViewById(R.id.tv_submit_reading_meter_serial);
        meterReadingEt = view.findViewById(R.id.et_submit_reading_meter_reading);

        submitBtn = view.findViewById(R.id.tv_submit_reading_submit);
        cancelBtn = view.findViewById(R.id.tv_submit_reading_cancel);

        submitBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

    }


    private void updateViews(Meter meter){
        this.meter = meter;
        customerNameTv.setText(meter.getCustomerName());
        meterSerialTv.setText(meter.getSerialNo());
    }


    private boolean validateReading(){
        String readingInput = meterReadingEt.getText().toString().trim();
        if(!readingInput.isEmpty()){
            int reading = Integer.parseInt(readingInput);
            return reading >= Integer.parseInt(meter.getPreviousReading());
        }

        return false;
    }

    private void submitReading(){
        if(validateReading()){
            meter.setCurrentReading(meterReadingEt.getText().toString().trim());
            meter.setIsRead(Constants.TRUE_STRING);
            meter.setReadOn(Helper.dateToString(new Date()));
            meter.setMeterLat(String.valueOf(readerCurrentLocation.getLatitude()));
            meter.setMeterLong(String.valueOf(readerCurrentLocation.getLongitude()));
            mainActivityViewModel.updateMeter(meter);
            Toast.makeText(getActivity(), "Reading submitted", Toast.LENGTH_SHORT).show();
            goBack();
        }else{
            Toast.makeText(getActivity(), "Invalid Input!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.tv_submit_reading_submit){
            submitReading();
        }else if(id == R.id.tv_submit_reading_cancel){
            goBack();
        }
    }

    private void goBack(){
        Helper.openFragment(getActivity().getSupportFragmentManager(), R.id.fl_main_activity, new MeterReadingFragment());
    }

    public void updateReaderCurrentLocation(Location location){
        this.readerCurrentLocation = location;
    }

}