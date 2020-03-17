package com.bridgelabz.statecensusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class StateCensusAnalyser {

    //METHOD TO LOAD CSV DATA AND COUNT NUMBER OF RECORD IN CSV FILE
    public int loadCSVData(String filePath) throws IOException {
        int numberOfRecords = 0;
        try (
                Reader reader = Files.newBufferedReader(Paths.get(filePath));
        ) {
            CsvToBean<CSVStateCensus> csvStateCensusCsvToBean = new CsvToBeanBuilder(reader)
                    .withType(CSVStateCensus.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<CSVStateCensus> csvStateCensusIterator = csvStateCensusCsvToBean.iterator();
            while (csvStateCensusIterator.hasNext()) {
                csvStateCensusIterator.next();
                numberOfRecords++;
            }
        }
        return numberOfRecords;
    }

    //MAIN METHOD
    public static void main(String[] args) {
        System.out.println("/**************************/ WELCOME TO STATE CENSUS ANALYSER /**************************/");
    }
}
