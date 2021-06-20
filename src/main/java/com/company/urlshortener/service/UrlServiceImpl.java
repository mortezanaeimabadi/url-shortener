package com.company.urlshortener.service;

import com.company.urlshortener.exception.BadRequestException;
import com.company.urlshortener.model.entity.Url;
import com.company.urlshortener.repository.UrlRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class UrlServiceImpl implements UrlService {

    @Autowired
    UrlRepository repository;

    @Override
    public String generateShortUrl(String url, HttpServletRequest request) {

        Url byFullUrl = repository.findByFullUrl(url);
        if(byFullUrl!=null)
            return getShortUrl(request,byFullUrl.getShortUrl());

        String urlHash = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
        var urlEntity = Url.builder().shortUrl(urlHash).fullUrl(url).build();
        repository.save(urlEntity);
        return getShortUrl(request,urlHash);
    }

    private String getShortUrl(HttpServletRequest request,String urlHash) {
        try{
            URL url = new URL(request.getRequestURL().toString());
            String protocol = url.getProtocol();
            String host = url.getHost();
            int port = url.getPort();

            if (port == -1) {
                return String.format("%s://%s/v1/url/forward/%s", protocol, host,urlHash);
            } else {
                return String.format("%s://%s:%d/v1/url/forward/%s", protocol, host, port,urlHash);
            }
        }catch (MalformedURLException e){
            throw new BadRequestException("invalid request!");
        }
    }


    @Override
    public String findFullUrlByShortUrl(String shortUrl) {
        return repository.findByShortUrl(shortUrl).getFullUrl();
    }
}
