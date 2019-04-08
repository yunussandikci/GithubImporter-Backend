package com.yunussandikci.GithubImporter.Controllers;

import com.yunussandikci.GithubImporter.Models.GithubAPI.Project;
import com.yunussandikci.GithubImporter.Repositories.OwnerRepository;
import com.yunussandikci.GithubImporter.Repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/projects")
public class ProjectsController {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping("/")
    public Iterable<Project> getAllImportedProject() {
        return projectRepository.findAll();
    }

    @GetMapping("/search/{keyword}")
    public Iterable<Project> searchInImportedProject(@PathVariable("keyword") String keyword) {
        return projectRepository.findProjectsByUsername(keyword);
    }

}