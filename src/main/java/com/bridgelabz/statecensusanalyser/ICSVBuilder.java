package com.bridgelabz.statecensusanalyser;

import com.bridgelabz.statecensusanalyserexception.CSVBuilderException;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder {
    <T> Iterator<T> getCSVIterator(Reader reader, Class<T> csvClass) throws CSVBuilderException;
}
