package io.github.emmaea.skywalker.util;

public class ContactUtils {

    public static boolean validatePhoneNumber(String phone) {
        boolean res = !phone.isBlank()
                && phone.startsWith("+")
                && phone.trim().length() == 13;
        System.out.println(res);

        for (char c : phone.substring(1).toCharArray()) {
            System.out.println(c);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return res;
    }

    /**
     * Splits phone number provided by user and returns country code and phone
     * 
     * @param phone
     * @return country code and phone
     */
    public static String[] splitPhoneNumber(String phone) {
        String ccode = phone.trim().substring(0, 4);
        String pphone = phone.trim().substring(4);
        return new String[] { ccode, pphone };
    }

}