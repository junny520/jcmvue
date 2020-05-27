package com.haiyishuzi.haiyishuzijcm.ui.index;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.haiyishuzi.haiyishuzijcm.R;
import com.haiyishuzi.haiyishuzijcm.binding.FragmentDataBindingComponent;
import com.haiyishuzi.haiyishuzijcm.callBack.UserFragmentLabel;
import com.haiyishuzi.haiyishuzijcm.databinding.IndexFragmentBinding;
import com.haiyishuzi.haiyishuzijcm.di.Injectable;
import com.haiyishuzi.haiyishuzijcm.ui.anim.AnimFragment;
import com.haiyishuzi.haiyishuzijcm.ui.common.NavigationController;
import com.haiyishuzi.haiyishuzijcm.ui.forms.FormsFragment;
import com.haiyishuzi.haiyishuzijcm.ui.home.HomeFragment;
import com.haiyishuzi.haiyishuzijcm.ui.setting.SettingFragment;
import com.haiyishuzi.haiyishuzijcm.util.AutoClearedValue;

import javax.inject.Inject;

/**
 * Created by wpl on 2017/10/25.
 * index
 */

public class IndexFragment extends Fragment implements Injectable,UserFragmentLabel {
      //灰色以及相对应的RGB值
      private int mGrayColor;
      private int mGrayRed;
      private int mGrayGreen;
      private int mGrayBlue;
      //蓝色以及相对应的RGB值
      private int mBlueColor;
      private int mBlueRed;
      private int mBlueGreen;
      private int mBlueBlue;
      private ImageView[] mBorderimageViews;  //外部的边框
      private ImageView[] mContentImageViews; //内部的内容
      private ImageView[] mWhiteImageViews;  //发现上面的白色部分
      private TextView[] mTitleViews;
      private boolean onclick = true;

      @Inject
      ViewModelProvider.Factory viewModelFactory;
      @Inject
      NavigationController navigationController;
      @VisibleForTesting
      AutoClearedValue<IndexFragmentBinding> binding;
      androidx.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
      private IndexViewModel indexViewModel;

      public static IndexFragment create() {
            IndexFragment indexFragment = new IndexFragment();
            Bundle bundle = new Bundle();
            indexFragment.setArguments(bundle);
            return indexFragment;
      }

