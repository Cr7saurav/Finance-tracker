package com.financialreality.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.financialreality.R;
import com.financialreality.model.LifeArea;
import com.financialreality.model.RealityCheck;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private SeekBar sbHealthReality;
    private SeekBar sbFinanceReality;
    private SeekBar sbCareerReality;
    private SeekBar sbRelReality;
    private SeekBar sbMood, sbEnergy;
    private Button btnSave, btnGoToFinance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();

        btnSave.setOnClickListener(v -> saveRealityCheck());
        btnGoToFinance.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, FinanceActivity.class));
        });
    }

    private void initViews() {
        sbHealthReality = findViewById(R.id.sb_health_reality);
        sbFinanceReality = findViewById(R.id.sb_finance_reality);
        sbCareerReality = findViewById(R.id.sb_career_reality);
        sbRelReality = findViewById(R.id.sb_relationships_reality);
        sbMood = findViewById(R.id.sb_mood);
        sbEnergy = findViewById(R.id.sb_energy);
        btnSave = findViewById(R.id.btn_save);
        btnGoToFinance = findViewById(R.id.btn_go_to_finance);
    }

    private void saveRealityCheck() {
        Map<LifeArea, Integer> realityScores = new HashMap<>();
        realityScores.put(LifeArea.HEALTH, sbHealthReality.getProgress());
        realityScores.put(LifeArea.FINANCE, sbFinanceReality.getProgress());
        realityScores.put(LifeArea.CAREER, sbCareerReality.getProgress());
        realityScores.put(LifeArea.RELATIONSHIPS, sbRelReality.getProgress());

        // For simplicity in the refined UI, we removed goal seekbars from the main dashboard
        // and focus on current reality tracking. Using a default goal of 8 for analysis.
        Map<LifeArea, Integer> goalScores = new HashMap<>();
        goalScores.put(LifeArea.HEALTH, 8);
        goalScores.put(LifeArea.FINANCE, 8);
        goalScores.put(LifeArea.CAREER, 8);
        goalScores.put(LifeArea.RELATIONSHIPS, 8);

        RealityCheck check = new RealityCheck(
                new Date(),
                realityScores,
                goalScores,
                sbMood.getProgress(),
                sbEnergy.getProgress()
        );

        String message = "Reality Check Saved!\nMood: " + check.getMood() + "/10";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        
        checkCorrelations(check);
    }

    private void checkCorrelations(RealityCheck check) {
        for (LifeArea area : LifeArea.values()) {
            int reality = check.getRealityScores().get(area);
            int goal = check.getGoalScores().get(area);
            if (goal - reality > 4) {
                Toast.makeText(this, "Big gap in " + area.getDisplayName() + ". Take it easy!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
