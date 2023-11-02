package com.portfolio.appPortfolio.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String projectId;
    @Column(nullable = false)
    private String projectName;
    @Column(nullable = false, length=500)
    private String projectDescription;
    @OneToMany(mappedBy = "projectDetails", cascade = CascadeType.ALL)
    private List<FileEntity> files;

    public ProjectEntity() {
    }

    public ProjectEntity(String projectId, String projectName, String projectDescription, List<FileEntity> files) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.files = files;
    }

    public long getId() {
        return id;
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

    public List<FileEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FileEntity> files) {
        this.files = files;
    }
}
