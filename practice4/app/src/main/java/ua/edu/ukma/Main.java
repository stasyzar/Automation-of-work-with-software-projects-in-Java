package ua.edu.ukma;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting the application...");

        CarDto carDto = new CarDto();

        System.out.println("\n--- Test 1: Empty fields ---");
        try {
            Validator.validate(carDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n--- Test 2: Populated fields ---");
        carDto.brand = "Toyota";
        carDto.model = "Camry";
        try {
            Validator.validate(carDto);
        } catch (Exception e) {
            System.err.println( e.getMessage());
        }
    }
}