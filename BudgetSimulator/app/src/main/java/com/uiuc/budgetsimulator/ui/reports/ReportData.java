package com.uiuc.budgetsimulator.ui.reports;

import java.io.Serializable;

public class ReportData implements Serializable {
  private final int weekNumber;
  private final int money;
  private final int health;
  private final int grade;
  private final int weeklySpending;
  private final int weeklyEarning;

  public ReportData(int weekNumber, int money, int health, int grade, int weeklySpending, int weeklyEarning) {
    this.weekNumber = weekNumber;
    this.money = money;
    this.health = health;
    this.grade = grade;
    this.weeklySpending = weeklySpending;
    this.weeklyEarning = weeklyEarning;
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
}
