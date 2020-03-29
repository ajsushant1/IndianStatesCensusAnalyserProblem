package com.bridgelabz.statecensusanalyser;

import com.bridgelabz.statecensusanalyserexception.CSVBuilderException;
import com.bridgelabz.statecensusanalyserexception.StateCensusAnalyserException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndianCensusAdapter extends CensusAdapter {
    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws StateCensusAnalyserException {
        Map<String, CensusDAO> censusDAOMap = super.loadCensusData(CSVStateCensus.class, csvFilePath[0]);
        if (csvFilePath.length == 1)
            return censusDAOMap;
        return loadStateCodeCSVData(censusDAOMap, csvFilePath[1]);
    }

    private Map<String, CensusDAO> loadStateCodeCSVData(Map<String, CensusDAO> censusDAOMap, String csvFilePath) throws StateCensusAnalyserException {
        String extension = csvFilePath.substring(csvFilePath.lastIndexOf(".") + 1);
        if (!extension.equals("csv")) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.INCORRECT_FILE_TYPE, "Incorrect file type");
        }
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCode> stateCodeIterator = csvBuilder.getCSVIterator(reader, CSVStateCode.class);
            Iterable<CSVStateCode> stateCodes = () -> stateCodeIterator;
            StreamSupport.stream(stateCodes.spliterator(), false)
                    .filter(csvStateCode -> censusDAOMap.get(csvStateCode.stateName) != null)
                    .forEach(csvStateCode -> censusDAOMap.get(csvStateCode.stateName).stateCode = csvStateCode.stateCode);
        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, "Incorrect delimiter or header in file");
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
