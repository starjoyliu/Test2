package com.myapplication;

/**
 * Created by star on 2017/10/30.
 */

public class Utility2 {
    private static Utility2 u;
    private int tmpData;

    public Utility2 (){}

    public static Utility2 getNewInstance() {
        if(u == null) {
            u = new Utility2();
        }
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
