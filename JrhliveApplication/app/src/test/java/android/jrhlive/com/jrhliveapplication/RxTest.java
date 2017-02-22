//package android.jrhlive.com.jrhliveapplication;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import cn.jrhlive.rxjava.activity.RxJavaTest;
//
//import static android.jrhlive.com.jrhliveapplication.Util.logList;
//
///**
// * desc:
// * Created by jiarh on 16/10/31 16:59.
// */
//
//public class RxTest {
//
//
//    RxJavaTest rxJavaTest ;
//
//    @Before
//    public void setUp(){
//        if (rxJavaTest==null){
//            rxJavaTest = new RxJavaTest();
//        }
//    }
//
//    @After
//    public void tearDown(){
//        rxJavaTest = null;
//    }
//
//
//    @Test(timeout = 3000)
//    public void testUseJust() throws Exception{
//        Thread.sleep(2000);
//        logList(rxJavaTest.useJust());
//    }
//
//    @Test
//    public void testUseRange(){
//        logList(rxJavaTest.useRange());
//    }
//
//    @Test
//    public void testUseRepeat(){
//        logList(rxJavaTest.useRepeat());
//    }
//}
