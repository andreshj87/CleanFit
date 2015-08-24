package com.zireck.projectk.data.repository.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zireck.projectk.data.entity.DaoMaster;
import com.zireck.projectk.data.entity.DaoSession;

import javax.inject.Inject;

/**
 * Created by Zireck on 24/08/2015.
 */
public class DataStore {

    @Inject Context mContext;

    protected DaoSession mDaoSession;

    public DataStore() {
        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(mContext, "projectk", null);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        mDaoSession = daoMaster.newSession();
    }
}
