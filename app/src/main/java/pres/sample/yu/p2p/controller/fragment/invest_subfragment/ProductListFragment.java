package pres.sample.yu.p2p.controller.fragment.invest_subfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pres.sample.yu.p2p.R;
import pres.sample.yu.p2p.bean.Product;
import pres.sample.yu.p2p.common.AppNetConfig;
import pres.sample.yu.p2p.view.RoundProgress;


/**
 * Created by shkstart on 2016/8/15 0015.
 */
public class ProductListFragment extends Fragment {

    @BindView(R.id.lv_product_list)
    ListView lvProductList;
    private List<Product> products;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_product_list, null);
        ButterKnife.bind(this, view);
        initData();
        return view;

    }

    private void initData() {
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.post(AppNetConfig.PRODUCT, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                JSONObject jsonObject = JSON.parseObject(content);
                if (jsonObject.getBoolean("success")) {
                    String data = jsonObject.getString("data");
                    products = JSON.parseArray(data, Product.class);
                }
                MyAdapter adapter = new MyAdapter(products);
                lvProductList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable error, String content) {
//                super.onFailure(error, content);
                Toast.makeText(ProductListFragment.this.getActivity(), "数据下载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class MyAdapter extends BaseAdapter {
        private List<Product> mProducts;

        public MyAdapter(List<Product> products) {
            mProducts = products;
        }

        @Override
        public int getCount() {
            return mProducts.size();
        }

        @Override
        public Object getItem(int i) {
            return mProducts.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder=null;
            if (view == null) {
                view = View.inflate(getActivity(), R.layout.item_product_list, null);
                holder=new ViewHolder(view);
                view.setTag(holder);
            }else {
                holder= (ViewHolder) view.getTag();
            }
            //装配数据
            Product product = mProducts.get(i);
            holder.pMoney.setText(product.money);
            holder.pMinzouzi.setText(product.minTouMoney);
            holder.pProgresss.setProgress(Integer.parseInt(product.progress));
            holder.pMinnum.setText(product.memberNum);
            holder.pName.setText(product.name);
            holder.pYearlv.setText(product.yearRate);
            holder.pSuodingdays.setText(product.suodingDays);
            return view;
        }

         class ViewHolder {
            @BindView(R.id.p_name)
            TextView pName;
            @BindView(R.id.p_money)
            TextView pMoney;
            @BindView(R.id.p_yearlv)
            TextView pYearlv;
            @BindView(R.id.p_suodingdays)
            TextView pSuodingdays;
            @BindView(R.id.p_minzouzi)
            TextView pMinzouzi;
            @BindView(R.id.p_minnum)
            TextView pMinnum;
            @BindView(R.id.p_progresss)
            RoundProgress pProgresss;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
