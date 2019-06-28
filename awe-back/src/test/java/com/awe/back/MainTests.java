package com.awe.back;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * description
 *
 * @author wangy QQ 837195190
 * <p>Created by admin on 2018/8/23.</p>
 */
public class MainTests {

    private ArrayList<Integer> arrayList = new ArrayList<>();

    public static void main(String[] args) {
        MainTests mainTests = new MainTests();

        new Thread(() -> mainTests.insert(Thread.currentThread())).start();
        new Thread(() -> mainTests.insert(Thread.currentThread())).start();
    }

    public void insert(Thread thread) {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            System.out.println(thread.getName());
            for (int i = 0; i < 5; i++) {
                arrayList.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(String.format("%s%s", thread.getName(), "unlock"));
            lock.unlock();
        }

    }

}
