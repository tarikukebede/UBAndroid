package et.com.act.unibillingandroid.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import et.com.act.unibillingandroid.Map.MapActivity;
import et.com.act.unibillingandroid.Models.Meter;
import et.com.act.unibillingandroid.R;
import et.com.act.unibillingandroid.Util.Constants;
import et.com.act.unibillingandroid.Util.Helper;
import et.com.act.unibillingandroid.ViewModels.Factory.MainActivityViewModelFactory;
import et.com.act.unibillingandroid.ViewModels.MainActivityViewModel;

public class MeterDetailFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MeterDetailFragment";

    private MainActivityViewModel mainActivityViewModel;
    private Meter meter;

    private TextView customerNameTv, customerPhoneTv, customerKebeleTv, customerWeredaTv, customerHouseNoTv,
    customerSubCityTv, meterSerialNoTv, customerBillIdTv, customerContractIdTv, meterCurrentReadingTv, meterPrevReadingTv,
    meterConsumptionTv, customerPenaltyTv, submitReadingBtn, enterReasonBtn;

    private LinearLayout actionContainerLL;
    private boolean openedFromMap = false;

    public MeterDetailFragment() {
        //Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meter_detail, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivityViewModel =
                new ViewModelProvider(requireActivity(), new MainActivityViewModelFactory(getActivity().getApplication()))
                        .get(MainActivityViewModel.class);

        mainActivityViewModel.getMeter().observe(getViewLifecycleOwner(), this::updateViews);
        mainActivityViewModel.getIsBackPressed().observe(getViewLifecycleOwner(), this::openFragment);
        mainActivityViewModel.getIsFromMap().observe(getViewLifecycleOwner(), status -> openedFromMap = status);
    }

    private void initViews(View view){
        customerNameTv = view.findViewById(R.id.tv_meter_detail_customer_name);
        customerKebeleTv = view.findViewById(R.id.tv_meter_detail_customer_kebele);
        customerPhoneTv = view.findViewById(R.id.tv_bill_detail_phone);
        customerWeredaTv = view.findViewById(R.id.tv_bill_detail_wereda);
        customerHouseNoTv = view.findViewById(R.id.tv_bill_detail_house_no);
        customerSubCityTv = view.findViewById(R.id.tv_bill_detail_sub_city);
        meterSerialNoTv = view.findViewById(R.id.tv_bill_detail_meter_serial);
        customerBillIdTv = view.findViewById(R.id.tv_bill_detail_bill_id);
        customerContractIdTv = view.findViewById(R.id.tv_bill_detail_contract_no);
        meterCurrentReadingTv = view.findViewById(R.id.tv_bill_detail_current_reading);
        meterPrevReadingTv = view.findViewById(R.id.tv_bill_detail_prev_reading);
        meterConsumptionTv = view.findViewById(R.id.tv_bill_detail_consumption);
        customerPenaltyTv = view.findViewById(R.id.tv_bill_detail_penalty);

        submitReadingBtn = view.findViewById(R.id.tv_bill_detail_submit_reading);
        enterReasonBtn = view.findViewById(R.id.tv_bill_detail_enter_reason);
        submitReadingBtn.setOnClickListener(this);
        enterReasonBtn.setOnClickListener(this);

        actionContainerLL = view.findViewById(R.id.ll_meter_detail_action_container);
    }

    private void updateViews(Meter meter){
        this.meter = meter;
        Log.d(TAG, "updateViews: " + new GsonBuilder().create().toJson(meter));
        if(meter != null){

            if(meter.getIsRead().matches(Constants.TRUE_STRING)){
                submitReadingBtn.setText(requireActivity().getResources().getString(R.string.edit_reading));
                enterReasonBtn.setText(requireActivity().getResources().getString(R.string.edit_reason));
            }

            customerNameTv.setText(meter.getCustomerName());
            customerKebeleTv.setText(meter.getKebele());
            customerPhoneTv.setText(meter.getPhonenumber());
            customerWeredaTv.setText(meter.getWereda());
            customerHouseNoTv.setText(meter.getHouseNumber());
            customerSubCityTv.setText(meter.getSubCity());
            meterSerialNoTv.setText(meter.getSerialNo());
            customerBillIdTv.setText(meter.getBillid());
            customerContractIdTv.setText(meter.getContractNumber());


            if(meter.getPenality() != null){
                customerPenaltyTv.setText(meter.getPenality());
            }else{
                customerPenaltyTv.setText(Constants.UNKNOWN);
            }

            if(meter.getConsumption() != null){
                meterConsumptionTv.setText(meter.getConsumption());
            }else{
                meterConsumptionTv.setText(Constants.UNKNOWN);
            }

            if(meter.getCurrentReading() != null){
                meterCurrentReadingTv.setText(String.valueOf(meter.getCurrentReading()));
            }else{
                meterCurrentReadingTv.setText(Constants.UNKNOWN);
            }

            if(meter.getPreviousReading() != null){
                meterPrevReadingTv.setText(meter.getPreviousReading());
            }else{
                meterPrevReadingTv.setText(Constants.UNKNOWN);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.tv_bill_detail_submit_reading){
            Helper.openFragmentWithSlideAnim(getActivity()
                    .getSupportFragmentManager(), R.id.fl_main_activity, new ReadingSubmissionFragment());
            mainActivityViewModel.setMeter(meter);
        }else if(id == R.id.tv_bill_detail_enter_reason){
            Helper.openFragmentWithSlideAnim(getActivity().
                    getSupportFragmentManager(), R.id.fl_main_activity, new ReasonSubmissionFragment());
            mainActivityViewModel.setMeter(meter);
        }
    }


    public void openFragment(boolean status){
        if(!openedFromMap){
            Helper.openFragment(requireActivity().getSupportFragmentManager(), R.id.fl_main_activity, new MeterReadingFragment());
        }else {
            Intent intent = new Intent(requireActivity(), MapActivity.class);
            requireActivity().startActivity(intent);
        }
    }
}