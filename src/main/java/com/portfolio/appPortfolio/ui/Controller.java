package com.portfolio.appPortfolio.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.appPortfolio.entity.FileEntity;
import com.portfolio.appPortfolio.service.AdminService;
import com.portfolio.appPortfolio.service.ArticleService;
import com.portfolio.appPortfolio.service.FileService;
import com.portfolio.appPortfolio.service.ProjectService;
import com.portfolio.appPortfolio.shared.AdminDto;
import com.portfolio.appPortfolio.shared.ArticleDto;
import com.portfolio.appPortfolio.shared.ProjectDto;
import com.portfolio.appPortfolio.ui.model.request.AdminRequest;
import com.portfolio.appPortfolio.ui.model.request.ArticleRequest;
import com.portfolio.appPortfolio.ui.model.request.ProjectRequest;
import com.portfolio.appPortfolio.ui.model.rest.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.hateoas.Link;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("portfolioApp")
public class Controller {
    @Autowired
    AdminService adminService;
    @Autowired
    ProjectService projectService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    FileService fileService;
    @Autowired
    ArticleService articleService;

    public FileProjectRest uploadFileRest(MultipartFile file){

        FileEntity fileName= fileService.uploadFile(file);
        return new FileProjectRest( fileName.getFileId(),fileName.getFileName(),fileName.getFileDownloadUri(),
                file.getContentType(),
                file.getSize(), fileName.getData());
    }

    @PostMapping(path = "/admin")
    public AdminRest createAdmin(@RequestBody AdminRequest adminDetails){
       ModelMapper modelMapper =new ModelMapper();
        AdminDto adminDto= modelMapper.map(adminDetails, AdminDto.class);
        AdminDto createAdmin= adminService.createAdmin(adminDto);
        AdminRest returnedValue= modelMapper.map(createAdmin, AdminRest.class);
        return returnedValue;
    }

