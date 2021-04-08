import java.util.*;

public class SimpleMovingAverageImpl implements SimpleMovingAverage<Double> {

    private Queue<Double> slidingStructure;
    private double incrementalSum = 0.0;
    private int period;

    /**
     * FIFO with window implementation and we will store the sum and increment/decrement as we add values
     * @param period
     */
    public SimpleMovingAverageImpl(int period) {
        if(period <= 0){
           throw new IllegalArgumentException("Invalid window size for structure");
        }
        slidingStructure = new LinkedList<>();
        this.period = period;
    }

    /**
     * Add new element to the end of an queue (FIFO) and shift window to right and increment/decrement sum
     *
     * @param element value to be appended to queue
     */
    @Override
    public void add(Double element) {

       if (slidingStructure.size() == period) {
           incrementalSum = incrementalSum - slidingStructure.poll();
       }

        slidingStructure.add(element);
       incrementalSum = incrementalSum + element;
    }

    /**
     * Calculates moving average value over a given period
     *
     * @return moving average
     */
    @Override
    public Double getMovingAverage() {
        return (incrementalSum / period);
    }

    /**
     * Return number of elements in the queue
     *
     * @return integer of data structure size
     */
    @Override
    public int size() {
        return slidingStructure.size();
    }

}