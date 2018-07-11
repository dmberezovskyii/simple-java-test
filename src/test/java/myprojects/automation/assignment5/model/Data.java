package myprojects.automation.assignment5.model;

import org.openqa.selenium.WebDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Hold Product information that is used among tests.
 */
public class Data {
    WebDriver driver;
    public static int downPayment;
    public static double vehicleCost;
    public static double carCost;
    public static int leaseTerm;
    public static double capCostResidual;
    private static final double warranty = 1500;
    private static final int residual = 1;
    private static final int mounthlyProfit = 85;
    private static final double monthlyInterestRate = 0.015; //18/1200

    public Data(WebDriver driver) {
        this.driver = driver;
    }


    public static String randomDateOfBirth() {

        Random random = new Random();
        int minDay = (int) LocalDate.of(1980, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(1990, 1, 1).toEpochDay();

        long randomDay = minDay + random.nextInt(maxDay - minDay);

        LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
        String dateOfBirth = randomBirthDate.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        return dateOfBirth;
    }

    public int getLeaserTerm() {
        return leaseTerm;
    }

    public static int getCapCostResidual(int cost) {

        switch (cost) {
            case 1000:
                capCostResidual = 0;
                break;
            case 1500:
                capCostResidual = 0;
                break;
            case 2000:
                capCostResidual = 500;
                break;
            case 3000:
                capCostResidual = 1000;
                break;
        }

        return (int) capCostResidual;

    }


    public static double getMonthlyInterestRate() {
        double rate = 18 / 1200;
        return rate;
    }

    public static double getCapCost() {
        double capCost = (vehicleCost + warranty) - getCapCostResidual(downPayment);
        return capCost;

    }

    public static double getLeaseRate() {

        double l1 = getCapCost() - (residual * Math.pow((1 + monthlyInterestRate), -36));
        double l2 = (((1 - Math.pow((1 + monthlyInterestRate), -(leaseTerm - 1))) / 0.015) + 1) + mounthlyProfit;
        double leaseRate = l1 / (((1 - Math.pow((1 + monthlyInterestRate), -(leaseTerm - 1))) / 0.015) + 1) + mounthlyProfit;
        return Math.rint(100.0 * leaseRate) / 100.0;

    }


    public static double getTaxes() {
        double taxes = getLeaseRate() * 0.13;
        return Math.rint(100.0 * taxes) / 100.0;
    }

    public static double getTotalLeasePMT() {
        double totalLease = getLeaseRate() + getTaxes();
        return totalLease;
    }


}
