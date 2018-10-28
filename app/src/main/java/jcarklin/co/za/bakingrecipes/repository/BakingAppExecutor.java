package jcarklin.co.za.bakingrecipes.repository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import jcarklin.co.za.bakingrecipes.repository.db.BakingAppDatabase;

public class BakingAppExecutor {

    private static final Object LOCK = new Object();
    private static BakingAppExecutor sInstance;
    private final Executor diskIO;

    private BakingAppExecutor(Executor diskIO) {
        this.diskIO = diskIO;
    }

    public static BakingAppExecutor getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new BakingAppExecutor(Executors.newSingleThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor diskIO() {
        return diskIO;
    }
}
