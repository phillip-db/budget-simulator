package com.uiuc.budgetsimulator.ui.reports;

import android.app.Application;

import java.io.Serializable;

public class ReportData implements Serializable {
  private final int weekNumber;
  private final int money;
  private final int health;
  private final int grade;

  public ReportData(int weekNumber, int money, int health, int grade) {
    this.weekNumber = weekNumber;
    this.money = money;
    this.health = health;
    this.grade = grade;
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
}
