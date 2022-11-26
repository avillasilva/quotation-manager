package br.inatel.quotationmanagement.exception;

public class StockNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public StockNotFoundException(String message) {
        super("There is no stock in the database with id " + message);
    }
}
