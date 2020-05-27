package com.haiyishuzi.haiyishuzijcm.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haiyishuzi.haiyishuzijcm.R;
import com.haiyishuzi.haiyishuzijcm.databinding.HomeItemBinding;
import com.haiyishuzi.haiyishuzijcm.ui.common.DataBoundListAdapter;
import com.haiyishuzi.haiyishuzijcm.util.Utils;
import com.haiyishuzi.haiyishuzijcm.vo.HomeMenu;

/**
 * Created by wpl on 2017/11/2.
 * 首页列表的adapter
 */

public class HomeGridAdapter extends DataBoundListAdapter<HomeMenu,HomeItemBinding> {

    private final androidx.databinding.DataBindingComponent dataBindingComponent;
    private final HomeItemClickCallback homeItemClickCallback;

    public HomeGridAdapter(DataBindingComponent dataBindingComponent, HomeItemClickCallback homeItemClickCallback){
        this.dataBindingComponent = dataBindingComponent;
        this.homeItemClickCallback = homeItemClickCallback;
    }

    @Override
    protected HomeItemBinding createBinding(ViewGroup parent) {
        HomeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.home_item,
                parent, false, dataBindingComponent);
        binding.getRoot().setOnClickListener(v->{
            HomeMenu menu = binding.getMenu();
            if (menu != null && homeItemClickCallback != null) {
                homeItemClickCallback.onClick(menu);
            }
        });
        binding.getRoot().setOnLongClickListener(v -> false);
        return binding;
    }

    @Override
    protected void bind(HomeItemBinding binding, HomeMenu item) {
        binding.setMenu(item);
    }

    @Override
    protected boolean areItemsTheSame(HomeMenu oldItem, HomeMenu newItem) {
        return Utils.instance().equals(oldItem.getClassName(), newItem.getClassName());
    }

    @Override
    protected boolean areContentsTheSame(HomeMenu oldItem, HomeMenu newItem) {
        return Utils.instance().equals(oldItem, newItem);
    }

    @FunctionalInterface
    public interface HomeItemClickCallback{
        void onClick(HomeMenu menu);
    }

    public interface HomeItemLongClickCallback{
        boolean onItemLongClick(RecyclerView.ViewHolder vh);
    }
}

