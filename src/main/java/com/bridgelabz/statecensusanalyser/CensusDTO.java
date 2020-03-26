package com.bridgelabz.statecensusanalyser;

public class CensusDTO {
    public String state;
    public int population;
    public int area;
    public int density;
    public String stateCode;

    public CensusDTO(CSVStateCensus csvStateCensus) {
        this.state = csvStateCensus.state;
        this.population = csvStateCensus.population;
        this.area = csvStateCensus.area;
        this.density = csvStateCensus.density;
    }

    public CensusDTO(CSVStateCode csvStateCode) {
        this.stateCode = csvStateCode.stateCode;
    }
}
