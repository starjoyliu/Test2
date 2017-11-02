package com.variable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by star on 2017/11/1.
 */
public class UtilityAsync {
    /**
     * sample code
     * AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob(){
     *      @Override
     *      public void doOnBackground() {
     *          // doOnBackground
     *
     *          AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
     *              @Override
     *              public void doInUIThread() {
     *                  // doInUIThread
     *              }
     *          });
     *      }
     * }, UtilityAsync.getNewInstance().getExecutorService());
     */


    private static UtilityAsync utilityAsync;
    private static ExecutorService executorService;

    private volatile static UtilityAsync u;
    public UtilityAsync(){}

    public static UtilityAsync getNewInstance() {
        if(u == null) {
            synchronized (UtilityAsync.class) {
                if(u == null) {
                    u = new UtilityAsync();
                }
            }
        }
        return u;
    }

    public ExecutorService getExecutorService(){
        if (executorService==null){
            /**
             * {@link Executors}
             */
            executorService = Executors.newCachedThreadPool();
        }
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
