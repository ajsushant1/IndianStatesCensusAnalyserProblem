package com.bridgelabz.statecensusanalysertest;

import com.bridgelabz.statecensusanalyser.StateCensusAnalyser;
import com.bridgelabz.statecensusanalyserexception.StateCensusAnalyserException;
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
    public void givenTheStateCensusCSVFile_WhenIncorrect_ShouldThrowCustomException() {
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
}
