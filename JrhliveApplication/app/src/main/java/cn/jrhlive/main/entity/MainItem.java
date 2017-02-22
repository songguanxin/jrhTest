package cn.jrhlive.main.entity;

import cn.jrhlive.basemvp.BaseEntity;

/**
 * desc:
 * Created by jiarh on 16/9/14 16:03.
 */
public class MainItem implements BaseEntity {
    String itemName;
    int id;

    public MainItem(String itemName,int id) {
        this.itemName = itemName;
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
