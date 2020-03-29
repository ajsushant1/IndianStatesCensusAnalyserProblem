package com.bridgelabz.statecensusanalyser;

import com.bridgelabz.statecensusanalyserexception.StateCensusAnalyserException;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {
    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws StateCensusAnalyserException {
        return super.loadCensusData(CSVUSCensus.class, csvFilePath[0]);
    }
}
