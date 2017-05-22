package com.santinocampos.android.count.Models.Item;

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
        View view = convertView;
        if (view == null)  {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.support_simple_spinner_dropdown_item, parent, false);
        }
        ItemType itemType = mItemTypes[position];
        String typeName = mContext.getString(itemType.getItemTypeNameID());
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(typeName);
        return view;
    }
}
