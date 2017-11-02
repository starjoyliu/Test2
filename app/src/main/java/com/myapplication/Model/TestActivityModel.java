package com.myapplication.Model;

import com.myapplication.Database.DBTest;

import java.util.List;

/**
 * Created by star on 2017/11/1.
 */

public class TestActivityModel {
    public DBTest load(){
        List<DBTest> dbTests = DBTest.getAll();
        if(dbTests.size()>0){
            return dbTests.get(0);
        }else{
            return null;
        }
    }

    public boolean update(String name, String phone){
        return DBTest.updateUser(name, phone);
    }
}
