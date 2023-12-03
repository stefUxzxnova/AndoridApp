package com.example.happyapp.usersRestApi.profile;

public class Message {
    private String body;

    public Message(Message copy) {
        this.body = copy.body;
    }

    public Message(String body){
        this.body = body;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String status) {
        this.body = body;
    }
}
