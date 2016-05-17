package ua.com.elius.familycart.family;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import ua.com.elius.familycart.R;

public class FamilyViewHolder extends RecyclerView.ViewHolder {

    ImageView photo;
    TextView name;
    CheckBox select;

    public FamilyViewHolder(View v) {
        super(v);
        photo = (ImageView) v.findViewById(R.id.contact_photo);
        name = (TextView) v.findViewById(R.id.contact_name);
        select = (CheckBox) v.findViewById(R.id.contact_select);
    }
}
