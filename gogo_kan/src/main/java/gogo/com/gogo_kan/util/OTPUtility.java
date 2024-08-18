package gogo.com.gogo_kan.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class OTPUtility {
    private static final Map<String, Long> otpMap = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public  long generateOTP() {
        // Define the length of the OTP
        int length = 6;

        // Define the characters from which the OTP will be composed
        String numbers = "012345678911121314";

        // Use a StringBuilder to store the OTP
        StringBuilder otp = new StringBuilder();

        // Use a random number generator to pick characters from the numbers string
        Random random = new Random();

        // Generate the OTP by appending random characters from the numbers string
        for (int i = 0; i < length; i++) {
            otp.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        // Convert the OTP string to a long
        return Long.parseLong(otp.toString());
    }

    public boolean isCorrect(String enteredOTP) {
        try {
            if (enteredOTP.length() > 6) {
                return false;
            }
            char[] otpChar = enteredOTP.toCharArray();
            for (char character : otpChar) {
                if (!Character.isDigit(character)) {
                    return false;
                }
            }
            return true;

        } catch (Exception e) {
            return false;
        }
    }


    public boolean storeOTP(String key, Long timestamp) {
        otpMap.put(key, timestamp);
        // remove automatically after one minutes
         scheduler.schedule(() -> otpMap.remove(key), 1, TimeUnit.MINUTES);
        return true;
    }

    public Long getStoredOTP(String key) {
        return otpMap.get(key);
    }

    public void removeOTP(String key) {
        otpMap.remove(key);
    }
}
