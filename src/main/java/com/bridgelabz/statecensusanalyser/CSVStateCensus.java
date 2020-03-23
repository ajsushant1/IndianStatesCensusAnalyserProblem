package com.bridgelabz.statecensusanalyser;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCensus {
    @CsvBindByName(column = "State", required = true)
    private String state;
    @CsvBindByName(column = "Population", required = true)
    private int population;
    @CsvBindByName(column = "AreaInSqKm", required = true)
    private int area;
    @CsvBindByName(column = "DensityPerSqKm", required = true)
    private int density;

    public String getState() {
        return state;
    }

    public int getPopulation() {
        return population;
    }

    public int getArea() {
        return area;
    }

    public int getDensity() {
        return density;
    }
}

