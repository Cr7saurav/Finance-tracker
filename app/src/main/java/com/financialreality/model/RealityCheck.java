package com.financialreality.model;

import java.util.Date;
import java.util.Map;

public class RealityCheck {
    private Date date;
    private Map<LifeArea, Integer> realityScores;
    private Map<LifeArea, Integer> goalScores;
    private int mood;
    private int energy;

    public RealityCheck(Date date, Map<LifeArea, Integer> realityScores, Map<LifeArea, Integer> goalScores, int mood, int energy) {
        this.date = date;
        this.realityScores = realityScores;
        this.goalScores = goalScores;
        this.mood = mood;
        this.energy = energy;
    }

    public Date getDate() { return date; }
    public Map<LifeArea, Integer> getRealityScores() { return realityScores; }
    public Map<LifeArea, Integer> getGoalScores() { return goalScores; }
    public int getMood() { return mood; }
    public int getEnergy() { return energy; }
}
