package com.jpastudy.bookstudy.domain.item;

import com.jpastudy.bookstudy.domain.Category;
import com.jpastudy.bookstudy.exception.NotiEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();


    //==비즈니스 로직 ==//

    /*
    * 재고 증가
    * */

    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    //remove quantity
    public void removeStock(int quantity){

        int restStock = this.stockQuantity - quantity;

        if(restStock<0){
            throw new NotiEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
