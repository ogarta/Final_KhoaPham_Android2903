package com.svute.appsale.presentation.view.activity.onboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svute.appsale.R;
import com.svute.appsale.common.AppConstant;
import com.svute.appsale.data.local.AppCache;
import com.svute.appsale.presentation.adapter.OnboardDingPagerAdapter;
import com.svute.appsale.presentation.view.activity.sign_in.SignInActivity;

/**
 * Created by Ogata on 7/25/2022.
 */
public class OnboarDingActivity extends AppCompatActivity {

    TextView tvRequestLogin;
    LinearLayout btnGetStarted;
    ViewPager2 onboardDingViewPager;
    OnboardDingPagerAdapter onboardDingPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        initial();
        event();
        // Request Login Text
        setTextRequestLogin();
    }

    private void event() {
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateLoginScreen();
            }
        });
    }

    private void navigateLoginScreen() {
        AppCache.getInstance(OnboarDingActivity.this)
                .setValue(AppConstant.ONBOARD_DING_FIRST_TIME_DISPLAY_KEY, true)
                .commit();

        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.alpha_fade_in, R.anim.alpha_fade_out);
    }

    private void initial() {
        tvRequestLogin = findViewById(R.id.textview_request_login);
        btnGetStarted = findViewById(R.id.button_get_started);
        onboardDingViewPager = findViewById(R.id.view_pager_onboard_ding);

        onboardDingPagerAdapter = new OnboardDingPagerAdapter(this);
        onboardDingViewPager.setAdapter(onboardDingPagerAdapter);
    }


    private void setTextRequestLogin() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("Already Have An Account?");
        int start = builder.length();
        builder.append(" Log In");
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                navigateLoginScreen();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.primary));
            }
        }, start, builder.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvRequestLogin.setText(builder);
        tvRequestLogin.setHighlightColor(Color.TRANSPARENT);
        tvRequestLogin.setMovementMethod(LinkMovementMethod.getInstance());
    }
}