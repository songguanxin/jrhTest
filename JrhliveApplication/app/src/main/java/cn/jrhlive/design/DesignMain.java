package cn.jrhlive.design;

import cn.jrhlive.design.strategy.Car;
import cn.jrhlive.design.strategy.LargeCar;
import cn.jrhlive.design.strategy.MidCar;
import cn.jrhlive.design.strategy.SmallCar;

/**
 * desc:
 * Created by jiarh on 16/12/1 13:42.
 */

public class DesignMain {

    public void start(){
        Car car = new Car(new SmallCar());
        car.execute();
        car.changeCarSpeed(new MidCar());
        car.execute();
        car.changeCarSpeed(new LargeCar());
        car.execute();
    }
}
