package com.bridgelabz.statecensusanalyser;


import com.bridgelabz.statecensusanalyserexception.CSVBuilderException;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder implements ICSVBuilder {
    @Override
    //METHOD ITERATE CSV DATA FROM FILE
    public <T> Iterator<T> getCSVIterator(Reader reader, Class<T> csvClass) throws CSVBuilderException {
        try {
            CsvToBeanBuilder csvToBeanBuilder = new CsvToBeanBuilder(reader)
                    .withType(csvClass)
                    .withIgnoreLeadingWhiteSpace(true);
            CsvToBean csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        } catch (IllegalStateException e) {
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.UNABLE_TO_PARSE, "Unable to parse");
        }
    }

}
