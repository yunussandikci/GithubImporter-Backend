package com.yunussandikci.GithubImporter;

import com.yunussandikci.GithubImporter.Controllers.ProjectsController;
import com.yunussandikci.GithubImporter.Models.License;
import com.yunussandikci.GithubImporter.Models.Owner;
import com.yunussandikci.GithubImporter.Models.Project;
import com.yunussandikci.GithubImporter.Models.Response.ImportedProjectsResponse;
import com.yunussandikci.GithubImporter.Models.Response.ProjectDetailResponse;
import com.yunussandikci.GithubImporter.Repositories.OwnerRepository;
import com.yunussandikci.GithubImporter.Repositories.ProjectRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectsControllerTest {

    @InjectMocks
    private ProjectsController projectsController;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private ProjectRepository projectRepository;

    private Owner testOwner;
    private License testLicense;
    private Project testProject;
    private Project testProject2;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        testOwner = new Owner();
        testOwner.setId(1);
        testOwner.setLogin("testOwner");
        testLicense = new License();
        testLicense.setId(1);
        testLicense.setName("testLicense");
        testLicense.setUrl("testUrl");
        testProject = new Project();
        testProject.setName("testProject");
        testProject.setId(1);
        testProject.setOwner(testOwner);
        testProject.setLicense(testLicense);
        testProject2 = new Project();
        testProject2.setName("testProject2");
        testProject2.setId(2);
        testProject2.setOwner(testOwner);
        when(ownerRepository.findOwnerByUsername(testOwner.getLogin())).thenReturn(testOwner);
        when(projectRepository.findProjectsByOwnerId(testOwner.getId())).thenReturn(Arrays.asList(testProject,testProject2));
        when(projectRepository.findById(testProject.getId())).thenReturn(Optional.of(testProject));
    }


    @Test
    public void testImportedProjects() {
        ResponseEntity responseEntity = projectsController.ImportedProjects(testOwner.getLogin());
        ImportedProjectsResponse response  = (ImportedProjectsResponse)responseEntity.getBody();
        verify(ownerRepository).findOwnerByUsername(testOwner.getLogin());
        verify(projectRepository).findProjectsByOwnerId(1);
        assertThat(response.getImportedProjects(),hasItems(testProject,testProject2));
        assertThat(response.getImportedProjects().get(0).getLicense(),equalTo(testLicense));
        assertThat(response.getImportedProjects().get(0).getOwner(),equalTo(testOwner));
        ResponseEntity wrongresponseEntity = projectsController.ImportedProjects("wrongUsername");
        assertThat(wrongresponseEntity.getStatusCodeValue(),equalTo(404));
    }

    @Test
    public void testProjectDetail() {
        ResponseEntity responseEntity = projectsController.ProjectDetail(testProject.getId());
        ProjectDetailResponse response  = (ProjectDetailResponse)responseEntity.getBody();
        assertThat(response.getProject(),equalTo(testProject));
        ResponseEntity wrongresponseEntity = projectsController.ProjectDetail(0);
        ProjectDetailResponse wrongResponse  = (ProjectDetailResponse)wrongresponseEntity.getBody();
        assertThat(wrongresponseEntity.getStatusCodeValue(),equalTo(404));
        assertNotNull(wrongResponse.getMessage());
    }


}
