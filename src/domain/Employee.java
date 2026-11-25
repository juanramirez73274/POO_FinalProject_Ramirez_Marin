public class Employee extends Customer {
    public Employee(int id, String name){
        super(id,name);
    }

    @Override 
    public String getInfo(){
        return "id: " + id + " ,Employee: " + name;
    }
}
