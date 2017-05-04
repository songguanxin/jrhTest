package daywork;

import org.junit.Test;

/**
 * desc:简述你理解的builder模式，写出简单的builder模式实现
 * Created by jiarh on 17/3/30 09:45.
 */

public class Day30 {

    /**
     * Build模式，常用于可多种配置功能，根据不同的配置产生不同的结果，实现所需的功能。如 AlertDialog.
     */


    @Test
    public void testLog() {

        LogMyUtil logutil = new LogMyUtil.Builder()
                .setAddPre(true)
                .setTimes(3)
                .create();
        logutil.logText("builder");

        LogMyUtil logutil2 = new LogMyUtil.Builder()
                .setAddPre(false)
                .setTimes(3)
                .create();
        logutil2.logText("builder");

    }


}

class LogMyUtil {

    public static boolean isAddprefix;
    public static int printContTimes;

    public static class Builder {
        public Builder setAddPre(boolean isadd) {
            isAddprefix = isadd;
            return this;
        }

        public Builder setTimes(int printTimes) {
            printContTimes = printTimes;
            return this;
        }

        public LogMyUtil create() {

            return new LogMyUtil();
        }
    }

    public void logText(String text) {
        if (printContTimes <= 0) {
            return;
        }

        String pre = "hello";
        StringBuffer sb = new StringBuffer(text);

        if (isAddprefix) {

            sb.insert(0,pre);
        }

        for (int i = 0; i < printContTimes; i++) {

            System.out.println(sb.toString());
        }
    }
}
