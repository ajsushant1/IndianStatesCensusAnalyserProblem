package com.bridgelabz.statecensusanalyser;

public class CensusDAO {
    public String state;
    public int population;
    public int area;
    public int density;
    public String stateCode;

    public CensusDAO(CSVStateCensus csvStateCensus) {
        this.state = csvStateCensus.state;
        this.population = csvStateCensus.population;
        this.area = csvStateCensus.area;
        this.density = csvStateCensus.density;
    }

}
