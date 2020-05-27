package com.haiyishuzi.haiyishuzijcm.util;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.request.RequestOptions;


import java.io.File;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class Utils {


      private static class Singleton {
            private static final Utils INSTANCE = new Utils();
            private static final RequestOptions OPTIONS = new RequestOptions();
      }

      private Utils() {

      }

      public static Utils instance() {
            return Singleton.INSTANCE;
      }

      public static RequestOptions options() {
            return Singleton.OPTIONS;
      }

      /**
       * 隐藏或者显示软键盘
       *
       * @param activity
       * @param view     需要光标的view
       * @param show     显示或者隐藏
       */
      public void dismissOrShowKeyboard(Activity activity, View view, Boolean show) {
            if (activity != null) {
                  InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                  if (show) {
                        imm.showSoftInput(view, 0);
                  } else {
                        IBinder windowToken = view.getWindowToken();
                        imm.hideSoftInputFromWindow(windowToken, 0);
                  }
            }
      }

      /**
       * 获取焦点并弹出软键盘
       *
       * @param activity
       * @param view     获取焦点的view
       */
      public void viewHasFocus(Activity activity, View view) {
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
            dismissOrShowKeyboard(activity, view, true);
      }

      /**
       * 加密算法
       *
       * @param rawPassword
       * @return
       */



      /**
       * 获取到照片集合中的本地照片并转成MultipartBody.Part便于网络传输
       * @param files 照片集合
       * @return
       */
      public List<MultipartBody.Part> filesToMultipartBodyParts(ArrayList<String> files) {
            List<MultipartBody.Part> parts = new ArrayList<>(files.size());
            for (String ads : files) {
                  File file = new File(ads);
                  if (file.exists()) {
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
                        MultipartBody.Part part = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
                        parts.add(part);
                  }
            }
            return parts;
      }

      public long getTimeZero(long time) {
            return time / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
      }

      public boolean hasSdcard() {
            String state = Environment.getExternalStorageState();
            return state.equals(Environment.MEDIA_MOUNTED);
      }

      /**
       * Date格式化
       *
       * @param dateDate
       * @return
       */
      public String dateToStr(Date dateDate, String format) {
            if (dateDate == null || TextUtils.isEmpty(format)) {
                  return "";
            }
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.format(dateDate);
      }

      /**
       * ocr识别返回出生日期转date
       *
       * @param strDate
       * @return
       */
      public Date strToDate(String strDate, String format) {
            if (strDate == null || TextUtils.isEmpty(format)) {
                  return new Date();
            }
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            ParsePosition pos = new ParsePosition(0);
            return formatter.parse(strDate, pos);
      }

      public int px2dp(Context context, float pxValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
      }

      public int dp2px(Context context, float dipValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale + 0.5f);
      }

      public boolean equals(Object o1, Object o2) {
            if (o1 == null) {
                  return o2 == null;
            }
            if (o2 == null) {
                  return false;
            }
            return o1.equals(o2);
      }

}
