package com.learning.javalearning.locale;

import java.text.Collator;
import java.util.Locale;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class CollatorTest {

    public static void main(String[] args) {

        // Compare two strings in the default locale
        Collator myCollator = Collator.getInstance();
        if( myCollator.compare("abc", "ABC") < 0 )
            System.out.println("abc is less than ABC");
        else
            System.out.println("abc is greater than or equal to ABC");

        //Get the Collator for US English and set its strength to PRIMARY
        Collator usCollator = Collator.getInstance(Locale.US);
        usCollator.setStrength(Collator.PRIMARY);
        if( usCollator.compare("abc", "ABC") == 0 ) {
            System.out.println("Strings are equivalent");
        }

    }
}
