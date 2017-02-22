package cn.jrhlive.widget.toolbar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cn.jrhlive.R;

/**
 * desc:
 * Created by jiarh on 16/9/13 10:40.
 */
public class ToolBarHelper {
    Toolbar toolbar;
    public static void init(AppCompatActivity activity, Toolbar toolbar){

        if (toolbar!=null){
            toolbar.setTitle(R.string.app_name);
            toolbar.setTitleTextColor(Color.WHITE);
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);


        }
    }
}
