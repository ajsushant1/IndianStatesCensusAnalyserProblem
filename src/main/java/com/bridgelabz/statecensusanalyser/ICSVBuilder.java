package com.bridgelabz.statecensusanalyser;

import com.bridgelabz.statecensusanalyserexception.CSVBuilderException;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder {
    <T> List<T> getCSVList(Reader reader, Class<T> csvClass) throws CSVBuilderException;

    <T> Iterator<T> getCSVIterator(Reader reader, Class<T> csvClass) throws CSVBuilderException;
}
