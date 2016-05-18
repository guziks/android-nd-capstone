package ua.com.elius.familycart.family;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import ua.com.elius.familycart.R;
import ua.com.elius.familycart.data.person.PersonColumns;
import ua.com.elius.familycart.data.person.PersonCursor;
import ua.com.elius.familycart.data.person.PersonSelection;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.ViewHolder>{

    private static final String TAG = "FamilyAdapter";

    private Context mContext;
    private PersonCursor mCursor;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView photo;
        TextView name;
        CheckBox select;

        public ViewHolder(View v) {
            super(v);
            photo = (ImageView) v.findViewById(R.id.contact_photo);
            name = (TextView) v.findViewById(R.id.contact_name);
            select = (CheckBox) v.findViewById(R.id.contact_select);
            select.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            boolean sharingToAllowed = select.isChecked();

            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);

            Toast.makeText(mContext, mCursor.getDisplayName(), Toast.LENGTH_SHORT).show();

            ContentValues values = new ContentValues();
            DatabaseUtils.cursorRowToContentValues(mCursor, values);
            values.put(PersonColumns.SHARING_TO_ALLOWED, sharingToAllowed);
            values.put(PersonColumns.TIME_MODIFIED, System.currentTimeMillis());
            PersonSelection where = new PersonSelection().gid(mCursor.getGid());

            mContext.getContentResolver().update(PersonColumns.CONTENT_URI, values, where.sel(), where.args());
//            mContext.getContentResolver().update(PersonColumns.CONTENT_URI, values,
//                    PersonColumns.GID + " = ?",
//                    new String[]{mCursor.getGid()});
//            mContext.getContentResolver().insert(PersonColumns.CONTENT_URI, values);
        }
    }

    public FamilyAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.family_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        holder.name.setText(mCursor.getDisplayName());
        String imageUrl = mCursor.getImageUrl();
        Glide.with(mContext)
                .load(imageUrl)
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
        holder.select.setChecked(mCursor.getSharingToAllowed());
    }

    @Override
    public int getItemCount() {
        if ( null == mCursor ) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(PersonCursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public PersonCursor getCursor() {
        return mCursor;
    }
}
