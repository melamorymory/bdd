package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {

    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement button1 = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] button");
    private SelenideElement button2 = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] button");
    private static SelenideElement balance1 = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");
    private static SelenideElement balance2 = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");
    private SelenideElement notification = $("[data-test-id=error-notification]").$(withText("Ошибка"));

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(int id) {
        val text = cards.get(id).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public CardRefillPage selectFirstCard() {
        button1.click();
        return new CardRefillPage();
    }

    public CardRefillPage selectSecondCard() {
        button2.click();
        return new CardRefillPage();
    }

    public SelenideElement notificationShouldBeVisible() {
        return notification.shouldBe(visible);
    }
}
