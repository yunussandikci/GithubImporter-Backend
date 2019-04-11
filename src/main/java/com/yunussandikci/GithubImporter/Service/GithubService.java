package com.yunussandikci.GithubImporter.Service;

import com.yunussandikci.GithubImporter.Models.GithubAPI.GithubResponse;
import com.yunussandikci.GithubImporter.Models.GithubAPI.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GithubService {

    private RestTemplate restTemplate;

    @Autowired
    public GithubService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    //TODO:Use multi-threading for every request maybe
    public List<Project> fetchUserRepositories(String username){
        List<Project> projects = new ArrayList<>();
        String currentPage = "https://api.github.com/users/" + username + "/repos?per_page=100&page=1";
        GithubResponse githubResponse;
        do{
            ResponseEntity<List<Project>> response = restTemplate.exchange(currentPage, HttpMethod.GET,null, new ParameterizedTypeReference<>() {});
            githubResponse = new GithubResponse(response);
            currentPage = githubResponse.getLinks().getNext();
            projects.addAll((List<Project>)githubResponse.getBody());
        }while (githubResponse.getLinks() != null && githubResponse.getLinks().getNext() != null);
        return projects;
    }

}
