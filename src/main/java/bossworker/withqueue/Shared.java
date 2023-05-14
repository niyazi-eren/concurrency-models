package bossworker.withqueue;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Shared {
    public ArrayDeque<Runnable> jobQueue;
    public ReentrantLock lock;
    public Condition condition;
    public boolean writable;
}
