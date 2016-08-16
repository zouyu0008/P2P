package pres.sample.yu.p2p.controller.fragment;

import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import pres.sample.yu.p2p.R;
import pres.sample.yu.p2p.bean.Image;
import pres.sample.yu.p2p.bean.Index;
import pres.sample.yu.p2p.bean.Product;
import pres.sample.yu.p2p.common.AppNetConfig;
import pres.sample.yu.p2p.controller.base.BaseFragment;
import pres.sample.yu.p2p.view.RoundProgress;

/**
 * Created by yu on 2016/8/12.
 */

public class HomeFragment extends BaseFragment {


    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_set)
    ImageView ivTitleSet;
    @BindView(R.id.vp_home)
    ViewPager vpHome;
    @BindView(R.id.cp_home)
    com.viewpagerindicator.CirclePageIndicator cpHome;
    @BindView(R.id.rp_home_progress)
    RoundProgress rpHomeProgress;
    @BindView(R.id.tv_home_yearrate)
    TextView tvHomeYearrate;
    @BindView(R.id.btn_home_join)
    Button btnHomeJoin;
    private Index index;
    private MyAdapter adapter;
    private AsyncHttpClient client = new AsyncHttpClient();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rpHomeProgress.setProgress(0);
            rpHomeProgress.setMax(100);

            for (int i = 0; i < currentProgress; i++) {
                rpHomeProgress.setProgress(rpHomeProgress.getProgress() + 1);

                SystemClock.sleep(30);
                //强制重绘
                rpHomeProgress.postInvalidate();
            }


        }
    };
    private int currentProgress;

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = UIUtils.getXmlView(R.layout.home_fragment);
//        ButterKnife.bind(this, view);
//        initTitle();
//        initData();
//        return view;
//    }

    @Override
    protected String getUrl() {
        return AppNetConfig.INDEX;
    }

    @Override
    protected RequestParams getParams() {
        return new RequestParams();
    }

    @Override
    public int getViewId() {
        return R.layout.home_fragment;
    }

    @Override
    public void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        ivTitleSet.setVisibility(View.GONE);
        tvTitle.setText("首页");
    }

    public void initData(String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        index = new Index();
        //解析json数据：gson / fastjson
        JSONObject jsonObject = JSON.parseObject(content);
        //解析并得到Product对象
        String proInfo = jsonObject.getString("proInfo");
        Product product = JSON.parseObject(proInfo, Product.class);
        //解析并得到Image构成的集合
        String imageArr = jsonObject.getString("imageArr");
        List<Image> images = JSON.parseArray(imageArr, Image.class);

        index.product = product;
        index.images = images;

        //将得到的数据加载显示在ViewPager中
        adapter = new MyAdapter();
        //显示数据
        vpHome.setAdapter(adapter);
        //设置Indicator的显示
        cpHome.setViewPager(vpHome);

        //根据得到的Product中的progress来更新自定义的圆形进度条的进度
        currentProgress = Integer.parseInt(index.product.progress);
        new Thread(runnable).start();

    }


    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return index.images == null ? 0 : index.images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getActivity());

            //设置为居中显示
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            //获取集合数据中指定位置的图片的url地址，联网将图片下载并设置给ImageView
            Picasso.with(getActivity()).load(index.images.get(position).IMAURL).into(imageView);
            Log.e("TAG", index.images.get(position).IMAURL + "");
            //添加到viewpager中
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);

            container.removeView((View) object);

        }
    }
}
