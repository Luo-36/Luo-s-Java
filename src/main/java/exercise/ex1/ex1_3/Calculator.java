package exercise.ex1.ex1_3;

import java.util.*;

public class Calculator {
    private static Scanner sc = new Scanner(System.in);
    private static String expression;
    private static Integer errCode;

    //两个栈
    static Deque<Integer> numStack = new ArrayDeque<>();
    static Deque<Character> charStack = new ArrayDeque<>();


    public static void main(String[] args) {
        while(true) {
            errCode = -1;
            numStack.clear();
            charStack.clear();

            //输入表达式
            System.out.println("请输入表达式（输入q退出）：");
            try {
                expression = sc.nextLine().trim();
                if ("q".equalsIgnoreCase(expression)) break;
            } catch (RuntimeException e) {
                System.out.println("非法输入！");
            }

            // 修改：空表达式直接跳过，否则后面 numStack.peek() 会抛异常
            if (expression.isEmpty()) continue;

            //解析表达式字符串
            String numToken = "";

            //完成数字栈和符号栈
            for(int i=0; i<expression.length(); i++) {

                char cur = expression.charAt(i);

                if(Character.isDigit(cur)) {
                    numToken += cur;
                } else if(cur=='+' || cur=='-' || cur=='*' || cur=='/' || cur=='(' || cur==')') {
                    // 修改：只有 numToken 非空才解析，防止 "(1+2)" 开头的 '(' 或 "+1" 导致 parseInt("") 崩溃
                    if(!numToken.isEmpty()) {
                        numStack.push(Integer.parseInt(numToken));
                        numToken="";
                    }

                    // 修改：运算符处理改成三类分支，先处理完再决定 push
                    if (cur == '(') {
                        charStack.push(cur);
                    } else if (cur == ')') {
                        // 修改：')' 本身不入栈，它的作用是把 '(' 之前的都算掉，再弹掉 '('
                        while (!charStack.isEmpty() && charStack.peek() != '(') {
                            calcOp();
                            if (errCode != -1) break;
                        }
                        // 修改：括号不匹配时（栈空还没遇到 '('）设错误码，不能直接 pop
                        if (charStack.isEmpty()) {
                            errCode = 3;
                            break;
                        }
                        charStack.pop();   // 弹掉 '('
                    } else {
                        // 修改：四则运算符，先按优先级把栈顶能算的算掉，再 push 当前运算符
                        while (!charStack.isEmpty()
                                && charStack.peek() != '('
                                && getPriority(charStack.peek()) >= getPriority(cur)) {
                            calcOp();
                            if (errCode != -1) break;
                        }
                        if (errCode != -1) break;
                        charStack.push(cur);
                    }

                } else {
                    errCode = 2;
                    break;
                }

            }

            // 新增：循环结束后收尾最后一个数字，否则 "1+2" 的 2 从未压栈
            if (!numToken.isEmpty()) {
                numStack.push(Integer.parseInt(numToken));
                numToken = "";
            }

            //处理剩下的
            // 修改：加 errCode == -1 条件，出错后不再继续 calcOp，避免覆盖错误码或操作空栈
            while(errCode == -1 && !charStack.isEmpty()) {
                calcOp();
            }

            //报错提示
            if(errCode!=-1) {
                checkError(errCode);
            } else if (numStack.size() == 1) {   // 修改：输出前判空，防御非法表达式
                System.out.println("="+numStack.peek());
            } else {
                System.out.println("表达式有误！");
            }

        }
        System.out.println("结束。");

    }

    //获取优先级
    public static int getPriority(char i) {
        if(i=='+' || i=='-') return 1;
        else if(i=='*' || i=='/') return 2;
        else if(i=='(') return 0;
        return -1;
    }

    //根据错误码提示报错
    public static void checkError(int errCode) {
        switch(errCode) {
            case 1 -> System.out.println("除数不可为0！");
            case 2 -> System.out.println("含有非法字符！");
            case 3 -> System.out.println("表达式有误！");
            default -> {
                return;
            }
        }
    }

    /**执行两个数字的运算（未作超范围处理）
     *  pop一个运算符，两个数字
     *  把运算结果放回数字栈
     */
    private static void calcOp(){
        if(charStack.isEmpty() || numStack.size()<2) {
            errCode = 3;
            return;
        }

        char op = charStack.pop();
        Integer num1 = numStack.pop();
        Integer num2 = numStack.pop();
        try {
            switch (op) {
                case '+' -> numStack.push(num2 + num1);
                case '-' -> numStack.push(num2 - num1);
                case '*' -> numStack.push(num2 * num1);
                case '/' -> {
                    if (num1 == 0) { errCode = 1; return; }
                    numStack.push(num2 / num1);
                }
                default -> errCode = 3;
            }
        } catch (ArithmeticException e) {
            errCode = 1;
        }
    }
}