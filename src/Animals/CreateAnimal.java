/**
 * Name of assignment submitter: Ariel Ariav
 * ID of assignment submitter: 209267988
 */

package Animals;


import Olympics.Medal;
import java.util.Calendar;
import java.util.Scanner;

/**
 *The CreateAnimal class provides a framework for creating various types of animals based on user input.
 * This class is abstract and includes static methods for creating different animal objects.
 *
 * The class allows you to create a water animal, a terrestrial animal, an air animal, a dog, a cat, a snake,
 * a whale, an eagle, a pigeon, an alligator and a dolphin.
 * The class ensures that the input provided by the user is validated before creating the animal objects.
 */
public abstract class CreateAnimal {

    // Scanner object to read input from the user
    private static Scanner scanner = new Scanner(System.in);

    //___________________________________________Air animal___________________________________________________
    /**
     * Creates an AirAnimal object based on user input.
     * @return AirAnimal object created with user-provided data.
     */
    public static AirAnimal createAirAnimal() {
        Scanner scanner = new Scanner(System.in); // new scanner
        // Ask for name
        String name = null;
        while (true) {
            System.out.print("Enter the animal name: ");
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please enter a valid name.");
            } else if (!name.matches("[a-zA-Z ]+")) { // Check if name contains only letters and spaces
                System.out.println("Name can only contain english letters and spaces. Please enter a valid name.");
            } else {
                break; // Exit loop if a valid name is provided
            }
        }

        // Ask for gender
        Animal.Gender selectedGender = null;
        while (true) {
            System.out.println("Choose a gender:");
            System.out.println("1. MALE");
            System.out.println("2. FEMALE");
            System.out.println("3. HERMAPHRODITE");

            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    continue;
                }
                int chosenGender = Integer.parseInt(input);

                if (chosenGender >= 1 && chosenGender <= 3) {
                    selectedGender = Animal.Gender.values()[chosenGender - 1];
                    break; // Exit loop if a valid gender is chosen
                } else {
                    System.out.println("Invalid choice. Please choose a valid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
            }
        }

