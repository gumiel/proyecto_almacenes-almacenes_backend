package com.gestion.almacenes.helpers.app_dictionary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppDictionaryConfig {

    @Value("${dictionary.gitlab.api_url}")
    private String gitlabApiUrl;

    @Value("${dictionary.gitlab.project_id}")
    private String gitlabProjectId;

    @Value("${dictionary.gitlab.page_slug}")
    private String gitlabPageSlug;

    @Value("${dictionary.gitlab.private_token}")
    private String gitlabPrivateToken;

    @Value("${dictionary.folder.package}")
    private String dictionaryFolderPackage;

    @Value("${dictionary.route.package}")
    private String dictionaryRoutePackage;

    public String getGitlabApiUrl() {
        return gitlabApiUrl;
    }

    public String getGitlabProjectId() {
        return gitlabProjectId;
    }

    public String getGitlabPageSlug() {
        return gitlabPageSlug;
    }

    public String getGitlabPrivateToken() {
        return gitlabPrivateToken;
    }

    public String getDictionaryFolderPackage() {
        return dictionaryFolderPackage;
    }

    public String getDictionaryRoutePackage() {
        return dictionaryRoutePackage;
    }
}