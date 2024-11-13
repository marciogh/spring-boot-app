package com.marciogh.account;

public class AccountEvent {

    public EventType evenType;
    public String accountId;
    public Payload payload;

    public AccountEvent(String accountId, EventType evenType, Payload payload) {
        this.accountId = accountId;
        this.evenType = evenType;
        this.payload = payload;
    }

}
