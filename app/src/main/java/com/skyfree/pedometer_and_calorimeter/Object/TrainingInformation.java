package com.skyfree.pedometer_and_calorimeter.Object;

public class TrainingInformation {
    private static int id;
    private static int steps;
    private static float distance;
    private static float calories;
    private static String date;
    private static String timeTraining;


    public TrainingInformation(){}

    public TrainingInformation(int id, int steps, float distance, float calories, String date, String timeTraining){
        this.id = id;
        this.steps = steps;
        this.distance = distance;
        this.calories = calories;
        this.timeTraining = timeTraining;
        this.date = date;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        TrainingInformation.id = id;
    }

    public static int getSteps() {
        return steps;
    }

    public static void setSteps(int steps) {
        TrainingInformation.steps = steps;
    }

    public static float getDistance() {
        return distance;
    }

    public static void setDistance(float distance) {
        TrainingInformation.distance = distance;
    }

    public static float getCalories() {
        return calories;
    }

    public static void setCalories(float calories) {
        TrainingInformation.calories = calories;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        TrainingInformation.date = date;
    }

    public static String getTimeTraining() {
        return timeTraining;
    }

    public static void setTimeTraining(String timeTraining) {
        TrainingInformation.timeTraining = timeTraining;
    }
}
