package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    int amount = 100;
    int amountOverBalance = 30_000;
    int negativeAmount = -300;
    int positiveAmount = 300;

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var dashboardPage = new DashboardPage();

        var balance1 = dashboardPage.getCardBalance(0);
        var balance2 = dashboardPage.getCardBalance(1);

        if (balance1 > balance2) {
            int transferSum = (balance1 - balance2) / 2;
            var cardInfo = DataHelper.getFirstCardInfo();
            var cardRefillPage = dashboardPage.selectSecondCard();
            cardRefillPage.moneyTransfer(cardInfo, transferSum);
        }
        if (balance1 < balance2) {
            int transferSum = (balance2 - balance1) / 2;
            var cardInfo = DataHelper.getSecondCardInfo();
            var cardRefillPage = dashboardPage.selectFirstCard();
            cardRefillPage.moneyTransfer(cardInfo, transferSum);
        }
    }

    @Test
    void shouldTransferMoneyFrom1To2Card() {
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.getCardBalance(0);
        var balanceSecondBefore = dashboardPage.getCardBalance(1);

        var cardRefillPage = dashboardPage.selectSecondCard();
        var cardInfo = DataHelper.getFirstCardInfo();
        cardRefillPage.moneyTransfer(cardInfo, amount);

        int balanceAfterOnCardFrom = DataHelper.balanceOnCardFrom(balanceSecondBefore, amount);
        int balanceAfterOnCardTo = DataHelper.balanceOnCardTo(balanceFirstBefore, amount);
        int balanceFirstAfter = dashboardPage.getCardBalance(0);
        int balanceSecondAfter = dashboardPage.getCardBalance(1);

        assertEquals(balanceFirstAfter, balanceAfterOnCardFrom);
        assertEquals(balanceSecondAfter, balanceAfterOnCardTo);
    }

    @Test
    void shouldTransferMoneyFrom2To1Card() {
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.getCardBalance(0);
        var balanceSecondBefore = dashboardPage.getCardBalance(1);

        var cardRefillPage = dashboardPage.selectFirstCard();
        var cardInfo = DataHelper.getSecondCardInfo();
        cardRefillPage.moneyTransfer(cardInfo, amount);

        int balanceAfterOnCardFrom = DataHelper.balanceOnCardFrom(balanceFirstBefore, amount);
        int balanceAfterOnCardTo = DataHelper.balanceOnCardTo(balanceSecondBefore, amount);
        int balanceFirstAfter = dashboardPage.getCardBalance(0);
        int balanceSecondAfter = dashboardPage.getCardBalance(1);

        assertEquals(balanceFirstAfter, balanceAfterOnCardTo);
        assertEquals(balanceSecondAfter, balanceAfterOnCardFrom);
    }

//    @Test
//    void shouldNotTransferAmountOverBalanceFrom2To1Card() {
//        var dashboardPage = new DashboardPage();
//        var cardRefillPage = dashboardPage.selectFirstCard();
//        var cardInfo = DataHelper.getSecondCardInfo();
//        cardRefillPage.moneyTransfer(cardInfo, amountOverBalance);
//        var error = new DashboardPage();
//        error.notificationShouldBeVisible();
//    }

//    @Test
//    void shouldNotTransferAmountOverBalanceFrom1To2Card() {
//        var dashboardPage = new DashboardPage();
//        var cardRefillPage = dashboardPage.selectSecondCard();
//        var cardInfo = DataHelper.getFirstCardInfo();
//        cardRefillPage.moneyTransfer(cardInfo, amountOverBalance);
//        var error = new DashboardPage();
//        error.notificationShouldBeVisible();
//    }

    @Test
    void shouldConvertNegativeAmountIntoPositive() {
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.getCardBalance(0);
        var balanceSecondBefore = dashboardPage.getCardBalance(1);

        var cardRefillPage = dashboardPage.selectSecondCard();
        var cardInfo = DataHelper.getFirstCardInfo();
        cardRefillPage.moneyTransfer(cardInfo, negativeAmount);

        int balanceAfterOnCardFrom = DataHelper.balanceOnCardFrom(balanceSecondBefore, positiveAmount);
        int balanceAfterOnCardTo = DataHelper.balanceOnCardTo(balanceFirstBefore, positiveAmount);
        int balanceFirstAfter = dashboardPage.getCardBalance(0);
        int balanceSecondAfter = dashboardPage.getCardBalance(1);

        assertEquals(balanceFirstAfter, balanceAfterOnCardFrom);
        assertEquals(balanceSecondAfter, balanceAfterOnCardTo);
    }
}
