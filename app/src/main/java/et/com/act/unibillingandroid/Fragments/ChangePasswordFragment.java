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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import et.com.act.unibillingandroid.Dto.State;
import et.com.act.unibillingandroid.Interfaces.NetworkRequestListener;
import et.com.act.unibillingandroid.Models.Auth;
import et.com.act.unibillingandroid.Network.RequestHandler;
import et.com.act.unibillingandroid.Network.Url;
import et.com.act.unibillingandroid.R;
import et.com.act.unibillingandroid.Util.Helper;
import et.com.act.unibillingandroid.ViewModels.Factory.SettingsActivityViewModelFactory;
import et.com.act.unibillingandroid.ViewModels.SettingsActivityViewModel;


public class ChangePasswordFragment extends Fragment implements View.OnClickListener {

    private EditText oldPasswordEt, newPasswordEt, confirmPasswordEt;
    private TextView submitChangeTv, cancelChangeTv;

    private SettingsActivityViewModel settingsActivityViewModel;

    private Auth auth;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        return initViews(view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingsActivityViewModel = new ViewModelProvider(requireActivity(),
                new SettingsActivityViewModelFactory(requireActivity().getApplication()))
                .get(SettingsActivityViewModel.class);

        settingsActivityViewModel.getAuth().observe(requireActivity(), auth -> {
            if(auth != null){
                updateAuth(auth);
            }
        });
    }

    private View initViews(View view){
        oldPasswordEt = view.findViewById(R.id.et_enter_old_password);
        newPasswordEt = view.findViewById(R.id.et_enter_new_password);
        confirmPasswordEt = view.findViewById(R.id.et_confirm_new_password);
        submitChangeTv = view.findViewById(R.id.tv_submit_password_change);
        cancelChangeTv = view.findViewById(R.id.tv_cancel_password_change);

        submitChangeTv.setOnClickListener(this);
        cancelChangeTv.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.tv_submit_password_change){
            submitPasswordChange();
        }else if(id == R.id.tv_cancel_password_change){
            Helper.openFragment(requireActivity().getSupportFragmentManager(),
                    R.id.fl_settings_activity, new SettingsMenuFragment());
        }
    }


    public void submitPasswordChange(){
        if(validateInput()){
            RequestHandler.changePassword(auth.getName(), newPasswordEt.getText().toString().trim(), this::handleNetworkChange);
        }
    }

    private boolean validateInput(){
        String oldPassword = oldPasswordEt.getText().toString().trim();
        String newPassword = newPasswordEt.getText().toString().trim();
        String confirmedPassword = confirmPasswordEt.getText().toString().trim();

        if(oldPassword.isEmpty()){
            showToast(R.string.please_enter_your_old_password);
            return false;
        }

        if(newPassword.isEmpty()){
            showToast(R.string.please_enter_your_new_password);
            return false;
        }

        if(confirmedPassword.isEmpty()){
            showToast(R.string.please_make_sure_password_match);
            return false;
        }

        return newPassword.matches(confirmedPassword);
    }

    public void updateAuth(Auth auth){
        this.auth = auth;
    }

    private void showToast(int id){
        Toast.makeText(requireActivity(), id, Toast.LENGTH_LONG).show();
    }


    private void handleNetworkChange(State state){
        if(state.getRequestState()){
            switch (state.getName()){
                case Url.CHANGE_PASSWORD:
                    showToast(R.string.password_change_success);
                    break;
            }
        }else{
            showToast(R.string.generic_network_request_failed);
        }
    }
}