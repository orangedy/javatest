/*
 * @(#) FutureTest.java 2015年1月12日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package thread;

import httpclient.ApiNotAvailableException;
import httpclient.HttpClient4Utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author hzdingyong
 * @version 2015年1月12日
 */
public class FutureTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Lazy Future
     * 与一般的Future不同，Lazy Future在创建之初不会主动开始准备引用的对象，而是等到请求对象时才开始相应的工作。
     * 因此，Lazy Future本身并不是为了实现并发，而是以节约不必要的运算资源为出发点，效果上与Lambda/Closure类似。
     * 例如设计某些API时，你可能需要返回一组信息，而其中某些信息的计算可能会耗费可观的资源。
     * 但调用者不一定都关心所有的这些信息，因此将那些需要耗费较多资源的对象以Lazy Future的形式提供，
     * 可以在调用者不需要用到特定的信息时节省资源。
     * 另外Lazy Future也可以用于避免过早的获取或锁定资源而产生的不必要的互斥。
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    @Test
    public void testTimeoutWithFuture() throws InterruptedException, ExecutionException, TimeoutException {
        Future<String> task = new Future<String>() {

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public String get() throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                Thread.sleep(2000);
                try {
                    String response = HttpClient4Utils.sendGet("http://www.baidu.com", null);
                    System.out.println(response);
                } catch (ApiNotAvailableException e) {
                    e.printStackTrace();
                }
                return "task done";
            }
        };

//        thrown.expect(TimeoutException.class);
        String result = task.get(1, TimeUnit.SECONDS);
        System.out.println(result);
    }

    @Test
    public void testFuture() throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<String> taskResult = es.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String response = HttpClient4Utils.sendGet("http://www.baidu.com", null);
                String repsonse1 = HttpClient4Utils.sendGet("http://www.baidu.com", null);
                return response;
            }
        });
        System.out.println("do my business");
        System.out.println("get taskResult");
        String result = taskResult.get();
        System.out.println("got result:" + result);
    }
}
