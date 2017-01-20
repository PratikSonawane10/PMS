package com.pms.Model;

public class CheckSelfieModel {
    private String title;
    private String selfieCheck;

    public CheckSelfieModel(){

    }
    public CheckSelfieModel(String selfieCheck){

        this.selfieCheck = selfieCheck;

    }
    public String getSelfie() {
        return selfieCheck;
    }

    public void setSelfie(String selfieCheck) {
        this.selfieCheck = selfieCheck;
    }

}
