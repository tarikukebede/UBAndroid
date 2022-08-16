package et.com.act.unibillingandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import et.com.act.unibillingandroid.Fragments.SettingsMenuFragment;
import et.com.act.unibillingandroid.R;
import et.com.act.unibillingandroid.Util.Helper;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViews();
        Helper.openFragment(getSupportFragmentManager(), R.id.fl_settings_activity, new SettingsMenuFragment());
    }

    private void initViews(){
        toolbar = findViewById(R.id.tb_settings_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.label_settings);
        toolbar.setNavigationOnClickListener(v->finish());
    }
}