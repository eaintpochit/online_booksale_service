package com.example.crudapi.service;

import com.example.crudapi.domain.Book;
import com.example.crudapi.util.UrlUtilConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class BookLoaderService {

    @Autowired
    RestTemplate restTemplate;

    String url = null;


    public Book requestBook(String bookId) {

        url = UrlUtilConstant.getRequestPurchaseBook;
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity http = new HttpEntity(header);
        Book getData = null;

        try {
            getData = restTemplate.exchange(url, HttpMethod.GET, http, Book.class, bookId).getBody();
        } catch (HttpClientErrorException ignored) {
        }

        return getData == null ? null : getData;


    }

}
