package com.example.pizzaorder;

public class Order {
    private String Size;
    private String Crust;
    private String WholeTop;
    private String LeftTop;
    private String RightTop;
    private String Payment;
    private String Total;
    private String Time;

    public Order(String size, String crust, String whole, String left, String right, String total, String payment, String time) {
        Size = size;
        Crust = crust;
        WholeTop = whole;
        LeftTop = left;
        RightTop = right;
        Total = total;
        Payment = payment;
        Time = time;
    }


    public void setTime(String time) {
        Time = time;
    }


    public String getTime() {
        return Time;
    }

    public String getSize() {
        return Size;
    }

    public String getCrust() {
        return Crust;
    }

    public String getWholeTop() {
        return WholeTop;
    }

    public String getLeftTop() {
        return LeftTop;
    }

    public String getRightTop() {
        return RightTop;
    }

    public String getPayment() {
        return Payment;
    }

    public String getTotal() {
        return Total;
    }

    public void setSize(String size) {
        Size = size;
    }

    public void setCrust(String crust) {
        Crust = crust;
    }

    public void setWholeTop(String wholeTop) {
        WholeTop = wholeTop;
    }

    public void setLeftTop(String leftTop) {
        LeftTop = leftTop;
    }

    public void setRightTop(String rightTop) {
        RightTop = rightTop;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public Order() {

    }

}
