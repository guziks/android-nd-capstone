package ua.com.elius.familycart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BoughtListFragment extends Fragment {

    public BoughtListFragment() {
        // Required empty public constructor
    }

    public static BoughtListFragment newInstance() {
        BoughtListFragment fragment = new BoughtListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bought_list, container, false);
    }

}
