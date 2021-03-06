package com.bridgelabz.statecensusanalyser;


import com.bridgelabz.statecensusanalyserexception.CSVBuilderException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder implements ICSVBuilder {

    //METHOD ITERATE CSV DATA FROM FILE USING LIST
    @Override
    public <T> List<T> getCSVList(Reader reader, Class<T> csvClass) throws CSVBuilderException {
        try {
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(csvClass)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        } catch (IllegalStateException e) {
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.UNABLE_TO_PARSE, "Unable to parse");
        }
    }

    //METHOD ITERATE CSV DATA FROM FILE USING ITERATOR
    @Override
    public <T> Iterator<T> getCSVIterator(Reader reader, Class<T> csvClass) throws CSVBuilderException {
        try {
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(csvClass)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.iterator();
        } catch (IllegalStateException e) {
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.UNABLE_TO_PARSE, "Unable to parse");
        }

    }
}
