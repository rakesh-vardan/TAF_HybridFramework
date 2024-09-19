package io.learn.data_provider;

import io.learn.exceptions.InvalidTestDataException;
import org.testng.annotations.DataProvider;

public class UserDataProvider {

    @DataProvider(name = "userData")
    public static Object[][] userData() {
        Object[][] data = {
                { "standard_user", "secret_sauce", true },
                { "locked_out_user", "secret_sauce", false },
                { "problem_user", "secret_sauce", true },
                { "performance_glitch_user", "secret_sauce", true },
                { "error_user", "secret_sauce", false },
                { "visual_user", "secret_sauce", true }
        };
        for (Object[] userData : data) {
            if (isInvalid(userData)) {
                throw new InvalidTestDataException("User data contains null values.");
            }
        }
        return data;
    }

    private static boolean isInvalid(Object[] userData) {
        return userData[0] == null || userData[1] == null;
    }
}