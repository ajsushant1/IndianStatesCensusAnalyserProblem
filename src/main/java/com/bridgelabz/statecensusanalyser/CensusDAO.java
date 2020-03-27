package com.bridgelabz.statecensusanalyser;

public class CensusDAO {
    public String state;
    public String stateCode;
    public int population;
    public double area;
    public double density;

    public CensusDAO(CSVStateCensus csvStateCensus) {
        this.state = csvStateCensus.state;
        this.population = csvStateCensus.population;
        this.area = csvStateCensus.area;
        this.density = csvStateCensus.density;
    }
}
