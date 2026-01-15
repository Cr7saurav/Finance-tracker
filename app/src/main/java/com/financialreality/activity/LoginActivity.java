package com.financialreality.activity;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.financialreality.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    
    private EditText etEmail, etPassword;
    private Button btnLogin, btnGoogleLogin;
    private TextView tvRegister, tvForgotPassword, tvAppName;
    private ImageButton btnTogglePassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private boolean isPasswordVisible = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        // Safely initialize Firebase Auth to prevent crashes if google-services.json is missing
        try {
            mAuth = FirebaseAuth.getInstance();
        } catch (Exception e) {
            Toast.makeText(this, "Firebase not configured. Using Demo Mode.", Toast.LENGTH_LONG).show();
        }
        
        initViews();
        setupGradientText();
        setupListeners();
    }
    
    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvAppName = findViewById(R.id.tvAppName);
        btnTogglePassword = findViewById(R.id.btnTogglePassword);
        progressBar = findViewById(R.id.progressBar);
    }
    
    private void setupGradientText() {
        Shader textShader = new LinearGradient(
                0, 0, tvAppName.getPaint().measureText(tvAppName.getText().toString()),
                tvAppName.getTextSize(),
                new int[]{
                        ContextCompat.getColor(this, R.color.gradient_start),
                        ContextCompat.getColor(this, R.color.gradient_mid),
                        ContextCompat.getColor(this, R.color.gradient_end)
                },
                new float[]{0, 0.5f, 1},
                Shader.TileMode.CLAMP
        );
        tvAppName.getPaint().setShader(textShader);
    }
    
    private void setupListeners() {
        btnTogglePassword.setOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible) {
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                btnTogglePassword.setImageResource(android.R.drawable.ic_menu_view);
            } else {
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                btnTogglePassword.setImageResource(android.R.drawable.ic_menu_view);
            }
            etPassword.setSelection(etPassword.getText().length());
        });
        
        btnLogin.setOnClickListener(v -> attemptLogin());
        
        btnGoogleLogin.setOnClickListener(v -> signInWithGoogle());
        
        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
        
        tvForgotPassword.setOnClickListener(v -> {
            Toast.makeText(this, "Reset link will be sent to your email.", Toast.LENGTH_SHORT).show();
        });
    }
    
    private void attemptLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        
        if (!validateInputs(email, password)) {
            return;
        }

        // Demo Mode bypass if Firebase is missing
        if (mAuth == null) {
            if (email.equals("admin@admin.com") && password.equals("Admin@123")) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finishAffinity();
            } else {
                showErrorSnackbar("Demo Login: use admin@admin.com / Admin@123");
            }
            return;
        }
        
        showProgress(true);
        
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    showProgress(false);
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finishAffinity();
                        }
                    } else {
                        String errorMessage = task.getException() != null ? 
                                task.getException().getMessage() : "Login failed";
                        showErrorSnackbar(errorMessage);
                    }
                });
    }
    
    private boolean validateInputs(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email address");
            etEmail.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }
    
    private void signInWithGoogle() {
        Toast.makeText(this, "Google Sign-in clicked", Toast.LENGTH_SHORT).show();
    }
    
    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        btnLogin.setEnabled(!show);
    }
    
    private void showErrorSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), 
                message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.financial_red));
        snackbar.show();
    }
}
