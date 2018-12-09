package pawan.com.loadcontactwithlivedata;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import pawan.com.loadcontactwithlivedata.listeners.OnRecyclerClick;

public class ShowContactAdapter extends RecyclerView.Adapter<ShowContactAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private OnRecyclerClick listener;
    private ArrayList mData;

    public ShowContactAdapter(Context context, Map<String,String> beans) {
        inflater = LayoutInflater.from(context);
        this.listener = listener;
        mData = new ArrayList();
        mData.addAll(beans.entrySet());

    }

    @Override
    public ShowContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_contacts, parent, false);
        ShowContactAdapter.ViewHolder viewHolder = new ShowContactAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ShowContactAdapter.ViewHolder holder, final int position) {

        Map.Entry<String, String> item =(Map.Entry)  mData.get(position);
        holder.tvName.setText(item.getKey());
        holder.tvValue.setText(item.getValue());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvName)
        TextView tvName;
    @BindView(R.id.tvValue)
        TextView tvValue;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}

