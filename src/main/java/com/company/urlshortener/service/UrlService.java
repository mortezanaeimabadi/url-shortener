package com.company.urlshortener.service;

import javax.servlet.http.HttpServletRequest;

public interface UrlService {
    String generateShortUrl(String url, HttpServletRequest request);
    String findFullUrlByShortUrl(String id);
}
