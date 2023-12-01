package com.uiuc.budgetsimulator.ui.reports;

import com.uiuc.budgetsimulator.MainActivity;
import com.uiuc.budgetsimulator.ui.home.Scenarios.Scenario.Category;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

public class ReportData implements Serializable {
    private final int weekNumber;
    private final int money;
    private final int health;
    private final int grade;
    private final int weeklySpending;
    private final int weeklyEarning;

    private int weeklyGoal = 100;

    private final Map<Category, Integer> categoryEarning;
    private final Map<Category, Integer> categorySpending;

    public ReportData(int weekNumber, int money, int health, int grade, int weeklySpending,
                      int weeklyEarning, Map<Category, Integer> categorySpending,
                      Map<Category, Integer> categoryEarning) {
        this.weekNumber = weekNumber;
        this.money = money;
        this.health = health;
        this.grade = grade;
        this.weeklySpending = weeklySpending;
        this.weeklyEarning = weeklyEarning;
        this.categoryEarning = categoryEarning;
        this.categorySpending = categorySpending;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public int getMoney() {
        return money;
    }

    public int getHealth() {
        return health;
    }

    public int getGrade() {
        return grade;
    }

    public int getWeeklySpending() {
        return weeklySpending;
    }

    public int getWeeklyEarning() {
        return weeklyEarning;
    }

    public Map<Category, Integer> getCategoryEarning() {
        return categoryEarning;
    }

    public Map<Category, Integer> getCategorySpending() {
        return categorySpending;
    }

    public int getWeeklyGoal(){ return weeklyGoal; }

    public void setWeeklyGoal(int weeklyGoal) { this.weeklyGoal = weeklyGoal; }
}
