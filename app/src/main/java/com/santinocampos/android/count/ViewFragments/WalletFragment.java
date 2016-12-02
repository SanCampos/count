package com.santinocampos.android.count.ViewFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.santinocampos.android.count.Models.Accountant;
import com.santinocampos.android.count.R;

/**
 * Created by thedr on 11/1/2016.
 */
public class WalletFragment extends Fragment {

    private TextView mWalletTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet_layout, container, false);

        mWalletTextView = (TextView) view.findViewById(R.id.wallet_text_view);
        update();

        return view;
    }

    public void update() {
        mWalletTextView.setText(String.valueOf(Math.round((Accountant.get(getActivity()).getTotalMoney()))));
    }
}
