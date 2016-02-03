package com.xmagicj.android.bizunittestdemo;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by
 * Mumu on 2016/2/3.
 */
public class LoginPresenter implements ILoginPresenter {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "xmj@xmj.com:xmj", "test@test.com:test"
    };

    ILoginView loginView;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void login(String email, String password) {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        loginView.setEmailError(null);
        loginView.setPasswordError(null);

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            loginView.setEmailError("This field is required");
            return;
        } else if (!isEmailValid(email)) {
            loginView.setEmailError("This email address is invalid");
            return;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            loginView.setPasswordError("This password is too short");
            return;
        }

        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        loginView.showLoading();
        mAuthTask = new UserLoginTask(email, password);
        mAuthTask.execute((Void) null);

    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            loginView.hideLoading();

            if (success) {
                loginView.loginSuccess();
            } else {
                loginView.loginFailed("Login failed");
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            loginView.hideLoading();
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 2;
    }
}
