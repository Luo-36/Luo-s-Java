package exercise.ex1.ex1_2;

import java.util.Scanner;

public class StringStatistics {

    public static void main(String[] args) {
        String str;
        Scanner sc = new Scanner(System.in);
        str = sc.nextLine();
        sc.close();
        analysisString(str);
    }

    public static void analysisString(String originStr) {
        if(originStr==null) return;

        int upperCase=0,
            lowerCase=0,
            num=0,
            space =0,
            other=0;
        for(char i: originStr.toCharArray()) {
            if(i>='A' && i<='Z') upperCase++;
            else if (i>='a' && i<='z') lowerCase++;
            else if (i>='0' && i<='9') num++;
            else if (i==' ') space++;
            else other++;
        }
        System.out.println("This String composed by:");
        System.out.println("Uppercase:"+upperCase);
        System.out.println("Lowercase:"+lowerCase);
        System.out.println("Num:"+num);
        System.out.println("space:"+ space);
        System.out.println("Other:"+other);
    }

}
