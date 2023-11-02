package com.portfolio.appPortfolio.ui.model.request;

import com.portfolio.appPortfolio.ui.model.rest.FileProjectRest;


import java.util.List;

public class ProjectRequest {

    private String projectId;
    private String projectName;
    private String projectDescription;
    private List<FileProjectRest> files;

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

    public List<FileProjectRest> getFiles() {
        return files;
    }

    public void setFiles(List<FileProjectRest> files) {
        this.files = files;
    }
}
