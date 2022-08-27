package com.example.crudapi.serviceImpl;

import com.example.crudapi.domain.*;
import com.example.crudapi.model.ResponseMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.springframework.stereotype.Component;

@Component
public class ResponseMessageServiceImpl {

    Gson gson = new Gson();
    ResponseMessage responseMessage = new ResponseMessage();

    public ResponseMessage prepareMberResponseMessage(String getBody) {

        MemberHistory mberHistory = new MemberHistory();
        Member mber = new Member();

        try {

            JsonObject jsonMberHistory = gson.fromJson(getBody, JsonObject.class);
            mberHistory = gson.fromJson(jsonMberHistory, MemberHistory.class);
            String strMerberHis = mberHistory.getData();
            JsonObject jsonObject = gson.fromJson(strMerberHis, JsonObject.class);
            mber = gson.fromJson(jsonObject, Member.class);

        } catch (JsonParseException ignored) {
        }

        String status = mberHistory.getStatus();
        String dateTime = mberHistory.getCreatedDateTime();

        responseMessage = prepareResponseMessage(status, dateTime, mber);

        return responseMessage;

    }

    public ResponseMessage prepareBankCardResponseMessage(String getBody) {

        BankCard bankCard = new BankCard();
        BankCardHistory bankCardHistory = new BankCardHistory();

        try {

            JsonObject jsonBankCardHistory = gson.fromJson(getBody, JsonObject.class);
            bankCardHistory = gson.fromJson(jsonBankCardHistory, BankCardHistory.class);
            String strbankCardHis = bankCardHistory.getData();
            JsonObject jsonObject = gson.fromJson(strbankCardHis, JsonObject.class);
            bankCard = gson.fromJson(jsonObject, BankCard.class);

        } catch (JsonParseException ignored) {
        }

        String status = bankCardHistory.getStatus();
        String dateTime = bankCardHistory.getCreatedDateTime();

        responseMessage = prepareResponseMessage(status, dateTime, bankCard);

        return responseMessage;

    }

    public ResponseMessage prepareBookResponseMessage(String getBody) {

        Book book = new Book();
        BookHistory bookHistory = new BookHistory();

        try {
            JsonObject jsonBookHistory = gson.fromJson(getBody, JsonObject.class);
            bookHistory = gson.fromJson(jsonBookHistory, BookHistory.class);
            String strbookHis = bookHistory.getData();
            JsonObject jsonObject = gson.fromJson(strbookHis, JsonObject.class);
            book = gson.fromJson(jsonObject, Book.class);
        } catch (JsonParseException ignored) {
        }

        String status = bookHistory.getStatus();
        String dateTime = bookHistory.getCreatedDateTime();

        responseMessage = prepareResponseMessage(status, dateTime, book);

        return responseMessage;
    }

    public ResponseMessage prepareResponseMessage(String status, String dateTime, Object data) {

        responseMessage.setStatusMessage(status);
        responseMessage.setCreatd_date_time(dateTime);
        responseMessage.setData(data);

        return responseMessage;

    }
}
