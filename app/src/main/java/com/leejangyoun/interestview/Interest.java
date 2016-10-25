package com.leejangyoun.interestview;

public class Interest {

    public int no;
    public String name;

    public Interest(int no, String name) {
        this.no = no;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
