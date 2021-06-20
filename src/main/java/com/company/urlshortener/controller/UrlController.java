package com.company.urlshortener.controller;

import com.company.urlshortener.exception.BadRequestException;
import com.company.urlshortener.model.dto.ShortUrlRequestDto;
import com.company.urlshortener.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

@RestController
@RequestMapping("/v1/url")
@Slf4j
@Api(value = "url shortener",description = "shortening url service")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    @ApiOperation(value = "Url Shortener", notes = "Shorten input url")
    public ResponseEntity<String> shortenUrl(@RequestBody @Valid ShortUrlRequestDto shortUrlRequestDto,
                                             HttpServletRequest request){
        validateUrl(shortUrlRequestDto.getUrl());
        var shortUrl = urlService.generateShortUrl(shortUrlRequestDto.getUrl(),request);
        return new ResponseEntity<>(shortUrl, HttpStatus.OK);
    }

    @GetMapping("/forward/{shorturl}")
    public ResponseEntity<Void> redirectToUrl(@PathVariable(name = "shorturl",required = true) String shortUrl){
        String fullUrl = urlService.findFullUrlByShortUrl(shortUrl);
        if (fullUrl != null) {
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(fullUrl)).build();
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private void validateUrl(String url){
        String[] customSchemes = { "http", "https" };
        UrlValidator validator=new UrlValidator(customSchemes);
        if(!validator.isValid(url)){
            throw new BadRequestException("URL is not valid!");
        }
    }
}
