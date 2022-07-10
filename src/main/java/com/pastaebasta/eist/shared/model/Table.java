package com.pastaebasta.eist.shared.model;

public class Table {
    private final long tableId;
    private final int numberOfSeats;
    private final int tableNumber;
    private static int id = 0;

    public Table(int numberOfSeats, int tableNumber) {
        this.tableId = id;
        id++;
        this.numberOfSeats = numberOfSeats;
        this.tableNumber = tableNumber;
    }


    //Getters
    public long getTableId() {
        return tableId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }
    //Setters

}