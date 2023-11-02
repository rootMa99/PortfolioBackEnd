package com.portfolio.appPortfolio.ui.model.rest;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class ProjectRest extends RepresentationModel<ProjectRest> {
    private String projectId;
    private String projectName;
    private String projectDescription;
    private List<FileRest> files;


    public ProjectRest(String projectId, String projectName, String projectDescription, List<FileRest> files) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.files = files;
    }

    public ProjectRest() {
    }


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public List<FileRest> getFiles() {
        return files;
    }

    public void setFiles(List<FileRest> files) {
        this.files = files;
    }
}
