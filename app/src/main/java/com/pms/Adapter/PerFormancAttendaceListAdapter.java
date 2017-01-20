package com.pms.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pms.Model.MyPerformanceListItem;
import com.pms.R;

import java.util.List;

public class PerFormancAttendaceListAdapter extends RecyclerView.Adapter<PerFormancAttendaceListAdapter.ViewHolder> {
    List<MyPerformanceListItem> myPerformanceListItem;
    View v;
    PerFormancAttendaceListAdapter.ViewHolder viewHolder;
    public PerFormancAttendaceListAdapter(List<MyPerformanceListItem> myPerformanceListItemArrayList) {
        this.myPerformanceListItem = myPerformanceListItemArrayList;
    }

    @Override
    public PerFormancAttendaceListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tab_attendance_items, viewGroup, false);
        viewHolder = new PerFormancAttendaceListAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PerFormancAttendaceListAdapter.ViewHolder viewHolder, int i) {
        MyPerformanceListItem myPerformanceListItemtArrayListItems = myPerformanceListItem.get(i);
        viewHolder.bindTrainerList(myPerformanceListItemtArrayListItems);
    }

    @Override
    public int getItemCount() {
        return myPerformanceListItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        public TextView month;
        public TextView workingDaysInMonth;
        public TextView AttendanceDays;
        public TextView attendancePercentage;

        private MyPerformanceListItem myPerformanceListItems;

        public ViewHolder(View itemView) {

            super(itemView);

            month = (TextView) itemView.findViewById(R.id.month);
            workingDaysInMonth = (TextView) itemView.findViewById(R.id.workingDaysInMonth);
            AttendanceDays = (TextView) itemView.findViewById(R.id.AttendanceDays);
            attendancePercentage = (TextView) itemView.findViewById(R.id.attendancePercentage);
        }

        public void bindTrainerList(MyPerformanceListItem myPerformanceList) {
            this.myPerformanceListItems = myPerformanceList;
//
//            String days = myPerformanceListItems.getNoOfWorkingDays();
//            String[] separated = days.split(".");
//            String spliteddays = separated[0];
//
//            String daysAttended = myPerformanceListItems.getsActualNoOfWorkingDays();
//            String[] separated2 = daysAttended.split(".");
//            String spliteddaysAttended = separated2[0];
//            workingDaysInMonth.setText(spliteddays);
//            AttendanceDays.setText(spliteddaysAttended);

            month.setText(myPerformanceListItems.getMonth());
            workingDaysInMonth.setText(myPerformanceListItems.getNoOfWorkingDays() +" Days");
            AttendanceDays.setText(myPerformanceListItems.getsActualNoOfWorkingDays()+" Days");
            attendancePercentage.setText(myPerformanceListItems.getPercentNoOfWorkingDays()+"%");

        }
    }
}
