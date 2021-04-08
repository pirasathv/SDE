import org.junit.Assert;
import org.junit.Test;

public class SimpleMovingAverageTest {

    private SimpleMovingAverage<Double> queue = new SimpleMovingAverageImpl(3);

    @Test
    public void singleEntrySMA() {
        queue = new SimpleMovingAverageImpl(3);
        queue.add(6.0);

        double actual = queue.getMovingAverage();
        Assert.assertEquals(2.0, actual,0);
    }

    @Test
    public void tenEntrySMA() {
        queue = new SimpleMovingAverageImpl(3);
        queue.add(1.0d);
        queue.add(2.0d);
        queue.add(3.0d);
        queue.add(4.0d);
        queue.add(5.0d);
        queue.add(6.0d);
        queue.add(7.0d);
        queue.add(8.0d);
        queue.add(9.0d);
        queue.add(10.0d);

        double actual = queue.getMovingAverage();
        Assert.assertEquals(9, actual,0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativePeriodCheck() {
        Assert.assertEquals(0.0,new SimpleMovingAverageImpl(-1));
    }

}
