package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardRefillPage {
    private SelenideElement form = $(".form");
    private static SelenideElement amountField = $("[data-test-id='amount'] input");
    private static SelenideElement fromField = $("[data-test-id='from'] input");
    private static SelenideElement transferButton = $("[data-test-id='action-transfer']");

    public CardRefillPage() {
        form.shouldBe(visible);
    }

    public void moneyTransfer(DataHelper.CardInfo fromCard, int transferSum) {
        String amount = Integer.toString(transferSum);
        amountField.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.DELETE);
        amountField.setValue(amount);
        fromField.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.DELETE);
        fromField.setValue(fromCard.getCardNumber());
        transferButton.click();
    }
}
