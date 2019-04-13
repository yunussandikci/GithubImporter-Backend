package com.yunussandikci.GithubImporter.Utils;

public class GithubHelper {
    public static String buildRepositoryPageUrl(String username, Integer perPage, Integer page){
        return String.format("https://api.github.com/users/%s/repos?per_page=%d&page=%d", username, perPage, page);
    }
}
