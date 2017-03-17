package daywork.day14;

import org.junit.Before;
import org.junit.Test;

/**
 * desc:
 * 事件委托模式
 一个班级，有两类学生，A类：不学习，玩，但是玩的东西不一样，有的是做游戏，与的是看电视（有点不合理）

 B类：放哨的学生，专门看老师的动向，如果老师进班了就立即通知大家。
 如此就形成了一个需求，放哨的学生要通知所有玩的学生：老师来了，而不同的学生有不同的反应，有的马上把电视关闭，有的停止玩游戏。

 设计的要求如下，让A类学生和B类学生完全解耦，即A类完全不知道B类的学生，却可以通知B类的学生。
 * Created by jiarh on 17/3/10 09:45.
 */

public class Day14 {

    StudentManager studentManagerA = new StudentManager();
    StudentManager studentManagerB = new StudentManager();

    @Before
    public void setUp(){
        studentManagerA.add(new StudentA("A1","打dota"));
        studentManagerA.add(new StudentA("A2","打LOL"));
        studentManagerA.add(new StudentA("A3","打怪兽"));
        studentManagerA.work();
        studentManagerB.add(new StudentB("B1","放哨看班主任"));
        studentManagerB.add(new StudentB("B2","放哨看校长"));
        studentManagerB.add(new StudentB("B3","放哨看教导主任"));
        studentManagerB.work();


    }


    @Test
    public void testStudent(){

        System.out.println("老师来啦");
        studentManagerB.notiyStop();
        studentManagerA.notiyStop();

    }

}

