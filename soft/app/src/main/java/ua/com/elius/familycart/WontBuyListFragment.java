package ua.com.elius.familycart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class WontBuyListFragment extends Fragment {

    public WontBuyListFragment() {
        // Required empty public constructor
    }

    public static WontBuyListFragment newInstance() {
        WontBuyListFragment fragment = new WontBuyListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wont_buy_list, container, false);
    }

}
