public class Customer {
    protected int id;
    protected String name;

    public Customer(int id, String name){
        setId(id);
        setName(name);
    }

    public void setId(int id){
        if(id<0){
            throw new IllegalArgumentException("id can't be negative");
        }
        this.id = id;
    }

    public void setName(String name){
        if(name!=null && name.matches("^[a-zA-ZñÑáéíóúÁÉÍÓÚ]+(?: [a-zA-ZñÑáéíóúÁÉÍÓÚ]+)*$")){
            this.name=name;
        }else{
            throw new IllegalArgumentException("name can't be null and it just can contain letters");
        }

    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }

    public String getInfo(){
        return "id: " + id + " ,Customer: " + name;
    }

    
    
}
