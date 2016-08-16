package pres.sample.yu.p2p.controller.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;
import pres.sample.yu.p2p.utils.UIUtils;
import pres.sample.yu.p2p.view.loadingpage;

/**
 * Created by yu on 2016/8/12.
 */

public abstract class BaseFragment extends Fragment {

    private pres.sample.yu.p2p.view.loadingpage loadingpage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = UIUtils.getXmlView(getViewID());
//        ButterKnife.bind(BaseFragment.this,view);
//        initTitle();
//        initData();
//        return view;
        loadingpage = new loadingpage(container.getContext()) {
            @Override
            public int layoutId() {
                return getViewId();
            }

            @Override
            protected void onSuccess(ResultState resultState, View successView) {
                ButterKnife.bind(BaseFragment.this, successView);
                initData(resultState.getContent());
                initTitle();
            }

            @Override
            protected RequestParams params() {
                return getParams();
            }

            @Override
            protected String url() {
                return getUrl();
            }
        };
        return loadingpage;
    }

    protected abstract String getUrl();

    protected abstract RequestParams getParams();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLoadingpage();
            }
        },2000);
    }

    private void showLoadingpage() {
        loadingpage.show();
    }


    public abstract int getViewId() ;

    public abstract void initData(String content);

    public abstract void initTitle();
}
