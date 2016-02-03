package com.xmagicj.android.bizunittestdemo;

import android.test.InstrumentationTestCase;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 * 利用MVP优势直接测试后台代码 无需启动界面
 * Created by
 * mumu on 15/2/6.
 */
public class LoginTest extends InstrumentationTestCase implements ILoginView {

    static String TAG = LoginTest.class.getSimpleName();

    private String mEmail;
    private String mPassword;
    private ILoginPresenter mLoginPresenter;
    CountDownLatch signal;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mEmail = "xmj@xmj.com";
        mPassword = "xmj";

        mLoginPresenter = new LoginPresenter(this);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testLogin() throws Throwable {
        signal = new CountDownLatch(1);
        mLoginPresenter.login(mEmail, mPassword);
        signal.await();
    }

    @Override
    public void showLoading() {
        Log.e(TAG, "showLoading");
    }

    @Override
    public void hideLoading() {
        Log.e(TAG, "hideLoading");

    }

    @Override
    public void setEmailError(String error) {
        Log.e(TAG, "setEmailError " + error);

    }

    @Override
    public void setPasswordError(String error) {
        Log.e(TAG, "setPasswordError " + error);

    }

    @Override
    public void loginFailed(String error) {
        Log.e(TAG, "loginFailed " + error);
        signal.countDown();
    }

    @Override
    public void loginSuccess() {
        Log.e(TAG, "loginSuccess ");
        signal.countDown();
    }
}
