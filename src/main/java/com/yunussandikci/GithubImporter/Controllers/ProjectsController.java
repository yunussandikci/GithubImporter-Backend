package com.yunussandikci.GithubImporter.Controllers;

import com.yunussandikci.GithubImporter.Models.Owner;
import com.yunussandikci.GithubImporter.Models.Project;
import com.yunussandikci.GithubImporter.Models.Response.ImportedProjectsResponse;
import com.yunussandikci.GithubImporter.Models.Response.ProjectDetailResponse;
import com.yunussandikci.GithubImporter.Repositories.OwnerRepository;
import com.yunussandikci.GithubImporter.Repositories.ProjectRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/projects")
@Api(value = "projects", description = "Operations about imported projects in the database.")
public class ProjectsController {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @ApiOperation(value = "View a list of imported projects of a user", response = ImportedProjectsResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list."), @ApiResponse(code = 404, message = "The projects related with username is not found.")})
    @CrossOrigin(origins = {"http://${CROSS_ORIGIN}", "https://${CROSS_ORIGIN}"})
    @GetMapping("/search/{username}")
    public ResponseEntity ImportedProjects(@PathVariable("username") String username) {
        Owner owner = ownerRepository.findOwnerByUsername(username);
        if (owner == null) {
            return ResponseEntity.status(404).body(new ImportedProjectsResponse(null, null, "User Not Found :("));
        } else {
            List<Project> projects = projectRepository.findProjectsByOwnerId(owner.getId());
            return ResponseEntity.ok(new ImportedProjectsResponse(projects, owner, null));
        }
    }

    @ApiOperation(value = "View one imported project details.", response = ProjectDetailResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved project."), @ApiResponse(code = 404, message = "The imported project with id is not found.")})
    @CrossOrigin(origins = {"http://${CROSS_ORIGIN}", "https://${CROSS_ORIGIN}"})
    @GetMapping("/{id}")
    public ResponseEntity ProjectDetail(@PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok(new ProjectDetailResponse(projectRepository.findById(id).get(), ""));
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(404).body(new ProjectDetailResponse(null, "Project Not Found"));
        }
    }

}