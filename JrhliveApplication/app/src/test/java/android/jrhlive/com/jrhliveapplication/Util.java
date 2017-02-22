package android.jrhlive.com.jrhliveapplication;

import java.util.List;

/**
 * desc:
 * Created by jiarh on 16/10/31 16:46.
 */

public class Util {
    public static void logList(List<String> list){
        if (list!=null&&list.size()>0)
        {

            for (String s :list)
                System.out.println(s);
        }
    }
}
