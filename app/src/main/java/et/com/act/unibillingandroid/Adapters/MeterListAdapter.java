package et.com.act.unibillingandroid.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import et.com.act.unibillingandroid.Interfaces.MeterOnClickListener;
import et.com.act.unibillingandroid.Models.Meter;
import et.com.act.unibillingandroid.R;

public class MeterListAdapter extends RecyclerView.Adapter<MeterListAdapter.meterViewHolder> {

    private List<Meter> meterList;
    private MeterOnClickListener meterOnClickListener;

    public MeterListAdapter(List<Meter> meterList, MeterOnClickListener meterOnClickListener){
        this.meterList = meterList;
        this.meterOnClickListener = meterOnClickListener;
    }

    @NonNull
    @Override
    public meterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.meter_list_item, parent, false);
        return new meterViewHolder(itemView, meterOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull meterViewHolder holder, int position) {
        Meter meter = meterList.get(position);

        if(meter != null){
            holder.customerNameTv.setText(meter.getCustomerName());
            holder.meterSerialTv.setText(meter.getSerialNo());
            holder.lastReadingTv.setText(meter.getPreviousReading());
        }

    }

    @Override
    public int getItemCount() {
        return meterList.size();
    }

    public class meterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView customerNameTv, meterSerialTv, lastReadingTv;
        private ImageView moreOptionIv;
        private MeterOnClickListener meterOnClickListener;

        public meterViewHolder(@NonNull View itemView, MeterOnClickListener listener) {
            super(itemView);
            meterOnClickListener = listener;
            customerNameTv = itemView.findViewById(R.id.tv_customer_name);
            meterSerialTv = itemView.findViewById(R.id.tv_meter_serial);
            lastReadingTv = itemView.findViewById(R.id.tv_last_reading);
            moreOptionIv = itemView.findViewById(R.id.iv_more_options);
            moreOptionIv.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            meterOnClickListener.OnMeterClick(getAdapterPosition(), meterList.get(getAdapterPosition()));
        }
    }

    public void setMeterList(List<Meter> meterList) {
        this.meterList = meterList;
        notifyDataSetChanged();
    }

    public void addMeters(List<Meter> meters){
        meterList = new ArrayList<>();
        if(meters != null && meters.size() > 0){
            for(Meter meter : meters){
                meterList.add(0, meter);
                notifyItemInserted(0);
            }
        }
    }

}
