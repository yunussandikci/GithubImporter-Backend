package com.yunussandikci.GithubImporter;

import com.yunussandikci.GithubImporter.Controllers.GithubController;
import com.yunussandikci.GithubImporter.Controllers.ProjectsController;
import com.yunussandikci.GithubImporter.Models.License;
import com.yunussandikci.GithubImporter.Models.Owner;
import com.yunussandikci.GithubImporter.Models.Project;
import com.yunussandikci.GithubImporter.Models.Response.ImportUserRepositoriesResponse;
import com.yunussandikci.GithubImporter.Models.Response.ImportedProjectsResponse;
import com.yunussandikci.GithubImporter.Models.Response.ProjectDetailResponse;
import com.yunussandikci.GithubImporter.Repositories.LicenseRepository;
import com.yunussandikci.GithubImporter.Repositories.OwnerRepository;
import com.yunussandikci.GithubImporter.Repositories.ProjectRepository;
import com.yunussandikci.GithubImporter.Service.GithubService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GithubControllerTest {

    @InjectMocks
    private GithubController githubController;

    @Mock
    private GithubService githubService;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private LicenseRepository licenseRepository;

    Owner testOwner = new Owner();
    License testLicense = new License();

    List<Project> projectList = new ArrayList<>();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        testOwner.setLogin("testOwnerLogin");
        testLicense.setId(1);
        for(int i=0;i<250;i++){
            Project testProject = new Project();
            testProject.setOwner(testOwner);
            testProject.setName("testProject " + i);
            if(i%2 == 0)
                testProject.setLicense(testLicense);
            projectList.add(testProject);
        }
    }

    @Test
    public void testImportUserRepositories() {
        when(githubService.fetchUserRepositories(testOwner.getLogin())).thenReturn(projectList);
        ResponseEntity responseEntity = githubController.importUserRepositories(testOwner.getLogin());
        ImportUserRepositoriesResponse response = (ImportUserRepositoriesResponse) responseEntity.getBody();
        verify(githubService).fetchUserRepositories(testOwner.getLogin());
        assertThat(responseEntity.getStatusCodeValue(),equalTo(200));
        assertThat(response.getImportedRepositoryCount(),equalTo(projectList.size()));
    }

}
