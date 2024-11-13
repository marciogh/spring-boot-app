package com.marciogh.account;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AccountEventProcessor {

    public Map<String, Account> accounts = new HashMap<String, Account>();

    public void process(AccountEvent event) throws Exception {

        switch (event.evenType) {
            case AccountCreated: {
                String accountId = event.accountId;
                if (!accounts.containsKey(accountId)) {
                    accounts.put(accountId,
                            new Account(AccountStatus.Outstanding, event.payload.balance()));
                } else {
                    throw new Exception("Account already exists");
                }
                break;
            }
            case AccountPaymentReceived: {
                processBalanceEvent(event);
                break;
            }
            case AccountChargeReceived: {
                processBalanceEvent(event);
                break;
            }
            case AccountRecalled: {
                break;
            }
        }
    }

    private void processBalanceEvent(AccountEvent event) {
        Integer balanceChange = 0;
        if (event.evenType == EventType.AccountChargeReceived) {
            balanceChange = event.payload.amount();
        }
        if (event.evenType == EventType.AccountPaymentReceived) {
            balanceChange = event.payload.amount() * -1;
        }
        Account account = accounts.get(event.accountId);
        accounts.put(event.accountId,
                new Account(account.status(), account.balance() + balanceChange));
    }

}
