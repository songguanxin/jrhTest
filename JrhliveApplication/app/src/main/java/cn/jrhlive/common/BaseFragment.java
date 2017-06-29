package cn.jrhlive.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jrhlibrary.utils.Mobile;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * desc:
 * Created by jiarh on 17/5/4 15:14.
 */

public abstract class BaseFragment extends RxFragment{

    View parentView;
    Unbinder unbinder;
    Activity mAct;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutId()>0){
            parentView = inflater.inflate(getLayoutId(),container,false);
            unbinder= ButterKnife.bind(this,parentView);
        }
        mAct = getActivity();
        System.out.println(this.getClass().getSimpleName() + "BaseFragment.onCreateView");
        return parentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Mobile.init(getActivity());
        initView();
        System.out.println(this.getClass().getSimpleName() + "BaseFragment.onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEvent();
        System.out.println(this.getClass().getSimpleName() + "BaseFragment.onActivityCreated");
    }


    public abstract int getLayoutId();
    public abstract void initView();
    public abstract void initEvent();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
