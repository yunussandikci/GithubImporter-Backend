package com.yunussandikci.GithubImporter;

import com.yunussandikci.GithubImporter.Models.Owner;
import com.yunussandikci.GithubImporter.Models.Project;
import com.yunussandikci.GithubImporter.Service.GithubService;
import com.yunussandikci.GithubImporter.Utils.GithubHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GithubServiceTest {

    @InjectMocks
    private GithubService githubService;

    @Mock
    private RestTemplate restTemplate;

    Owner testOwner = new Owner();
    List<Project> firstPageProjectList = new ArrayList<>();
    List<Project> secondPageProjectList = new ArrayList<>();
    List<Project> thirdPageProjectList = new ArrayList<>();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        testOwner.setLogin("testuser");
        for(int i = 0; i<250; i++){
            Project testProject = new Project();
            testProject.setName("Project " + i);
            testProject.setOwner(testOwner);
            if(i < 100)
                firstPageProjectList.add(testProject);
            else if (i < 200)
                secondPageProjectList.add(testProject);
            else
                thirdPageProjectList.add(testProject);
        }
        HttpHeaders headersFirst = new HttpHeaders();
        headersFirst.add("Link", "<https://api.github.com/user/testuser/repos?per_page=100&page=2>; rel=\"next\", <https://api.github.com/user/testuser/repos?per_page=100&page=3>; rel=\"last\"");
        ResponseEntity<List<Project>> firstPageEntity = new ResponseEntity<>(firstPageProjectList,headersFirst, HttpStatus.OK);

        HttpHeaders headersSecond = new HttpHeaders();
        headersSecond.add("Link", "<https://api.github.com/user/testuser/repos?per_page=100&page=3>; rel=\"next\", <https://api.github.com/user/testuser/repos?per_page=100&page=3>; rel=\"last\"");
        ResponseEntity<List<Project>>  secondPageEntity = new ResponseEntity<>(secondPageProjectList,headersSecond, HttpStatus.OK);
        HttpHeaders headersThird = new HttpHeaders();
        ResponseEntity<List<Project>>  thirdPageEntity = new ResponseEntity<>(thirdPageProjectList,headersThird, HttpStatus.OK);
        when(restTemplate.exchange(eq(GithubHelper.buildRepositoryPageUrl("testuser",100,1)),ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(),any(ParameterizedTypeReference.class))).thenReturn(firstPageEntity);
        when(restTemplate.exchange(eq(GithubHelper.buildRepositoryPageUrl("testuser",100,2)),ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(),any(ParameterizedTypeReference.class))).thenReturn(secondPageEntity);
        when(restTemplate.exchange(eq(GithubHelper.buildRepositoryPageUrl("testuser",100,3)),ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(),any(ParameterizedTypeReference.class))).thenReturn(thirdPageEntity);
    }

    @Test
    public void testGithubService() {
        List<Project> projectList = githubService.fetchUserRepositories("testuser");
        verify(restTemplate).exchange(eq(GithubHelper.buildRepositoryPageUrl("testuser",100,1)),ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(),any(ParameterizedTypeReference.class));
        verify(restTemplate).exchange(eq(GithubHelper.buildRepositoryPageUrl("testuser",100,2)),ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(),any(ParameterizedTypeReference.class));
        verify(restTemplate).exchange(eq(GithubHelper.buildRepositoryPageUrl("testuser",100,3)),ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(),any(ParameterizedTypeReference.class));
        List<Project> allPageProjects = new ArrayList<>();
        allPageProjects.addAll(firstPageProjectList);
        allPageProjects.addAll(secondPageProjectList);
        allPageProjects.addAll(thirdPageProjectList);
        assertThat(projectList,equalTo(allPageProjects));
    }
}

