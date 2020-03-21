package com.bridgelabz.statecensusanalyser;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder implements ICSVBuilder {
    @Override
    //METHOD ITERATE CSV DATA FROM FILE
    public Iterator getCSVIterator(Reader reader, Class csvClass) {
        CsvToBeanBuilder csvToBeanBuilder = new CsvToBeanBuilder(reader)
                .withType(csvClass)
                .withIgnoreLeadingWhiteSpace(true);
        CsvToBean csvToBean = csvToBeanBuilder.build();
        return csvToBean.iterator();
    }

}
