package daywork;

/**
 * desc:service有几种启动方式，各自的生命周期以及区别是什么？
 * Created by jiarh on 17/3/1 09:32.
 */

public class day8 {
    /**
     *
     *
     *
     * Service 的启动方式有两种：
     *
     * 1：通过context.startService() context.stopService()
     *
     *  [startService()]---onCreate()(没有启动时，只会调用一次)---onStartCommond()---[stopService()]onDestory();
     *
     *  若不调用stopService(),Activity销毁后 ，service 依然存在
     *
     *  2：bindService()  unBindService
     *
     *  [bindService()]---onCreate()--onBind()--[unBindService()]onUnBind()--onDestory();
     *
     *  获取一个保持稳定的连接通信，通过返回的IBinder通信
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */


}
