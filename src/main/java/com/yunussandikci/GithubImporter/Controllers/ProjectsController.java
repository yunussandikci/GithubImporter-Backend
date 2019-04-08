package com.yunussandikci.GithubImporter.Controllers;

import com.yunussandikci.GithubImporter.Models.GithubAPI.Project;
import com.yunussandikci.GithubImporter.Repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/projects")
public class ProjectsController {
    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/")
    public Iterable<Project> getAllImportedProject() {
        return projectRepository.findAll();
    }

}