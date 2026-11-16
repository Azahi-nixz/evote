package Pages;

public class Voter {

    private String voterId;
    private String name;
    private boolean registered;
    private int age;

    public Voter(String voterId, String name, boolean registered, int age) {
        this.voterId = voterId;
        this.name = name;
        this.registered = registered;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    @Override
    public String toString() {
        return name + " (ID: " + voterId + ")";
    }
}
