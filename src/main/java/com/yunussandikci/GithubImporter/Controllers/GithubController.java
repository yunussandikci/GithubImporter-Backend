package com.yunussandikci.GithubImporter.Controllers;


import com.yunussandikci.GithubImporter.Models.License;
import com.yunussandikci.GithubImporter.Models.Owner;
import com.yunussandikci.GithubImporter.Models.Project;
import com.yunussandikci.GithubImporter.Models.Response.ImportUserRepositoriesResponse;
import com.yunussandikci.GithubImporter.Repositories.LicenseRepository;
import com.yunussandikci.GithubImporter.Repositories.OwnerRepository;
import com.yunussandikci.GithubImporter.Repositories.ProjectRepository;
import com.yunussandikci.GithubImporter.Service.GithubService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/github")
@Api(value = "github", description = "Operations about importing projects from GitHub api.")
public class GithubController {

    @Autowired
    private GithubService githubService;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private LicenseRepository licenseRepository;

    @ApiOperation(value = "Import user repositories as a project into database.", response = ImportUserRepositoriesResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully imported projects."), @ApiResponse(code = 400, message = "The repositories related with username is not found in github or any error happened.")})
    @CrossOrigin(origins = "${CROSS_ORIGIN}")
    @GetMapping("/import/{username}")
    public ResponseEntity importUserRepositories(@PathVariable("username") String username) {
        try {
            List<Project> projectList = githubService.fetchUserRepositories(username);
            Set<Owner> ownerSet = new HashSet<>();
            Set<License> licenceSet = new HashSet<>();
            for (Project item : projectList) {
                ownerSet.add(item.getOwner());
                if (item.getLicense() != null)
                    licenceSet.add(item.getLicense());
            }
            licenseRepository.saveAll(licenceSet);
            ownerRepository.saveAll(ownerSet);
            projectRepository.saveAll(projectList);
            return ResponseEntity.ok(new ImportUserRepositoriesResponse(projectList.size(), null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ImportUserRepositoriesResponse(null, "An error happened."));
        }
    }

}
