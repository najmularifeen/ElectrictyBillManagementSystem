package com.example.demo4;

import java.util.Scanner;
public enum Category
{
    RESIDENTIAL, COMMERCIAL, INDUSTRY;

    static Scanner sc = new Scanner(System.in);
    // private Customer currentCustomer = null;

    public static Category selectCategory()
    {
        System.out.println("\nSelect category:");
        System.out.println("1. Residential");
        System.out.println("2. Commercial");
        System.out.println("3. Industry");

        Category category = null;

        while (category == null)
        {
            System.out.print("Enter choice: ");
            int categoryChoice = sc.nextInt();

            switch (categoryChoice)
            {
                case 1:
                    category = Category.RESIDENTIAL;
                    break;
                case 2:
                    category = Category.COMMERCIAL;
                    break;
                case 3:
                    category = Category.INDUSTRY;
                    break;
                default:
                    System.out.println("Invalid category choice");
            }
        }
        sc.nextLine();
        return category;
    }

}


