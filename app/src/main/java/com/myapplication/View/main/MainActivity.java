package com.myapplication.View.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.arasthel.asyncjob.AsyncJob;
import com.google.firebase.crash.FirebaseCrash;
import com.log.Logger;
import com.myapplication.EventBus.EBDBTest;
import com.myapplication.R;
import com.myapplication.View.base.BaseActivity;
import com.myapplication.View.test.TestActivity;
import com.myapplication.utility.Utility3;
import com.variable.UtilityAsync;
import com.variable.UtilitySwitchActivity;
import com.variable.UtilityToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends BaseActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private Thread T1, T2;
    private Object obj1 = new Object();
    private Object obj2 = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseCrash.log(String.valueOf(Log.ERROR));
        FirebaseCrash.logcat(Log.ERROR, TAG, "error");
        FirebaseCrash.report(new Exception("My first Android non-fatal error 1"));

        TextView text = findViewById(R.id.text);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("load", "load");
                UtilitySwitchActivity.getNewInstance().switchActivity(activity, TestActivity.class, true, bundle);
            }
        });

        T1 = new ThreadOnjectOne();
        T2 = new ThreadOnjectTwo();

        T1.start();
        T2.start();
    }

    private class ThreadOnjectOne extends Thread{
        @Override
        public void run() {
//            synchronized (obj1) {
//                Log.d(TAG, "ThreadOne: Holding obj 1...");
//
//                Log.d(TAG, "ThreadOne: Waiting for obj 2...");
//
//                synchronized (obj2) {
//                    Log.d(TAG, "ThreadOne: Holding obj 1 & 2...");
//                }
//            }

//                Log.d(TAG, "1. u setTmpData");
//                Utility.getNewInstance().setTmpData(10000);
//                Log.d(TAG, String.format("1. u TmpData: %s", Utility.getNewInstance().getTmpData()));

//                Log.d(TAG, "2. u2 setTmpData");
//                Utility2.getNewInstance().setTmpData(10000);
//                Log.d(TAG, String.format("2. u2 TmpData: %s", Utility2.getNewInstance().getTmpData()));

            //DB test, insert data
//            for (int i=0;i<10;i++){
//                DBTest.insertUser(String.format("Star %s", String.valueOf(i)), "096300000" + String.valueOf(i));
//
//                DBTest.updateUser(String.format("Star %s", String.valueOf(i)), "096300000" + String.valueOf(i+1));
//            }
//
//            List<DBTest> dbTestList =  DBTest.getAll();
//            int size = dbTestList.size();
//            for (DBTest d : dbTestList){
//                Logger.d(d.getName() + " " + d.getPhone());
//            }

            Logger.d("5. u3 setTmpData");
            Utility3.getNewInstance().setTmpData(10000);
            Logger.d(String.format("5. u3 TmpData: %s", Utility3.getNewInstance().getTmpData()));
        }
    }

    private class ThreadOnjectTwo extends Thread{
        @Override
        public void run() {
//            synchronized (obj2) {
//                Log.d(TAG, "ThreadTwo: Holding obj 2...");
//
//                Log.d(TAG, "ThreadTwo: Waiting for obj 1...");
//
//                synchronized (obj1) {
//                    Log.d(TAG, "ThreadTwo: Holding obj 1 & 2...");
//                }
//            }

//                Log.d(TAG, "3. u setTmpData");
//                Utility.getNewInstance().setTmpData(10);
//                Log.d(TAG, String.format("3. u TmpData: %s", Utility.getNewInstance().getTmpData()));

//                Log.d(TAG, "4. u2 setTmpData");
//                Utility2.getNewInstance().setTmpData(10);
//                Log.d(TAG, String.format("4. u2 TmpData: %s", Utility2.getNewInstance().getTmpData()));

            Logger.d("6. u3 setTmpData");
            Utility3.getNewInstance().setTmpData(10);
            Logger.d(String.format("6. u3 TmpData: %s", Utility3.getNewInstance().getTmpData()));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob(){
            @Override
            public void doOnBackground() {
                // doOnBackground
                Logger.d("7. u3 setTmpData");
                Utility3.getNewInstance().setTmpData(10000);
                Logger.d(String.format("7. u3 TmpData: %s", Utility3.getNewInstance().getTmpData()));

                AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                    @Override
                    public void doInUIThread() {
                        // doInUIThread
                    }
                });
            }
        }, UtilityAsync.getNewInstance().getExecutorService());

        AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob(){
            @Override
            public void doOnBackground() {
                // doOnBackground
                Logger.d("8. u3 setTmpData");
                Utility3.getNewInstance().setTmpData(10);
                Logger.d(String.format("8. u3 TmpData: %s", Utility3.getNewInstance().getTmpData()));

                AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                    @Override
                    public void doInUIThread() {
                        // doInUIThread
                    }
                });
            }
        }, UtilityAsync.getNewInstance().getExecutorService());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        UtilityAsync.getNewInstance().closeExecutorService();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Subscribe
    public void onEvent(EBDBTest event) {
        UtilityToast.getNewInstance().show(activity, event.getMessage());
    }
}
