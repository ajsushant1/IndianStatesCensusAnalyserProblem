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

    List<CensusDTO> censusList = null;
    Map<String, CensusDTO> censusMap = null;

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
                CensusDTO censusDTO = new CensusDTO(stateCensusIterator.next());
                this.censusMap.put(censusDTO.state, censusDTO);
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
                CensusDTO censusDTO = new CensusDTO(stateCodeIterator.next());
                this.censusMap.put(censusDTO.stateCode, censusDTO);
                censusList = censusMap.values().stream().collect(Collectors.toList());
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
        Comparator<CensusDTO> censusComparator = Comparator.comparing(censusDTO -> censusDTO.state);
        this.sortCSVData(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT STATE CODE DATA BY STATE CODE
    public String getStateCodeWiseSortedData() throws StateCensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "No census data");
        }
        Comparator<CensusDTO> stateCodeComparator = Comparator.comparing(censusDTO -> censusDTO.stateCode);
        this.sortCSVData(stateCodeComparator);
        String sortedStateCodeJson = new Gson().toJson(censusList);
        return sortedStateCodeJson;
    }

    //METHOD TO SORT CSV DATA
    private void sortCSVData(Comparator<CensusDTO> csvComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                CensusDTO census1 = censusList.get(j);
                CensusDTO census2 = censusList.get(j + 1);
                if (csvComparator.compare(census1, census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }
            }
        }
    }
}
