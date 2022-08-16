package et.com.act.unibillingandroid.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import et.com.act.unibillingandroid.Activities.SettingsActivity;
import et.com.act.unibillingandroid.Activities.StarterActivity;
import et.com.act.unibillingandroid.R;
import et.com.act.unibillingandroid.Util.Helper;
import et.com.act.unibillingandroid.ViewModels.Factory.SettingsActivityViewModelFactory;
import et.com.act.unibillingandroid.ViewModels.SettingsActivityViewModel;


public class SettingsMenuFragment extends Fragment implements View.OnClickListener {

    private LinearLayout changePasswordLL, moreInfoLL, aboutLL, logoutLL;
    private SettingsActivityViewModel settingsActivityViewModel;

    public SettingsMenuFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_menu, container, false);
        return initViews(view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingsActivityViewModel = new ViewModelProvider(requireActivity(),
                new SettingsActivityViewModelFactory(requireActivity().getApplication())).get(SettingsActivityViewModel.class);

    }

    private View initViews(View view){
        changePasswordLL = view.findViewById(R.id.ll_change_password);
        moreInfoLL = view.findViewById(R.id.ll_more_info);
        aboutLL = view.findViewById(R.id.ll_about);
        logoutLL = view.findViewById(R.id.ll_logout);

        changePasswordLL.setOnClickListener(this);
        moreInfoLL.setOnClickListener(this);
        aboutLL.setOnClickListener(this);
        logoutLL.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.ll_change_password){
            openFragment(new ChangePasswordFragment());
        }else if(id == R.id.ll_more_info){
            openFragment(new MoreInfoFragment());
        }else if(id == R.id.ll_about){
            openFragment(new AboutFragment());
        }else if(id == R.id.ll_logout){
            logout();
        }
    }

    private void logout(){
        settingsActivityViewModel.deleteAuth();
        Intent intent = new Intent(requireActivity(), StarterActivity.class);
        startActivity(intent);
    }


    private void openFragment(Fragment fragment){
        Helper.openFragment(requireActivity().getSupportFragmentManager(), R.id.fl_settings_activity, fragment);
    }
}