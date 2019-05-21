package com.zoobie.aads.compression;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{
//        Compressor compressor = new Compressor("can you can cans as a canner can can cans");
//        String inputString = "HHHLO WORLD";
//        compressor.compress();
        Controller controller = new Controller("input.txt","results.txt");
        //controller.runCompressor();
        controller.runDecompressor();

    }
}