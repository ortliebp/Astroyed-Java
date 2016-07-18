package package1;

/**
 * Created by tyleranson on 3/14/16.
 */
public class GameClockTimer {

    private float milsecondsPerCycle;

    private long lastUpdate;

    private int elapsedCycles;

    private float excessCycles;

    private boolean isPaused;

    public float getMilsecondsPerCycle() {
        return milsecondsPerCycle;
    }

    public void setMilsecondsPerCycle(float milsecondsPerCycle) {
        this.milsecondsPerCycle = (1.0f / milsecondsPerCycle) * 1000;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getElapsedCycles() {
        return elapsedCycles;
    }

    public void setElapsedCycles(int elapsedCycles) {
        this.elapsedCycles = elapsedCycles;
    }

    public float getExcessCycles() {
        return excessCycles;
    }

    public void setExcessCycles(float excessCycles) {
        this.excessCycles = excessCycles;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public GameClockTimer(float cyclesPerSecond){
        setMilsecondsPerCycle(cyclesPerSecond);
        reset();
    }

    public void reset() {
        this.elapsedCycles = 0;
        this.excessCycles = 0.0f;
        this.lastUpdate = getCurrentTime();
        this.isPaused = false;
    }

    public void update() {

    }

    public boolean hasElapsedCycle() {
        if(elapsedCycles > 0) {
            this.elapsedCycles--;
            return true;
        }
        return false;
    }

    private static final long getCurrentTime() {
        return (System.nanoTime() / 1000000L);
    }
}




