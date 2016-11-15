package com.santinocampos.android.count.Views;

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
    private ImageButton mAddItemButton;
    private ImageButton mIncreaseCashButton;
    private Callbacks mCallbacks;


    public interface Callbacks {
        void addItem();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet_layout, container, false);

        Accountant accountant = Accountant.get(getActivity());

        mWalletTextView = (TextView) view.findViewById(R.id.wallet_text_view);
        mWalletTextView.setText(String.valueOf(accountant.getTotalMoney()));

        mAddItemButton = (ImageButton) view.findViewById(R.id.btn_add_item);
        mAddItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.addItem();
            }
        });

        return view;
    }
}
