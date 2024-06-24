package com.example.demo.util;

import org.passay.*;
import java.util.Arrays;

public class ValidatePass {
    public static boolean isValid(String password) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new UppercaseCharacterRule(1),
                new LowercaseCharacterRule(1),
                new DigitCharacterRule(1),
                new SpecialCharacterRule(1),
                new WhitespaceRule(),
                new RepeatCharacterRegexRule(3)
        ));

        RuleResult result = validator.validate(new PasswordData(password));
        return result.isValid();
    }
}