package com.variable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by star on 2017/11/1.
 */
public class UtilityAsync {
    private static UtilityAsync utilityAsync;
    private static ExecutorService executorService;

    private volatile static UtilityAsync u;
    public UtilityAsync(){}

    public static UtilityAsync getNewInstance() {
        if(u == null) {
            synchronized (UtilityAsync.class) {
                if(u == null) {
                    u = new UtilityAsync();
                    /**
                     * {@link Executors}
                     */
                    executorService = Executors.newCachedThreadPool();
                }
            }
        }
        return u;
    }

    public ExecutorService getExecutorService(){
        return executorService;
    }

    public void closeExecutorService(){
        if(executorService!=null){
            executorService.shutdownNow();
            executorService = null;
        }
        if(utilityAsync !=null){
            utilityAsync = null;
        }
    }
}
