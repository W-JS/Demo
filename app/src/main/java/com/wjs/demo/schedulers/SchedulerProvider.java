package com.wjs.demo.schedulers;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SchedulerProvider implements BaseSchedulerProvider {

    private static volatile SchedulerProvider instance = null;

    private SchedulerProvider() {
    }

    public static SchedulerProvider getInstance() {
        if (instance == null) {
            instance = new SchedulerProvider();
        }
        return instance;
    }

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
