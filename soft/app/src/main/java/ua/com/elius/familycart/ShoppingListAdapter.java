package ua.com.elius.familycart;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private String[][] mDataset;

    public ShoppingListAdapter(String[][] dataset) {
        mDataset = dataset;
    }

    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_item, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTitle.setText(mDataset[position][0]);
        holder.mQuantity.setText(mDataset[position][1]);
        holder.mDescription.setText(mDataset[position][2]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public TextView mQuantity;
        public TextView mDescription;

        public ViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.item_title);
            mQuantity = (TextView) v.findViewById(R.id.item_quantity);
            mDescription = (TextView) v.findViewById(R.id.item_description);
        }
    }
}
