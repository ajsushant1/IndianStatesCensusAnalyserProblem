package com.bridgelabz.statecensusanalyser;

import java.util.Comparator;

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

    public CensusDAO(CSVUSCensus csvusCensus) {
        this.state = csvusCensus.state;
        this.stateCode = csvusCensus.stateId;
        this.population = csvusCensus.population;
        this.area = csvusCensus.totalArea;
        this.density = csvusCensus.populationDensity;
    }

    public static Comparator<CensusDAO> getSortComparator(StateCensusAnalyser.SortingMode mode) {
        if (mode.equals(StateCensusAnalyser.SortingMode.STATE))
            return Comparator.comparing(census -> census.state);
        if (mode.equals(StateCensusAnalyser.SortingMode.POPULATION))
            return Comparator.comparing(CensusDAO::getPopulation).reversed();
        if (mode.equals(StateCensusAnalyser.SortingMode.AREA))
            return Comparator.comparing(CensusDAO::getArea).reversed();
        if (mode.equals(StateCensusAnalyser.SortingMode.DENSITY))
            return Comparator.comparing(CensusDAO::getDensity).reversed();
        return null;
    }

    public int getPopulation() {
        return population;
    }

    public double getArea() {
        return area;
    }

    public double getDensity() {
        return density;
    }

    public Object getCensusDTO(StateCensusAnalyser.Country country) {
        if (country.equals(StateCensusAnalyser.Country.INDIA))
            return new CSVStateCensus(state, population, area, density);
        return null;
    }
}
