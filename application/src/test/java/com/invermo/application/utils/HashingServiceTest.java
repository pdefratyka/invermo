package com.invermo.application.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HashingServiceTest {

    @Test
    void shouldGenerateSameHashesForTheSameText() {
        final String input = "password";
        final String result_1 = HashingService.hashText(input);
        final String result_2 = HashingService.hashText(input);
        Assertions.assertEquals(result_1, result_2);
    }

    @Test
    void shouldGenerateDifferentHashesForDifferentText() {
        final String result_1 = HashingService.hashText("input_1");
        final String result_2 = HashingService.hashText("input_2");
        Assertions.assertNotEquals(result_1, result_2);
    }
}
