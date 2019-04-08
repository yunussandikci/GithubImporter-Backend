package com.yunussandikci.GithubImporter.Controllers;


import com.yunussandikci.GithubImporter.Models.GithubAPI.Project;
import com.yunussandikci.GithubImporter.Repositories.LicenseRepository;
import com.yunussandikci.GithubImporter.Repositories.OwnerRepository;
import com.yunussandikci.GithubImporter.Repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("api/v1/github")
public class GithubController {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private LicenseRepository licenseRepository;

    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/search/{username}")
    public List<Project> searchUserRepositories(@PathVariable("username") String username){
        ResponseEntity<List<Project>> response = restTemplate.exchange("https://api.github.com/users/" + username + "/repos",HttpMethod.GET,null, new ParameterizedTypeReference<>() {});
        List<Project> repositories = response.getBody();
        return repositories;
    }

    @GetMapping("/import/{username}")
    public ResponseEntity<String> importUserRepositories(@PathVariable("username") String username){
        ResponseEntity<List<Project>> response = restTemplate.exchange("https://api.github.com/users/" + username + "/repos",HttpMethod.GET,null, new ParameterizedTypeReference<>() {});
        List<Project> repositoryResponseBody = response.getBody();
        for (Project item :repositoryResponseBody){
            if (item.getLicense() != null) {
                licenseRepository.save(item.getLicense());
            }
            ownerRepository.save(item.getOwner());
            projectRepository.save(item);
        }

        return ResponseEntity.ok("");
    }

}
