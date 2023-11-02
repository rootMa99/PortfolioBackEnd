package com.portfolio.appPortfolio.shared;

import com.portfolio.appPortfolio.entity.FileEntity;

import java.util.List;

public class ProjectDto {

    private long id;
    private String projectId;
    private String projectName;
    private String projectDescription;
    private List<FileDto> files;

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

    public List<FileDto> getFiles() {
        return files;
    }

    public void setFiles(List<FileDto> files) {
        this.files = files;
    }
}
