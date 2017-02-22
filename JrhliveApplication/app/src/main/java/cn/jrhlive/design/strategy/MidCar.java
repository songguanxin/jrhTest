package cn.jrhlive.design.strategy;

/**
 * desc:
 * Created by jiarh on 16/12/1 13:44.
 */

public class MidCar implements MultiSpeed {
    @Override
    public int speed() {
        return 100;
    }
}
