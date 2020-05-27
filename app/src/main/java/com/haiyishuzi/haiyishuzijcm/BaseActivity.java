package com.haiyishuzi.haiyishuzijcm;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.haiyishuzi.haiyishuzijcm.callBack.PermissionsCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zp on 2018/4/19.
 */

public class BaseActivity extends AppCompatActivity {
      private static final int REQUEST_PERMISSION = 0;
      private static final int REQUEST_START_APPLICATION_DETAILS = 1;
      public String[] permissions;
      public PermissionsCallback permissionsCallback;

      public void populateAutoComplete(String[] permissions, @NonNull PermissionsCallback permissionsCallback) {
            this.permissions = permissions;
            this.permissionsCallback = permissionsCallback;
            if (null == permissions || permissions.length == 0 || mayRequestPession(permissions)) {
                  permissionsCallback.callback();
            }
      }

      public boolean mayRequestPession(@NonNull String[] permissions) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                  return true;
            }
            List<String> permissionList = new ArrayList<>();
            for (String perssion : permissions) {
                  if (checkSelfPermission(perssion) != PackageManager.PERMISSION_GRANTED) {
                        permissionList.add(perssion);
                  }
            }
            if (permissionList.size() == 0) {
                  return true;
            }
            requestPermissions((String[]) permissionList.toArray(new String[0]), REQUEST_PERMISSION);
            return false;
      }

      /**
       * Callback received when a permissions request has been completed.
       */
      @RequiresApi(api = Build.VERSION_CODES.M)
      @Override
      public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults) {
            if (requestCode == REQUEST_PERMISSION) {
                  final List<String> permissionList = new ArrayList<>();
                  for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                              permissionList.add(permissions[i]);
                        }
                  }
                  List<String> rationaleList = new ArrayList<>();
                  for (String permission : permissionList) {
                        if (shouldShowRequestPermissionRationale(permission)) {//允许、禁止后不再询问为true
                              rationaleList.add(permission);
                        }
                  }
                  if (permissionList.size() == 0) {
                        permissionsCallback.callback();
                  } else {
                        if (rationaleList.size() == 0) {
                              Snackbar.make(findViewById(R.id.container), R.string.permission_requisite, Snackbar.LENGTH_INDEFINITE)
                                        .setAction(android.R.string.ok, v -> {
                                              //跳转到设置页面
                                              Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                              Uri uri = Uri.fromParts("package", getPackageName(), null);
                                              intent.setData(uri);
                                              startActivityForResult(intent, REQUEST_START_APPLICATION_DETAILS);
                                        }).show();
                        } else {
                              Snackbar.make(findViewById(R.id.container), R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                                        .setAction(android.R.string.ok, v -> requestPermissions((String[]) permissionList.toArray(new String[0]), REQUEST_PERMISSION)).show();
                        }
                  }
            }
      }

      /**
       * Dispatch incoming result to the correct fragment.
       *
       * @param requestCode
       * @param resultCode
       * @param data
       */
      @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch (requestCode) {
                  case REQUEST_START_APPLICATION_DETAILS:
                        if (mayRequestPession(permissions)) {
                              permissionsCallback.callback();
                        }
                        break;
                  default:
                        super.onActivityResult(requestCode,resultCode,data);
//                        getSupportFragmentManager().findFragmentById(R.id.container).onActivityResult(requestCode, resultCode, data);
                        break;

            }
      }
}
