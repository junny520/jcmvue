package com.haiyishuzi.haiyishuzijcm.ui.common;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.haiyishuzi.haiyishuzijcm.MainActivity;
import com.haiyishuzi.haiyishuzijcm.R;
import com.haiyishuzi.haiyishuzijcm.callBack.PermissionsCallback;
import com.haiyishuzi.haiyishuzijcm.ui.index.IndexFragment;
import com.haiyishuzi.haiyishuzijcm.ui.load.LoadFragment;
import com.haiyishuzi.haiyishuzijcm.ui.login.LoginFragment;
import com.haiyishuzi.haiyishuzijcm.vo.HomeMenu;
import com.haiyishuzi.haiyishuzijcm.vo.Login;

import javax.inject.Inject;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * A utility class that handles navigation in {@link MainActivity}.
 */
public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;
    private final MainActivity mainActivity;
    private final View rootView;

    @Inject
    public NavigationController(MainActivity mainActivity) {
        this.containerId = R.id.container;
        this.mainActivity = mainActivity;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
        rootView = mainActivity.findViewById(R.id.container);
    }

    public void navigateToLoad() {
        LoadFragment loadFragment = LoadFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(containerId, loadFragment)
                .commitAllowingStateLoss();
    }

    public void navigateToLogin(Login login) {
        LoginFragment loginFragment = LoginFragment.newInstance(login);
        fragmentManager.beginTransaction()
                .replace(containerId, loginFragment)
                .commitAllowingStateLoss();
    }

    public void navigateToIndex() {
        PermissionsCallback callback = () -> {
            IndexFragment indexFragment = IndexFragment.create();
            fragmentManager.beginTransaction()
                    .replace(containerId, indexFragment)
                    .commitAllowingStateLoss();
        };
        mainActivity.populateAutoComplete(new String[]{WRITE_EXTERNAL_STORAGE}, callback);
    }




    public void navigateToMenu(@NonNull HomeMenu menu) {
        PermissionsCallback callback = () -> {
            try {
                Class<?> cls = Class.forName(menu.getClassName());
                Object obj = cls.newInstance();
                if (obj instanceof Fragment) {
                    Fragment fragment = (Fragment) obj;
                    if (menu.getExtra() != null)
                        fragment.setArguments(menu.getExtra());
                    fragmentManager.beginTransaction()
                            .replace(containerId, fragment)
                            .addToBackStack(null)
                            .commitAllowingStateLoss();
                } else {
                    Intent intent = new Intent(mainActivity.getBaseContext(), cls);
                    if (menu.getExtra() != null)
                        intent.putExtras(menu.getExtra());
                    mainActivity.startActivity(intent);
                }
            } catch (Exception e) {
                Snackbar.make(rootView, "该模块正在建设中...", Snackbar.LENGTH_SHORT).show();
            }
        };
        mainActivity.populateAutoComplete(menu.permission, callback);

    }
}
