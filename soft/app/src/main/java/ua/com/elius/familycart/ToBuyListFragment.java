package ua.com.elius.familycart;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ToBuyListFragment extends Fragment {

    public ToBuyListFragment() {
        // Required empty public constructor
    }

    public static ToBuyListFragment newInstance() {
        ToBuyListFragment fragment = new ToBuyListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_buy_list, container, false);
    }

}