    @PostMapping(path = "/project")
    public EntityModel<ProjectRest> createProject(@RequestParam("files")MultipartFile[] files,
                                                  @RequestParam("projectDetails") String projectDetails) throws JsonProcessingException {

        ModelMapper mp=new ModelMapper();
        ProjectRequest project =objectMapper.readValue(projectDetails, ProjectRequest.class);
        List<FileProjectRest>filesList = Arrays.asList(files)
                .stream()
                .map(file ->uploadFileRest(file))
                .collect(Collectors.toList());


        project.setFiles(filesList);
        ProjectDto projectDto = mp.map(project, ProjectDto.class);
        ProjectDto createProject= projectService.createProject(projectDto);
        ProjectRest projectRest= mp.map(createProject, ProjectRest.class);
        Link projectsLink= WebMvcLinkBuilder.linkTo(Controller.class).slash("projects").withRel("projects");
        Link projectLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getProject(projectRest.getProjectId())).withRel("project");
        Link articlesLink= WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(Controller.class).getArticles(0,25)
        ).withRel("articles");


        return EntityModel.of(projectRest, Arrays.asList(projectsLink, projectLink, articlesLink));
    }

    //post an Article
    @PostMapping(path = "/article")
    public EntityModel<ArticleRest> createArticle (@RequestParam("file") MultipartFile[] files,
                                      @RequestParam("ArticleDetails") String articleDetails ) throws JsonProcessingException {
        ModelMapper mp= new ModelMapper();
        ArticleRequest article= objectMapper.readValue(articleDetails, ArticleRequest.class);
        List<FileProjectRest> filesList=  Arrays.asList(files)
                .stream()
                .map(file ->uploadFileRest(file))
                .collect(Collectors.toList());
        article.setFiles(filesList);
        ArticleDto articleDto=mp.map(article, ArticleDto.class);
        ArticleDto createArticle= articleService.createArticle(articleDto);
        ArticleRest articleRest= mp.map(createArticle,ArticleRest.class);
        Type listType=new TypeToken<List<FileRest>>(){}.getType();
        List<FileRest> rests=new ModelMapper().map(articleRest.getFiles(), listType);
        for (FileRest fileRest: rests){
            Link selfLink=
                    WebMvcLinkBuilder.linkTo(FilesController.class).slash("downloadFile").slash(fileRest.getFileId()).withSelfRel();
            fileRest.add(selfLink);
        }
        Link articlesLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getArticles(0,25)).withRel(
                        "articles");
        Link articleLink=
                WebMvcLinkBuilder.linkTo((WebMvcLinkBuilder.methodOn(Controller.class).getArticleById(articleRest.getArticleId()))).withRel("article");
        Link projectsLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getProjects(0,25)).withRel(
                        "articles");
        return  EntityModel.of(articleRest, articleLink, articlesLink, projectsLink);
    }

    //Get Projects and Project by projectId and the same for Articles
    @GetMapping(path = "/projects")
    public CollectionModel<ProjectRest> getProjects(@RequestParam(value = "page", defaultValue = "0") int page,
                                                    @RequestParam(value = "limit", defaultValue = "25") int limit){
        ModelMapper mp= new ModelMapper();
        List<ProjectRest> returnedResult=new ArrayList<>();
        List<ProjectDto> result=projectService.getProjects(page, limit);

        //for (ProjectDto projectDto:result){
           // ProjectRest projectRest= mp.map(projectDto, ProjectRest.class);
         //   returnedResult.add(projectRest);

       // }
        Type listType= new  TypeToken<List<ProjectRest>>(){}.getType();
        returnedResult=new ModelMapper().map(result, listType);
        for (ProjectRest pr: returnedResult){
            Link selfLink =
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getProject(pr.getProjectId())).withSelfRel();
            Type fileListType=new TypeToken<List<FileRest>>(){}.getType();
             List<FileRest> rests=new ModelMapper().map(pr.getFiles(), fileListType);
             for (FileRest fr:rests){
                 Link sefLink=
                         WebMvcLinkBuilder.linkTo(FilesController.class).slash("downloadFile").slash(fr.getFileId()).withSelfRel();
                 fr.add(sefLink);
             }
             pr.add(selfLink);
        }
        Link articlesLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getArticles(0,25)).withRel(
                        "articles");
        Link selfLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getProjects(page,limit)).withSelfRel();

        return  CollectionModel.of(returnedResult,articlesLink,selfLink);
    }

    @GetMapping(path = "/project/{id}")
    public EntityModel<ProjectRest> getProject(@PathVariable String id){
        ModelMapper mp= new ModelMapper();
        ProjectDto projectDto= projectService.getProject(id);
        ProjectRest projectRest= mp.map(projectDto, ProjectRest.class);

        Type listType=new TypeToken<List<FileRest>>(){}.getType();
        List<FileRest> rests=new ModelMapper().map(projectRest.getFiles(), listType);
        for (FileRest fileRest: rests){
            Link selfLink=
                    WebMvcLinkBuilder.linkTo(FilesController.class).slash("downloadFile").slash(fileRest.getFileId()).withSelfRel();
            fileRest.add(selfLink);
        }
        Link projectsLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getProjects(0,25)).withRel(
                        "projects");
        Link articlesLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getArticles(0,25)).withRel(
                        "articles").withRel("articles");
        Link selfLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getProject(id)).withSelfRel();

        return  EntityModel.of(projectRest, projectsLink, articlesLink,selfLink);
    }

    @GetMapping(path = "/articles")
    public CollectionModel<ArticleRest> getArticles(@RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "limit", defaultValue = "25")int limit){
        ModelMapper mp = new ModelMapper();
        List<ArticleRest> listArticle= new ArrayList<>();
        List<ArticleDto> listArticleDto=articleService.getArticles(page,limit);
     //   for (ArticleDto articleDto:listArticleDto){
       //     ArticleRest articleRest=mp.map(articleDto, ArticleRest.class);
         //   listArticle.add(articleRest);
        //}
        Type typeList= new TypeToken<List<ArticleRest>>(){}.getType();
        listArticle=new ModelMapper().map(listArticleDto, typeList);
        for (ArticleRest ar: listArticle){
            Type fileTypeList= new TypeToken<List<FileRest>>(){}.getType();
            List<FileRest> fileRestList= new ModelMapper().map(ar.getFiles(), fileTypeList);
            for (FileRest fr: fileRestList){
                Link selfLink=
                        WebMvcLinkBuilder.linkTo(FilesController.class).slash("downloadFile").slash(fr.getFileId()).withSelfRel();
                fr.add(selfLink);
            }
            Link selfLink=
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class)
                    .getArticleById(ar.getArticleId())).withSelfRel();
            ar.add(selfLink);
        }

        Link projectsLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getProjects(0,25)).withRel(
                        "projects");
        Link selfLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getArticles(page,limit)).withSelfRel();

        return CollectionModel.of(listArticle, projectsLink, selfLink);
    }

    @GetMapping(path = "/article/{articleId}")
    public EntityModel<ArticleRest> getArticleById(@PathVariable String articleId){
        ModelMapper mp=new ModelMapper();
        ArticleDto articleDto=articleService.getArticleById(articleId);
        ArticleRest rest=mp.map(articleDto, ArticleRest.class);
        Type listType=new TypeToken<List<FileRest>>(){}.getType();
        List<FileRest> fileRestList=new ModelMapper().map(rest.getFiles(), listType);
        for (FileRest fr: fileRestList){
            Link selfLink =
                    WebMvcLinkBuilder.linkTo(FilesController.class).slash("downloadFile").slash(fr.getFileId()).withSelfRel();
            fr.add(selfLink);
        }

        Link projectsLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getProjects(0,25)).withRel(
                        "Projects");
        Link articlesLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getProjects(0,25)).withRel(
                        "articles");
        Link selfLink=WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getArticleById(articleId)).withSelfRel();

        return EntityModel.of(rest, projectsLink, articlesLink, selfLink);
    }

    //Update Article and Project

    @PutMapping(path = "/project/{projectId}")
    public EntityModel<ProjectRest> updateProject(@PathVariable String projectId,@RequestBody ProjectRequest projectRequest){
        ProjectDto projectDto= new ProjectDto();
        ProjectRest projectRest= new ProjectRest();
        BeanUtils.copyProperties(projectRequest, projectDto);

        ProjectDto result= projectService.updateProject(projectId, projectDto);
        BeanUtils.copyProperties(result, projectRest);

        Link articlesLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getArticles(0,25)).withRel(
                        "projects");
        Link projectsLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getProjects(0,25)).withRel(
                        "projects");

        return EntityModel.of(projectRest, projectsLink, articlesLink);
    }

    @PutMapping(path = "/article/{articleId}")
    public EntityModel<ArticleRest> updateArticle(@PathVariable String articleId, @RequestBody ArticleRequest articleRequest){
        ArticleDto articleDto= new ArticleDto();
        ArticleRest result= new ArticleRest();
        BeanUtils.copyProperties(articleRequest, articleDto);

        ArticleDto returnedDto= articleService.updateArticle(articleId, articleDto);
        BeanUtils.copyProperties(returnedDto, result);

        Link articlesLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getArticles(0,25)).withRel(
                        "projects");
        Link projectsLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getProjects(0,25)).withRel(
                        "projects");

        return EntityModel.of(result, articlesLink, projectsLink);
    }

    //delete Project and Article

    @DeleteMapping(path = "/project/{projectId}")
    public EntityModel<OperationStatusResult> deleteProject(@PathVariable String projectId){

        OperationStatusResult operation=new OperationStatusResult();
        operation.setOperationName("DELETE");
        projectService.deleteProject( projectId);
        operation.setOperationResult(OperationStatus.SUCCESS.name());
        Link articlesLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getArticles(0,25)).withRel(
                        "projects");
        Link projectsLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getProjects(0,25)).withRel(
                        "projects");

        return EntityModel.of(operation, projectsLink, articlesLink);
    }
    @DeleteMapping(path = "/article/{articleId}")
    public EntityModel<OperationStatusResult> deleteArticle(@PathVariable String articleId){

        OperationStatusResult operation=new OperationStatusResult();
        operation.setOperationName("DELETE");
        articleService.deleteArticle(articleId);
        operation.setOperationResult(OperationStatus.SUCCESS.name());
        Link articlesLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getArticles(0,25)).withRel(
                        "projects");
        Link projectsLink=
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getProjects(0,25)).withRel(
                        "projects");
        return EntityModel.of(operation, articlesLink, projectsLink);
    }
}
