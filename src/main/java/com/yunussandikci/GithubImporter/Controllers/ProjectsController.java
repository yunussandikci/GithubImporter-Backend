package com.yunussandikci.GithubImporter.Controllers;

import com.yunussandikci.GithubImporter.Models.GithubAPI.Project;
import com.yunussandikci.GithubImporter.Repositories.OwnerRepository;
import com.yunussandikci.GithubImporter.Repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/projects")
public class ProjectsController {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/")
    public Iterable<Project> getAllImportedProject() {
        return projectRepository.findAll();
    }

    ///TODO: Delete crossorigin on deployment phase"
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/search/{keyword}")
    public Iterable<Project> searchInImportedProject(@PathVariable("keyword") String keyword) {
        return projectRepository.findProjectsByUsername(keyword);
    }

}