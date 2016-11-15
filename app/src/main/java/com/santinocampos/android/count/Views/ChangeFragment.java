package com.santinocampos.android.count.Views;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santinocampos.android.count.Models.Accountant;
import com.santinocampos.android.count.R;

/**
 * Created by thedr on 11/1/2016.
 */
public class ChangeFragment extends Fragment {

    private TextView mTextView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_change, container, false);

        mTextView = (TextView) v.findViewById(R.id.change_text_view);
        setChange();

        return v;
    }

    public void setChange() {
        mTextView.setText(String.valueOf(Math.round(Accountant.get(getActivity()).getChange())));
    }

}
