package com.bridgelabz.statecensusanalysertest;

import com.bridgelabz.statecensusanalyser.StateCensusAnalyser;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class StateCensusAnalyserTest {
    StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
    private static final String CSV_FILE_PATH = "src/test/resources/StateCensusData.csv";

    @Test
    public void givenTheStatesCensusCSVFile_WhenNumberOfRecordMatches_ShouldReturnTrue() {
        try {
            int numberOfRecords = stateCensusAnalyser.loadCSVData(CSV_FILE_PATH);
            Assert.assertEquals(29, numberOfRecords);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
