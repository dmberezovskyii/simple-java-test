package myprojects.automation.assignment5.utils;

import org.testng.Assert;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataConverter {

    private DataConverter() {

    }

    /**
     * Extracts product quantity value from In Stock label displayed on product details page.
     *
     * @param label The content of In Stock label.
     * @return Parsed numeric value.
     */
    public static double parseStringPrice(String label) {
        label = label.startsWith("$") ? label.substring(1) : label;
        label.replaceAll(",", "");
        return Double.parseDouble(label);
    }

    public static int parseValue(String label) {
        Matcher qtyMatcher = Pattern.compile("^[0-9]").matcher(label);
        Assert.assertTrue(qtyMatcher.find(), "Unable to extract In Stock (quantity) value!");
        return Integer.parseInt(qtyMatcher.group(1));
    }

    /**
     * Extracts price value from price labels.
     *
     * @param label The content of some label with price value.
     * @return Parsed float value of the price.
     */
    public static float parsePriceValue(String label) {
        Matcher priceMatcher = Pattern.compile("^(.*) ₴$").matcher(label);
        Assert.assertTrue(priceMatcher.find(), "Unable to extract price value!");

        try {
            DecimalFormatSymbols separators = new DecimalFormatSymbols();
            separators.setDecimalSeparator(',');
            return new DecimalFormat("#0.00", separators).parse(priceMatcher.group(1)).floatValue();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts float price value to string representation.
     *
     * @param value
     * @return
     */
    public static String convertPriceValue(float value) {
        DecimalFormatSymbols separators = new DecimalFormatSymbols();
        separators.setDecimalSeparator(',');
        return new DecimalFormat("#0.00", separators).format(value);
    }
}
