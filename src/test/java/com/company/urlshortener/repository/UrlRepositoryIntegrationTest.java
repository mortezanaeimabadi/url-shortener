package com.company.urlshortener.repository;

import com.company.urlshortener.model.entity.Url;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UrlRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UrlRepository urlRepository;

    @Test
    public void shouldStoreShortUrlAlongWithFullUrl() {
        var newUrl = Url.builder().fullUrl("http://google.com").shortUrl("abcd").build();
        urlRepository.save(newUrl);
        org.hamcrest.MatcherAssert.assertThat(newUrl.getId(),notNullValue());
    }

    @Test
    public void shouldFindFullUrlByShortUrl() {
        var newUrl = Url.builder().fullUrl("http://google.com").shortUrl("abcd").build();
        urlRepository.save(newUrl);

        var findUrl = urlRepository.findByShortUrl(newUrl.getShortUrl());
        org.hamcrest.MatcherAssert.assertThat(findUrl.getFullUrl(),equalTo(newUrl.getFullUrl()));
    }

    @Test
    public void shouldFindShortUrlByFullUrl() {
        var newUrl = Url.builder().fullUrl("http://google.com").shortUrl("abcd").build();
        urlRepository.save(newUrl);

        var findUrl = urlRepository.findByFullUrl(newUrl.getFullUrl());
        org.hamcrest.MatcherAssert.assertThat(findUrl.getShortUrl(),equalTo(newUrl.getShortUrl()));
    }

}
