package com.haiyishuzi.haiyishuzijcm.ui.home;

import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.haiyishuzi.haiyishuzijcm.R;
import com.haiyishuzi.haiyishuzijcm.binding.FragmentDataBindingComponent;
import com.haiyishuzi.haiyishuzijcm.callBack.UserFragmentLabel;
import com.haiyishuzi.haiyishuzijcm.databinding.HomeFragmentBinding;
import com.haiyishuzi.haiyishuzijcm.di.Injectable;
import com.haiyishuzi.haiyishuzijcm.ui.DividerGridItemDecoration;
import com.haiyishuzi.haiyishuzijcm.ui.common.NavigationController;
import com.haiyishuzi.haiyishuzijcm.util.AutoClearedValue;

import javax.inject.Inject;

public class HomeFragment extends Fragment implements Injectable,UserFragmentLabel {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    NavigationController navigationController;
    @VisibleForTesting
    AutoClearedValue<HomeFragmentBinding> binding;
    androidx.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    private HomeViewModel mViewModel;
    AutoClearedValue<HomeGridAdapter> adapter;
    private ItemTouchHelper mItemTouchHelper;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        HomeFragmentBinding homeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, homeFragmentBinding);
        return homeFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this,viewModelFactory).get(HomeViewModel.class);
        HomeGridAdapter homeGridAdapter = new HomeGridAdapter(dataBindingComponent,
                menu -> navigationController.navigateToMenu(menu));
        initRecyclerView();
        binding.get().recyclerView.setAdapter(homeGridAdapter);
        adapter = new AutoClearedValue<>(this, homeGridAdapter);
        mViewModel.load();
    }

    private void initRecyclerView() {
        binding.get().recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(binding.get().recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {

            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                mItemTouchHelper.startDrag(vh);
                //获取系统震动服务
                Vibrator vib = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);//震动70毫秒
                vib.vibrate(70);
            }
        });
        binding.get().recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.get().recyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()));
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            /**
             * 是否处理滑动事件 以及拖拽和滑动的方向 如果是列表类型的RecyclerView的只存在UP和DOWN，如果是网格类RecyclerView则还应该多有LEFT和RIGHT
             * @param recyclerView
             * @param viewHolder
             * @return
             */

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = 0;
                    //                    final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //得到当拖拽的viewHolder的Position
                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
                int toPosition = target.getAdapterPosition();
                mViewModel.swip(fromPosition, toPosition);
                adapter.get().notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //                int position = viewHolder.getAdapterPosition();
                //                myAdapter.notifyItemRemoved(position);
                //                datas.remove(position);
            }

            /**
             * 重写拖拽可用
             * @return
             */

            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }

            /**
             * 长按选中Item的时候开始调用
             *
             * @param viewHolder
             * @param actionState
             */

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                //                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                //                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                //                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            /**
             * 手指松开的时候还原
             * @param recyclerView
             * @param viewHolder
             */

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                //                viewHolder.itemView.setBackgroundColor(0);
            }
        });

        mItemTouchHelper.attachToRecyclerView(binding.get().recyclerView);

        mViewModel.getResults().observe(this, result -> {
            adapter.get().replace(result == null ? null : result.getData());
            binding.get().executePendingBindings();
        });
    }
}
