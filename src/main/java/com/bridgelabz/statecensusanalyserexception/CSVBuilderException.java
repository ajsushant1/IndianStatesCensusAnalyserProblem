package com.bridgelabz.statecensusanalyserexception;

public class CSVBuilderException extends Exception {
    ExceptionType type;

    public CSVBuilderException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }

    public enum ExceptionType {
        UNABLE_TO_PARSE
    }
}
