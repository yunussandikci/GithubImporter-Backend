package com.yunussandikci.GithubImporter.Service;

import com.yunussandikci.GithubImporter.Models.GithubAPI.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GithubService {

    private RestTemplate restTemplate;

    @Autowired
    public GithubService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public List<Project> fetchUserRepositories(String username){
        ResponseEntity<List<Project>> response = restTemplate.exchange("https://api.github.com/users/" + username + "/repos", HttpMethod.GET,null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

}
