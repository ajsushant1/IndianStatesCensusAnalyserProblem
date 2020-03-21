package com.bridgelabz.statecensusanalyser;

public class CSVBuilderFactory {

    public static ICSVBuilder createCSVBuilder() {
        return new OpenCSVBuilder();
    }
}
