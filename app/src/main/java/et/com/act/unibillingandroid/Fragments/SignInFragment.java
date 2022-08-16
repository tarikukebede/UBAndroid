package et.com.act.unibillingandroid.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import et.com.act.unibillingandroid.Dto.State;
import et.com.act.unibillingandroid.Interfaces.NetworkRequestListener;
import et.com.act.unibillingandroid.Models.Auth;
import et.com.act.unibillingandroid.Network.Dto.SignInRequestDto;
import et.com.act.unibillingandroid.Network.RequestHandler;
import et.com.act.unibillingandroid.R;
import et.com.act.unibillingandroid.ViewModels.StarterActivityViewModel;


public class SignInFragment extends Fragment implements NetworkRequestListener {
    private static final String TAG = "SignInFragment";

    private EditText etUsername, etPassword;
    private TextView btnLogin, etError, progressText;
    private LinearLayout signInFormContainer, progressContainer;


    private StarterActivityViewModel starterActivityViewModel;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        starterActivityViewModel = new ViewModelProvider(requireActivity()).get(StarterActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        init(view);
        return view;
    }


    private void init(View view){
        etUsername = view.findViewById(R.id.nav_header_name);
        etPassword = view.findViewById(R.id.et_password);
        progressText = view.findViewById(R.id.sing_in_fragment_progress_text);
        signInFormContainer = view.findViewById(R.id.sign_in_fragment_form_container);
        progressContainer = view.findViewById(R.id.sign_in_fragment_progress_container);
        etError = view.findViewById(R.id.et_error);
        btnLogin = view.findViewById(R.id.tv_login);
        btnLogin.setOnClickListener(v -> {
            //Todo: More input validation needs to be done!
            signIn(etUsername.getText().toString().trim(), etPassword.getText().toString().trim());
        });
    }


    private void toggleProgressBar(boolean state){
        if(state){
            progressContainer.setVisibility(View.VISIBLE);
            progressText.setText(R.string.logging_in);
        }else{
            progressContainer.setVisibility(View.GONE);
        }
    }

    private void toggleFormContainer(boolean state){
        if(state){
            signInFormContainer.setVisibility(View.VISIBLE);
        }else{
            signInFormContainer.setVisibility(View.GONE);
        }
    }


    private void signIn(String username, String password){
        SignInRequestDto requestDto = new SignInRequestDto();
        requestDto.setUserName(username);
        requestDto.setPassword(password);
        RequestHandler.singIn(this, requestDto);
        toggleFormContainer(false);
        toggleProgressBar(true);
    }

    @Override
    public void OnNetworkRequestCompleteListener(State state) {
        if(state.getRequestState()){
            starterActivityViewModel.insert((Auth) state.getResult());
        }else{
            Toast.makeText(requireContext(), "Oops error while trying to log in. Please retry", Toast.LENGTH_LONG).show();
            toggleFormContainer(true);
            toggleProgressBar(false);
        }

        Log.d(TAG, "OnRequestComplete: " + state.getRequestState());
    }
}