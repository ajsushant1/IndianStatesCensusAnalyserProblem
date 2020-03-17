package com.bridgelabz.statecensusanalyser;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCensus {
    @CsvBindByName(column = "State")
    private String state;
    @CsvBindByName(column = "Population")
    private int population;
    @CsvBindByName(column = "AreaInSqKm")
    private int area;
    @CsvBindByName(column = "DensityPerSqKm")
    private int density;
}
