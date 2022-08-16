package et.com.act.unibillingandroid.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import et.com.act.unibillingandroid.Adapters.MeterListAdapter;
import et.com.act.unibillingandroid.Interfaces.MeterOnClickListener;
import et.com.act.unibillingandroid.Models.Meter;
import et.com.act.unibillingandroid.R;
import et.com.act.unibillingandroid.Util.Helper;
import et.com.act.unibillingandroid.ViewModels.Factory.MainActivityViewModelFactory;
import et.com.act.unibillingandroid.ViewModels.MainActivityViewModel;


public class ReadingHistoryFragment extends Fragment implements MeterOnClickListener {
    private static final String TAG = "ReadingHistoryFragment";

    private MeterListAdapter meterListAdapter;
    private RecyclerView meterListRv;
    private RecyclerView.LayoutManager layoutManager;

    private TextView messageDescriptionTv;
    private LinearLayout messageContainerLL;

    private MainActivityViewModel mainActivityViewModel;


    public ReadingHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reading_history, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivityViewModel = new ViewModelProvider(requireActivity(), new MainActivityViewModelFactory(requireActivity()
                .getApplication())).get(MainActivityViewModel.class);
        mainActivityViewModel.getReadMeters().observe(requireActivity(), this::updateMetersRv);
    }

    private void initViews(View view){
        meterListRv = view.findViewById(R.id.fragment_history_meter_list);
        meterListRv.setHasFixedSize(true);
        meterListAdapter = new MeterListAdapter(new ArrayList<>(), this);
        layoutManager = new LinearLayoutManager(requireActivity());
        meterListRv.setLayoutManager(layoutManager);
        meterListRv.setAdapter(meterListAdapter);
        messageDescriptionTv = view.findViewById(R.id.tv_reading_history_message_detail);
        messageContainerLL = view.findViewById(R.id.ll_fragment_reading_history_message_container);
    }

    @Override
    public void OnMeterClick(int position, Meter meter) {
        mainActivityViewModel.setMeter(meter);
        Helper.openFragment(requireActivity().getSupportFragmentManager(), R.id.fl_main_activity, new MeterDetailFragment());
    }

    private void updateMetersRv(List<Meter> meters){
        if(meters != null){
            if(meters.size() > 0){
                Log.d(TAG, "updateMetersRv: got meters");
                messageContainerLL.setVisibility(View.GONE);
                meterListRv.setVisibility(View.VISIBLE);
                meterListAdapter.setMeterList(meters);
            }else{
                messageContainerLL.setVisibility(View.VISIBLE);
                meterListRv.setVisibility(View.GONE);
            }
        }else{
            messageContainerLL.setVisibility(View.VISIBLE);
            meterListRv.setVisibility(View.GONE);
        }
    }
}