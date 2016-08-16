package pres.sample.yu.p2p.controller.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import pres.sample.yu.p2p.R;
import pres.sample.yu.p2p.controller.base.BaseFragment;

/**
 * Created by yu on 2016/8/12.
 */

public class MoreFragment extends BaseFragment {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_set)
    ImageView ivTitleSet;


    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected RequestParams getParams() {
        return new RequestParams();
    }

    @Override
    public int getViewId() {
        return R.layout.more_fragment;
    }

    @Override
    public void initData(String content) {

    }



    public void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        ivTitleSet.setVisibility(View.GONE);
        tvTitle.setText("更多");
    }

}
