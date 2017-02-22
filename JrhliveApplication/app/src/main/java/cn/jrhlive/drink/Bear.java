package cn.jrhlive.drink;

/**
 * desc:
 * Created by jiarh on 16/12/5 16:23.
 */

public class Bear {
    private int cap = 8;
    private String name;

    public Bear(int cap,String name) {
        this.cap = cap;
        this.name = name;
    }

    public int getNowCap(){
        return cap;
    }

    public void setCap(int cap){
        this.cap = cap;
    }

    public boolean isall(){
        return cap==8;
    }
    public boolean hasBear(){
        return cap>0;
    }

}