        // Ask for weight
        double weight = 0;
        while (true) {
            System.out.print("Enter the weight of the animal: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }
            try {
                weight = Double.parseDouble(input);
                if (weight <= 0) {
                    System.out.println("Weight must be positive. Please enter a valid weight.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Ask for speed
        double speed = 0;
        while (true) {
            System.out.print("Enter the speed of the animal: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }
            try {
                speed = Double.parseDouble(input);
                if (speed < 0) {
                    System.out.println("Speed cannot be negative. Please enter a positive number.");
                } else {
                    break; // Exit loop if a valid speed is provided
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Medals
        int numOfMedals = 0;
        while (true) {
            System.out.print("Enter the number of medals the animal has won: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a valid integer.");
                continue;
            }
            try {
                numOfMedals = Integer.parseInt(input);
                if (numOfMedals < 0) {
                    System.out.println("Number of medals cannot be negative. Please enter a valid number.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        Medal[] medals = new Medal[numOfMedals];
        for (int i = 0; i < numOfMedals; i++) {
            // Tournament name
            String medalName = null;
            while (true) {
                System.out.print("Enter the tournament name of medal number " + (i+1) + ":");
                medalName = scanner.nextLine().trim();
                if (medalName.isEmpty()) {
                    System.out.println("Tournament name cannot be empty. Please enter a valid name.");
                } else if (!medalName.matches("[a-zA-Z ]+")) { // Check if name contains only letters and spaces
                    System.out.println("Tournament name can only contain english letters and spaces. Please enter a valid name.");
                } else {
                    break; // Exit loop if a valid name is provided
                }
            }

            // Year
            int year = 0;
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            while (true) {
                System.out.print("Enter the year of winning the medal: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Invalid input. Please enter a valid year.");
                    continue;
                }
                try {
                    year = Integer.parseInt(input);
                    if (year <= 0) {
                        System.out.println("Year must be positive. Please enter a valid year.");
                    } else if (year > currentYear) {
                        System.out.println("Year cannot be in the future. Please enter a valid year.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid year.");
                }
            }

            // Medal type
            Medal.Type type = null;
            while (true) {
                System.out.println("Enter the type of medal:");
                System.out.println("1. GOLD");
                System.out.println("2. SILVER");
                System.out.println("3. BRONZE");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    continue;
                }
                try {
                    int inputInt = Integer.parseInt(input);
                    if (inputInt >= 1 && inputInt <= 3) {
                        type = Medal.Type.values()[inputInt - 1];
                        break;
                    } else {
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                }
            }

            medals[i] = new Medal(medalName, year, type);
        }

        // wingspan
        double wingspan = 0;
        while (true) {
            System.out.print("Enter the wingspan of the animal: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }
            try {
                wingspan = Double.parseDouble(input);
                if (wingspan <= 0) {
                    System.out.println("wingspan must be positive. Please enter a valid number.");
                } else {
                    break; // Exit loop if a valid speed is provided
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return new AirAnimal(name, selectedGender, weight, speed, medals, wingspan);

    }



    //___________________________________________Water animal___________________________________________________
    /**
     * Creates a WaterAnimal object based on user input.
     * @return WaterAnimal object created with user-provided data.
     */
    public static WaterAnimal createWaterAnimal() {
        Scanner scanner = new Scanner(System.in); // new scanner

        // Ask for name
        String name = null;
        while (true) {
            System.out.print("Enter the animal name: ");
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please enter a valid name.");
            } else if (!name.matches("[a-zA-Z ]+")) { // Check if name contains only letters and spaces
                System.out.println("Name can only contain English letters and spaces. Please enter a valid name.");
            } else {
                break; // Exit loop if a valid name is provided
            }
        }

        // Ask for gender
        Animal.Gender selectedGender = null;
        while (true) {
            System.out.println("Choose a gender:");
            System.out.println("1. MALE");
            System.out.println("2. FEMALE");
            System.out.println("3. HERMAPHRODITE");

            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please choose 1, 2, or 3.");
                continue;
            }

            try {
                int chosenGender = Integer.parseInt(input);
                if (chosenGender >= 1 && chosenGender <= 3) {
                    selectedGender = Animal.Gender.values()[chosenGender - 1];
                    break; // Exit loop if a valid gender is chosen
                } else {
                    System.out.println("Invalid choice. Please choose a valid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
            }
        }

        // Ask for weight
        double weight = 0;
        while (true) {
            System.out.print("Enter the weight of the animal: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            try {
                weight = Double.parseDouble(input);
                if (weight <= 0) {
                    System.out.println("Weight must be positive. Please enter a valid weight.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Ask for speed
        double speed = 0;
        while (true) {
            System.out.print("Enter the speed of the animal: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            try {
                speed = Double.parseDouble(input);
                if (speed < 0) {
                    System.out.println("Speed cannot be negative. Please enter a positive number.");
                } else {
                    break; // Exit loop if a valid speed is provided
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Medals
        int numOfMedals = 0;
        while (true) {
            System.out.print("Enter the number of medals the animal has won: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a valid integer.");
                continue;
            }

            try {
                numOfMedals = Integer.parseInt(input);
                if (numOfMedals < 0) {
                    System.out.println("Number of medals cannot be negative. Please enter a valid number.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        Medal[] medals = new Medal[numOfMedals];
        for (int i = 0; i < numOfMedals; i++) {
            // Tournament name
            String medalName = null;
            while (true) {
                System.out.print("Enter the medal tournament name: ");
                medalName = scanner.nextLine().trim();
                if (medalName.isEmpty()) {
                    System.out.println("Tournament name cannot be empty. Please enter a valid name.");
                } else if (!medalName.matches("[a-zA-Z ]+")) { // Check if name contains only letters and spaces
                    System.out.println("Tournament name can only contain English letters and spaces. Please enter a valid name.");
                } else {
                    break; // Exit loop if a valid name is provided
                }
            }

            // Year
            int year = 0;
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            while (true) {
                System.out.print("Enter the year of winning the medal: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Invalid input. Please enter a valid year.");
                    continue;
                }

                try {
                    year = Integer.parseInt(input);
                    if (year <= 0) {
                        System.out.println("Year must be positive. Please enter a valid year.");
                    } else if (year > currentYear) {
                        System.out.println("Year cannot be in the future. Please enter a valid year.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid year.");
                }
            }

            // Medal type
            Medal.Type type = null;
            while (true) {
                System.out.println("Enter the type of medal:");
                System.out.println("1. GOLD");
                System.out.println("2. SILVER");
                System.out.println("3. BRONZE");

                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    continue;
                }

                try {
                    int choice = Integer.parseInt(input);
                    if (choice >= 1 && choice <= 3) {
                        type = Medal.Type.values()[choice - 1];
                        break;
                    } else {
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                }
            }

            medals[i] = new Medal(medalName, year, type);
        }

        // Dive Depth
        double diveDepth = 0.0;
        while (true) {
            System.out.print("Enter the dive depth of the animal: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            try {
                diveDepth = Double.parseDouble(input);
                if (diveDepth > 0) {
                    System.out.println("Dive depth must be non-positive. Please enter a valid number.");
                } else {
                    break; // Exit loop if a valid dive depth is provided
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return new WaterAnimal(name, selectedGender, weight, speed, medals, diveDepth);
    }


    //___________________________________________Terrestrial animal___________________________________________________
    /**
     * Creates a TerrestrialAnimals object based on user input.
     * @return TerrestrialAnimals object created with user-provided data.
     */
    public static TerrestrialAnimals createTerrestrialAnimal() {
        Scanner scanner = new Scanner(System.in); // new scanner

        // Ask for name
        String name = null;
        while (true) {
            System.out.print("Enter the animal name: ");
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please enter a valid name.");
            } else if (!name.matches("[a-zA-Z ]+")) { // Check if name contains only letters and spaces
                System.out.println("Name can only contain English letters and spaces. Please enter a valid name.");
            } else {
                break; // Exit loop if a valid name is provided
            }
        }

        // Ask for gender
        Animal.Gender selectedGender = null;
        while (true) {
            System.out.println("Choose a gender:");
            System.out.println("1. MALE");
            System.out.println("2. FEMALE");
            System.out.println("3. HERMAPHRODITE");

            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please choose 1, 2, or 3.");
                continue;
            }

            try {
                int chosenGender = Integer.parseInt(input);
                if (chosenGender >= 1 && chosenGender <= 3) {
                    selectedGender = Animal.Gender.values()[chosenGender - 1];
                    break; // Exit loop if a valid gender is chosen
                } else {
                    System.out.println("Invalid choice. Please choose a valid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
            }
        }

        // Ask for weight
        double weight = 0;
        while (true) {
            System.out.print("Enter the weight of the animal: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            try {
                weight = Double.parseDouble(input);
                if (weight <= 0) {
                    System.out.println("Weight must be positive. Please enter a valid weight.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Ask for speed
        double speed = 0;
        while (true) {
            System.out.print("Enter the speed of the animal: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            try {
                speed = Double.parseDouble(input);
                if (speed < 0) {
                    System.out.println("Speed cannot be negative. Please enter a positive number.");
                } else {
                    break; // Exit loop if a valid speed is provided
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Medals
        int numOfMedals = 0;
        while (true) {
            System.out.print("Enter the number of medals the animal has won: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a valid integer.");
                continue;
            }

            try {
                numOfMedals = Integer.parseInt(input);
                if (numOfMedals < 0) {
                    System.out.println("Number of medals cannot be negative. Please enter a valid number.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        Medal[] medals = new Medal[numOfMedals];
        for (int i = 0; i < numOfMedals; i++) {
            // Tournament name
            String medalName = null;
            while (true) {
                System.out.print("Enter the medal tournament name: ");
                medalName = scanner.nextLine().trim();
                if (medalName.isEmpty()) {
                    System.out.println("Tournament name cannot be empty. Please enter a valid name.");
                } else if (!medalName.matches("[a-zA-Z ]+")) { // Check if name contains only letters and spaces
                    System.out.println("Tournament name can only contain English letters and spaces. Please enter a valid name.");
                } else {
                    break; // Exit loop if a valid name is provided
                }
            }

            // Year
            int year = 0;
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            while (true) {
                System.out.print("Enter the year of winning the medal: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Invalid input. Please enter a valid year.");
                    continue;
                }

                try {
                    year = Integer.parseInt(input);
                    if (year <= 0) {
                        System.out.println("Year must be positive. Please enter a valid year.");
                    } else if (year > currentYear) {
                        System.out.println("Year cannot be in the future. Please enter a valid year.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid year.");
                }
            }

            // Medal type
            Medal.Type type = null;
            while (true) {
                System.out.println("Enter the type of medal:");
                System.out.println("1. GOLD");
                System.out.println("2. SILVER");
                System.out.println("3. BRONZE");

                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    continue;
                }

                try {
                    int choice = Integer.parseInt(input);
                    if (choice >= 1 && choice <= 3) {
                        type = Medal.Type.values()[choice - 1];
                        break;
                    } else {
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                }
            }

            medals[i] = new Medal(medalName, year, type);
        }

        // Number of legs
        int numOfLegs = 0;
        while (true) {
            System.out.print("Enter number of legs: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            try {
                numOfLegs = Integer.parseInt(input);
                if (numOfLegs < 0) {
                    System.out.println("Number of legs cannot be negative. Please enter a valid number.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return new TerrestrialAnimals(name, selectedGender, weight, speed, medals, numOfLegs);
    }

    //______________________________________Dog___________________________________________________________________
    /**
     * Creates a new Dog object based on provided TerrestrialAnimals information.
     * Prompts user to specify the dog breed, validates input,
     * and creates a new Dog object.
     *
     * @param infoForThisDog A TerrestrialAnimals object containing information about the dog.
     * @return A new Dog object initialized with provided information and breed.
     */
    public static Dog createDog(TerrestrialAnimals infoForThisDog){
        Scanner scanner = new Scanner(System.in); // new scanner
        // Ask for breed
        String breed = null;
        while (true) {
            System.out.print("Enter the dog breed: ");
            breed = scanner.nextLine().trim();

            if (breed.isEmpty()) {
                System.out.println("Breed cannot be empty. Please enter a valid breed.");
            } else if (!breed.matches("[a-zA-Z ]+")) { // Check if breed contains only letters and spaces
                System.out.println("Breed can only contain english letters and spaces. Please enter a valid breed.");
            } else {
                break; // Exit loop if a valid name is provided
            }
        }
        Dog newOne = new Dog(infoForThisDog.getName(), infoForThisDog.getGender(), infoForThisDog.getWeight(),
                infoForThisDog.getSpeed(), infoForThisDog.getMedals(), infoForThisDog.getNoLegs(), breed);
        return newOne;
    }

    //______________________________________Cat___________________________________________________________________
    /**
     * Creates a new Cat object based on provided TerrestrialAnimals information.
     * Prompts user to specify whether the cat is castrated, validates input,
     * and creates a new Cat object.
     *
     * @param infoForThisCat A TerrestrialAnimals object containing information about the cat.
     * @return A new Cat object initialized with provided information and castrated status.
     */
    public static Cat createCat(TerrestrialAnimals infoForThisCat) {
        Scanner scanner = new Scanner(System.in); // new scanner

        // Is castrated
        boolean castrated = false;
        while (true) {
            System.out.println("Is the cat castrated?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            try {
                int choice = Integer.parseInt(input);
                if (choice == 1) {
                    castrated = true;
                    break;
                } else if (choice == 2) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please choose 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        Cat newOne = new Cat(infoForThisCat.getName(), infoForThisCat.getGender(), infoForThisCat.getWeight(), infoForThisCat.getSpeed(),
                infoForThisCat.getMedals(), infoForThisCat.getNoLegs(), castrated);
        return newOne;
    }


    //______________________________________Snake___________________________________________________________________
    /**
     * Creates a new Snake object based on provided TerrestrialAnimals information.
     * Prompts user to enter the length of the snake and whether it is poisonous,
     * validates input, and creates a new Snake object.
     *
     * @param infoForThisSnake A TerrestrialAnimals object containing information about the snake.
     * @return A new Snake object initialized with provided information, length, and poisonous status.
     */
    public static Snake createSnake(TerrestrialAnimals infoForThisSnake) {
        Scanner scanner = new Scanner(System.in); // new scanner

        // Ask for length
        double length = 0.0;
        while (true) {
            System.out.print("Enter the length of the snake: ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                try {
                    length = Double.parseDouble(input);
                    if (length < 0) {
                        System.out.println("Length cannot be negative. Please enter a positive number.");
                    } else {
                        break; // Exit loop if a valid speed is provided
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            } else {
                System.out.println("Input cannot be empty. Please enter a valid number.");
            }
        }

        // Ask for Poisonous
        Snake.Poisonous poisonous = Snake.Poisonous.LOW;
        while (true) {
            System.out.println("what is the snake poisonous level?");
            System.out.println("1. Low");
            System.out.println("2. Medium");
            System.out.println("3. High");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                try {
                    int choice = Integer.parseInt(input);
                    if (choice == 1) {
                        break;
                    } else if (choice == 2) {
                        poisonous = Snake.Poisonous.MEDIUM;
                        break;
                    } else if (choice == 3) {
                        poisonous = Snake.Poisonous.HIGH;
                        break;
                    } else {
                        System.out.println("Invalid choice. Please choose 1 or 2.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            } else {
                System.out.println("Input cannot be empty. Please enter a valid number.");
            }
        }

        Snake newOne = new Snake(infoForThisSnake.getName(), infoForThisSnake.getGender(), infoForThisSnake.getWeight(),
                infoForThisSnake.getSpeed(), infoForThisSnake.getMedals(), infoForThisSnake.getNoLegs(), length, poisonous);
        return newOne;
    }



    //______________________________________Alligator___________________________________________________________________
    /**
     * Creates a new Alligator object based on provided WaterAnimal information.
     * Prompts user to enter the area of living and number of legs for the alligator, validates input, and creates a new Alligator object.
     *
     * @param infoForThisAlligator A WaterAnimal object containing information about the alligator.
     * @return A new Alligator object initialized with provided information and specified area of living and number of legs.
     */
    public static Alligator createAlligator(WaterAnimal infoForThisAlligator){
        Scanner scanner = new Scanner(System.in); // new scanner

        // area of living
        String areaOfLiving = null;
        while (true) {
            System.out.print("Enter the alligator area of living: ");
            areaOfLiving = scanner.nextLine().trim();
            if (areaOfLiving.isEmpty()) {
                System.out.println("Area of living cannot be empty. Please enter a valid area.");
            } else if (!areaOfLiving.matches("[a-zA-Z ]+")) { // Check if area contains only letters and spaces
                System.out.println("Area of living can only contain English letters and spaces. Please enter a valid area.");
            } else {
                break; // Exit loop if a valid area is provided
            }
        }

        // number of legs
        int noOfLegs = 0;
        while (true) {
            System.out.print("Enter the number of legs of the alligator: ");
            try {
                noOfLegs = Integer.parseInt(scanner.nextLine().trim());
                if (noOfLegs <= 0) {
                    System.out.println("Number of legs must be a positive integer. Please enter a valid number.");
                } else {
                    break; // Exit loop if a valid number of legs is provided
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        Alligator newOne = new Alligator(infoForThisAlligator.getName(), infoForThisAlligator.getGender(),
                infoForThisAlligator.getWeight(), infoForThisAlligator.getSpeed(), infoForThisAlligator.getMedals(),
                infoForThisAlligator.getDiveDepth(), noOfLegs, areaOfLiving);
        return newOne;
    }

    //______________________________________Whale___________________________________________________________________
    /**
     * Creates a new Whale object based on provided WaterAnimal information.
     * Prompts user to enter the food type for the whale, validates input, and creates a new Whale object.
     *
     * @param infoForThisWhale A WaterAnimal object containing information about the whale.
     * @return A new Whale object initialized with provided information and specified food type.
     */
    public static Whale createWhale(WaterAnimal infoForThisWhale){
        Scanner scanner = new Scanner(System.in); // new scanner
        // food type
        String foodType = null;
        while (true) {
            System.out.print("Enter the whale food type: ");
            foodType = scanner.nextLine().trim();
            if (foodType.isEmpty()) {
                System.out.println("food type cannot be empty. Please enter a valid food type.");
            } else if (!foodType.matches("[a-zA-Z ]+")) { // Check if food type contains only letters and spaces
                System.out.println("Food type can only contain english letters and spaces. Please enter a valid food type.");
            } else {
                break; // Exit loop if a valid food type is provided
            }
        }

        Whale newOne = new Whale(infoForThisWhale.getName(), infoForThisWhale.getGender(), infoForThisWhale.getWeight(),
                infoForThisWhale.getSpeed(), infoForThisWhale.getMedals(), infoForThisWhale.getDiveDepth(), foodType);
        return newOne;
    }


    //______________________________________Dolphin___________________________________________________________________
    /**
     * Creates a new Dolphin object based on provided WaterAnimal information.
     * Prompts user to choose water type (Sea or Sweet), validates input, and creates a new Dolphin object.
     *
     * @param infoForThisDolphin A WaterAnimal object containing information about the dolphin.
     * @return A new Dolphin object initialized with provided information and chosen water type.
     */
    public static Dolphin createDolphin(WaterAnimal infoForThisDolphin) {
        Scanner scanner = new Scanner(System.in); // new scanner

        // Water type
        Dolphin.WaterType waterType = Dolphin.WaterType.Sea;
        while (true) {
            System.out.println("Please choose water type for the dolphin: ");
            System.out.println("1. Sea");
            System.out.println("2. Sweet");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            try {
                int choice = Integer.parseInt(input);
                if (choice == 1) {
                    break;
                } else if (choice == 2) {
                    waterType = Dolphin.WaterType.Sweet;
                    break;
                } else {
                    System.out.println("Invalid choice. Please choose 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        Dolphin newOne = new Dolphin(infoForThisDolphin.getName(), infoForThisDolphin.getGender(), infoForThisDolphin.getWeight(),
                infoForThisDolphin.getSpeed(), infoForThisDolphin.getMedals(), infoForThisDolphin.getDiveDepth(), waterType);
        return newOne;
    }


    //______________________________________Eagle___________________________________________________________________
    /**
     * Creates a new Eagle object based on provided AirAnimal information.
     * Prompts user to enter the altitude of flight, validates input, and creates a new Eagle object.
     *
     * @param infoForThisEagle An AirAnimal object containing information about the eagle.
     * @return A new Eagle object initialized with provided information and altitude of flight.
     */
    public static Eagle createEagle(AirAnimal infoForThisEagle) {
        Scanner scanner = new Scanner(System.in); // new scanner

        // Altitude
        double altitudeOfFlight = 0.0;
        while (true) {
            System.out.print("Enter the altitude of flight: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Altitude of flight cannot be empty.");
                continue;
            }

            try {
                altitudeOfFlight = Double.parseDouble(input);
                if (altitudeOfFlight < 0) {
                    System.out.println("Altitude of flight cannot be negative. Please enter a positive number.");
                } else if (altitudeOfFlight > Eagle.MAX_ALTITUDE) {
                    System.out.println("Altitude of flight cannot be more than " + Eagle.MAX_ALTITUDE + ". Please enter a valid altitude.");
                } else {
                    break; // Exit loop if a valid altitude is provided
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        Eagle newOne = new Eagle(infoForThisEagle.getName(), infoForThisEagle.getGender(), infoForThisEagle.getWeight(),
                infoForThisEagle.getSpeed(), infoForThisEagle.getMedals(), infoForThisEagle.getWingspan(), altitudeOfFlight);
        return newOne;
    }


    //______________________________________Pigeon___________________________________________________________________
    /**
     * Creates a new Pigeon object based on provided AirAnimal information.
     * Prompts user to enter the Pigeon's family, validates input, and creates a new Pigeon object.
     *
     * @param infoForThisPigeon An AirAnimal object containing information about the pigeon.
     * @return A new Pigeon object initialized with provided information and family.
     */
    public static Pigeon createPigeon(AirAnimal infoForThisPigeon) {
        Scanner scanner = new Scanner(System.in); // new scanner

        // family
        String family = null;
        while (true) {
            System.out.print("Enter the Pigeon family: ");
            family = scanner.nextLine().trim();

            if (family.isEmpty()) {
                System.out.println("Family cannot be empty. Please enter a valid Family.");
            } else if (!family.matches("[a-zA-Z ]+")) {
                System.out.println("Family can only contain english letters and spaces. Please enter a valid family.");
            } else {
                break; // Exit the loop if valid family was provided
            }
        }

        Pigeon newOne = new Pigeon(infoForThisPigeon.getName(), infoForThisPigeon.getGender(), infoForThisPigeon.getWeight(),
                infoForThisPigeon.getSpeed(), infoForThisPigeon.getMedals(), infoForThisPigeon.getWingspan(), family);
        return newOne;
    }
}
