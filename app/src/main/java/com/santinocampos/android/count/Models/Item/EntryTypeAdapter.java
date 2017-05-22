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

public class EntryTypeAdapter extends ArrayAdapter<EntryType> {

    private Context mContext;
    private EntryType[] mEntryTypes;

    public EntryTypeAdapter(Context context, int resource, EntryType[] types) {
        super(context, resource, types);

        mContext = context;
        mEntryTypes = types;
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
        EntryType mEntryType = mEntryTypes[position];
        String typeName = mContext.getString(mEntryType.getItemTypeNameID());
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(typeName);
        return view;
    }
}
