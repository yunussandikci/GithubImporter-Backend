package com.yunussandikci.GithubImporter.Repositories;

import com.yunussandikci.GithubImporter.Models.GithubAPI.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Integer> {

    //TODO: Check for sql injection
    @Query(value = "SELECT * FROM project p where p.owner_id = :owner_id", nativeQuery = true)
    List<Project> findProjectsByOwnerId(@Param("owner_id") Integer owner_id);
}