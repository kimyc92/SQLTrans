package com.springboot.batch.common.exception;

public class RoutingDataSourceException extends RuntimeException {

    private static final long serialVersionUID = -2464069459370258425L;

    public RoutingDataSourceException() {
        super();
    }

    public RoutingDataSourceException(String message) {
        super(message);
    }

    public RoutingDataSourceException(Throwable t) {
        super(t);
    }

    public RoutingDataSourceException(String message, Throwable t) {
        super(message, t);
    }
}
