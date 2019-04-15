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

    public GithubService() {
        restTemplate = new RestTemplate();
    }

    /**
     * This Runnable is used as page fetcher for fetchUserRepositories function.
     * It fetches a repository page than add this fetched page repositories into projects object.
     */
    public class fetchUserRepositoriesRunnable implements Runnable{
        private String pageUrl;
        private List<Project> projects;

        public fetchUserRepositoriesRunnable(String pageUrl, List<Project> projects) {
            this.pageUrl = pageUrl;
            this.projects = projects;
        }

        @Override
        public void run() {
            ResponseEntity<List<Project>> response = restTemplate.exchange(pageUrl, HttpMethod.GET,null, new ParameterizedTypeReference<List<Project>>() {});
            projects.addAll(response.getBody());
        }
    }

    /**
     * This function used to fetch user repositories from GitHub API.
     * Firstly, it fetches the first page than calculates how many pages remain.
     * Secondly, it splits all remaining pages into Threads and waits until all Threads finish.
     * Finally, it returns all repositories any specific user has.
     * @param username
     * @return List of fetched projects
     */
    public List<Project> fetchUserRepositories(String username){
        Integer perPage = 100;
        List<Project> projects = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        ResponseEntity<List<Project>> firstPage = restTemplate.exchange((buildRepositoryPageUrl(username, perPage,1)), HttpMethod.GET,null, new ParameterizedTypeReference<List<Project>>() {});
        GithubResponse githubResponse = new GithubResponse(firstPage);
        projects.addAll((List<Project>)githubResponse.getBody());
        if(githubResponse.getLinks().getLast() == null)
            return projects;
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
