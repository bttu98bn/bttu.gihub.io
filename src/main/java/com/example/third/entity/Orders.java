package com.example.third.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    String Id;
    Float Amount;
    String Customer_Address;
    String Customer_Email;
    String Customer_Name;
    String Customer_Phone;
    Date Order_Date;
    public Object[] toArray()
    {
        return new Object[]{this.Id, this.Amount, this.Customer_Address, this.Customer_Email, this.Customer_Name, this.Customer_Phone};
    }

   


}
