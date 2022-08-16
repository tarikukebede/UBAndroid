package et.com.act.unibillingandroid.Fragments;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import et.com.act.unibillingandroid.Adapters.MeterListAdapter;
import et.com.act.unibillingandroid.Interfaces.MeterOnClickListener;
import et.com.act.unibillingandroid.Interfaces.MeterSearchLister;
import et.com.act.unibillingandroid.Interfaces.OnBackPressedListener;
import et.com.act.unibillingandroid.Models.Meter;
import et.com.act.unibillingandroid.R;
import et.com.act.unibillingandroid.Services.LocationTrackService;
import et.com.act.unibillingandroid.Util.Constants;
import et.com.act.unibillingandroid.Util.Helper;
import et.com.act.unibillingandroid.Util.MapHelper;
import et.com.act.unibillingandroid.ViewModels.Factory.MainActivityViewModelFactory;
import et.com.act.unibillingandroid.ViewModels.MainActivityViewModel;

public class MeterReadingFragment extends Fragment implements MeterOnClickListener, OnBackPressedListener, MeterSearchLister {

    public static final String TAG = "MeterReadingFragment";

    private LocationTrackService locationTrackService;

    private MeterListAdapter meterListAdapter;
    private RecyclerView meterListRv;
    private RecyclerView.LayoutManager layoutManager;

    private MainActivityViewModel mainActivityViewModel;

    private TextView totalMetersUnreadTv, totalMetersReadTv;
    private SearchView searchMeterSv;

    private Location readerCurrentLocation;

    public MeterReadingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_meter_reading, container, false);
        initViews(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivityViewModel = new ViewModelProvider(requireActivity(), new MainActivityViewModelFactory(requireActivity().getApplication()))
                .get(MainActivityViewModel.class);

        mainActivityViewModel.getReadMeterCount().observe(requireActivity(), this::updateTotalReadMetersCount);
        mainActivityViewModel.getUnreadMeterCount().observe(requireActivity(), this::updateTotalUnreadMeterCount);
        mainActivityViewModel.getMeterSearchResult().observe(requireActivity(), this::updateMeterListAdapter);
        mainActivityViewModel.getReaderCurrentLocation().observe(requireActivity(), this::updateReaderCurrentLocation);
    }

    private void initViews(View view){
        meterListRv = view.findViewById(R.id.meter_list_rv);
        layoutManager = new LinearLayoutManager(requireActivity());
        meterListRv.setLayoutManager(layoutManager);
        meterListRv.setHasFixedSize(true);
        meterListAdapter = new MeterListAdapter(new ArrayList<>(), this);
        meterListRv.setAdapter(meterListAdapter);

        totalMetersUnreadTv = view.findViewById(R.id.tv_total_meters_assigned_count);
        totalMetersReadTv = view.findViewById(R.id.tv_total_meters_read_count);
        searchMeterSv = view.findViewById(R.id.sv_search_meter);
        searchMeterSv.setIconifiedByDefault(true);
        searchMeterSv.setFocusable(true);
        searchMeterSv.setIconified(true);
        searchMeterSv.requestFocusFromTouch();
        searchMeterSv.setQueryHint(getActivity().getResources().getString(R.string.search_something));

        EditText searchEditText = (EditText) searchMeterSv.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.BLACK);

        searchMeterSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String contractNo) {
                Log.d("Weird", contractNo);
                mainActivityViewModel.searchMetersByContractNo(contractNo);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String contractNo) {
                //Log.d("Weird", contractNo);
                //mainActivityViewModel.searchMetersByContractNo(contractNo);
                return false;
            }
        });
    }


    @Override
    public void OnMeterClick(int position, Meter meter) {
        Helper.openFragment(requireActivity().getSupportFragmentManager(), R.id.fl_main_activity, new MeterDetailFragment());
        mainActivityViewModel.setMeter(meter);
    }


    private void updateTotalReadMetersCount(Integer count){
        totalMetersReadTv.setText(String.valueOf(count));
    }

    private void updateTotalUnreadMeterCount(Integer count){
        totalMetersUnreadTv.setText(String.valueOf(count));
    }

    public void updateMeterListAdapter(List<Meter> meters){
        if(meters != null && meters.size() > 0) {
            Log.d(TAG, "updateMeterListAdapter: meter_search result Lat :" + meters.get(0).getMeterLat() + " Lng: " + meters.get(0).getMeterLong());
            Map<String, List<Meter>> separationResult = MapHelper.separateMeterDataForSearchResult(meters);
            List<Meter> cleanMeters = separationResult.get(Constants.METERS_WITH_CLEAN_COORDINATES);
            List<Meter> uncleanMeters = separationResult.get(Constants.METERS_WITH_UNCLEAN_COORDINATES);

            Log.d(TAG, "updateMeterListAdapter: clean " + cleanMeters.size());
            Log.d(TAG, "updateMeterListAdapter: unclean " + uncleanMeters.size());

            if (cleanMeters != null && cleanMeters.size() > 0) {
                List<Meter> nearByMeters = MapHelper.getNearByMeters(cleanMeters , readerCurrentLocation);
                if (nearByMeters != null && nearByMeters.size() > 0) {
                    meterListAdapter.addMeters(nearByMeters);
                } else {
                    Toast.makeText(requireActivity(), R.string.no_nearby_meters, Toast.LENGTH_LONG).show();
                }
            } else {
                Log.d(TAG, "updateMeterListAdapter: there is no meter with clean meter coordinate, showing meters with faulty " +
                        "coordinate data.");
            }

            meterListAdapter.addMeters(uncleanMeters);

        }else{
            Toast.makeText(requireContext(), "There are no meters with the given contract number", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "updateMeterListAdapter: there are no meters with the given contract number");
        }
    }

    public void updateReaderCurrentLocation(Location location){
        this.readerCurrentLocation = location;
    }

    @Override
    public void onBackPressed() {
        requireActivity().finish();
    }

    @Override
    public void onMeterSearchResult(List<Meter> meterList) {
        Log.d(TAG, "onMeterSearchResult: =======> " + meterList.size());
    }
}