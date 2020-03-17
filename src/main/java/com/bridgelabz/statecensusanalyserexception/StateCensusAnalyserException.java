package com.bridgelabz.statecensusanalyserexception;

import java.io.IOException;

public class StateCensusAnalyserException extends Exception {

    //ENUM
    public enum ExceptionType {
        NO_SUCH_FILE;
    }

    public ExceptionType type;

    //CONSTRUCTOR
    public StateCensusAnalyserException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}
