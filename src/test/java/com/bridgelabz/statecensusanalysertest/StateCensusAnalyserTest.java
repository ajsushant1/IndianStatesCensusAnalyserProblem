package com.bridgelabz.statecensusanalysertest;

import com.bridgelabz.statecensusanalyser.StateCensusAnalyser;
import com.bridgelabz.statecensusanalyserexception.StateCensusAnalyserException;
import org.junit.Assert;
import org.junit.Test;

public class StateCensusAnalyserTest {
    StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
    private static final String CSV_FILE_PATH = "src/test/resources/StateCensusData.csv";
    private static final String INCORRECT_FILE_NAME = "src/test/resource/StateCensusData.csv";
    private static final String INCORRECT_FILE_TYPE = "src/test/resources/StateCensusData.pdf";

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
}
