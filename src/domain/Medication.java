public class Medication {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int minStock;
    private boolean active;
    private Supplier supplier;

    public Medication(int id, String name, double price, int stock,int minStock, Supplier supplier){
        setId(id);
        setName(name);
        setPrice(price);
        if(stock<0){
            throw new IllegalArgumentException("stock can't be negative");
        }
        this.stock=stock;
        setMinStock(minStock);
        this.active = true;
        setSupplier(supplier);
    }
//getters
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public double getPrice(){
        return price;
    }
    public int getStock(){
        return stock;
    }
    public int getMinStock(){
        return minStock;
    }
    public boolean isActive(){
        return active;
    }
    public Supplier getSupplier(){
        return supplier;
    }
//setters
    public void setId(int id){
        if(id<0){
            throw new IllegalArgumentException("id cant't be negative");
        }
        this.id=id;
    }
    public void setName(String name){
         if(name!=null && name.matches("^(?=.*[a-zA-ZñÑáéíóúÁÉÍÓÚüÜ])[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚüÜ./()%+-]+(?: [a-zA-Z0-9ñÑáéíóúÁÉÍÓÚüÜ./()%+-]+)*$")){
            this.name=name;
        }else{
            throw new IllegalArgumentException("name can't contain gaps");
        }
    }
    public void setPrice(double price){
        if(price<0){
            throw new IllegalArgumentException("price can't be negative");
        }
        this.price=price;
    }
   
    public void setMinStock(int minStock){
        if(minStock<=0){
            throw new IllegalArgumentException("minStock can't be negative and it must be over 0");
        }
        this.minStock=minStock;
    }
    public void setSupplier(Supplier supplier){
        this.supplier=supplier;
    }
//methods
    public void deactivate(){
        active = false;
    }
    public boolean isLowStock(){
       return getStock()<=getMinStock();
    }
    public void increaseStock(int quantity){
        if(quantity<=0){
            throw new IllegalArgumentException("quantity can't be negative or 0, product have to be available");
        }
        this.stock += quantity;
    }
    public void decreaseStock(int quantity){
        if(quantity<=0||isActive()==false){
            throw new IllegalArgumentException("quantity can't be negative or 0, product have to be available");
        }
        if(quantity>this.stock){
            throw new IllegalArgumentException("there aren't enought stock");
        }
        this.stock-=quantity;
    }
}
