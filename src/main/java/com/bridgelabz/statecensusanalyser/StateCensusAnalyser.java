package com.bridgelabz.statecensusanalyser;

import com.bridgelabz.statecensusanalyserexception.StateCensusAnalyserException;
import com.google.gson.Gson;

import java.util.*;
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

    //METHOD TO SORT US CENSUS DATA BY POPULATION
    public String getPopulationWiseUSSortedCensusData() throws StateCensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "No census data");
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.population);
        this.sortCSVData(censusComparator);
        Collections.reverse(censusList);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT CSV DATA
    private void sortCSVData(Comparator<CensusDAO> csvComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                CensusDAO census1 = censusList.get(j);
                CensusDAO census2 = censusList.get(j + 1);
                if (csvComparator.compare(census1, census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }
            }
        }
    }

    public enum SortingMode {STATE, POPULATION, DENSITY, AREA}

    public enum Country {INDIA, US}
}
