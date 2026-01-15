package com.financialreality.utils;

import android.text.TextUtils;
import android.util.Patterns;
import java.util.regex.Pattern;

public class ValidationUtils {
    
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && 
               Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    
    public static boolean isValidPassword(String password) {
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            return false;
        }
        
        // Check for at least one uppercase, one lowercase, one digit, one special char
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$");
        return pattern.matcher(password).matches();
    }
    
    public static boolean isValidName(String name) {
        return !TextUtils.isEmpty(name) && name.length() >= 2;
    }
    
    public static boolean isSamePassword(String password, String confirmPassword) {
        return !TextUtils.isEmpty(password) && 
               !TextUtils.isEmpty(confirmPassword) && 
               password.equals(confirmPassword);
    }
}
