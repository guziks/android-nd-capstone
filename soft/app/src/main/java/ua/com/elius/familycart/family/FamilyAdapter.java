package ua.com.elius.familycart.family;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.Collections;

import ua.com.elius.familycart.R;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyViewHolder>{

    private static final String TAG = "FamilyAdapter";

    private ArrayList<String[]> mDataset;
    private Context mContext;

    public FamilyAdapter(String[][] dataset, Context context) {
        mDataset = new ArrayList<>();
        Collections.addAll(mDataset, dataset);

        mContext = context;
    }

    @Override
    public FamilyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.family_list_item, parent, false);

        return new FamilyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FamilyViewHolder holder, int position) {
        holder.name.setText(mDataset.get(position)[1]);
        String url = mDataset.get(position)[2];
        Glide.with(mContext)
                .load(url)
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(holder.photo) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.photo.setImageDrawable(circularBitmapDrawable);
            }
        });
//        Glide.with(mContext)
//                .load(url)
//                .transform(new CircleTransform(context))
//                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
