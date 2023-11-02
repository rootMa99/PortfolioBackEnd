package com.portfolio.appPortfolio.service.impl;

import com.portfolio.appPortfolio.entity.ProjectEntity;
import com.portfolio.appPortfolio.exception.ServiceException;
import com.portfolio.appPortfolio.repositories.ProjectRepository;
import com.portfolio.appPortfolio.service.ProjectService;
import com.portfolio.appPortfolio.shared.FileDto;
import com.portfolio.appPortfolio.shared.ProjectDto;
import com.portfolio.appPortfolio.shared.Utils;
import com.portfolio.appPortfolio.ui.model.rest.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    Utils utils;

    @Override
    public ProjectDto createProject(ProjectDto projectDto) {

        ModelMapper mp= new ModelMapper();
        mp.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        for (int i=0; i<projectDto.getFiles().size();i++){
            FileDto fileDto= projectDto.getFiles().get(i);
            fileDto.setProjectDetails(projectDto);
            projectDto.getFiles().set(i, fileDto);
        }
        ProjectEntity project= mp.map(projectDto, ProjectEntity.class);
        project.setProjectId(utils.generateProjectId(22));
        ProjectEntity resutl = projectRepository.save(project);
        ProjectDto returnRes= mp.map(resutl, ProjectDto.class);

        return returnRes;
    }

    @Override
    public List<ProjectDto> getProjects(int page, int limit) {

        ModelMapper mp=new ModelMapper();
        mp.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<ProjectDto> returnedResult=new ArrayList<>();
        Pageable pageableRequest= PageRequest.of(page,limit);
        Page<ProjectEntity>pages=projectRepository.findAll(pageableRequest);
        List<ProjectEntity>projectEntities= pages.getContent();
        if (projectEntities.size()==0)throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()+" List of Projects is Empty");

        for (ProjectEntity projectEntity:projectEntities){
            ProjectDto projectDto= mp.map(projectEntity,ProjectDto.class);
            returnedResult.add(projectDto);
        }

        return returnedResult;
    }

    @Override
    public ProjectDto getProject(String id) {
        ModelMapper mp= new ModelMapper();
        mp.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ProjectEntity project= projectRepository.findByProjectId(id);
        if (project==null)throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()+" ID is: "+id);
        ProjectDto projectDto= mp.map(project, ProjectDto.class);

        return projectDto;
    }

    @Override
    public ProjectDto updateProject(String projectId, ProjectDto projectDto) {
        ModelMapper mp= new ModelMapper();
        mp.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ProjectEntity projectEntity= projectRepository.findByProjectId(projectId);
        if (projectEntity==null)throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()+" ID is: "+projectId);
        if (projectDto.getProjectDescription()!=null){
            projectEntity.setProjectDescription(projectDto.getProjectDescription());
        }
        if (projectDto.getProjectName()!=null){
            projectEntity.setProjectName(projectDto.getProjectName());
        }
        ProjectEntity returnedProject;
        try{
            returnedProject=projectRepository.save(projectEntity);
        }catch( Exception e){
            throw new ServiceException(ErrorMessages.COULD_NOT_UPDATE_RECORD.getErrorMessage());
        }
        ProjectDto returnedProjectDto=mp.map(returnedProject, ProjectDto.class);
        return returnedProjectDto;
    }

    @Override
    public void deleteProject(String projectId) {
        ProjectEntity projectEntity= projectRepository.findByProjectId(projectId);
        if (projectEntity==null) throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        try{
            projectRepository.delete(projectEntity);
        }catch (Exception e){
            throw new ServiceException(ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessage());
        }
    }
}
