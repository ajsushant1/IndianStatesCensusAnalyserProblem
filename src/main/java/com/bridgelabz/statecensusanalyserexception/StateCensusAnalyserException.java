package com.bridgelabz.statecensusanalyserexception;

public class StateCensusAnalyserException extends Exception {

    public ExceptionType type;

    //CONSTRUCTOR
    public StateCensusAnalyserException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }

    //ENUM CLASS
    public enum ExceptionType {
        NO_SUCH_FILE, INCORRECT_FILE_TYPE, INCORRECT_DELIMITER_OR_HEADER
    }
}
