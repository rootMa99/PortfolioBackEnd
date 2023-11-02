package com.portfolio.appPortfolio.service;

import com.portfolio.appPortfolio.shared.ProjectDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProjectService {
  ProjectDto createProject(ProjectDto projectDto);

  List<ProjectDto> getProjects(int page, int limit);

  ProjectDto getProject(String id);


  ProjectDto updateProject(String projectId, ProjectDto projectDto);

  void deleteProject(String projectId);

}
