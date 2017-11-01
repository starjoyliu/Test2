package com.myapplication.utility;

/**
 * Created by star on 2017/10/30.
 */

public class Utility3 {
    private static final Utility3 u = new Utility3();
    private int tmpData;

    public Utility3 (){}

    public static Utility3 getNewInstance() {
        return u;
    }

    public void setTmpData(int range){
        tmpData = 0;
        for(int i=0;i<range;i++){
            tmpData = tmpData + i;
        }
    }

    public int getTmpData(){
        return tmpData;
    }
}
