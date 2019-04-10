package com.yunussandikci.GithubImporter.Repositories;

import com.yunussandikci.GithubImporter.Models.GithubAPI.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Integer> {


    //TODO: Check for sql injection
    @Query(value = "SELECT * FROM project p inner join owner o where p.owner_id=o.id and o.login = :username", nativeQuery = true)
    List<Project> findProjectsByUsername(@Param("username") String username);
}