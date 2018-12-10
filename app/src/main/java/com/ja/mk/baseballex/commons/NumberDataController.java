package com.ja.mk.baseballex.commons;

public class NumberDataController{
    private static String numberData = "";
    private static int tryCount = 0;
    private static long tryTime = 0;


    public static String getNumberData(){
        return numberData;
    }

    public static int getTryCount(){
        return tryCount;
    }

    public static long getTryTime() {
        return tryTime/1000;
    }

    public static void newNumberData(){
        while (true) {
            int number = (int)(Math.random() * 9000) + 1000;
            String strNumber = "" + number;

            if(isGoodNumber(strNumber)){
                numberData = strNumber;
                tryCount = 0;
                tryTime = System.currentTimeMillis();
                break;
            }
        }
    }

    public static boolean isGoodNumber(String strNumber){
        char [] arrNumbers = strNumber.toCharArray();
        for(int  i = 0 ; i < arrNumbers.length ; i++){
            for(int x = i+1 ; x < arrNumbers.length ; x++){
                if(arrNumbers[i] == arrNumbers[x]){
                    return false;
                }
            }
        }
        return true;
    }

    public static ResultData equalsNumber(String number){

        tryCount++;

        if(number.equals(numberData)){
            tryTime = System.currentTimeMillis() - tryTime;
            return new ResultData(0,0 , true);
        }

        int strikeCount = 0;
        int ballCount = 0;

        for(int i = 0 ; i < number.length() ; i++){
            for(int x = 0 ; x < numberData.length() ; x++){
                if(number.charAt(i) == numberData.charAt(x)){
                    if(i == x){
                        strikeCount++;
                    }else{
                        ballCount++;
                    }
                    break;
                }
            }
        }

        return new ResultData(strikeCount,ballCount,false);
    }
}
