package com.iqbal.karim.iqbalkarim_hw2;

public class CreditCard {
    private String cardholderName;
    private String cardNumber;
    private String cvc;
    private String cardExpiry;

    public CreditCard(String cardholderName, String cardNumber, String cvc, String cardExpiry) {
        this.cardholderName = cardholderName;
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.cardExpiry = cardExpiry;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "cardholderName='" + cardholderName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvc='" + cvc + '\'' +
                ", cardExpiry='" + cardExpiry + '\'' +
                '}';
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getCardExpiry() {
        return cardExpiry;
    }

    public void setCardExpiry(String cardExpiry) {
        this.cardExpiry = cardExpiry;
    }
}
