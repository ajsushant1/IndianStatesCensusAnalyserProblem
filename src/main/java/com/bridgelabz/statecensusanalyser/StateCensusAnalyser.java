package com.bridgelabz.statecensusanalyser;

import com.bridgelabz.statecensusanalyserexception.StateCensusAnalyserException;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StateCensusAnalyser {

    List<CensusDAO> censusList = null;
    Map<String, CensusDAO> censusDAOMap = null;
    private Country country;

    //CONSTRUCTOR
    public StateCensusAnalyser(Country country) {
        this.country = country;
    }

    //MAIN METHOD
    public static void main(String[] args) {
        System.out.println("/**************************/ WELCOME TO STATE CENSUS ANALYSER /**************************/");
    }

    //METHOD TO LOAD CENSUS DATA
    public int loadStateCensusCSVData(Country country, String... csvFilePath) throws StateCensusAnalyserException {
        censusDAOMap = CensusAdapterFactory.getCensusData(country, csvFilePath);
        censusList = censusDAOMap.values().stream().collect(Collectors.toList());
        return censusDAOMap.size();
    }

    //METHOD TO SORT CENSUS DATA
    public String getSortedCensusData(SortingMode mode) throws StateCensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "No census data");
        }
        ArrayList arrayList = censusDAOMap.values().stream()
                .sorted(CensusDAO.getSortComparator(mode))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        return new Gson().toJson(arrayList);
    }

    //ENUM FOR SORTING MODE
    public enum SortingMode {STATE, POPULATION, DENSITY, AREA}

    //ENUM FOR COUNTRY
    public enum Country {INDIA, US}
}
