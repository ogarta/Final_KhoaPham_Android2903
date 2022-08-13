package com.svute.appsale.presentation.view.activity.sign_in;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.svute.appsale.R;
import com.svute.appsale.common.AppConstant;
import com.svute.appsale.common.SpannedCommon;
import com.svute.appsale.common.StringCommon;
import com.svute.appsale.data.local.AppCache;
import com.svute.appsale.data.model.User;
import com.svute.appsale.data.remote.dto.AppResource;
import com.svute.appsale.presentation.view.activity.home.HomeActivity;
import com.svute.appsale.presentation.view.activity.sign_up.SignUpActivity;

/**
 * Created by Ogata on 7/25/2022.
 */
public class SignInActivity extends AppCompatActivity {

    private SignInViewModel viewModel;
    private LinearLayout layoutLoading, btnSignIn;
    private TextInputEditText txtInputEditEmail, txtInputEditPassword;
    private TextView tvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_sign_in);

        initial();
        observerData();
        event();
    }

    private void observerData() {
        viewModel.getResourceUser().observe(this, new Observer<AppResource<User>>() {
            @Override
            public void onChanged(AppResource<User> userAppResource) {
                switch (userAppResource.status) {
                    case SUCCESS:
                        Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        layoutLoading.setVisibility(View.GONE);
                        AppCache.getInstance(SignInActivity.this)
                                .setValue(AppConstant.TOKEN_KEY, userAppResource.data.getToken())
                                .commit();
//                        String infoUser = userAppResource.data.getName() +"#"+ userAppResource.data.getEmail() +"#"+ userAppResource.data.getPhone();
//                        AppCache.getInstance(SignInActivity.this)
//                                .setValue(AppConstant.NAME_KEY, infoUser)
//                                .commit();
                        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.alpha_fade_in, R.anim.alpha_fade_out);
                        break;
                    case LOADING:
                        layoutLoading.setVisibility(View.VISIBLE);
                        break;
                    case ERROR:
                        Toast.makeText(SignInActivity.this, userAppResource.message, Toast.LENGTH_SHORT).show();
                        layoutLoading.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    private void event() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtInputEditEmail.getText().toString();
                String password = txtInputEditPassword.getText().toString();

                if (StringCommon.isValidEmail(email) && !password.isEmpty()) {
                    viewModel.signIn(email, password);
                }
                if (password.isEmpty() || email.isEmpty()){
                    Toast.makeText(SignInActivity.this,"Bạn chưa nhập tài khoản hoặc mật khẩu",Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set Click Register
        setTextRegister();
    }

    private void initial() {
        layoutLoading = findViewById(R.id.layout_loading);
        txtInputEditEmail = findViewById(R.id.textEditEmail);
        txtInputEditPassword = findViewById(R.id.textEditPassword);
        btnSignIn = findViewById(R.id.sign_in);
        tvRegister = findViewById(R.id.text_view_register);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SignInViewModel(SignInActivity.this);
            }
        }).get(SignInViewModel.class);
    }

    private void setTextRegister() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("Don't have an account?");
        builder.append(SpannedCommon.setClickColorLink("Register", this, () -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            overridePendingTransition(R.anim.alpha_fade_in, R.anim.alpha_fade_out);
        }));
        tvRegister.setText(builder);
        tvRegister.setHighlightColor(Color.TRANSPARENT);
        tvRegister.setMovementMethod(LinkMovementMethod.getInstance());
    }
}