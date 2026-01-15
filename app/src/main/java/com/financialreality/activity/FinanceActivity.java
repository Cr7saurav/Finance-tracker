package com.financialreality.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.financialreality.R;

public class FinanceActivity extends AppCompatActivity {

    private EditText etPlannedBudget, etActualSpent;
    private EditText etSavingsTarget, etCurrentSavings;
    private SeekBar sbFinanceStress;
    private Button btnAnalyze;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_finance);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.finance_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();

        btnAnalyze.setOnClickListener(v -> analyzeFinance());
    }

    private void initViews() {
        etPlannedBudget = findViewById(R.id.et_planned_budget);
        etActualSpent = findViewById(R.id.et_actual_spent);
        etSavingsTarget = findViewById(R.id.et_savings_target);
        etCurrentSavings = findViewById(R.id.et_current_savings);
        sbFinanceStress = findViewById(R.id.sb_finance_stress);
        btnAnalyze = findViewById(R.id.btn_analyze_finance);
        tvResult = findViewById(R.id.tv_finance_result);
    }

    private void analyzeFinance() {
        String plannedStr = etPlannedBudget.getText().toString();
        String actualStr = etActualSpent.getText().toString();
        String targetStr = etSavingsTarget.getText().toString();
        String currentStr = etCurrentSavings.getText().toString();

        if (plannedStr.isEmpty() || actualStr.isEmpty() || targetStr.isEmpty() || currentStr.isEmpty()) {
            tvResult.setText("Please fill in all fields to see the reality check.");
            return;
        }

        double planned = Double.parseDouble(plannedStr);
        double actual = Double.parseDouble(actualStr);
        double target = Double.parseDouble(targetStr);
        double current = Double.parseDouble(currentStr);
        int stress = sbFinanceStress.getProgress();

        StringBuilder feedback = new StringBuilder();
        feedback.append("Analysis:\n\n");

        if (actual > planned) {
            double over = actual - planned;
            feedback.append("⚠️ You are $").append(String.format("%.2f", over))
                    .append(" over budget. Consider tracking individual transactions.\n\n");
        } else {
            feedback.append("✅ Great job! You are under budget by $")
                    .append(String.format("%.2f", planned - actual)).append(".\n\n");
        }

        double progress = (current / target) * 100;
        feedback.append("📈 Savings Progress: ").append(String.format("%.1f", progress)).append("%\n");
        if (progress < 20 && stress > 7) {
            feedback.append("💡 High stress detected with low savings. prioritize an emergency fund first.\n");
        } else if (progress >= 100) {
            feedback.append("🎉 Savings goal reached! Time to look into investment options.\n");
        }

        tvResult.setText(feedback.toString());
    }
}
