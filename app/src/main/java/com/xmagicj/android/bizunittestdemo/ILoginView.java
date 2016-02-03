package com.xmagicj.android.bizunittestdemo;

/**
 * Created by
 * <p/>
 * Mumu on 2016/2/3.
 */
public interface ILoginView {
    void showLoading();

    void hideLoading();

    void setEmailError(String error);

    void setPasswordError(String error);

    void loginFailed(String error);

    void loginSuccess();
}
