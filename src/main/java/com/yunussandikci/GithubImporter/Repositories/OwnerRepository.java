package com.yunussandikci.GithubImporter.Repositories;

import com.yunussandikci.GithubImporter.Models.GithubAPI.Owner;
import com.yunussandikci.GithubImporter.Models.GithubAPI.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Integer> {

    //TODO: Check for sql injection
    @Query(value = "SELECT * FROM owner o where o.login = :username", nativeQuery = true)
    Owner findOwnerByUsername(@Param("username") String username);
}
