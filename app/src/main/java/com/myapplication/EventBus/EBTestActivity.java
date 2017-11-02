package com.myapplication.EventBus;

/**
 * Created by star on 2017/11/1.
 */

public class EBTestActivity {
    private int channelId;

    public EBTestActivity(int channelId) {
        this.channelId = channelId;
    }

    public int getChannelId(){
        return channelId;
    }
}
