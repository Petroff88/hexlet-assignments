package exercise.daytime;
import jakarta.annotation.PostConstruct;

public class Day implements Daytime {
    private String name = "day";

    public String getName() {
        return name;
    }

    // BEGIN

    @PostConstruct
    public void init(){
        String message = "Bean Day is initialized!";
        System.out.println(message);
    }
    // END
}
