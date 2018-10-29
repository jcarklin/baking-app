package jcarklin.co.za.bakingrecipes.repository.model;

public class FetchStatus {

    public enum Status {
        SUCCESS, ERROR, LOADING
    }

    private final Status status;
    private final Integer statusMessageRvalue;

    public FetchStatus(Status status, Integer messageRvalue) {
        this.status = status;
        this.statusMessageRvalue = messageRvalue;
    }

    public Status getStatus() {
        return status;
    }

    public Integer getStatusMessage() {
        return statusMessageRvalue;
    }
}
