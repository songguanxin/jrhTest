package design;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.jrhlive.design.DesignMain;

/**
 * desc:
 * Created by jiarh on 16/12/1 13:51.
 */

public class Design {

    DesignMain designMain;

    @Before
    public void setUp(){
        if (designMain==null)
            designMain = new DesignMain();

    }
    @Test
    public void testStrategy(){
        designMain.start();
    }

    @After
    public void tearDown(){
        designMain=null;
    }
}
