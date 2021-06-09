/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 *
 * @author Matthew Jones, 220077681
 */
public class ReadStakeholderSer 
{
    String stakeholder = "stakeholder.ser";
    FileWriter fileW;
    BufferedWriter bufferW;
    BufferedWriter noobN;
    InputStream inStream;
    ObjectInputStream objStream;
   
    public void myFile(String myFile)
    {
        try
        {
            fileW = new FileWriter(new File(myFile));
            bufferW = new BufferedWriter(fileW);
            System.out.println(" Has been created "+ myFile );
           
        } catch (IOException ioe)
        {
            System.exit(0);
        }
    }
    
    private String formatDate(String tome)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "dd MMM yyyy",
                Locale.ENGLISH);

        LocalDate parTime = LocalDate.parse(tome);
        return parTime.format(formatter);
    }
    
    private ArrayList<Customer> custList()
    {
     ArrayList<Customer> customers = new ArrayList<>();
     try
     {
        inStream = new FileInputStream(stakeholder);
        objStream = new ObjectInputStream(inStream);
            while (true)
            {
                Object obj = objStream .readObject();
                if (obj instanceof Customer)
                {
                    customers.add((Customer) obj);
                }
            }
           
        } catch (EOFException eofe)
        {
           
        } catch (IOException | ClassNotFoundException e)
        {
           System.exit(0);
           
        } finally
        {
            try
            {
               bufferW.close();
                objStream.close();
               
            } catch (IOException e)
            {
            }
        }
        if (!customers.isEmpty())
        {
            Collections.sort(customers,
                    (Customer sort, Customer sorted) ->
                     sort.getStHolderId().compareTo(sorted.getStHolderId())
            );
        }
        return customers;
    }
    public void readCustomer()
    {
        String formula = "%s\t%-10s\t%-10s\t%-10s\t%-10s\n";
        String line = "===========================================================\n";
       
        try
        {  
            System.out.print( "======================= CUSTOMERS =========================\n");
           
            System.out.printf(formula, "ID", "Name", "Surname",
                    "Date Of Birth", "Age");
           
            System.out.print(line);
           
            for (int i = 0; i < custList().size(); i++)
            {  
                System.out.printf(
                        formula,
                        custList().get(i).getStHolderId(),
                        custList().get(i).getFirstName(),
                        custList().get(i).getSurName(),
                        formatDate(custList().get(i).getDateOfBirth()),
                        calculateAge(custList().get(i).getDateOfBirth())
                );
            }
           System.out.println("\nAmmount of Customers that can rent:" + canRent());
           System.out.println("\nAmmount of Customers who cannot rent:"+ canNotRent());
        } catch (Exception e)
        {
        }
    }
    
    private ArrayList<Supplier> supList()
    {
        ArrayList<Supplier> suppliers = new ArrayList<>();
       
        try
        {
            inStream = new FileInputStream(new File(stakeholder));
            objStream = new ObjectInputStream(inStream);
            while (true)
            {
                Object obj = objStream.readObject();
                if (obj instanceof Supplier)
                {
                    suppliers.add((Supplier) obj);
                }
            }
           
        } catch (EOFException eofe)
        {
         
        } catch (IOException | ClassNotFoundException e)
        {
        } finally
        {
            try
            {
                inStream.close();
                objStream.close();
               
            } catch (IOException e)
            {
            }
        }
        if (!suppliers.isEmpty())
        {
            Collections.sort(
            suppliers,
            (Supplier su, Supplier sou) ->
            su.getName().compareTo(sou.getName())
            );
        }
        return suppliers;
    }
   
    public void readSupplier()
    {
        try
        {
            System.out.print("======================= SUPPLIERS =========================\n");
            System.out.printf("%s\t%-20s\t%-10s\t%-10s\n", "ID", "Name", "Prod Type",
                "Description");
            System.out.print("===========================================================\n");
            for (int i = 0; i < supList().size(); i++)
            {
               System.out.printf(
                        "%s\t%-20s\t%-10s\t%-10s\n",
                        supList().get(i).getStHolderId(),
                        supList().get(i).getName(),
                        supList().get(i).getProductType(),
                        supList().get(i).getProductDescription()
                );
            }
           
        } catch (Exception e)
        {
        }
    }
   
    private int calculateAge(String tome)
    {
        LocalDate parseDob = LocalDate.parse(tome);
        int dobYear  = parseDob.getYear();
        ZonedDateTime todayDate = ZonedDateTime.now();
        int currentYear = todayDate.getYear();
        return currentYear - dobYear;
    }
   
    private int canRent()
    {
        int canRent = 0;
        for (int i = 0; i < custList().size(); i++)
        {
            if (custList().get(i).getCanRent())
            {
                canRent += 1;
            }
        }
        return canRent;
    }
   
    private int canNotRent()
    {
        int canNotRent = 0;
        for (int i = 0; i < custList().size(); i++)
        {
            if (!custList().get(i).getCanRent())
            {
                canNotRent += 1;
            }
        }
        return canNotRent;
    }
   
    public void closeFile(String myFile)
    {
        try
        {
            fileW.close();
            bufferW.close();
            System.out.println(myFile + " has been closed");

        } catch (IOException ex)
        {
        }
    }
}
