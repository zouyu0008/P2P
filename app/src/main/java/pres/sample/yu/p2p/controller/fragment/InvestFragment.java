package pres.sample.yu.p2p.controller.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import pres.sample.yu.p2p.R;
import pres.sample.yu.p2p.controller.base.BaseFragment;
import pres.sample.yu.p2p.controller.fragment.invest_subfragment.ProductHotFragment;
import pres.sample.yu.p2p.controller.fragment.invest_subfragment.ProductListFragment;
import pres.sample.yu.p2p.controller.fragment.invest_subfragment.ProductRecommondFragment;
import pres.sample.yu.p2p.utils.UIUtils;

/**
 * Created by yu on 2016/8/12.
 */

public class InvestFragment extends BaseFragment {


    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_set)
    ImageView ivTitleSet;
    @BindView(R.id.tab_invest_indicator)
    TabPageIndicator tabInvestIndicator;
    @BindView(R.id.vp_invest)
    ViewPager vpInvest;

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
        return R.layout.invest_fragment;
    }

    @Override
    public void initData(String content) {
        initFragment();
        FragmentManager fragmentManager = getFragmentManager();
        PagerAdapter adapter = new MyAdapter(fragmentManager);
        vpInvest.setAdapter(adapter);
        tabInvestIndicator.setViewPager(vpInvest);
    }

    List<Fragment> fragmentList = new ArrayList<>();

    private void initFragment() {
        ProductListFragment productListFragment = new ProductListFragment();
        ProductRecommondFragment productRecommondFragment = new ProductRecommondFragment();
        ProductHotFragment productHotFragment = new ProductHotFragment();
        fragmentList.add(productListFragment);
        fragmentList.add(productRecommondFragment);
        fragmentList.add(productHotFragment);
    }


    public void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        ivTitleSet.setVisibility(View.GONE);
        tvTitle.setText("投资");
    }

    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return UIUtils.getStringArray(R.array.invest_array)[position];
        }
    }
}