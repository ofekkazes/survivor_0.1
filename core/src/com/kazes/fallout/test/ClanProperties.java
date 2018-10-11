package com.kazes.fallout.test;

/**
 * City's properties
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class ClanProperties {
    public static int Warriors = 0;
    public static int Doctors = 0;
    public static boolean Merchant = false;
    public static int PayedMercenaries = 0;
    public static int Scientists = 0;

    public static int getNPCCount() {
        return Warriors + Doctors + PayedMercenaries + Scientists + (Merchant ? 1 : 0);
    }
    public static int getValue(Jobs job) {
        switch (job) {
            case Warrior: return Warriors;
            case Doctor: return Doctors;
            case Merchant: return (Merchant ? 1 : 0);
            case Scientist:return Scientists;
            case PayedMercenary: return PayedMercenaries;
        }
        return 0;
    }
}
