package com.bridgelabz.statecensusanalyser;

import com.bridgelabz.statecensusanalyserexception.CSVBuilderException;
import com.bridgelabz.statecensusanalyserexception.StateCensusAnalyserException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class CensusAdapter {
    public abstract Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws StateCensusAnalyserException;

    public <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String csvFilePath) throws StateCensusAnalyserException {
        Map<String, CensusDAO> censusDAOMap = new HashMap<>();
        String extension = csvFilePath.substring(csvFilePath.lastIndexOf(".") + 1);
        if (!extension.equals("csv")) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.INCORRECT_FILE_TYPE, "Incorrect file type");
        }
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> stateCensusIterator = csvBuilder.getCSVIterator(reader, censusCSVClass);
            Iterable<E> stateCensuses = () -> stateCensusIterator;
            if (censusCSVClass.getName().equals("com.bridgelabz.statecensusanalyser.CSVStateCensus")) {
                StreamSupport.stream(stateCensuses.spliterator(), false)
                        .map(CSVStateCensus.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            } else if (censusCSVClass.getName().equals("com.bridgelabz.statecensusanalyser.CSVUSCensus")) {
                StreamSupport.stream(stateCensuses.spliterator(), false)
                        .map(CSVUSCensus.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, "Incorrect delimiter or header");
        } catch (NoSuchFileException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.NO_SUCH_FILE, "No such file");
        } catch (IOException e) {
            e.getStackTrace();
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
        return censusDAOMap;
    }
}
