package com.iqbal.karim.iqbalkarim_hw2;

import android.database.Cursor;

import java.util.ArrayList;

public class Commons {
    public static ArrayList<Phone> phones;
    public static Cursor cursor;
    public static Phone phone;
    public static CreditCard card;
    public static ArrayList<CreditCard> cards;

    public static CreditCard getCard() {
        return card;
    }

    public static void setCard(CreditCard card) {
        Commons.card = card;
    }

    public static ArrayList<CreditCard> getCards() {
        return cards;
    }

    public static void setCards(ArrayList<CreditCard> cards) {
        Commons.cards = cards;
    }

    public static Cursor getCursor() {
        return cursor;
    }

    public static void setCursor(Cursor cursor) {
        Commons.cursor = cursor;
    }

    public static Phone getPhone() {
        return phone;
    }

    public static void setPhone(Phone phone) {
        Commons.phone = phone;
    }

    public static ArrayList<Phone> getPhones() {
        return phones;
    }

    public static void setPhones(ArrayList<Phone> phones) {
        Commons.phones = phones;
    }
}
