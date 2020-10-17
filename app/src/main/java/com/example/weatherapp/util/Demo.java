package com.example.weatherapp.util;

class Demo {
    void printPattern(int numberOfLines) {
        for (int i = 0; i < numberOfLines; i++) {

            // for space
            for (int k = 0; k < numberOfLines; k++) {

            }

            for (int j = 0; j < numberOfLines; j++) {
                if (i <= j) {
                    System.out.print("*");
                }
            }

            System.out.println("\n");
        }
    }
}
//* 0,0
//** 1 0, 1 1