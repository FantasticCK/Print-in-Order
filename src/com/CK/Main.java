package com.CK;

import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
	// write your code here
    }
}

// synchronized
class Foo {
    Set<Integer> set;

    public Foo() {
        set = new HashSet<>();
    }

    public synchronized void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        set.add(1);
        this.notifyAll();
    }

    public synchronized void second(Runnable printSecond) throws InterruptedException {
        while (!set.contains(1)) {
            this.wait();
        }
        printSecond.run();
        set.add(2);
        this.notifyAll();
    }

    public synchronized void third(Runnable printThird) throws InterruptedException {

        while (!set.contains(1) || !set.contains(2)) {
            this.wait();
        }
        printThird.run();
    }
}

class Foo {
    Semaphore run2, run3;

    public Foo() {
        run2 = new Semaphore(0);
        run3 = new Semaphore(0);
    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        run2.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        run2.acquire();
        printSecond.run();
        run3.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
        run3.acquire();
        printThird.run();
    }
}