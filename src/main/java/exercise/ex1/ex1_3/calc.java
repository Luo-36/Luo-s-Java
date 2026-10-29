package exercise.ex1.ex1_3;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

//这版是重构版

public class calc {
    private static Scanner sc = new Scanner(System.in);
    private static Deque<Double> numsStack = new ArrayDeque<>();
    private static Deque<Character> charStack = new ArrayDeque<>();
    private static String expression;

    public static void main(String[] args) {

        while(true) {
            System.out.println("请输入表达式（输入q退出）：");

            expression = sc.nextLine().trim();
            if(expression.equalsIgnoreCase("q")) break;

            if (expression.isEmpty()) {
                System.out.println("表达式为空！");
                continue;
            }

            //处理表达式
            try {
                parseExpression();
            } catch (Exception e) {
                System.out.println("错误："+e.getMessage());
                continue;
            }

        }

        System.out.println("结束");


    }

    //获得优先级
    private static int getPriority(Character op) {
        if(op=='+' || op=='-') return 1;
        else if(op=='*' || op=='/') return 2;
        else if(op=='(') return 0;
        else return -1;
    }

    //解析表达式
    private static void parseExpression() throws Exception{
        //每结束完一轮，重置数据
        String numToken = "";
        numsStack.clear();
        charStack.clear();

        for(int i=0; i<expression.length(); i++) {

            //获得当前字符
            Character cur = expression.charAt(i);

            if(Character.isDigit(cur) || cur=='.') {
                numToken+=cur;

            } else if(cur=='+' || cur=='-' || cur=='*' || cur=='/' || cur=='(' || cur==')') {
                //数字词元入栈
                if(!numToken.isEmpty()) {
                    try {
                        numsStack.push(Double.parseDouble(numToken));
                    } catch (Exception e) {
                        throw new Exception("表达式有误！",e);
                    }
                    numToken = "";
                }

                if(cur=='(') charStack.push(cur);
                else if(cur==')') {
                    while(!charStack.isEmpty() && charStack.peek()!='(') {
                        opCalc();
                    }
                    if(charStack.isEmpty()) throw new Exception("表达式不合法！");
                    charStack.pop();    //去掉（
                }
                else {
                    while(!charStack.isEmpty()
                            && charStack.peek()!='('
                            && getPriority(charStack.peek()) >= getPriority(cur)
                    ) {
                        opCalc();
                    }
                    charStack.push(cur);

                }
            }
            else {
                throw new Exception("包含非法字符！");
            }
        }

        if (!numToken.isEmpty()) {
            try {
                numsStack.push(Double.parseDouble(numToken));
            } catch (NumberFormatException e) {
                throw new Exception("表达式有误！", e);
            }
            numToken = "";
        }

        while(!charStack.isEmpty()) {
            opCalc();
        }
        if (numsStack.size() != 1) throw new Exception("表达式有误！");
        System.out.println("=" + numsStack.peek());

    }


    //处理两个数的运算
    private static void opCalc() throws Exception{
        if(charStack.isEmpty() || numsStack.size()<2) throw new Exception("表达式有误！");

        Character op = charStack.pop();
        Double num1 = numsStack.pop();
        Double num2 = numsStack.pop();

        switch (op) {
            case '+' -> numsStack.push(num2+num1);
            case '-' -> numsStack.push(num2-num1);
            case '*' -> numsStack.push(num2*num1);
            case '/' -> {
                if(num1==0) {
                    throw new Exception("除数不可为0");
                }
                numsStack.push(num2/num1);
            }
            default -> throw new Exception("表达式有误！");
        }
    }

}
