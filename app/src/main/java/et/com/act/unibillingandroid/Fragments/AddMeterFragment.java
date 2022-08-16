package et.com.act.unibillingandroid.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import et.com.act.unibillingandroid.R;
import et.com.act.unibillingandroid.Util.Helper;


public class AddMeterFragment extends Fragment {

    private TextView submitBtn, cancelBtn, customerNameTv, meterSerialTv;
    private EditText meterReadingEt;

    public AddMeterFragment() {
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
        return inflater.inflate(R.layout.fragment_add_meter, container, false);
    }
}