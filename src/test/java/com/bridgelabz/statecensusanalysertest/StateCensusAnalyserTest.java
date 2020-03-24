package com.bridgelabz.statecensusanalysertest;

import com.bridgelabz.statecensusanalyser.CSVStateCensus;
import com.bridgelabz.statecensusanalyser.CSVStateCode;
import com.bridgelabz.statecensusanalyser.StateCensusAnalyser;
import com.bridgelabz.statecensusanalyserexception.StateCensusAnalyserException;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class StateCensusAnalyserTest {
    private static final String CSV_FILE_PATH = "src/test/resources/StateCensusData.csv";
    private static final String INCORRECT_FILE_NAME = "src/test/resource/StateCensusData.csv";
    private static final String INCORRECT_FILE_TYPE = "src/test/resources/StateCensusData.pdf";
    private static final String INCORRECT_DELIMITER_FILE = "src/test/resources/StateCensusDataIncorrectDelimiter.csv";
    private static final String INCORRECT_HEADER_FILE = "src/test/resources/StateCensusDataIncorrectHeader.csv";
    private static final String STATE_CODE_CSV_FILE_PATH = "src/test/resources/StateCode.csv";
    private static final String STATE_CODE_INCORRECT_CSV_FILE_PATH = "src/test/resource/StateCode.csv";
    private static final String STATE_CODE_INCORRECT_FILE_TYPE = "src/test/resources/StateCode.pdf";
    private static final String STATE_CODE_INCORRECT_DELIMITER_FILE = "src/test/resources/StateCodeIncorrectDelimiter.csv";
    private static final String STATE_CODE_INCORRECT_HEADER_FILE = "src/test/resources/StateCodeIncorrectHeader.csv";
    StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();

    @Test
    public void givenTheStatesCensusCSVFile_WhenNumberOfRecordMatches_ShouldReturnTrue() {
        try {
            int numberOfRecords = stateCensusAnalyser.loadCSVData(CSV_FILE_PATH);
            Assert.assertEquals(29, numberOfRecords);
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenIncorrectFilePath_ShouldThrowCustomException() {
        try {
            stateCensusAnalyser.loadCSVData(INCORRECT_FILE_NAME);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.NO_SUCH_FILE, e.type);
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenIncorrectFileType_ShouldThrowCustomException() {
        try {
            stateCensusAnalyser.loadCSVData(INCORRECT_FILE_TYPE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INCORRECT_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenIncorrectDelimiter_ShouldThrowCustomException() {
        try {
            stateCensusAnalyser.loadCSVData(INCORRECT_DELIMITER_FILE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenIncorrectHeader_ShouldThrowCustomException() {
        try {
            stateCensusAnalyser.loadCSVData(INCORRECT_HEADER_FILE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenTheStatesCodeCSVFile_WhenNumberOfRecordMatches_ShouldReturnTrue() {
        try {
            int numberOfRecords = stateCensusAnalyser.loadStateCodeCSVData(STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37, numberOfRecords);
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenTheStateCodeCSVFile_WhenIncorrectFilePath_ShouldThrowCustomException() {
        try {
            stateCensusAnalyser.loadStateCodeCSVData(STATE_CODE_INCORRECT_CSV_FILE_PATH);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.NO_SUCH_FILE, e.type);
        }
    }

    @Test
    public void givenTheStateCodeCSVFile_WhenIncorrectFileType_ShouldThrowCustomException() {
        try {
            stateCensusAnalyser.loadStateCodeCSVData(STATE_CODE_INCORRECT_FILE_TYPE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INCORRECT_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenTheStateCodeCSVFile_WhenIncorrectDelimiter_ShouldThrowCustomException() {
        try {
            stateCensusAnalyser.loadStateCodeCSVData(STATE_CODE_INCORRECT_DELIMITER_FILE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenTheStateCodeCSVFile_WhenIncorrectHeader_ShouldThrowCustomException() {
        try {
            stateCensusAnalyser.loadStateCodeCSVData(STATE_CODE_INCORRECT_HEADER_FILE);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenTheStateCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            stateCensusAnalyser.loadCSVData(CSV_FILE_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedCensusData();
            CSVStateCensus[] csvStateCensuses = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Andhra Pradesh", csvStateCensuses[0].state);
            Assert.assertEquals("West Bengal", csvStateCensuses[28].state);
        } catch (StateCensusAnalyserException e) {
            e.getStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusDataInJson_WhenNoCensusData_ShouldThrowException() {
        try {
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedCensusData();
            CSVStateCensus[] csvStateCensuses = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenTheStateCodeData_WhenSortedOnStateCode_ShouldReturnSortedResult() {
        try {
            stateCensusAnalyser.loadStateCodeCSVData(STATE_CODE_CSV_FILE_PATH);
            String stateCodeWiseSortedData = stateCensusAnalyser.getStateCodeWiseSortedData();
            CSVStateCode[] csvStateCodes = new Gson().fromJson(stateCodeWiseSortedData, CSVStateCode[].class);
            Assert.assertEquals("AD", csvStateCodes[0].stateCode);
            Assert.assertEquals("WB", csvStateCodes[36].stateCode);
        } catch (StateCensusAnalyserException e) {
            e.getStackTrace();
        }
    }

    @Test
    public void givenTheStateCodeDataInJson_WhenNoCensusData_ShouldThrowException() {
        try {
            String stateCodeWiseSortedData = stateCensusAnalyser.getStateCodeWiseSortedData();
            CSVStateCode[] csvStateCodes = new Gson().fromJson(stateCodeWiseSortedData, CSVStateCode[].class);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }
}
