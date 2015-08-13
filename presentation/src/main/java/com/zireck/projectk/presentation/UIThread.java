package com.zireck.projectk.presentation;

import com.zireck.projectk.domain.executor.PostExecutionThread;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Zireck on 13/08/2015.
 */
@Singleton
public class UIThread implements PostExecutionThread {

    @Inject
    public UIThread() {

    }

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
