package com.lasalle.second.part.ghostlocator.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.lasalle.second.part.ghostlocator.R;

import java.util.Arrays;
import java.util.List;

public class ProfileFragment extends Fragment implements FacebookCallback<LoginResult> {

    private static final List<String> FB_PERMISSION_REQUEST = Arrays.asList("public_profile", "email");

    private CallbackManager callbackManager;
    private View view;


    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        if(isUserIsLogged()) {
            writeNameInEditText();
        }
        else {
            setupLoginButton();
        }

        return view;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        // App code
        AccessToken accessToken = loginResult.getAccessToken();
        Log.d("Facebook", accessToken.toString());
        writeNameInEditText();
    }

    @Override
    public void onCancel() {
        // App code
    }

    @Override
    public void onError(FacebookException exception) {
        // App code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isUserIsLogged() {
        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        if((currentAccessToken != null ) && !currentAccessToken.isExpired()) {
            AccessToken.refreshCurrentAccessTokenAsync();
            return true;
        }

        return false;
    }

    private void writeNameInEditText() {
        TextView editText = (TextView) view.findViewById(R.id.fragment_profile_user_name);

        // set name
        Profile profile = Profile.getCurrentProfile();
        editText.setText("Hola " + profile.getName());

        editText.setVisibility(View.VISIBLE);
    }

    private void setupLoginButton() {
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.fragment_profile_facebook_login_button);
        loginButton.setReadPermissions(FB_PERMISSION_REQUEST);
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, this);
    }
}