      @Nullable
      @Override
      public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            IndexFragmentBinding indexFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.index_fragment, container, false, dataBindingComponent);
            binding = new AutoClearedValue<>(this, indexFragmentBinding);
            return indexFragmentBinding.getRoot();
      }

      @Override
      public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            indexViewModel = ViewModelProviders.of(this, viewModelFactory).get(IndexViewModel.class);
            //跳转到用户信息界面
            //        binding.get().top.rightBtn.setOnClickListener((V)->navigationController.indexToUser());
            //跳转到个人设置界面
            //        binding.get().top.headBack.setOnClickListener((V)->navigationController.indexToSetting());
            initColor();
            initData();
            initEvent();

      }

      private void initColor() {
            mGrayColor = getResources().getColor(R.color.gray);
            mGrayRed = Color.red(mGrayColor);
            mGrayGreen = Color.green(mGrayColor);
            mGrayBlue = Color.blue(mGrayColor);
            mBlueColor = getResources().getColor(R.color.colorPrimary);
            mBlueRed = Color.red(mBlueColor);
            mBlueGreen = Color.green(mBlueColor);
            mBlueBlue = Color.blue(mBlueColor);
      }

      private void initData() {
            mBorderimageViews = new ImageView[]{binding.get().shouyeIv, binding.get().xiaquIv, binding.get().jixiaoIv, binding.get().shezhiIv};
            mContentImageViews = new ImageView[]{binding.get().shouyeIvTop, binding.get().xiaquIvTop, binding.get().jixiaoIvTop, binding.get().shezhiIvTop};
            mWhiteImageViews = new ImageView[]{binding.get().shouyeIvWhite, binding.get().xiaquIvWhite, binding.get().jixiaoIvTop, binding.get().shezhiIvTop};
            mTitleViews = new TextView[]{binding.get().shouyeTv, binding.get().xiaquTv, binding.get().jixiaoTv, binding.get().shezhiTv};
            ItemPagerAdapter adapter = new ItemPagerAdapter(getChildFragmentManager());
            binding.get().pagerView.setAdapter(adapter);
            setSelection(0);
      }

      private void initEvent() {
            binding.get().shouyeLl.setOnClickListener((v) -> setSelection(0));
            binding.get().xiaquLl.setOnClickListener((v) -> setSelection(1));
            binding.get().jixiaoLl.setOnClickListener((v) -> setSelection(2));
            binding.get().shezhiLl.setOnClickListener((v) -> setSelection(3));
            binding.get().pagerView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                  @Override
                  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        if (positionOffset > 0 && !onclick) {
                              if (positionOffset < 0.5) {
                                    //  滑动到一半前，上一页的边框保持蓝色不变，下一页的边框由灰色变为蓝色
                                    mBorderimageViews[position].setColorFilter(mBlueColor, PorterDuff.Mode.SRC_IN);
                                    mBorderimageViews[position + 1].setColorFilter(getGrayToGreen(positionOffset), PorterDuff.Mode.SRC_IN);
                                    //   上一页的内容保持由实心变为透明，下一页的内容保持透明
                                    mContentImageViews[position].setAlpha((1 - 2 * positionOffset));
                                    mContentImageViews[position + 1].setAlpha(0f);
                                    //文字颜色变化
                                    mTitleViews[position].setTextColor(mBlueColor);
                                    mTitleViews[position + 1].setTextColor(getGrayToGreen(positionOffset));

                              } else {
                                    //滑动到一半后，上一页的边框由lvse变为灰色，，下一页边框保持蓝色不变
                                    mBorderimageViews[position].setColorFilter(getGreenToGray(positionOffset), PorterDuff.Mode.SRC_IN);
                                    mBorderimageViews[position + 1].setColorFilter(mBlueColor, PorterDuff.Mode.SRC_IN);
                                    //上一页的内容保持透明，下一页的内容由透明变为实心蓝色
                                    mContentImageViews[position].setAlpha(0f);
                                    mContentImageViews[position + 1].setAlpha(2 * positionOffset - 1);
                                    mTitleViews[position].setTextColor(getGreenToGray(positionOffset));
                                    mTitleViews[position + 1].setTextColor(mBlueColor);
                                    if (position > 0.8) {
                                          mWhiteImageViews[position + 1].setVisibility(View.VISIBLE);
                                          mWhiteImageViews[position + 1].setAlpha(10 * positionOffset - 8);
                                    } else {
                                          mWhiteImageViews[position + 1].setVisibility(View.GONE);
                                    }
                              }
                        }
                  }

                  @Override
                  public void onPageSelected(int position) {
                        setSelection(position);
                  }

                  @Override
                  public void onPageScrollStateChanged(int state) {
                        if (state == 1) {
                              onclick = false;
                        } else if (state == 0) {
                              onclick = true;
                        }
                  }
            });
      }

      /**
       * 设置索引  当前导航页边框蓝色，内容实心蓝，其他页边框灰色，内容透明
       *
       * @param position 选中的一个
       */
      private void setSelection(int position) {
            for (int i = 0; i < mBorderimageViews.length; i++) {
                  if (i == position) {
                        mBorderimageViews[i].setColorFilter(mBlueColor, PorterDuff.Mode.SRC_IN);
                        mContentImageViews[i].setAlpha(1f);
                        mWhiteImageViews[i].setVisibility(View.VISIBLE);
                        mTitleViews[i].setTextColor(mBlueColor);
                  } else {
                        mBorderimageViews[i].setColorFilter(mGrayColor, PorterDuff.Mode.SRC_IN);
                        mContentImageViews[i].setAlpha(0f);
                        mWhiteImageViews[i].setVisibility(View.GONE);
                        mTitleViews[i].setTextColor(mGrayColor);
                  }
            }
            binding.get().pagerView.setCurrentItem(position, false);
      }

      /**
       * 偏移量在 0——0.5区间 ，左边一项颜色不变，右边一项颜色从灰色变为蓝色，根据两点式算出变化函数
       *
       * @param positionOffset 偏移
       * @return 获取偏移量
       */
      private int getGrayToGreen(float positionOffset) {
            int red = (int) (positionOffset * (mBlueRed - mGrayRed) * 2 + mGrayRed);
            int green = (int) (positionOffset * (mBlueGreen - mGrayGreen) * 2 + mGrayGreen);
            int blue = (int) ((positionOffset) * (mBlueBlue - mGrayBlue) * 2 + mGrayBlue);
            Log.d("why ", "#### " + red + "  " + green + "  " + blue);
            return Color.argb(255, red, green, blue);
      }

      /**
       * 偏移量在 0.5--1 区间，颜色从蓝色变成灰色，根据两点式算出变化函数
       *
       * @param positionOffset 偏移
       * @return 获取偏移量
       */
      private int getGreenToGray(float positionOffset) {
            int red = (int) (positionOffset * (mGrayRed - mBlueRed) * 2 + 2 * mBlueRed - mGrayRed);
            int green = (int) (positionOffset * (mGrayGreen - mBlueGreen) * 2 + 2 * mBlueGreen - mGrayGreen);
            int blue = (int) (positionOffset * (mGrayBlue - mBlueBlue) * 2 + 2 * mBlueBlue - mGrayBlue);
            Log.d("why ", "#### " + red + "  " + green + "  " + blue);
            return Color.argb(255, red, green, blue);
      }


      /**
       * viewpager适配器
       */
      class ItemPagerAdapter extends FragmentPagerAdapter {

            public ItemPagerAdapter(FragmentManager fm) {
                  super(fm);
            }

            @Override
            public int getCount() {
                  return 4;
            }

            /**
             * Return the Fragment associated with a specified position.
             *
             * @param position
             */
            @Override
            public Fragment getItem(int position) {
                  switch (position) {
                        case 0:
                              return HomeFragment.newInstance();
                        case 1:
                              return FormsFragment.newInstance();
                        case 2:
                              return AnimFragment.newInstance();
                        case 3:
                              return SettingFragment.newInstance();
                        default:
                              return HomeFragment.newInstance();
                  }
            }
      }
}
