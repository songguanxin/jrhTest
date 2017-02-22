package rxtest.rxhomework.bean;

import java.util.List;

/**
 * desc:
 * Created by jiarh on 16/12/27 13:42.
 */

public class Group {

    private String name;
    /**
     * 0 主群； 1 子群；
     */
    private int type;
    private String img;

    List<Member> datas;

    public Group(List<Member> datas, String name, int type) {
        this.datas = datas;
        this.name = name;
        this.type = type;
    }

    public List<Member> getDatas() {

        return datas;
    }

    public void setDatas(List<Member> datas) {
        this.datas = datas;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
