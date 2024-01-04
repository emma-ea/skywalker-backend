package io.github.emmaea.skywalker.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ContactUtilsTest {

    @Test
    void testSplitPhoneNumber() {
        String[] res = ContactUtils.splitPhoneNumber("+233540650527");
        assertEquals(res[0], "+233");
        assertEquals(res[1], "540650527");
    }

    @Test
    void testValidatePhoneNumber() {
        boolean res = ContactUtils.validatePhoneNumber("+233540650527");
        assertTrue(res);
    }
}
