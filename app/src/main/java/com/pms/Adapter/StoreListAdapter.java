package com.pms.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.pms.Model.StoreListItems;
import com.pms.R;
import com.pms.Store_Form;

import java.util.List;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ViewHolder>{

    List<StoreListItems> storeListItems;
    View v;
    ViewHolder viewHolder;
    LinearLayout greenColorLinearLayout;

    public StoreListAdapter(List<StoreListItems> storeListItemsArrayList) {
        this.storeListItems = storeListItemsArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_store_list_items, viewGroup, false);
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        StoreListItems notificationItem = storeListItems.get(position);
        viewHolder.bindNotificationDetailsList(notificationItem );
    }

    @Override
    public int getItemCount() {
        return storeListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView storeListStatus;
        public TextView storeName;
        public TextView storeAddress;
        public View cardView;
        public View dividerLine;
        public String color;
        public String status;

        RelativeLayout statuscolorLayout;
        StoreListItems storeListItems = new StoreListItems();

        public ViewHolder(View itemView) {
            super(itemView);

            storeName = (TextView) itemView.findViewById(R.id.storeName);
            storeAddress = (TextView) itemView.findViewById(R.id.storeAddress);
            cardView = itemView;
            statuscolorLayout = (RelativeLayout) itemView.findViewById(R.id.statusRelativeLayout);
            //    dividerLine = itemView.findViewById(R.id.storeListDivider);
            //cardView.setOnClickListener(this);


        }

        public void bindNotificationDetailsList(StoreListItems storeListItems) {
            this.storeListItems = storeListItems;
            storeName.setText(storeListItems.getshopName());
            storeAddress.setText(storeListItems.getAddress());
            color = storeListItems.getcolor();
            status = storeListItems.getstatus();

            if(status.equals("False")  && color.equals("Red")){
                cardView.setOnClickListener(this);
            }
            else if(status.equals("True") && color.equals("Green")){
                cardView.setOnClickListener(this);
            }

            if(color.equals("Red")){
                statuscolorLayout.setBackgroundResource(R.color.statusRed);
            }
            else if( color.equals("Green")){
                statuscolorLayout.setBackgroundResource(R.color.statusGreen);
            }
            else if(color.equals("Blue")){
                statuscolorLayout.setBackgroundResource(R.color.statusBlue);
            }
        }

        @Override
        public void onClick(View v) {
            String checkColor =  storeListItems.getcolor();

            if (this.storeListItems != null) {

                if(checkColor.equals("Green")){
                    Toast.makeText(v.getContext(),"This Store is Completed ",Toast.LENGTH_LONG).show();
                }
                else if(checkColor.equals("Blue")){
                    Toast.makeText(v.getContext(),"This Store is Completed ",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent storeFormInformation = new Intent(v.getContext(), Store_Form.class);
                    storeFormInformation.putExtra("shopID", storeListItems.getshopID());
                    storeFormInformation.putExtra("beatID", storeListItems.getbeatID());
                    storeFormInformation.putExtra("shopName", storeListItems.getshopName());
                    storeFormInformation.putExtra("Address", storeListItems.getAddress());
                    storeFormInformation.putExtra("color", storeListItems.getcolor());
                    storeFormInformation.putExtra("status", storeListItems.getstatus());
                    v.getContext().startActivity(storeFormInformation);
                }
            }
        }
    }
}
