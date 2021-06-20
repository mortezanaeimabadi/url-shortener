package com.company.urlshortener.service;

import com.company.urlshortener.exception.BadRequestException;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceUnitTest {

    @Mock
    private UrlService urlService;

    private HashFunction hashFunction;

    @Before
    public void setUp(){
        this.hashFunction = Hashing.murmur3_32();
    }

    @Test
    public void shouldGenerate_58f3ae21_For_google_url() {
        assertThat(hashFunction.hashString("http://google.com", StandardCharsets.UTF_8).toString(),
                is("58f3ae21"));
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowBadRequestExceptionForInvalidShortenUrl() {
        MockHttpServletRequest request=new MockHttpServletRequest();
        request.setServerName("http://localhost:9001/v1/url/");
        request.setRequestURI("/shorten");

        given(urlService.generateShortUrl("ftp://google.com",request)).willThrow(new BadRequestException("invalid request!"));
        urlService.generateShortUrl("ftp://google.com", request);

    }

}
