package System;


import Animals.*;
import java.util.Scanner;
import static Animals.CreateAnimal.*;

public class Sys {
    public static void main(String[] args) {
        // Create a Scanner object to read input from the user
        Scanner scanner = new Scanner(System.in);

        int numOfAnimals = 0;


        // Loop until valid input is provided
        while (true) {
            try {
                // Ask the user for input
                System.out.print("Enter the number of animals you want to create in the system: ");

                // This conversion may throw a "NumberFormatException" if the input is not convertible to an integer.
                numOfAnimals = Integer.parseInt(scanner.nextLine()); // Read a line of text

                if (numOfAnimals < 0) {
                    System.out.println("Number of animals cannot be negative. Please enter a positive number.");
                } else {
                    break; // Exit the loop if input is valid
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Create an array to hold the user's chosen animals
        Animal[] userAnimalsArr = new Animal[numOfAnimals];

        for (int i = 0; i < numOfAnimals; i++) {
            while (true) {
                System.out.println("Please choose Type of animal number " + (i + 1) + ": ");
                System.out.println("1. Air animal");
                System.out.println("2. Water animal");
                System.out.println("3. Terrestrial animal");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Invalid input. Please choose 1, 2, or 3.");
                    continue;
                }
                int typeChoice;
                try {
                    typeChoice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please choose 1, 2, or 3.");
                    continue;
                }

                switch (typeChoice) {
                    case 1:
                        while (true) {
                            System.out.println("Please choose the air animal: ");
                            System.out.println("1. Eagle");
                            System.out.println("2. Pigeon");
                            input = scanner.nextLine().trim();
                            if (input.isEmpty()) {
                                System.out.println("Invalid input. Please choose 1 or 2.");
                                continue;
                            }
                            int airAnimalChoice;
                            try {
                                airAnimalChoice = Integer.parseInt(input);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please choose 1 or 2.");
                                continue;
                            }

                            if (airAnimalChoice == 1) {
                                // Create an Eagle
                                AirAnimal eagleInfo = createAirAnimal();
                                userAnimalsArr[i] = CreateAnimal.createEagle(eagleInfo);
                                break;
                            } else if (airAnimalChoice == 2) {
                                // Create a Pigeon
                                AirAnimal pigeonInfo = createAirAnimal();
                                userAnimalsArr[i] = CreateAnimal.createPigeon(pigeonInfo);
                                break;
                            } else {
                                System.out.println("Invalid input. Please choose 1 or 2.");
                            }
                        }
                        break;

                    case 2:
                        while (true) {
                            System.out.println("Please choose the water animal: ");
                            System.out.println("1. Alligator");
                            System.out.println("2. Whale");
                            System.out.println("3. Dolphin");
                            input = scanner.nextLine().trim();
                            if (input.isEmpty()) {
                                System.out.println("Invalid input. Please choose 1, 2, or 3.");
                                continue;
                            }
                            int waterAnimalChoice;
                            try {
                                waterAnimalChoice = Integer.parseInt(input);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please choose 1, 2, or 3.");
                                continue;
                            }

                            if (waterAnimalChoice == 1) {
                                // Create an Alligator
                                WaterAnimal alligatorInfo = createWaterAnimal();
                                userAnimalsArr[i] = createAlligator(alligatorInfo);
                                break;
                            } else if (waterAnimalChoice == 2) {
                                // Create a Whale
                                WaterAnimal whaleInfo = createWaterAnimal();
                                userAnimalsArr[i] = createWhale(whaleInfo);
                                break;
                            } else if (waterAnimalChoice == 3) {
                                // Create a Dolphin
                                WaterAnimal dolphinInfo = createWaterAnimal();
                                userAnimalsArr[i] = createDolphin(dolphinInfo);
                                break;
                            } else {
                                System.out.println("Invalid input. Please choose 1, 2, or 3.");
                            }
                        }
                        break;

                    case 3:
                        while (true) {
                            System.out.println("Please choose the terrestrial animal: ");
                            System.out.println("1. Dog");
                            System.out.println("2. Cat");
                            System.out.println("3. Snake");
                            input = scanner.nextLine().trim();
                            if (input.isEmpty()) {
                                System.out.println("Invalid input. Please choose 1, 2, or 3.");
                                continue;
                            }
                            int terrestrialAnimalChoice;
                            try {
                                terrestrialAnimalChoice = Integer.parseInt(input);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please choose 1, 2, or 3.");
                                continue;
                            }

                            if (terrestrialAnimalChoice == 1) {
                                // Create a Dog
                                TerrestrialAnimals dogInfo = createTerrestrialAnimal();
                                userAnimalsArr[i] = createDog(dogInfo);
                                break;
                            } else if (terrestrialAnimalChoice == 2) {
                                // Create a Cat
                                TerrestrialAnimals catInfo = createTerrestrialAnimal();
                                userAnimalsArr[i] = createCat(catInfo);
                                break;
                            } else if (terrestrialAnimalChoice == 3) {
                                // Create a Snake
                                TerrestrialAnimals snakeInfo = createTerrestrialAnimal();
                                userAnimalsArr[i] = createSnake(snakeInfo);
                                break;
                            } else {
                                System.out.println("Invalid input. Please choose 1, 2, or 3.");
                            }
                        }
                        break;

                    default:
                        System.out.println("Invalid choice. Please choose 1, 2, or 3.");
                        continue; // Restart the loop to choose again
                }

                // Break out of the typeChoice loop once an animal is successfully chosen and created
                break;
            }
        }

        while (true) {
            System.out.println("Please choose:");
            System.out.println("1. View information about all the animals in the system");
            System.out.println("2. View the speech of every animal in the system");
            System.out.println("3. Exit");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please choose 1, 2, or 3.");
                continue; // ask for input again
            }

            if (choice == 1) {
                for (int i = 0; i < userAnimalsArr.length; i++) {
                    System.out.println(userAnimalsArr[i].toString());
                }
            } else if (choice == 2) {
                for (int i = 0; i < userAnimalsArr.length; i++) {
                    userAnimalsArr[i].makeSound();
                    System.out.println();
                }
            } else if (choice == 3) {
                System.out.println("Thank you for using the system.");
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid input. Please choose 1, 2, or 3.");
            }
        }



    }
    }
