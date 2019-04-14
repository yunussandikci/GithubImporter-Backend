package com.yunussandikci.GithubImporter.Controllers;

import com.yunussandikci.GithubImporter.Models.Owner;
import com.yunussandikci.GithubImporter.Models.Project;
import com.yunussandikci.GithubImporter.Models.Response.ImportedProjectsResponse;
import com.yunussandikci.GithubImporter.Models.Response.ProjectDetailResponse;
import com.yunussandikci.GithubImporter.Repositories.OwnerRepository;
import com.yunussandikci.GithubImporter.Repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/projects")
public class ProjectsController {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/search/{username}")
    public ResponseEntity ImportedProjects(@PathVariable("username") String username) {
        Owner owner = ownerRepository.findOwnerByUsername(username);
        if(owner == null){
            return ResponseEntity.status(404).body(new ImportedProjectsResponse(null,null,"User Not Found :("));
        }else{
            List<Project> projects = projectRepository.findProjectsByOwnerId(owner.getId());
            return ResponseEntity.ok(new ImportedProjectsResponse(projects,owner,null));
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}")
    public ResponseEntity ProjectDetail(@PathVariable("id") Integer id) {
        try{
            return ResponseEntity.ok(new ProjectDetailResponse(projectRepository.findById(id).get(),""));
        }catch (NoSuchElementException exception){
            return ResponseEntity.status(404).body(new ProjectDetailResponse(null,"Project Not Found"));
        }
    }

}