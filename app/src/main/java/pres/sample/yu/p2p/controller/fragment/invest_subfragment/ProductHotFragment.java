package pres.sample.yu.p2p.controller.fragment.invest_subfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pres.sample.yu.p2p.R;


/**
 * Created by shkstart on 2016/8/15 0015.
 */
public class ProductHotFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(),R.layout.fragment_product_hot,null);
        return view;

    }
}
