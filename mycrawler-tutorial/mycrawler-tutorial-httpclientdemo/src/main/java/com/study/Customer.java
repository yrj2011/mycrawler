package com.study;

/**
 * PackageName  com.study
 * Description  please write description.
 * User:        Administrator
 * Time:        2016/10/28 10:42
 */
public class Customer
{
    private String id;
    private boolean isNewRecord;
    private String customerName;

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public boolean isNewRecord()
    {
        return isNewRecord;
    }

    public void setNewRecord(boolean newRecord)
    {
        isNewRecord = newRecord;
    }
}
