package com.slidemenubasestructuredemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slidemenubasestructuredemo.R;
import com.slidemenubasestructuredemo.activities.BaseActivity;

public class NavigationDrawerListAdapter extends RecyclerView.Adapter<NavigationDrawerListAdapter.ViewHolder> {

    public Context mContext;
    public String[] mDrawerItemArray;

    public NavigationDrawerListAdapter(Context context, String[] drawerItemArray) {
        this.mContext = context;
        this.mDrawerItemArray = drawerItemArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_drawer_recycle_menu, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.getTextViewNavigationItem().setText(mDrawerItemArray[position]);

        viewHolder.getImageViewNavigationItem().setImageResource(R.drawable.ic_launcher);
        viewHolder.getLinearLayoutMain().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity)mContext).onNavigationDrawerItemClick(viewHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDrawerItemArray.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout linearLayoutMain;
        private final ImageView imageViewNavigationItem;
        private final TextView textViewNavigationItem;


        public ViewHolder(View v) {
            super(v);
            linearLayoutMain = (LinearLayout) v.findViewById(R.id.linear_layout_main);
            imageViewNavigationItem = (ImageView) v.findViewById(R.id.image_view_navigation_item);
            textViewNavigationItem = (TextView) v.findViewById(R.id.text_view_navigation_item);

        }

        public LinearLayout getLinearLayoutMain() {
            return linearLayoutMain;
        }

        public ImageView getImageViewNavigationItem() {
            return imageViewNavigationItem;
        }

        public TextView getTextViewNavigationItem() {
            return textViewNavigationItem;
        }
    }

}
