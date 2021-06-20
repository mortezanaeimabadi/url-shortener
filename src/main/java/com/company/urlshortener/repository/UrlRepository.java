package com.company.urlshortener.repository;

import com.company.urlshortener.model.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url,Long> {
    Url findByShortUrl(String shortUrl);
    Url findByFullUrl(String fullUrl);
}
