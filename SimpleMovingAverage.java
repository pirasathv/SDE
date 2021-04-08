import java.util.Iterator;

public interface SimpleMovingAverage<T> {

    /**
     * Append the new element to the end of the data structure
     *
     * @param element value to be appended to data structure
     */
    void add(T element);

    /**
     * A simple moving average (SMA) is an arithmetic moving average calculated by adding recent prices and
     * then dividing that figure by the number of time periods in the calculation average.
     * https://www.investopedia.com/terms/s/sma.asp
     * Calculate and return moving average
     *
     * @return T moving average
     */
    T getMovingAverage();

    /**
     * Return number of elements in the data structure
     *
     * @return integer of data structure size
     */
    int size();

}