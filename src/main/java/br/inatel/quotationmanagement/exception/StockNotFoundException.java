package br.inatel.quotationmanagement.exception;

public class StockNotFoundException extends Exception {
    public StockNotFoundException(String message) {
        super("There is no stock in the database with id " + message);
    }
}
