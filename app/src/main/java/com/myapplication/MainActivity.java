package com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private Thread T1, T2;
    private Object obj1 = new Object();
    private Object obj2 = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

            Log.d(TAG, "5. u3 setTmpData");
            Utility3.getNewInstance().setTmpData(10000);
            Log.d(TAG, String.format("5. u3 TmpData: %s", Utility3.getNewInstance().getTmpData()));
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

            Log.d(TAG, "6. u3 setTmpData");
            Utility3.getNewInstance().setTmpData(10);
            Log.d(TAG, String.format("6. u3 TmpData: %s", Utility3.getNewInstance().getTmpData()));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
