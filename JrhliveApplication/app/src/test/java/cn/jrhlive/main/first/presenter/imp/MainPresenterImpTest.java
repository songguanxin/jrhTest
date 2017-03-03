package cn.jrhlive.main.first.presenter.imp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import cn.jrhlive.main.entity.MainItem;
import cn.jrhlive.main.first.interactor.imp.MainInteractorImp;
import cn.jrhlive.main.first.view.MainView;

import static org.mockito.Mockito.verify;

/**
 * desc:
 * Created by jiarh on 17/3/1 15:17.
 */
public class MainPresenterImpTest {


    MainInteractorImp mainInteractorImp;

    @Mock
    List<MainItem> mainItems;

    @Mock
    MainView mainView;

    MainPresenterImp mainPresenterImp;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        mainInteractorImp = new MainInteractorImp();
        mainPresenterImp = new MainPresenterImp(mainInteractorImp);
        mainPresenterImp.onAttachView(mainView);

    }


    @Test
    public void testOnCreate() {
        mainPresenterImp.onCreate();
        mainItems= mainInteractorImp.getDatas();
        verify(mainView).showProgress();
        verify(mainView).initViewData(mainItems);
        verify(mainView).showMsg("success");
        verify(mainView).hideProgress();

    }
}