package com.pms.Adapter;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pms.Model.DoneStoreListItems;
import com.pms.Model.StoreListItems;
import com.pms.R;
import com.pms.Store_Form;

import java.util.List;

public class DoneStoreListAdapter extends RecyclerView.Adapter<DoneStoreListAdapter.ViewHolder>{

    List<DoneStoreListItems> doneStoreListItems;
    View v;
    ViewHolder viewHolder;
    LinearLayout greenColorLinearLayout;

    public DoneStoreListAdapter( List<DoneStoreListItems> doneStoreListItems) {
        this.doneStoreListItems = doneStoreListItems;
    }

    @Override
    public DoneStoreListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_done_store_list_items, viewGroup, false);

        viewHolder = new DoneStoreListAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DoneStoreListAdapter.ViewHolder viewHolder, int position) {
        DoneStoreListItems notificationItem = doneStoreListItems.get(position);
        int cpostion = viewHolder.getAdapterPosition();
        viewHolder.bindNotificationDetailsList(notificationItem, cpostion );

    }

    @Override
    public int getItemCount() {
        return doneStoreListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView doneStoreName;
        public TextView doneStoreAddress;
        public View doneCardView;
        public String doneColor;
        RelativeLayout doneStatusColorLayout;
        DoneStoreListItems doneStoreListItems = new DoneStoreListItems();

        public ViewHolder(View itemView) {
            super(itemView);

            doneStoreName = (TextView) itemView.findViewById(R.id.doneStoreName);
            doneStoreAddress = (TextView) itemView.findViewById(R.id.doneStoreAddress);
            doneCardView = itemView;
            doneStatusColorLayout = (RelativeLayout) itemView.findViewById(R.id.doneStatusRelativeLayout);
        }

        public void bindNotificationDetailsList(DoneStoreListItems doneStoreListItems,int cpostion) {
            this.doneStoreListItems = doneStoreListItems;

            doneColor = doneStoreListItems.getdoneColor();
             if(doneColor.equals("Green")){
                doneStoreName.setText(doneStoreListItems.getdoneShopName());
                doneStoreAddress.setText(doneStoreListItems.getdoneAddress());
                doneStatusColorLayout.setBackgroundResource(R.color.statusGreen);
             }
            else if(doneColor.equals("Red")){
                 doneStoreName.setText(doneStoreListItems.getdoneShopName());
                 doneStoreAddress.setText(doneStoreListItems.getdoneAddress());
                 doneStatusColorLayout.setBackgroundResource(R.color.statusRed);
             }
        }

        @Override
        public void onClick(View v) {

        }
    }

}
