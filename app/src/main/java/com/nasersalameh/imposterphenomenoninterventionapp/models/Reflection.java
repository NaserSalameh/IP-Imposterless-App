package com.nasersalameh.imposterphenomenoninterventionapp.models;

import java.io.Serializable;

//Imports Serializable to send across Intents
public class Reflection implements Serializable {


    private Goal goal;

    private String greatAchievement;
    private String achievementType;

    private String bestAbility;

    private String blocker;
    private int blockerDifficulty;

    private boolean deadlineMet;
    private String deadlineReason;

    private int successScore;
    private String lowSuccessReason;

    private int expectationScore;
    private String lowExpectationReason;

    public Reflection(Goal goal) {
        this.goal = goal;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public String getGreatAchievement() {
        return greatAchievement;
    }

    public void setGreatAchievement(String greatAchievement) {
        this.greatAchievement = greatAchievement;
    }

    public String getAchievementType() {
        return achievementType;
    }

    public void setAchievementType(String achievementType) {
        this.achievementType = achievementType;
    }

    public String getBestAbility() {
        return bestAbility;
    }

    public void setBestAbility(String bestAbility) {
        this.bestAbility = bestAbility;
    }

    public String getBlocker() {
        return blocker;
    }

    public void setBlocker(String blocker) {
        this.blocker = blocker;
    }

    public int getBlockerDifficulty() {
        return blockerDifficulty;
    }

    public void setBlockerDifficulty(int blockerDifficulty) {
        this.blockerDifficulty = blockerDifficulty;
    }

    public boolean isDeadlineMet() {
        return deadlineMet;
    }

    public void setDeadlineMet(boolean deadlineMet) {
        this.deadlineMet = deadlineMet;
    }

    public String getDeadlineReason() {
        return deadlineReason;
    }

    public void setDeadlineReason(String deadlineReason) {
        this.deadlineReason = deadlineReason;
    }

    public int getSuccessScore() {
        return successScore;
    }

    public void setSuccessScore(int successScore) {
        this.successScore = successScore;
    }

    public String getLowSuccessReason() {
        return lowSuccessReason;
    }

    public void setLowSuccessReason(String lowSuccessReason) {
        this.lowSuccessReason = lowSuccessReason;
    }

    public int getExpectationScore() {
        return expectationScore;
    }

    public void setExpectationScore(int expectationScore) {
        this.expectationScore = expectationScore;
    }

    public String getLowExpectationReason() {
        return lowExpectationReason;
    }

    public void setLowExpectationReason(String lowExpectationReason) {
        this.lowExpectationReason = lowExpectationReason;
    }
}
