package com.bridgelabz.statecensusanalysertest;

import com.bridgelabz.statecensusanalyser.CSVStateCensus;
import com.bridgelabz.statecensusanalyser.CensusDAO;
import com.bridgelabz.statecensusanalyser.StateCensusAnalyser;
import com.bridgelabz.statecensusanalyserexception.StateCensusAnalyserException;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import static com.bridgelabz.statecensusanalyser.StateCensusAnalyser.Country.INDIA;
import static com.bridgelabz.statecensusanalyser.StateCensusAnalyser.Country.US;

public class StateCensusAnalyserTest {
    private static final String STATE_CENSUS_CSV_FILE_PATH = "src/test/resources/StateCensusData.csv";
    private static final String INCORRECT_FILE_NAME = "src/test/resource/StateCensusData.csv";
    private static final String INCORRECT_FILE_TYPE = "src/test/resources/StateCensusData.pdf";
    private static final String INCORRECT_DELIMITER_FILE = "src/test/resources/StateCensusDataIncorrectDelimiter.csv";
    private static final String INCORRECT_HEADER_FILE = "src/test/resources/StateCensusDataIncorrectHeader.csv";
    private static final String STATE_CODE_CSV_FILE_PATH = "src/test/resources/StateCode.csv";
    private static final String STATE_CODE_INCORRECT_CSV_FILE_PATH = "src/test/resource/StateCode.csv";
    private static final String STATE_CODE_INCORRECT_FILE_TYPE = "src/test/resources/StateCode.pdf";
    private static final String STATE_CODE_INCORRECT_DELIMITER_FILE = "src/test/resources/StateCodeIncorrectDelimiter.csv";
    private static final String STATE_CODE_INCORRECT_HEADER_FILE = "src/test/resources/StateCodeIncorrectHeader.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "src/test/resources/USCensusData.csv";
    StateCensusAnalyser indianCensusAnalyser = new StateCensusAnalyser(INDIA);
    StateCensusAnalyser usCensusAnalyser = new StateCensusAnalyser(US);


    @Test
    public void givenTheStatesCensusCSVFile_WhenNumberOfRecordMatches_ShouldReturnTrue() {
        try {
            int numberOfRecords = indianCensusAnalyser.loadStateCensusCSVData(INDIA, STATE_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numberOfRecords);
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenIncorrectFilePath_ShouldThrowCustomException() {
        try {
            indianCensusAnalyser.loadStateCensusCSVData(INDIA, INCORRECT_FILE_NAME);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.NO_SUCH_FILE, e.type);
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenIncorrectFileType_ShouldThrowCustomException() {
        try {
            indianCensusAnalyser.loadStateCensusCSVData(INDIA, INCORRECT_FILE_TYPE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INCORRECT_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenIncorrectDelimiter_ShouldThrowCustomException() {
        try {
            indianCensusAnalyser.loadStateCensusCSVData(INDIA, INCORRECT_DELIMITER_FILE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenIncorrectHeader_ShouldThrowCustomException() {
        try {
            indianCensusAnalyser.loadStateCensusCSVData(INDIA, INCORRECT_HEADER_FILE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenTheStatesCodeCSVFile_WhenNumberOfRecordMatches_ShouldReturnTrue() {
        try {
            int numberOfRecords = indianCensusAnalyser.loadStateCensusCSVData(INDIA, STATE_CENSUS_CSV_FILE_PATH, STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29, numberOfRecords);
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenTheStateCodeCSVFile_WhenIncorrectFilePath_ShouldThrowCustomException() {
        try {
            indianCensusAnalyser.loadStateCensusCSVData(INDIA, STATE_CODE_INCORRECT_CSV_FILE_PATH);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.NO_SUCH_FILE, e.type);
        }
    }

    @Test
    public void givenTheStateCodeCSVFile_WhenIncorrectFileType_ShouldThrowCustomException() {
        try {
            indianCensusAnalyser.loadStateCensusCSVData(INDIA, STATE_CODE_INCORRECT_FILE_TYPE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INCORRECT_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenTheStateCodeCSVFile_WhenIncorrectDelimiter_ShouldThrowCustomException() {
        try {
            indianCensusAnalyser.loadStateCensusCSVData(INDIA, STATE_CODE_INCORRECT_DELIMITER_FILE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenTheStateCodeCSVFile_WhenIncorrectHeader_ShouldThrowCustomException() {
        try {
            indianCensusAnalyser.loadStateCensusCSVData(INDIA, STATE_CODE_INCORRECT_HEADER_FILE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenTheStateCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            indianCensusAnalyser.loadStateCensusCSVData(INDIA, STATE_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = indianCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.STATE);
            CSVStateCensus[] csvStateCensuses = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Andhra Pradesh", csvStateCensuses[0].state);
        } catch (StateCensusAnalyserException e) {
            e.getStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusDataInJson_WhenNoCensusData_ShouldThrowException() {
        try {
            String sortedCensusData = indianCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.STATE);
            CSVStateCensus[] csvStateCensuses = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenTheStateCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        try {
            indianCensusAnalyser.loadStateCensusCSVData(INDIA, STATE_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = indianCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.POPULATION);
            CSVStateCensus[] csvStateCensuses = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals(199812341, csvStateCensuses[0].population);
        } catch (StateCensusAnalyserException e) {
            e.getStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusData_WhenSortedOnDensityPerSqKm_ShouldReturnSortedResult() {
        try {
            indianCensusAnalyser.loadStateCensusCSVData(INDIA, STATE_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = indianCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.DENSITY);
            CSVStateCensus[] csvStateCensuses = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals(1102, csvStateCensuses[0].density, 0);
        } catch (StateCensusAnalyserException e) {
            e.getStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusData_WhenSortedOnAreaInPerSqKm_ShouldReturnSortedResult() {
        try {
            indianCensusAnalyser.loadStateCensusCSVData(INDIA, STATE_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = indianCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.AREA);
            CensusDAO[] censusDAOS = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals(342239, censusDAOS[0].area, 0);
        } catch (StateCensusAnalyserException e) {
            e.getStackTrace();
        }
    }

    @Test
    public void givenTheUSCensusCSVFile_WhenNumberOfRecordMatches_ShouldReturnTrue() {
        try {
            int numberOfRecords = usCensusAnalyser.loadStateCensusCSVData(StateCensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, numberOfRecords);
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenTheUSCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        try {
            usCensusAnalyser.loadStateCensusCSVData(StateCensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(StateCensusAnalyser.SortingMode.POPULATION);
            CensusDAO[] censusDAOS = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("California", censusDAOS[0].state);
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}
