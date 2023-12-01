package com.uiuc.budgetsimulator.ui.home;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;



public class Scenarios implements Parcelable {
    public Scenarios() {
    }
    public Scenario[] scenarios;

    protected Scenarios(Parcel in) {
        scenarios = in.createTypedArray(Scenario.CREATOR);
    }

    public static final Creator<Scenarios> CREATOR = new Creator<Scenarios>() {
        @Override
        public Scenarios createFromParcel(Parcel in) {
            return new Scenarios(in);
        }

        @Override
        public Scenarios[] newArray(int size) {
            return new Scenarios[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(scenarios, flags);
    }

    public static class Scenario implements Parcelable{
        public Scenario() {

        }

        public enum Category
        {
            NONE, ENTERTAINMENT, FOOD, WORK, ALLOWANCE, EMERGENCY;


            public static final int[] categoryColors = new int[]{Color.LTGRAY, Color.parseColor("#2E86AB"), Color.parseColor("#65743A"), Color.parseColor("#DFDFDF"), Color.parseColor("#736372"), Color.parseColor("#F24236")};
        }
        public String event;

        public Category category = Category.NONE;

        public Choice[] choices;

        public static class Choice implements Parcelable {
            public Choice (){

            }
            public String choice;
            public int healthOutcome;
            public int gradeOutcome;
            public int moneyOutcome;

            protected Choice(Parcel in) {
                choice = in.readString();
                healthOutcome = in.readInt();
                gradeOutcome = in.readInt();
                moneyOutcome = in.readInt();
            }

            public static final Creator<Choice> CREATOR = new Creator<Choice>() {
                @Override
                public Choice createFromParcel(Parcel in) {
                    return new Choice(in);
                }

                @Override
                public Choice[] newArray(int size) {
                    return new Choice[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(choice);
                dest.writeInt(healthOutcome);
                dest.writeInt(gradeOutcome);
                dest.writeInt(moneyOutcome);
            }
        }
        public int healthOutcome;
        public int gradeOutcome;
        public int moneyOutcome;

        protected Scenario(Parcel in) {
            event = in.readString();
            category = Category.valueOf(in.readString());
            choices = in.createTypedArray(Choice.CREATOR);
            healthOutcome = in.readInt();
            gradeOutcome = in.readInt();
            moneyOutcome = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(event);
            dest.writeString(category.name());
            dest.writeTypedArray(choices, flags);
            dest.writeInt(healthOutcome);
            dest.writeInt(gradeOutcome);
            dest.writeInt(moneyOutcome);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Scenario> CREATOR = new Creator<Scenario>() {
            @Override
            public Scenario createFromParcel(Parcel in) {
                return new Scenario(in);
            }

            @Override
            public Scenario[] newArray(int size) {
                return new Scenario[size];
            }
        };
    }
}

