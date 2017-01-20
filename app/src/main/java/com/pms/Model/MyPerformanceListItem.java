package com.pms.Model;

public class MyPerformanceListItem {
    public String Month;
    public String NoOfWorkingDays;
    public String ActualNoOfWorkingDays;
    public String NoOfShops;
    public String ActualNoOfShops;
    public String IncentiveAmount;
    public String PercentNoOfWorkingDays;
    public String PercentNoOfShopes;

    public MyPerformanceListItem() {
    }

    public MyPerformanceListItem(String Month, String NoOfWorkingDays, String ActualNoOfWorkingDays  , String NoOfShops , String ActualNoOfShops , String IncentiveAmount,String PercentNoOfWorkingDays , String PercentNoOfShopes) {

        this.Month = Month;
        this.ActualNoOfWorkingDays = ActualNoOfWorkingDays;
        this.NoOfWorkingDays = NoOfWorkingDays;
        this.NoOfShops = NoOfShops;
        this.ActualNoOfShops = ActualNoOfShops;
        this.IncentiveAmount = IncentiveAmount;

        this.PercentNoOfWorkingDays = PercentNoOfWorkingDays;
        this.PercentNoOfShopes = PercentNoOfShopes;

    }

    public String getMonth() {
        return Month;
    }
    public void setMonth(String Month) {
        this.Month = Month;
    }

    public String getNoOfWorkingDays() {
        return NoOfWorkingDays;
    }

    public void setNoOfWorkingDays(String NoOfWorkingDays) {
        this.NoOfWorkingDays = NoOfWorkingDays;
    }

    public String getsActualNoOfWorkingDays() {
        return ActualNoOfWorkingDays;
    }

    public void setsActualNoOfWorkingDays(String ActualNoOfWorkingDays) {
        this.ActualNoOfWorkingDays = ActualNoOfWorkingDays;
    }

    public String getNoOfShop() {
        return NoOfShops;
    }
    public void setNoOfShop(String NoOfShops) {
        this.NoOfShops = NoOfShops;
    }

    public String getActualNoOfShops() {
        return ActualNoOfShops;
    }
    public void setActualNoOfShops(String ActualNoOfShops) {
        this.ActualNoOfShops = ActualNoOfShops;
    }

    public String getIncentiveAmount() {
        return IncentiveAmount;
    }
    public void setIncentiveAmount(String IncentiveAmount)
    {
        this.IncentiveAmount = IncentiveAmount;
    }
    public String getPercentNoOfWorkingDays() {
        return PercentNoOfWorkingDays;
    }
    public void setPercentNoOfWorkingDays(String PercentNoOfWorkingDays) {
        this.PercentNoOfWorkingDays = PercentNoOfWorkingDays;
    }

    public String getPercentNoOfShope() {
        return PercentNoOfShopes;
    }
    public void setPercentNoOfShopes(String PercentNoOfShopes) {
        this.PercentNoOfShopes = PercentNoOfShopes;
    }

}
