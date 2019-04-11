package com.yunussandikci.GithubImporter.Models.GithubAPI;

import org.springframework.http.ResponseEntity;

public class GithubResponse {

    private final Object body;

    private GithubLinks links;

    public GithubResponse(ResponseEntity response) {
        this.body = response.getBody();
        this.links = new GithubLinks(response);
    }

    public Object getBody() {
        return body;
    }

    public GithubLinks getLinks() {
        return links;
    }
}
