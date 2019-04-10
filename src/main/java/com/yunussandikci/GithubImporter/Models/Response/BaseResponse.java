package com.yunussandikci.GithubImporter.Models.Response;

abstract class BaseResponse {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
