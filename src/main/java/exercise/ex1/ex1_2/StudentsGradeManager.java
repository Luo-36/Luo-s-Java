package exercise.ex1.ex1_2;

import java.util.Arrays;
import java.util.Scanner;

public class StudentsGradeManager {
    private static int maxLen = 10;
    private static int curLen =0;
    private static Student[] students = new Student[maxLen];
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while(true){
            int sel = -1;
            drawMenu();
            try {
                String temp = sc.nextLine().trim();
                sel = Integer.parseInt(temp);
            } catch (RuntimeException e) {
                System.out.println("输入有误，请重新输入！");
                continue;
            }

            switch (sel) {
                case 1 -> add(create());
                case 2 -> show(students,curLen);
                case 3 -> average(students,curLen);
                case 4 -> max(students,curLen);
                case 5 -> showDesc(students,curLen);
                case 6 -> bandScore(students,curLen);
                case 0 -> {return;}
                default -> {
                    System.out.println("输入有误，请重新输入！");
                    continue;
                }
            }
            System.out.println();
        }
    }

    //1.录入学生
    public static Student create() {
        int id = 0;
        String name = "";
        double grade = 0;


        while(true) {
            String line = "";
            try{
               System.out.println("请输入新学生的姓名：");
               line = sc.nextLine().trim();
               name = line;
               System.out.println("请输入新学生的学号：");
               line = sc.nextLine().trim();
               id = Integer.parseInt(line);
               System.out.println("请输入新学生的成绩：");
               line = sc.nextLine().trim();
               grade = Double.parseDouble(line);
               if(id < 0 || grade<0) {
                throw new RuntimeException();
               }
               break;
            } catch (RuntimeException e) {
                System.out.println("输入有误，请重新输入！");
                continue;
            }
        }
        Student ns = new Student(id, name, grade);


        return ns;
    }
    public static void add(Student ns) {
        if(curLen >= maxLen) {
            resize();
        }
        students[curLen] = ns;
        curLen++;
        System.out.println("添加成功！");
        System.out.println("当前学生人数："+curLen);

    }

    //2.列出全部（通用）
    public static void show(Student[] stu,int len) {
        System.out.println("姓名\t成绩\t学号\t");
        for(int i=0; i<len; i++) {
            stu[i].showInfo();
        }
    }
    /**
     * 为什么不用for(Student i: stu)
     * 因为这样也会把数组里的null遍历进去
     * 触发空指针异常
     * */

    //3.平均分
    public static void average(Student[] stu,int len) {
        if(len == 0) {
            System.out.println("还没有学生数据！");
            return;
        }
        double sum = 0.0f;
        for(int i=0; i<len; i++) {
            sum+=students[i].getGrade();
        }
        System.out.printf("平均分：%.1f",(sum/len));
        System.out.println();
    }

    //4.最高分
    public static void max(Student[] stu,int len) {
        if(len == 0){
            System.out.println("还没有录入学生！");
            return;
        }
        double max = -1;
        for(int i=0; i<len; i++) {
            if(students[i].getGrade()>max) max = students[i].getGrade();
        }
        System.out.println("最高分为："+max);
    }

    //5.按成绩降序
    private static Student[] sortDown(Student[] stu,int len) {
        Student[] newStu = Arrays.copyOf(stu, len);
        for(int cur=0; cur<len-1; cur++) {
            int maxIdx = cur;
            for(int compare=cur+1; compare< len; compare++) {
                if(newStu[compare].getGrade()>newStu[maxIdx].getGrade()) {
                    maxIdx = compare;
                }
            }
            if(maxIdx != cur) {
                Student temp = newStu[cur];
                newStu[cur] = newStu[maxIdx];
                newStu[maxIdx] = temp;
            }
        }
        return newStu;
    }
    private static void showDesc(Student[] stu,int len) {
        Student[] ns = sortDown(stu,len);
        System.out.println("按成绩降序：");
        show(ns,len);
    }

    //6.分数段
    private static void bandScore(Student[] stu,int len) {
        int[] rank = new int[5];
        for(int i=0; i<len; i++) {
            double grade = students[i].getGrade();
            if(grade>=90) rank[0]++;
            else if(grade<90 && grade>=80) rank[1]++;
            else if(grade<80 && grade>=70) rank[2]++;
            else if(grade<70 && grade>=60) rank[3]++;
            else rank[4]++;
        }
        System.out.println(">=90:"+rank[0]);
        System.out.println("89~80:"+rank[1]);
        System.out.println("79~70:"+rank[2]);
        System.out.println("69~60:"+rank[3]);
        System.out.println("<60:"+rank[4]);
    }

    private static void resize() {
        int newLen = maxLen+maxLen/2;
        Student[] newStus = new Student[newLen];

        for(int i=0; i<curLen; i++) {
            newStus[i] = students[i];
        }
        maxLen = newLen;
        students = newStus;
    }


    //打印菜单
    private static void drawMenu() {
        System.out.println("==== 学生成绩管理 ====");
        System.out.println("\t1.录入学生");
        System.out.println("\t2.列出全部");
        System.out.println("\t3.平均分");
        System.out.println("\t4.最高分");
        System.out.println("\t5.按成绩降序");
        System.out.println("\t6.分数段分布");
        System.out.println("\t0.退出");
        System.out.print("请选择：");
    }

}
