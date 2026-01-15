package com.financialreality.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.financialreality.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordDialog extends Dialog {
    
    private Context context;
    private EditText etEmail;
    private Button btnSubmit, btnCancel;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    
    public ForgotPasswordDialog(Context context) {
        super(context);
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_forgot_password);
        
        etEmail = findViewById(R.id.etEmail);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        progressBar = findViewById(R.id.progressBar);
        
        btnCancel.setOnClickListener(v -> dismiss());
        
        btnSubmit.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            if (isValidEmail(email)) {
                sendResetPasswordEmail(email);
            }
        });
    }
    
    private boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return false;
        }
        
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email");
            etEmail.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void sendResetPasswordEmail(String email) {
        showProgress(true);
        
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    showProgress(false);
                    if (task.isSuccessful()) {
                        Toast.makeText(context, 
                                "Password reset email sent. Check your inbox.", 
                                Toast.LENGTH_LONG).show();
                        dismiss();
                    } else {
                        String errorMessage = task.getException() != null ? 
                                task.getException().getMessage() : "Failed to send reset email";
                        etEmail.setError(errorMessage);
                    }
                });
    }
    
    private void showProgress(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            btnSubmit.setEnabled(false);
            btnCancel.setEnabled(false);
        } else {
            progressBar.setVisibility(View.GONE);
            btnSubmit.setEnabled(true);
            btnCancel.setEnabled(true);
        }
    }
}
