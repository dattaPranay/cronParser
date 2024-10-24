package com.pranay;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        CronExpParser cp = new CronExpParserImpl();
        //String ret = cp.parse("*/5 8-18 * * 1-5,4-5,6 /usr/bin/monitor_health.sh\n");

        String ret = cp.parse(String.join(" ", args));
        System.out.println(ret);
    }
}