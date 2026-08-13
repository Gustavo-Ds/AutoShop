package br.com.ssdev.autoshop.models;

import lombok.Getter;

@Getter
public enum OrderStatus {
    OPEN("Open"),
    IN_PROGRESS("In Progress"),
    FINISHED("Finished"),
    CANCELED("Canceled"),
    WAITING_PARTS("Waiting Parts"),
    ESTIMATE("Estimate");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
