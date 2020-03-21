package com.bridgelabz.statecensusanalyser;

import com.bridgelabz.statecensusanalyserexception.StateCensusAnalyserException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;

public class StateCensusAnalyser {

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
            OpenCSVBuilder openCSVBuilder = new OpenCSVBuilder();
            Iterator<CSVStateCensus> csvStateCensusIterator = openCSVBuilder.getCSVIterator(reader, CSVStateCensus.class);
            while (csvStateCensusIterator.hasNext()) {
                csvStateCensusIterator.next();
                numberOfRecords++;
            }
        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, "Incorrect delimiter or header");
        } catch (NoSuchFileException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.NO_SUCH_FILE, "No such file");
        } catch (IOException e) {
            e.getStackTrace();
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
            OpenCSVBuilder openCSVBuilder = new OpenCSVBuilder();
            Iterator<CSVStateCode> csvStateCodeIterator = openCSVBuilder.getCSVIterator(reader, CSVStateCode.class);
            while (csvStateCodeIterator.hasNext()) {
                csvStateCodeIterator.next();
                numberOfRecords++;
            }
        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, "Incorrect delimiter or header in file");
        } catch (NoSuchFileException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.NO_SUCH_FILE, "No such file");
        } catch (IOException e) {
            e.getStackTrace();
        }
        return numberOfRecords;
    }
}
