package com.company.urlshortener.controller;

import com.company.urlshortener.exception.BadRequestException;

import com.company.urlshortener.model.dto.ShortUrlRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UrlControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    private String baseUrl;

    @Before
    public void setUp() {
        baseUrl="/v1/url";
    }

    @Test
    public void givenValidUrlShouldReturnStatusOk() throws Exception {
        ShortUrlRequestDto requestDto = ShortUrlRequestDto.builder().
                url("https://www.wikipedia.org/").build();

        mvc.perform(post(baseUrl+"/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowExceptionIfUrlHasInvalidProtocolFTP() throws Exception {
        ShortUrlRequestDto requestDto = ShortUrlRequestDto.builder().
                url("ftp://www.wikipedia.org/").build();

            MvcResult result = mvc.perform(post(baseUrl + "/shorten")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(getJson(requestDto)))
                    .andExpect(status().isInternalServerError())
                    .andReturn();

            String responseString = result.getResponse().getContentAsString();
            Assert.assertFalse(responseString.startsWith("http"));
    }

    @Test
    public void givenValidUrlShouldNotStoreDuplicateIfAlreadyExists() throws Exception {
        ShortUrlRequestDto requestDto = ShortUrlRequestDto.builder().
                url("https://www.wikipedia.org/").build();

        MvcResult resultUrl1 = mvc.perform(post(baseUrl+"/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult resultUrl2 = mvc.perform(post(baseUrl+"/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        String responseUrl1 = resultUrl1.getResponse().getContentAsString();
        String responseUrl2 = resultUrl2.getResponse().getContentAsString();

        Assert.assertEquals(responseUrl1, responseUrl2);
    }

    @Test
    public void givenValidUrlShouldStoreIfNotExists() throws Exception {
        ShortUrlRequestDto requestDto1 = ShortUrlRequestDto.builder().
                url("https://www.google.com/").build();
        ShortUrlRequestDto requestDto2 = ShortUrlRequestDto.builder().
                url("https://www.oracle.com/").build();

        MvcResult resultUrl1 = mvc.perform(post(baseUrl+"/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(requestDto1)))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult resultUrl2 = mvc.perform(post(baseUrl+"/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(requestDto2)))
                .andExpect(status().isOk())
                .andReturn();

        String responseUrl1 = resultUrl1.getResponse().getContentAsString();
        String responseUrl2 = resultUrl2.getResponse().getContentAsString();

        Assert.assertNotEquals(responseUrl1, responseUrl2);
    }

    @Test
    public void givenValidUrlShouldReturnWithShortenUrlHasHttp() throws Exception {
        ShortUrlRequestDto requestDto = ShortUrlRequestDto.builder().
                url("https://www.wikipedia.org/").build();

        MvcResult result = mvc.perform(post(baseUrl + "/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(requestDto)))
                .andExpect(status().isOk())
                .andReturn();
        String responseString = result.getResponse().getContentAsString();
        Assert.assertTrue(responseString.startsWith("http"));
    }

    private String getJson(ShortUrlRequestDto requestDto) throws JsonProcessingException {
        return mapper.writeValueAsString(requestDto);
    }

}
