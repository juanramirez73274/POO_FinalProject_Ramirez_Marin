public class Supplier extends Customer {
    private String phone;
    private boolean active;

    public Supplier(int id, String name, String phone){
        super(id,name);
        setPhone(phone);
        this.active=true;
    }

    public String getPhone(){
        return phone;
    }
    public boolean isActive(){
        return active;
    }

    public void deactivate(){
        active = false;
    }

    @Override 
    public void setName(String name){
        if(name==null||!name.trim().matches("^(?=.*\\p{L})[\\p{L}0-9.,&'()/+-]+(?: [\\p{L}0-9.,&'()/+-]+)*$")){ //it allows letters digits and . , & ' ( ) / + -
            throw new IllegalArgumentException("supplier name must contain at least one letter and only letters, digits and . , & ' ( ) / + -");
        }
        this.name=name.trim();
    }

    public void setPhone(String phone){
        if(phone==null||!phone.matches("\\d{7,15}")){
            throw new IllegalArgumentException("phone can't be null and it must have between 7-15 nums ");
        }
        this.phone = phone;
    }

    @Override
    public String getInfo() {
        return "id: " + id + " ,Supplier: " + name + " ,phone: " + phone + " ,Supplier is active: " + active;
    }
}
