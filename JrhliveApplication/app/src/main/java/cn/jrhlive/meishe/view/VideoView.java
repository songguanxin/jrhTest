package cn.jrhlive.meishe.view;

import cn.jrhlive.basemvp.BaseView;


/**
 * desc:
 * Created by jiarh on 17/5/11 15:42.
 */

public interface VideoView extends BaseView {

    void play();
    void addCaption(boolean add);
    void seek();
    void moveSeekBar(int progress);
    void reset();
    void updateSeekBar();
    void stopSeekBar();

}
