package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class LoginTest {

    @AfterAll
    static void teardown() {
        cleanDatabase();
    }

    @Test
    void shouldSuccessfulLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisible();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());

    }

    @Test
    void shouldGetErrorNotificationIFLoginWithRandomUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoRandom();
        var verificationPage = loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisible();

    }

    @Test
    void shouldGetErrorNotificationIFLoginWithRandomVerificationCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisible();
        var verificationCode = DataHelper.getVerificationCodeRandom();
        verificationPage.verifyButtonClick(verificationCode.getCode());
        verificationPage.verifyErrorNotificationVisible();

    }
}
