package com.example.demo4;

public class CallGenerateBill
{
    public static void generateBill()
    {

        Category category=Category.selectCategory();
        GenerateBill generateBill=new GenerateBill(category);
        generateBill.inputDetails();

        int unitsConsumed=generateBill.ConsumedUnits();
        System.out.println("Units Consumed: "+unitsConsumed);
        int billwithouttaxes= generateBill.BillWithoutTaxes(unitsConsumed);
        System.out.println("\nBill Without Taxes: "+billwithouttaxes);              //-----
        int billwithtaxes=generateBill.BillWithTaxes(billwithouttaxes);
        System.out.println("Taxes in the bill: "+ (billwithtaxes-billwithouttaxes));
        //System.out.println("Bill with Eid package is: "+);//-------
        System.out.println("Total Bill after adding Taxes: "+billwithtaxes);

       double eidpackage=generateBill.eidpackage(billwithtaxes);
        System.out.println("Bill with Eid discount is: "+eidpackage);
        generateBill.WriteBilltoFile(unitsConsumed,billwithtaxes,eidpackage);
        System.out.println("Bill added to the file successfully");

    }


}
