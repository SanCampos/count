package com.santinocampos.android.count.ItemType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.santinocampos.android.count.R;


/**
 * Created by thedr on 1/9/2017.
 */

public class ItemTypeAdapter extends ArrayAdapter<ItemType> {

    private Context mContext;
    private ItemType[] mItemTypes;

    public ItemTypeAdapter(Context context, int resource, ItemType[] types) {
        super(context, resource, types);

        mContext = context;
        mItemTypes = types;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {   // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item

        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {   // This view starts when we click the spinner.
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(R.layout.support_simple_spinner_dropdown_item, parent, false);
        }
        ItemType it = mItemTypes[position];
        String name = mContext.getString(it.getItemTypeNameID());
        TextView itemTypeName = (TextView) row.findViewById(android.R.id.text1);
        itemTypeName.setText(name);
        return row;
    }
}
