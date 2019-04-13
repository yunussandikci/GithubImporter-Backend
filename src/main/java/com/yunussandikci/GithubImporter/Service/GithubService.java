package com.yunussandikci.GithubImporter.Service;

import com.yunussandikci.GithubImporter.Models.GithubAPI.GithubResponse;
import static com.yunussandikci.GithubImporter.Utils.GithubHelper.buildRepositoryPageUrl;
import com.yunussandikci.GithubImporter.Models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class GithubService {

    private RestTemplate restTemplate;


    @Autowired
    public GithubService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public class fetchUserRepositoriesRunnable implements Runnable{
        private String pageUrl;
        private List<Project> projects;

        public fetchUserRepositoriesRunnable(String pageUrl, List<Project> projects) {
            this.pageUrl = pageUrl;
            this.projects = projects;
        }

        @Override
        public void run() {
            ResponseEntity<List<Project>> response = restTemplate.exchange(pageUrl, HttpMethod.GET,null, new ParameterizedTypeReference<>() {});
            projects.addAll(response.getBody());
        }
    }

    public List<Project> fetchUserRepositories(String username){
        Integer perPage = 100;
        List<Project> projects = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        ResponseEntity<List<Project>> firstPage = restTemplate.exchange((buildRepositoryPageUrl(username, perPage,1)), HttpMethod.GET,null, new ParameterizedTypeReference<>() {});
        GithubResponse githubResponse = new GithubResponse(firstPage);
        projects.addAll((List<Project>)githubResponse.getBody());
        Integer lastPage = Integer.parseInt(githubResponse.getLinks().getLast().split("=")[2]);

        for(int i=2;i<=lastPage;i++){
            Runnable fecthRepositoryRunnable = new fetchUserRepositoriesRunnable(buildRepositoryPageUrl(username, perPage,i),projects);
            Thread pageThread = new Thread(fecthRepositoryRunnable);
            pageThread.start();
            threads.add(pageThread);
        }

        for (int i=0;i<threads.size();i++){
            try{
                threads.get(i).join();
            }catch (Exception ignored){ }
        }
        return projects;
    }

}
