package com.yunussandikci.GithubImporter.Models.Response;

import com.yunussandikci.GithubImporter.Models.GithubAPI.Owner;
import com.yunussandikci.GithubImporter.Models.GithubAPI.Project;

import java.util.List;

public class ProjectDetailResponse extends BaseResponse {

    public ProjectDetailResponse(Project project, String message) {
        this.project = project;
        this.setMessage(message);
    }

    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

