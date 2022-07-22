package test;
//https://reime005.medium.com/from-java-to-javascript-callback-and-promise-cc9c0d8be304
public class SynchronousExample {
    public interface ISomeEventListener {
        void someEvent();
    }

    private ISomeEventListener someEventListener;

    public void setSomeEventListener(ISomeEventListener someEventListener) {
        this.someEventListener = someEventListener;
    }

    public void doSomeHeavyCalculation(final long howLongInMs) {
        System.out.println("Doing some heavy work...");
        // some heavy work...
        try {
            Thread.sleep(howLongInMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (this.someEventListener != null) {
            someEventListener.someEvent();
        }
    }
}
