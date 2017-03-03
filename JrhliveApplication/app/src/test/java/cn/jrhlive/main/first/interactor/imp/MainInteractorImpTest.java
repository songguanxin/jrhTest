package cn.jrhlive.main.first.interactor.imp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import cn.jrhlive.main.entity.MainItem;

/**
 * desc:
 * Created by jiarh on 17/3/1 14:24.
 */
public class MainInteractorImpTest {





    MainInteractorImp mainInteractorImp;

    @Before
    public void setUp(){

        mainInteractorImp = new MainInteractorImp();
    }


    @Test
    public void getDatas() throws Exception {

        List<MainItem> lists = mainInteractorImp.getDatas();


        Assert.assertEquals(11,lists.size());

    }

    @Test
    public void loadMainItems() throws Exception {

    }

}