package com.myapplication.utility;

/**
 * Created by star on 2017/10/30.
 */

public class Utility {
    private volatile static Utility u;
    private int tmpData;

    public Utility (){}

    public static Utility getNewInstance() {
        if(u == null) {
            synchronized (Utility.class) {
                if(u == null) {
                    u = new Utility();
                }
            }
        }
        return u;
    }

    public void setTmpData(int range){
        tmpData = 0;
        for (int i=0;i<range;i++){
            tmpData = tmpData + i;
        }
    }

    public int getTmpData(){
        return tmpData;
    }
}
