package com.jrhlibrary.utils;

import java.util.List;

/**
 * desc:
 * Created by jiarh on 17/1/20 15:34.
 */

public class ListUtils {
    public static boolean isNotEmplyList(List<?> list){
        return list!=null&&list.size()>0;
    }
}
