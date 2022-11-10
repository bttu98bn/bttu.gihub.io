package com.example.third.entity;



import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class product {
    String code;
    String name;
    Float price;
    String image;
    Date create_date;
    Boolean active;

    public Object[] toArray()
    {
        return new Object[]{this.code, this.name, this.price, this.create_date, this.image, this.active};
    }

    public Object[] toArrayForUpdate()
    {
        return new Object[]{this.name, this.price, this.image, this.create_date, this.active, this.code};
    }
}
