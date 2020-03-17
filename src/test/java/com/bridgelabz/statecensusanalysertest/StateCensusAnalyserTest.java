package com.bridgelabz.statecensusanalysertest;

import com.bridgelabz.statecensusanalyser.StateCensusAnalyser;
import com.bridgelabz.statecensusanalyserexception.StateCensusAnalyserException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class StateCensusAnalyserTest {
    StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
    private static final String CSV_FILE_PATH = "src/test/resources/StateCensusData.csv";
    private static final String FILE_PATH = "src/test/resource/StateCensusData.csv";

    @Test
    public void givenTheStatesCensusCSVFile_WhenNumberOfRecordMatches_ShouldReturnTrue() {
        try {
            int numberOfRecords = stateCensusAnalyser.loadCSVData(CSV_FILE_PATH);
            Assert.assertEquals(29, numberOfRecords);
        } catch (StateCensusAnalyserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenIncorrect_ShouldThrowCustomException() {
        try {
            stateCensusAnalyser.loadCSVData(FILE_PATH);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.ExceptionType.NO_SUCH_FILE, e.type);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
