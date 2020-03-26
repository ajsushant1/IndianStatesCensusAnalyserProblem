package com.bridgelabz.statecensusanalyser;

import com.bridgelabz.statecensusanalyserexception.CSVBuilderException;
import com.bridgelabz.statecensusanalyserexception.StateCensusAnalyserException;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class StateCensusAnalyser {

    List<CensusDAO> censusList = null;
    Map<String, CensusDAO> censusMap = null;

    //CONSTRUCTOR
    public StateCensusAnalyser() {
        this.censusMap = new HashMap<>();
        this.censusList = new ArrayList<>();
    }

    //MAIN METHOD
    public static void main(String[] args) {
        System.out.println("/**************************/ WELCOME TO STATE CENSUS ANALYSER /**************************/");

    }

    //METHOD TO LOAD STATE CENSUS CSV DATA AND COUNT NUMBER OF RECORD IN CSV FILE
    public int loadCSVData(String filePath) throws StateCensusAnalyserException {
        int numberOfRecords = 0;
        String extension = filePath.substring(filePath.lastIndexOf(".") + 1);
        if (!extension.equals("csv")) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.INCORRECT_FILE_TYPE, "Incorrect file type");
        }
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCensus> stateCensusIterator = csvBuilder.getCSVIterator(reader, CSVStateCensus.class);
            while (stateCensusIterator.hasNext()) {
                CensusDAO censusDAO = new CensusDAO(stateCensusIterator.next());
                this.censusMap.put(censusDAO.state, censusDAO);
                censusList = censusMap.values().stream().collect(Collectors.toList());
            }
            numberOfRecords = censusMap.size();
        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, "Incorrect delimiter or header");
        } catch (NoSuchFileException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.NO_SUCH_FILE, "No such file");
        } catch (IOException e) {
            e.getStackTrace();
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
        return numberOfRecords;
    }

    //METHOD TO LOAD STATE CODE CSV DATA AND COUNT NUMBER OF RECORD IN CSV FILE
    public int loadStateCodeCSVData(String filePath) throws StateCensusAnalyserException {
        int numberOfRecords = 0;
        String extension = filePath.substring(filePath.lastIndexOf(".") + 1);
        if (!extension.equals("csv")) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.INCORRECT_FILE_TYPE, "Incorrect file type");
        }
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCode> stateCodeIterator = csvBuilder.getCSVIterator(reader, CSVStateCode.class);
            while (stateCodeIterator.hasNext()) {
                CSVStateCode csvStateCode = stateCodeIterator.next();
                CensusDAO censusDTO = censusMap.get(csvStateCode.stateName);
                if (censusDTO == null)
                    continue;
                censusDTO.stateCode = csvStateCode.stateCode;
            }
            numberOfRecords = censusMap.size();
        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, "Incorrect delimiter or header in file");
        } catch (NoSuchFileException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.NO_SUCH_FILE, "No such file");
        } catch (IOException e) {
            e.getStackTrace();
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
        return numberOfRecords;
    }

    //METHOD TO SORT STATE CENSUS DATA BY STATE
    public String getStateWiseSortedCensusData() throws StateCensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "No census data");
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.state);
        this.sortCSVData(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT STATE CENSUS DATA BY POPULATION
    public String getPopulationWiseSortedCensusData() throws StateCensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "No census data");
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.population);
        this.sortCSVData(censusComparator);
        Collections.reverse(censusList);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT STATE CENSUS DATA BY POPULATION DENSITY
    public String getPopulationDensityWiseSortedCensusData() throws StateCensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "No census data");
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.density);
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
}
