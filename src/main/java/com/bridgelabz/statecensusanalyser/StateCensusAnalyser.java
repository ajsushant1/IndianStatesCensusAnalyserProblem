package com.bridgelabz.statecensusanalyser;

import com.bridgelabz.statecensusanalyserexception.CSVBuilderException;
import com.bridgelabz.statecensusanalyserexception.StateCensusAnalyserException;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

public class StateCensusAnalyser {

    List<CSVStateCensus> csvStateCensusList = null;
    List<CSVStateCode> stateCodeList = null;

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
            csvStateCensusList = csvBuilder.getCSVList(reader, CSVStateCensus.class);
            numberOfRecords = csvStateCensusList.size();
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
            stateCodeList = csvBuilder.getCSVList(reader, CSVStateCode.class);
            numberOfRecords = stateCodeList.size();
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
        if (csvStateCensusList == null || csvStateCensusList.size() == 0) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "No census data");
        }
        Comparator<CSVStateCensus> censusComparator = Comparator.comparing(csvStateCensus -> csvStateCensus.getState());
        this.sortCSVData(censusComparator, csvStateCensusList);
        String sortedStateCensusJson = new Gson().toJson(csvStateCensusList);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT STATE CODE DATA BY STATE CODE
    public String getStateCodeWiseSortedData() {
        Comparator<CSVStateCode> stateCodeComparator = Comparator.comparing(csvStateCode -> csvStateCode.getStateCode());
        this.sortCSVData(stateCodeComparator, stateCodeList);
        String sortedStateCodeJson = new Gson().toJson(stateCodeList);
        return sortedStateCodeJson;
    }

    //METHOD TO SORT CSV DATA
    private <T> void sortCSVData(Comparator<T> csvComparator, List<T> csvList) {
        for (int i = 0; i < csvList.size() - 1; i++) {
            for (int j = 0; j < csvList.size() - i - 1; j++) {
                T census1 = csvList.get(j);
                T census2 = csvList.get(j + 1);
                if (csvComparator.compare(census1, census2) > 0) {
                    csvList.set(j, census2);
                    csvList.set(j + 1, census1);
                }
            }
        }
    }
}
