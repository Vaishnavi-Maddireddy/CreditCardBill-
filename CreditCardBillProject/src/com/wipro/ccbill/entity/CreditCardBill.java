package com.wipro.ccbill.entity;
import java.util.Date;

import com.wipro.ccbill.exception.InputValidationException;
public class CreditCardBill {
	private String creditCardNo;
	private String customerId;
	private Date billDate;
	private Transaction monthTransactions[];
	public CreditCardBill()
	{
		
	}
	public CreditCardBill (String creditCardNo,String customerId, Date BillDate,Transaction monthTransactions[], Date billDate)
	{
		this.creditCardNo=creditCardNo;
		this.customerId=customerId;
		this.billDate=billDate;
		this.monthTransactions=monthTransactions;
	}
	public String validateData() throws InputValidationException
    {
        if((creditCardNo!=null)&&(creditCardNo.length()==16)&&(customerId!=null)&&(customerId.length()>=6))
        {
        	return "valid";
        }
        else
        {
        	throw new InputValidationException();
        }
    }
	public double getTotalAmount(String transactionType) //DB
	{
		double amount=0.0;
		if(!(transactionType.equals("CR") || transactionType.equals("DB")))
		{
			return 0.0;
		}
		else
		{
			for(int i=0;i<monthTransactions.length;i++)
			{
				if(monthTransactions[i].getTransactionType().equals(transactionType))
				{
					amount+=monthTransactions[i].getTransactionAmount();
				}
			}
		}
        return amount;
	}
	public double calculateBillAmount() throws InputValidationException
	{
        if(!validateData().equals("valid"))
	    {
        	return 0.0;
	    }
	    if(monthTransactions==null||monthTransactions.length==0)
	    {
	        return 0.0;
	    }
	    double outstanding_amount=getTotalAmount("DB")-getTotalAmount("CR");
	    double interest=(outstanding_amount*(19.9/100)/12);
	    return outstanding_amount+interest;
	}	
}
