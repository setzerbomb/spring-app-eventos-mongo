package com.eventos.app.common.exceptions;

import com.mongodb.MongoException;

public class DataException extends Exception {
    public DataException(String msg) {
        super(msg);
    }

}
