package test;
import org.apache.log4j.Logger;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


public class PromiseSupportC<T> implements Future<T> {
    private static final Logger LOGGER = Logger.getLogger(PromiseSupportC.class);

    private static final int RUNNING = 1;
    private static final int FAILED = 2;
    private static final int COMPLETED = 3;

    private final Object lock;

    private volatile int state = RUNNING;
    private T value;
    private Exception exception;


    public PromiseSupportC(Object lock) {
        this.lock = lock;
    }

    void fulfill(T value) {
        this.value = value;
        this.state = COMPLETED;
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    void fulfillExceptionally(Exception exception) {
        this.exception = exception;
        this.state = FAILED;
        synchronized (lock) {
            lock.notifyAll();
        }
    }

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
        return state > RUNNING;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        synchronized (lock) {
            while (state == RUNNING) {
                lock.wait();
            }
        }
        if (state == COMPLETED) {
            return value;
        }
        throw new ExecutionException(exception);
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws ExecutionException {
        synchronized (lock) {
            while (state == RUNNING) {
                try {
                    lock.wait(unit.toMillis(timeout));
                } catch (InterruptedException e) {
                    LOGGER.warn("Interrupted!", e);
                    Thread.currentThread().interrupt();
                }
            }
        }

        if (state == COMPLETED) {
            return value;
        }
        throw new ExecutionException(exception);
    }
}
