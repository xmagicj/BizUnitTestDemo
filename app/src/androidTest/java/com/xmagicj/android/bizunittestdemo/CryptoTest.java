package com.xmagicj.android.bizunittestdemo;

import android.test.InstrumentationTestCase;
import android.util.Log;

/**
 * if key and IV is "0123456789abcdef"
 * Created by mumu on 15/2/6.
 */
public class CryptoTest extends InstrumentationTestCase {

    static String TAG = CryptoTest.class.getSimpleName();

    private String clearText1;
    private String clearText2;
    private String clearText3;
    private String clearText4;

    private String encryptedText1;
    private String encryptedText2;
    private String encryptedText3;
    private String encryptedText4;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Crypto.setKEY("0123456789abcdef");
        Crypto.setIV("0123456789abcdef");

        clearText1 = "this is test String 1234567890 hi@email.com";
        clearText2 = "fasdff";
        clearText3 = "我来自www.wenhq.com";
        clearText4 = "123456";

        encryptedText1 = "fc+olSrHeVTPhYONKnQHZCkFs0eB1oSRiKaar4oE1q92GPct4gP6keQcuA9WpCV0";
        encryptedText2 = "j713HY7OcF8V1SeHztGL2w==";
        encryptedText3 = "UJcGNQ2BNcosV30vSxIzMI8PhiQe8tKrVJx7TsmXx8k=";
        encryptedText4 = "AL5riQzgwmMhXZX+5MtU0A==";
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testEncrypt() {
        try {
            String et1 = Crypto.encryptAES(clearText1);
            String et2 = Crypto.encryptAES(clearText2);
            String et3 = Crypto.encryptAES(clearText3);
            String et4 = Crypto.encryptAES(clearText4);
            Log.e(TAG, et1);
            Log.e(TAG, et2);
            assertTrue(et2.equals(encryptedText2.trim()));
            Log.e(TAG, et3);
            assertTrue(et3.equals(encryptedText3));
            Log.e(TAG, et4);
            assertTrue(et4.equals(encryptedText4));

            String ct1 = Crypto.decryptAES(encryptedText1);
            String ct2 = Crypto.decryptAES(encryptedText2);
            String ct3 = Crypto.decryptAES(encryptedText3);
            String ct4 = Crypto.decryptAES(encryptedText4);
            Log.e(TAG, ct1);
            Log.e(TAG, ct2);
            assertTrue(ct2.equals(clearText2));
            Log.e(TAG, ct3);
            assertTrue(ct3.equals(clearText3));
            Log.e(TAG, ct4);
            assertTrue(ct4.equals(clearText4));
            //test

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
