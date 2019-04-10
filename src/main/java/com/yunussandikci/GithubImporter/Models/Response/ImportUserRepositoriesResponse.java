package com.yunussandikci.GithubImporter.Models.Response;

public class ImportUserRepositoriesResponse extends BaseResponse {

    public ImportUserRepositoriesResponse(Integer importedRepositoryCount,String message){
        this.importedRepositoryCount = importedRepositoryCount;
        this.setMessage(message);
    }

    private Integer importedRepositoryCount;

    public Integer getImportedRepositoryCount() {
        return importedRepositoryCount;
    }

    public void setImportedRepositoryCount(Integer importedRepositoryCount) {
        this.importedRepositoryCount = importedRepositoryCount;
    }
}
