package com.ryx.credit.common.util;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * User: 王琪
 * Date: 15-1-26
 * Time: 上午10:31
 * To change this template use File | Settings | File Templates.
 */
public class ThreadPool {

    private static final int POOL_SIZE = Runtime.getRuntime().availableProcessors() * 10;

    private static ExecutorService pool = null;

    private ThreadPool(){

    }

    static {
        pool = Executors.newFixedThreadPool(POOL_SIZE);
    }

    public static ExecutorService getPool(){
        if(pool == null){
            pool = Executors.newFixedThreadPool(POOL_SIZE);
        }
        return pool;
    }

    public static void putThreadPool(Runnable thread){
        getPool().execute(thread);
    }

    public static Future<Object> submitThreadPool(Callable<Object> task){
        Future<Object> future = getPool().submit(task);
        return future;
    }

    public static void submitThreadPool(Callable<Object> task, Queue<Future<Object>> queue){
        Future<Object> future = getPool().submit(task);
        queue.add(future);
    }

    public static void closePool(){
        getPool().shutdown();
    }
}
