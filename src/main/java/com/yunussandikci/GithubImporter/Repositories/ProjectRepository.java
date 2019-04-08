package com.yunussandikci.GithubImporter.Repositories;

import com.yunussandikci.GithubImporter.Models.GithubAPI.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Integer> { }