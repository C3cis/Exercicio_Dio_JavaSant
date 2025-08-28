@SpringBootApplication
public class BibliotecaOnlineApplication {
    public static void main(String[] args) {
        SpringApplication.run(BibliotecaOnlineApplication.class, args);
      
        DatabaseConnection c1 = DatabaseConnection.getInstance();
        DatabaseConnection c2 = DatabaseConnection.getInstance();
        System.out.println(c1 == c2); 
    }
}
