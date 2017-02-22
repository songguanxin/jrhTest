package cn.jrhlive.eventbus;

import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.RxBus.RxBus;
import cn.jrhlive.activity.BaseActivity;
import cn.jrhlive.main.entity.MainItem;
import cn.jrhlive.main.first.interactor.imp.MainInteractorImp;

public class EeventBusSecondActivity extends BaseActivity {


    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.button2)
    Button button2;
    StringBuffer sb = new StringBuffer();
    @BindView(R.id.button3)
    Button button3;

    @Override
    protected void initEvent() {

        for (MainItem mainItem : new MainInteractorImp().getDatas()) {
            sb.append(mainItem.getItemName() + "\n");
        }
        textView2.setText(sb.toString());

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_eevnt_bus_second;
    }


    @OnClick(R.id.button2)
    public void onClick() {
        sb.insert(0,"EventBus**");
        EventBus.getDefault().post(new EventBusEvent(sb.toString()));
        finish();
    }


    @OnClick(R.id.button3)
    public void onRxBusClick() {
        sb.insert(0,"RxBus**");
        RxBus.getDefault().post(new EventBusEvent(sb.toString()));
        finish();
    }
}
