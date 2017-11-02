package com.myapplication.Database;

import com.log.Logger;
import com.myapplication.EventBus.EBDBTest;
import com.orm.SugarRecord;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by star on 2017/11/1.
 */

public class DBTest extends SugarRecord {
    private final static String TAG = DBTest.class.getSimpleName();

    private String name;
    private String phone;

    public DBTest(){
        super();
    }

    public DBTest(String name
            , String phone) {
        this.name = name;
        this.phone = phone;
    }

    /**
     * 取得所有資料
     * @return
     */
    public static List<DBTest> getAll(){
        return listAll(DBTest.class);
    }

    public static void insertUser(String name, String phone){
        List<DBTest> dbTests = find(DBTest.class, "name=?", name);
        if(dbTests.size()>0){
            //已經存在
            Logger.e(String.format("insertUser %s is exist", name));
            EventBus.getDefault().post(new EBDBTest(String.format("insertUser %s is exist", name)));
        }else{
            DBTest dbTest = new DBTest(name, phone);
            dbTest.save();
        }

    }

    public static DBTest getUser(String name){
        List<DBTest> dbTests = find(DBTest.class, "name=?", name);
        if (dbTests.size()>0){
            return dbTests.get(0);
        }else{
            return null;
        }
    }

    public static boolean updateUser(String name, String phone){
        List<DBTest> dbTests = find(DBTest.class, "name=?", name);
        if (dbTests.size()>0){
            DBTest dbTest = dbTests.get(0);
            dbTest.name = name;
            dbTest.phone = phone;
            dbTest.save();
            return true;
        }else{
            return false;
        }
    }

    public static void deleteUser(String name){
        deleteAll(DBTest.class, "name=?", name);
    }

    public String getName(){
        return name;
    }

    public String getPhone(){
        return phone;
    }
}
