package com.santinocampos.android.count.ViewFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santinocampos.android.count.Models.Accountant;
import com.santinocampos.android.count.Models.Item;
import com.santinocampos.android.count.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by thedr on 11/1/2016.
 */
public class ItemListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mItemNameTextView;
        private TextView mItemPriceTextView;
        private TextView mItemCountTextView;

        public ItemHolder(View itemView) {
            super(itemView);
            mItemNameTextView = (TextView) itemView.findViewById(R.id.item_name_textView);
            mItemPriceTextView = (TextView) itemView.findViewById(R.id.item_price_textView);
            mItemCountTextView = (TextView) itemView.findViewById(R.id.item_count_textView);
        }

        public void bindItem(Item item, int count) {
            mItemNameTextView.setText(item.getName());
            mItemPriceTextView.setText(String.valueOf(item.getPrice()));
            mItemCountTextView.setText('(' + String.valueOf(count) + ')');
        }

        @Override
        public void onClick(View v) {
            Accountant accountant = Accountant.get(getActivity());
            System.out.println("TESTING");
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
        private Map<Item, Integer> mItemList;

        public ItemAdapter(Map<Item, Integer> itemList) {
            mItemList = itemList;
        }

        public ItemHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_item, parent, false);
            return new ItemHolder(view);
        }

        public void onBindViewHolder(ItemHolder holder, int position) {
            Item item = new ArrayList<>(mItemList.keySet()).get(position);
            int count = mItemList.get(item);
            holder.bindItem(item, count);
        }

        public int getItemCount() {
            return mItemList.size();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_list, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        update();

        return v;
    }

    public void update() {
        Accountant accountant = Accountant.get(getActivity());

        if (mAdapter == null) {
            mAdapter = new ItemAdapter(accountant.getItemList());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
}
