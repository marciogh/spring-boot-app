package com.marciogh.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Map;

/*
 * Jack: {Status: Settled, Balance: 0} Olivia: {Status: Recalled, Balance: 50} Robert: {Status:
 * Outstanding, Balance: 25} Jen: {Status: Overpaid, Balance: -10}
 */
@SpringBootTest
public class AccountEventProcessorTest {

    @Autowired
    AccountEventProcessor accountEventProcessor;

    @Test
    public void testAccountList() throws Exception {
        accountEventProcessor
                .process(new AccountEvent("Jack", EventType.AccountCreated, new Payload(50, 0)));
        accountEventProcessor
                .process(new AccountEvent("Olivia", EventType.AccountCreated, new Payload(50, 0)));
        accountEventProcessor
                .process(new AccountEvent("Robert", EventType.AccountCreated, new Payload(100, 0)));
        accountEventProcessor
                .process(new AccountEvent("Jen", EventType.AccountCreated, new Payload(50, 0)));

        Map<String, Account> excpted = Map.of("Jack", new Account(AccountStatus.Outstanding, 50),
                "Olivia", new Account(AccountStatus.Outstanding, 50), "Robert",
                new Account(AccountStatus.Outstanding, 100), "Jen",
                new Account(AccountStatus.Outstanding, 50));
        assertThat(accountEventProcessor.accounts).isEqualTo(excpted);
    }

    @Test
    public void testBalanceChanges() throws Exception {
        accountEventProcessor
                .process(new AccountEvent("Jack", EventType.AccountCreated, new Payload(50, 0)));
        accountEventProcessor.process(
                new AccountEvent("Jack", EventType.AccountChargeReceived, new Payload(0, 10)));
        accountEventProcessor.process(
                new AccountEvent("Jack", EventType.AccountPaymentReceived, new Payload(0, 60)));
        assertThat(accountEventProcessor.accounts.get("Jack").balance()).isZero();
    }

}
