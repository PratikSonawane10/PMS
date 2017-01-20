package com.pms.Adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pms.Model.MyPerformanceListItem;
import com.pms.R;

import java.util.List;

public class PerFormanceIncentiveListAdapter extends RecyclerView.Adapter<PerFormanceIncentiveListAdapter.ViewHolder> {
    List<MyPerformanceListItem> myPerformanceListItem;
    View v;
    PerFormanceIncentiveListAdapter.ViewHolder viewHolder;

    public PerFormanceIncentiveListAdapter(List<MyPerformanceListItem> myPerformanceListItemArrayList) {
        this.myPerformanceListItem = myPerformanceListItemArrayList;
    }

    @Override
    public PerFormanceIncentiveListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tab_incentive_items, viewGroup, false);
        viewHolder = new PerFormanceIncentiveListAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PerFormanceIncentiveListAdapter.ViewHolder viewHolder, int i) {
        MyPerformanceListItem myPerformanceListItemtArrayListItems = myPerformanceListItem.get(i);
        viewHolder.bindTrainerList(myPerformanceListItemtArrayListItems);
    }

    @Override
    public int getItemCount() {
        return myPerformanceListItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView month;
        public TextView Incentive;


        private MyPerformanceListItem myPerformanceListItems;

        public ViewHolder(View itemView) {

            super(itemView);

            month = (TextView) itemView.findViewById(R.id.month);
            Incentive = (TextView) itemView.findViewById(R.id.Incentive);

        }

        public void bindTrainerList(MyPerformanceListItem myPerformanceList) {
            this.myPerformanceListItems = myPerformanceList;

            month.setText(myPerformanceListItems.getMonth());
            Incentive.setText( myPerformanceListItems.getIncentiveAmount());


        }
    }
}
