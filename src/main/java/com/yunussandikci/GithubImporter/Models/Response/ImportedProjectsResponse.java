package com.yunussandikci.GithubImporter.Models.Response;

import com.yunussandikci.GithubImporter.Models.Owner;
import com.yunussandikci.GithubImporter.Models.Project;

import java.util.List;

public class ImportedProjectsResponse extends BaseResponse {

    public ImportedProjectsResponse(List<Project> importedProjects, Owner owner, String message) {
        this.importedProjects = importedProjects;
        this.owner = owner;
        this.setMessage(message);
    }

    private List<Project> importedProjects;
    private Owner owner;

    public List<Project> getImportedProjects() {
        return importedProjects;
    }

    public void setImportedProjects(List<Project> importedProjects) {
        this.importedProjects = importedProjects;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
