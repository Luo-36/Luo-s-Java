package exercise.ex1.ex1_1;

import java.util.Random;
import java.util.Scanner;

public class GuessNumber {
    final static int timesLimit = 7;

    public static void main(String[] args) {
        Random r = new Random();
        Scanner sc = new Scanner(System.in);

        int target = r.nextInt(101)+1;
        int times = 0;
        int guess = -1;

        while(true) {
            /*坑1：
            * 因为scanner缓冲区没读干净连续触发报错的死循环
            * 1.
            * 直接用guess接sc的nextInt()在遇到非法数据时会抛异常
            * 但是此时非法数据还在缓冲区里，下一次循环还会读到，会连续触发异常
            * 可以想到在异常处理用sc.nextLine()读掉一行非法数据
            * 但是如果输入很快就有可能读不干净，导致用户下一次输入的合法数据也不被接受
            * 2.
            * 因此选择用先读一行nextLine的方式来解决
            * 先读一行存String，再对String进行处理，拿到int
            * 不会出现残留，每一次回车都读到\n
            * 每轮循环消耗一行
            * */

            //处理输入
            System.out.println("请输入你猜测的数字：(1~100)");
            String line = sc.nextLine().trim();         //建议先读一行，再对一行进行处理
            try {
                guess = Integer.parseInt(line);
            } catch (RuntimeException e) {
                System.out.println("请输入合理的数据！");
                System.out.println();
                continue;
            }
            times++;

            //判断
            if(target == guess || times>=timesLimit) {
                if(target == guess) {
                    System.out.println("猜对啦！");
                    System.out.println("你一共猜了"+times+"次");
                } else if(times>=timesLimit) {
                    System.out.println("还是不对呢。");
                    System.out.println("答案是："+target);
                    System.out.println("次数用完啦！下次走运。");
                }

                boolean retry = false;
                System.out.println("再来一局？【y/n】");

                while(true) {
                    String select = sc.nextLine().trim();
                    if(select.equals("y") || select.equals("Y")) {
                        target = r.nextInt(101)+1;
                        guess = -1;
                        times = 0;
                        retry = true;
                    } else if(select.equals("N") || select.equals("n")) {
                        System.out.println("游戏结束。");
                    } else {
                        System.out.println("请输入y/n！");
                        continue;
                    }
                    break;
                }
                if(retry) continue;
                else break;
            } else if(guess > target){
                System.out.println("猜大啦！再试试");
                System.out.println("还剩"+(timesLimit-times)+"次机会哦。");
            } else if(guess < target) {
                System.out.println("猜小啦！再试试");
                System.out.println("还剩"+(timesLimit-times)+"次机会哦。");
            }

        }
    }


}
