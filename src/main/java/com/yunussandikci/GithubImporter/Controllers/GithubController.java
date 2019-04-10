package com.yunussandikci.GithubImporter.Controllers;


import com.yunussandikci.GithubImporter.Models.GithubAPI.Project;
import com.yunussandikci.GithubImporter.Models.Response.ImportUserRepositoriesResponse;
import com.yunussandikci.GithubImporter.Repositories.LicenseRepository;
import com.yunussandikci.GithubImporter.Repositories.OwnerRepository;
import com.yunussandikci.GithubImporter.Repositories.ProjectRepository;
import com.yunussandikci.GithubImporter.Service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("api/v1/github")
public class GithubController {

    @Autowired
    private GithubService githubService;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private LicenseRepository licenseRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/import/{username}")
    public ResponseEntity importUserRepositories(@PathVariable("username") String username){
        try{
            List<Project> repositoryResponseBody = githubService.fetchUserRepositories(username);
            for (Project item :repositoryResponseBody){
                if (item.getLicense() != null) {
                    licenseRepository.save(item.getLicense());
                }
                ownerRepository.save(item.getOwner());
                projectRepository.save(item);
            }
            return ResponseEntity.ok(new ImportUserRepositoriesResponse(repositoryResponseBody.size()-1,null));
        }catch (Exception e){
            return ResponseEntity.status(400).body(new ImportUserRepositoriesResponse(null,"An error happened."));
        }
    }

}
