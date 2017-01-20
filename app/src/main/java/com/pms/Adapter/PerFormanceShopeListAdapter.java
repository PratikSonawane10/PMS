package com.pms.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pms.Model.MyPerformanceListItem;
import com.pms.R;
import java.util.List;

public class PerFormanceShopeListAdapter extends RecyclerView.Adapter<PerFormanceShopeListAdapter.ViewHolder> {
    List<MyPerformanceListItem> myPerformanceListItem;
    View v;
    ViewHolder viewHolder;
    public PerFormanceShopeListAdapter(List<MyPerformanceListItem> myPerformanceListItemArrayList) {
        this.myPerformanceListItem = myPerformanceListItemArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tab_shope_items, viewGroup, false);
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        MyPerformanceListItem myPerformanceListItemtArrayListItems = myPerformanceListItem.get(i);
        viewHolder.bindTrainerList(myPerformanceListItemtArrayListItems);
    }

    @Override
    public int getItemCount() {
        return myPerformanceListItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        public TextView month;
        public TextView workingShopesInMonth;
        public TextView AttendanceShopes;
        public TextView shopeAttendancePercentage;

        private MyPerformanceListItem myPerformanceListItems;

        public ViewHolder(View itemView) {

            super(itemView);

            month = (TextView) itemView.findViewById(R.id.month);
            workingShopesInMonth = (TextView) itemView.findViewById(R.id.workingShopesInMonth);
            AttendanceShopes = (TextView) itemView.findViewById(R.id.AttendanceShopes);
            shopeAttendancePercentage = (TextView) itemView.findViewById(R.id.shopeAttendancePercentage);
        }

        public void bindTrainerList(MyPerformanceListItem myPerformanceList) {
            this.myPerformanceListItems = myPerformanceList;


//            String shopes = myPerformanceListItems.getNoOfShop();
//            String[] separated = shopes.split(".");
//            String splitedShope = separated[0];
//
//            String shopesAttended = myPerformanceListItems.getActualNoOfShops();
//            String[] separated2 = shopesAttended.split(".");
//            String splitedShopeAttended = separated2[0];
//
//            workingShopesInMonth.setText(splitedShope+" Days");
//            AttendanceShopes.setText(splitedShopeAttended);

            month.setText(myPerformanceListItems.getMonth());
            workingShopesInMonth.setText( myPerformanceListItems.getNoOfShop()+ " Outlets");
            AttendanceShopes.setText( myPerformanceListItems.getActualNoOfShops()+ " Outlets");
            shopeAttendancePercentage.setText(myPerformanceListItems.getPercentNoOfShope() + "%");

        }
    }
}
