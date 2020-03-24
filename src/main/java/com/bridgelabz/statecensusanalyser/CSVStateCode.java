package com.bridgelabz.statecensusanalyser;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCode {
    @CsvBindByName(column = "SrNo", required = true)
    private int serialNumber;
    @CsvBindByName(column = "StateName",required = true)
    private String stateName;
    @CsvBindByName(column = "TIN",required = true)
    private int tin;
    @CsvBindByName(column = "StateCode",required = true)
    private String stateCode;

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getStateName() {
        return stateName;
    }

    public int getTin() {
        return tin;
    }

    public String getStateCode() {
        return stateCode;
    }
}
