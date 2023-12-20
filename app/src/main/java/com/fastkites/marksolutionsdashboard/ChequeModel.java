package com.fastkites.marksolutionsdashboard;

public class ChequeModel {

    String nameOnCheque, dueDate, number, bank, amount;

    public ChequeModel(String nameOnCheque, String dueDate, String number, String bank, String amount) {
        this.nameOnCheque = nameOnCheque;
        this.dueDate = dueDate;
        this.number = number;
        this.bank = bank;
        this.amount = amount;
    }

    public String getNameOnCheque() {
        return nameOnCheque;
    }

    public void setNameOnCheque(String nameOnCheque) {
        this.nameOnCheque = nameOnCheque;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
