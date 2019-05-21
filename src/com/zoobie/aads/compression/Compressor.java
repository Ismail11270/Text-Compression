package com.zoobie.aads.compression;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Compressor {

    private final int dictionarySize = 8;
    private final int bufferSize = 4;
    private String inputString;
    private Vector<Character> inputVec;
    private Vector<Character> dictAndBuff;
    private String dictionary;

    public Compressor(String inputString) {
        this.inputString = inputString;
        inputVec = new Vector<>();
        dictAndBuff = new Vector<>();
    }

    public Compressor(List<Output> result, Character dictionary){
        this.result = result;
        this.dictionary = "";
        for(int i = 0; i < dictionarySize; i++){
            this.dictionary += dictionary;
        }
        this.inputString = "";
    }

    private List<Output> result = new ArrayList<>();


    public void compress() {

        char[] input;

        input = inputString.toCharArray();

        for (Character i : input) {
            inputVec.add(i);
        }
        //Initialize the dictionary
        for (int i = 0; i < dictionarySize; i++) {
            dictAndBuff.add(input[0]);
        }
        //Buffer
        for (int i = 0; i < bufferSize; i++) {
            dictAndBuff.add(inputVec.get(0));
            inputVec.removeElementAt(0);
        }

        System.out.print(dictAndBuff);
        System.out.println(inputVec);


        while(dictAndBuff.size() > dictionarySize){
            Output output = contains(dictAndBuff);
            System.out.println(output);
            moveLense(output.getSize());
            result.add(output);
            System.out.println(dictAndBuff);
        }

        printResults();
    }

    public void decompress(){
        for(Output i : result){
//            Output i = result.get(0);
            if(!i.getFlag()){
                String a = dictionary.substring(i.getOffset(),i.getOffset() + i.getSize());
                System.out.println(a);
                System.out.println("HELLO");
                dictionary = dictionary.substring(0+i.getSize(),dictionarySize - i.getSize());
                dictionary += a;
            }else{
                dictionary = dictionary.substring(1,dictionarySize - 2);
                dictionary+=i.getLetter();

            }
        }
    }
    private Output contains(Vector<Character> dictAndBuff) {
        String dict = "";
        String buff = "";
        for (int i = 0; i < dictionarySize; i++) {
            dict += dictAndBuff.get(i);
        }
        boolean canAddToBuffer = true;
        for (int i = dictionarySize; i < bufferSize + dictionarySize && canAddToBuffer; i++) {
            try {
                buff += dictAndBuff.get(i);
            }catch(ArrayIndexOutOfBoundsException e){
                canAddToBuffer = false;
            }
        }
//        System.out.println("dict " + dict);
//        System.out.println("buffer " + buff);
        for (int i = 0; i < buff.length(); i++) {
            StringBuffer subString = new StringBuffer(buff.substring(0, buff.length() - i));
            subString.reverse();
            String reSubString = subString.toString();
            StringBuffer dictTwo = new StringBuffer(dict);
            dictTwo.reverse();
            String reDict = dictTwo.toString();
            if (reDict.contains(reSubString)) {
                System.out.println("CONTAINS " + subString.reverse());
                return new PositiveOutput(reDict.indexOf(reSubString) + buff.length() - i - 1, buff.length() - i);
            }
        }
        return new NegativeOutput(buff.charAt(0));
    }


    public void moveLense(int size) {
        for (int i = 0; i < size; i++) {
            dictAndBuff.removeElementAt(0);
            if (inputVec.size() != 0) {
                dictAndBuff.add(inputVec.get(0));
                inputVec.removeElementAt(0);
            }
        }
    }

    public void printResults(){
        for(Output i : result){
            System.out.println(i);
        }
    }

    public List<Output> getResult(){
        return result;
    }

}