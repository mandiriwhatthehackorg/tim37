package com.essensift.mandirihack.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.essensift.mandirihack.R;
import com.essensift.mandirihack.database.model.Member;

import java.util.List;

/**
 * Created by Darari on 28/06/2019.
 */

public class AdapterRvSwipeEnable extends RecyclerView.Adapter<AdapterRvSwipeEnable.MyViewHolder> {

    private String TAG = "ADAPTER_RV_SWIPE";
    private List<Member> items;
    private Context context;

    public AdapterRvSwipeEnable(Context context, List<Member> items) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void removeItem(int position) {
        items.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Member k = items.get(position);

    }

    public void restoreItem(Member item, int position) {
        items.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout viewBackground;
        public LinearLayout viewForeground;
        //TextView textDriverName, textDriverPhone, textDriverPin;

        MyViewHolder(View itemView) {
            super(itemView);
            /*textDriverName = itemView.findViewById(R.id.textItemDriverName);
            textDriverPhone = itemView.findViewById(R.id.textItemDriverPhone);
            textDriverPin = itemView.findViewById(R.id.textItemDriverPin);*/
            viewBackground = itemView.findViewById(R.id.viewItemMemberBackground);
            viewForeground = itemView.findViewById(R.id.viewItemMemberForeground);
        }
    }
}
