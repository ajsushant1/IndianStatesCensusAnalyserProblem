package com.bridgelabz.statecensusanalyser;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCensus {
    @CsvBindByName(column = "State", required = true)
    public String state;
    @CsvBindByName(column = "Population", required = true)
    public int population;
    @CsvBindByName(column = "AreaInSqKm", required = true)
    public double area;
    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public double density;

    public CSVStateCensus(String state, int population, double area, double density) {
        this.state = state;
        this.population = population;
        this.area = area;
        this.density = density;
    }

    public CSVStateCensus() {
    }
}

