package cn.jrhlive.design.strategy;

/**
 * desc:
 * Created by jiarh on 16/12/1 13:45.
 */

public class Car {
    MultiSpeed mMultiSpeed;

    public Car(MultiSpeed mMultiSpeed) {
        this.mMultiSpeed = mMultiSpeed;
    }

    public void changeCarSpeed(MultiSpeed speed){
        this.mMultiSpeed = speed;
    }
    public void execute(){

        System.out.println("speed="+mMultiSpeed.speed());
    }
}
