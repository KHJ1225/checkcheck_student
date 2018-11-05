package kr.ac.cbnu.checkcheck_student;



public class Global {
    private static Global ourInstance = new Global();
    public static Global getInstance(){
        return ourInstance;
    }
    private Global(){

    }

    private int stunumber;
    private String stuname;
    private String stumajor;

    public int getStunumber() {
        return stunumber;
    }

    public void setStunumber(int stunumber) {
        this.stunumber = stunumber;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public String getStumajor() {
        return stumajor;
    }

    public void setStumajor(String stumajor) {
        this.stumajor = stumajor;
    }
}
