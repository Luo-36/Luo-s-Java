package exercise.ex1.ex1_2;

public class Student {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Student(int id, String name, double grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    private int id;
    private String name;
    private double grade;

    public void showInfo(){
        System.out.print(name+"\t");
        System.out.print(grade+"\t");
        System.out.println(id+"\t");
    }

}
