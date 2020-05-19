package com.jpastudy.bookstudy.service;

import com.jpastudy.bookstudy.domain.item.Book;
import com.jpastudy.bookstudy.domain.item.Item;
import com.jpastudy.bookstudy.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId , Book bookParam){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(bookParam.getPrice());
        findItem.setName(bookParam.getName());
        findItem.setStockQuantity(bookParam.getStockQuantity());
    }

    public List<Item> findItems(){
        return itemRepository.findAll();

    }
    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

}
