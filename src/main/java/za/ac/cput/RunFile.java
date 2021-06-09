/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput;

/**
 *
 * @author Matthew Jones, 220077681
 */
public class RunFile 
{
    public static void main(String[] args) 
    {
        ReadStakeholderSer object = new ReadStakeholderSer();
        object.myFile("customerOutput.txt");
        object.readCustomer();
        object.closeFile("customerOutput.txt");
        System.out.println("---------------------------------------------------");
        object.myFile("supplierOutput.txt");
        object.readSupplier();
        object.closeFile("supplierOutput.txt");
    }
}
