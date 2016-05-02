package com.wenming.weiswift;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.wenming.weiswift.common.login.AccessTokenKeeper;
import com.wenming.weiswift.fragment.discovery.DiscoverFragment;
import com.wenming.weiswift.fragment.home.HomeFragment;
import com.wenming.weiswift.fragment.message.MessageFragment;
import com.wenming.weiswift.fragment.post.PostActivity;
import com.wenming.weiswift.fragment.profile.ProfileFragment;


public class MainActivity extends FragmentActivity {

    private static final int HOME_FRAGMENT = 0X001;
    private static final int MESSAGE_FRAGMENT = 0X002;
    private static final int DISCOVERY_FRAGMENT = 0X004;
    private static final int PROFILE_FRAGMENT = 0X005;
    private Context mContext;
    private HomeFragment mHomeFragment;
    private MessageFragment mMessageFragment;
    private DiscoverFragment mDiscoverFragment;
    private ProfileFragment mProfileFragment;

    private FragmentManager mFragmentManager;
    private Oauth2AccessToken mAccessToken;
    private ImageView mHomeTab, mMessageTab, mDiscoeryTab, mProfile;
    private FrameLayout mPostTab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.mainactivity_layout);
        mHomeTab = (ImageView) findViewById(R.id.tv_home);
        mMessageTab = (ImageView) findViewById(R.id.tv_message);
        mDiscoeryTab = (ImageView) findViewById(R.id.tv_discovery);
        mProfile = (ImageView) findViewById(R.id.tv_profile);
        mPostTab = (FrameLayout) findViewById(R.id.fl_post);
        mContext = this;
        setSessionValid();
        mFragmentManager = getSupportFragmentManager();
        setTabFragment(HOME_FRAGMENT);
        setUpListener();
    }

    private void setUpListener() {
        mHomeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabFragment(HOME_FRAGMENT);
            }
        });
        mMessageTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabFragment(MESSAGE_FRAGMENT);
            }
        });
        mPostTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });
        mDiscoeryTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabFragment(DISCOVERY_FRAGMENT);

            }
        });
        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabFragment(PROFILE_FRAGMENT);
            }
        });
    }

    private void setSessionValid() {
        mAccessToken = AccessTokenKeeper.readAccessToken(mContext);
        if (mAccessToken.isSessionValid()) {
            NewFeature.LOGIN_STATUS = true;
        } else {
            NewFeature.LOGIN_STATUS = false;
        }
    }

    private void setTabFragment(int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideAllFragments(transaction);

        switch (index) {
            case HOME_FRAGMENT:
                mHomeTab.setSelected(true);
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.contentLayout, mHomeFragment);
                } else {
                    transaction.show(mHomeFragment);
                }
                break;
            case MESSAGE_FRAGMENT:
                mMessageTab.setSelected(true);
                if (mMessageFragment == null) {
                    mMessageFragment = new MessageFragment();
                    transaction.add(R.id.contentLayout, mMessageFragment);
                } else {
                    transaction.show(mMessageFragment);
                }
                break;

            case DISCOVERY_FRAGMENT:
                mDiscoeryTab.setSelected(true);
                if (mDiscoverFragment == null) {
                    mDiscoverFragment = new DiscoverFragment();
                    transaction.add(R.id.contentLayout, mDiscoverFragment);
                } else {
                    transaction.show(mDiscoverFragment);
                }
                break;
            case PROFILE_FRAGMENT:
                mProfile.setSelected(true);
                if (mProfileFragment == null) {
                    mProfileFragment = new ProfileFragment();
                    transaction.add(R.id.contentLayout, mProfileFragment);
                } else {
                    transaction.show(mProfileFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideAllFragments(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mMessageFragment != null) {
            transaction.hide(mMessageFragment);
        }

        if (mDiscoverFragment != null) {
            transaction.hide(mDiscoverFragment);
        }
        if (mProfileFragment != null) {
            transaction.hide(mProfileFragment);
        }
        mHomeTab.setSelected(false);
        mMessageTab.setSelected(false);
        mDiscoeryTab.setSelected(false);
        mProfile.setSelected(false);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mHomeFragment != null) {
            mHomeFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
