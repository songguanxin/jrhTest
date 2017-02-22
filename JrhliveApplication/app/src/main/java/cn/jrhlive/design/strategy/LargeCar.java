package cn.jrhlive.design.strategy;

/**
 * desc:
 * Created by jiarh on 16/12/1 13:45.
 */

public class LargeCar implements MultiSpeed {
    @Override
    public int speed() {
        return 80;
    }
}
