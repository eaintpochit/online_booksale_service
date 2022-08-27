package com.example.crudapi.service;


import com.example.crudapi.model.ResponseMessage;
import com.example.crudapi.util.UrlUtilConstant;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class BookLoaderService {

    @Autowired
    RestTemplate restTemplate;

    String url = null;
    ResponseMessage responseMessage = new ResponseMessage();
    Gson gson = new Gson();

    public Object requestBook(String bookId, String bookName, String author) {
//        url = UrlUtilConstant.getRequestPurchaseBook;
        url = UrlUtilConstant.getRequestPurchaseBook;
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> http = new HttpEntity(header);
        Object getData = null;

        try {
            getData = restTemplate.exchange(url, HttpMethod.GET, http, Object.class, bookId, bookName, author).getBody();
        } catch (HttpClientErrorException ignored) {
        }

        return getData == null ? null : getData;


    }

}
