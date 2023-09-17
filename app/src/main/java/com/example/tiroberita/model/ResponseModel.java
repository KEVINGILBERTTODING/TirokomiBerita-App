package com.example.tiroberita.model;

public class ResponseModel {
    private Boolean success;
    private String message;
    private DataModel data;

    public ResponseModel(Boolean success, String message, DataModel dataModel) {
        this.success = success;
        this.message = message;
        this.data = dataModel;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public DataModel getDataModel() {
        return data;
    }
}
