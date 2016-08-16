package pres.sample.yu.p2p.controller.avtivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pres.sample.yu.p2p.common.AppManager;
import pres.sample.yu.p2p.R;
import pres.sample.yu.p2p.controller.fragment.HomeFragment;
import pres.sample.yu.p2p.controller.fragment.InvestFragment;
import pres.sample.yu.p2p.controller.fragment.MoreFragment;
import pres.sample.yu.p2p.controller.fragment.UserFragment;

public class MainActivity extends FragmentActivity {


    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.iv_main_home)
    ImageView ivMainHome;
    @BindView(R.id.tv_main_home)
    TextView tvMainHome;
    @BindView(R.id.ll_main_home)
    LinearLayout llMainHome;
    @BindView(R.id.iv_main_invest)
    ImageView ivMainInvest;
    @BindView(R.id.tv_main_invest)
    TextView tvMainInvest;
    @BindView(R.id.ll_main_invest)
    LinearLayout llMainInvest;
    @BindView(R.id.iv_main_user)
    ImageView ivMainUser;
    @BindView(R.id.tv_main_user)
    TextView tvMainUser;
    @BindView(R.id.ll_main_user)
    LinearLayout llMainUser;
    @BindView(R.id.iv_main_more)
    ImageView ivMainMore;
    @BindView(R.id.tv_main_more)
    TextView tvMainMore;
    @BindView(R.id.ll_main_more)
    LinearLayout llMainMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        AppManager.getinstance().add(MainActivity.this);
    }

    @OnClick({R.id.ll_main_home, R.id.ll_main_invest, R.id.ll_main_user, R.id.ll_main_more})
    public void onClick(View view) {
        reSetTab();
        switch (view.getId()) {
            case R.id.ll_main_home:
                changeFragment(new HomeFragment());
                ivMainHome.setImageResource(R.drawable.bid01);
                tvMainHome.setTextColor(Color.parseColor("#18B4ED"));
                break;
            case R.id.ll_main_invest:
                changeFragment(new InvestFragment());
                ivMainInvest.setImageResource(R.drawable.bid03);
                tvMainInvest.setTextColor(Color.parseColor("#18B4ED"));
                break;
            case R.id.ll_main_user:
                changeFragment(new UserFragment());
                ivMainUser.setImageResource(R.drawable.bid05);
                tvMainUser.setTextColor(Color.parseColor("#18B4ED"));
                break;
            case R.id.ll_main_more:
                changeFragment(new MoreFragment());
                ivMainMore.setImageResource(R.drawable.bid07);
                tvMainUser.setTextColor(Color.parseColor("#18B4ED"));
                break;
        }
    }

    private void reSetTab() {
        ivMainHome.setImageResource(R.drawable.bid02);
        ivMainInvest.setImageResource(R.drawable.bid04);
        ivMainUser.setImageResource(R.drawable.bid06);
        ivMainMore.setImageResource(R.drawable.bid08);

        tvMainHome.setTextColor(Color.parseColor("#878787"));
        tvMainInvest.setTextColor(Color.parseColor("#878787"));
        tvMainUser.setTextColor(Color.parseColor("#878787"));
        tvMainMore.setTextColor(Color.parseColor("#878787"));
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_main, fragment).commit();
    }


    private void initData() {
        changeFragment(new HomeFragment());
        ivMainHome.setImageResource(R.drawable.bid01);
        tvMainHome.setTextColor(Color.parseColor("#18B4ED"));
    }

}
