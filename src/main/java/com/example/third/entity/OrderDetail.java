
package com.example.third.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    String Num;
    Float Amount;
    Float Price;
    int Quantity;
    String Order_ID;
    String Product_ID;
}
