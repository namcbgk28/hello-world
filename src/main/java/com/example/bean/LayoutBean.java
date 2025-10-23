package com.example.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("layoutBean")
@SessionScoped
public class LayoutBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private String currentPage = "/employee/list.xhtml";

    public String getCurrentPage() {
        return currentPage;
    }

    public void loadPage(String pagePath) {
        this.currentPage = pagePath;
        System.out.println("Loaded page: " + pagePath);
    }
}