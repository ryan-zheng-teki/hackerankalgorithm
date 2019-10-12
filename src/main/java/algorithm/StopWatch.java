package algorithm;

public class StopWatch {

    /* Private Instance Variables */
    /**
     * Stores the start time when an object of the StopWatch class is initialized.
     */
    private long startTime;

    /**
     * Custom constructor which initializes the {@link #startTime} parameter.
     */
    public StopWatch() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Gets the elapsed time (in seconds) since the time the object of StopWatch was initialized.
     *
     * @return Elapsed time in seconds.
     */
    public void getElapsedTime() {
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) / (1000));
    }
}