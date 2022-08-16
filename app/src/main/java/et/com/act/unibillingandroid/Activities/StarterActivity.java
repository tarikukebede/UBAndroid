package et.com.act.unibillingandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.GsonBuilder;

import et.com.act.unibillingandroid.Fragments.SignInFragment;
import et.com.act.unibillingandroid.Fragments.SplashFragment;
import et.com.act.unibillingandroid.Models.Auth;
import et.com.act.unibillingandroid.Network.Url;
import et.com.act.unibillingandroid.R;
import et.com.act.unibillingandroid.Util.Helper;
import et.com.act.unibillingandroid.ViewModels.Factory.StarterActivityViewModelFactory;
import et.com.act.unibillingandroid.ViewModels.StarterActivityViewModel;

public class StarterActivity extends AppCompatActivity {
    private static final String TAG = "StarterActivity";

    private StarterActivityViewModel starterActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);

        initViewModels();
        Helper.openFragment(getSupportFragmentManager(),R.id.fl_splash_activity, new SplashFragment());
        tryAutoLogin();

    }

    private void tryAutoLogin(){
        starterActivityViewModel.getAuth().observe(this, auth -> {
            Log.d(TAG, "tryAutoLogin: " + new GsonBuilder().create().toJson(auth));
            if(auth != null && !auth.getToken().isEmpty()){
                Intent intent = new Intent(StarterActivity.this, MainActivity.class);
                intent.putExtra(Url.SING_IN, auth);
                startActivity(intent);
            }else{
                Helper.openFragmentWithSlideAnim(getSupportFragmentManager(), R.id.fl_splash_activity, new SignInFragment());
            }
        });
    }

    private void initViewModels(){
        starterActivityViewModel = new ViewModelProvider(this, new StarterActivityViewModelFactory(getApplication()))
                .get(StarterActivityViewModel.class);
    }
}