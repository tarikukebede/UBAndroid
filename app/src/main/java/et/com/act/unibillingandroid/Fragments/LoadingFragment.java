package et.com.act.unibillingandroid.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import et.com.act.unibillingandroid.Dto.Loading;
import et.com.act.unibillingandroid.R;
import et.com.act.unibillingandroid.ViewModels.Factory.MainActivityViewModelFactory;
import et.com.act.unibillingandroid.ViewModels.MainActivityViewModel;


public class LoadingFragment extends Fragment {

    private TextView loadingTitle, loadingMessage;
    private MainActivityViewModel mainActivityViewModel;


    public LoadingFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loading, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        loadingTitle = view.findViewById(R.id.fragment_loading_title);
        loadingMessage = view.findViewById(R.id.fragment_loading_message);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivityViewModel = new ViewModelProvider(requireActivity(), new MainActivityViewModelFactory(requireActivity()
                .getApplication())).get(MainActivityViewModel.class);
        mainActivityViewModel.getLoading().observe(requireActivity(), this::updateLoadingViews);
    }

    private void updateLoadingViews(Loading loading){
        loadingTitle.setText(loading.getLoadingTitle());
        loadingMessage.setText(loading.getLoadingMessage());
    }
}