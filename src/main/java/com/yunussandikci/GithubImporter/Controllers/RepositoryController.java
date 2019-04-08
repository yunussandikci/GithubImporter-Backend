package com.yunussandikci.GithubImporter.Controllers;


import com.yunussandikci.GithubImporter.Models.GithubAPI.Repository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("api/v1/repositories")
public class RepositoryController {

        @GetMapping("/{username}")
        public List<Repository> get(@PathVariable("username") String username){
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List<Repository>> response = restTemplate.exchange("https://api.github.com/users/" + username + "/repos",HttpMethod.GET,null,new ParameterizedTypeReference<List<Repository>>(){});
            List<Repository> employees = response.getBody();
            return employees;
        }

}
