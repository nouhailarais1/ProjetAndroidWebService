package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.Borrow;
import fr.ugesellsloaning.api.entities.ReturnProduct;
import fr.ugesellsloaning.api.repositories.IBorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowServices {
    @Autowired
    private IBorrowRepository borrowRepostory;

    @Autowired
    ReturnProductServices returnProductServices;

    public void save(Borrow borrow){

        borrowRepostory.save(borrow);

    }

    public Iterable<Borrow> listBorrow(){

        Iterable<Borrow> list =borrowRepostory.findAll();;
        for (Borrow b:list) {
            List<ReturnProduct> r = returnProductServices.getReturnProductByProduct(b.getProduct());
            for (ReturnProduct t: r) {
                if(b.isReturned()){
                    b.setReturnProduct(t);
                }
            }
        }
        return list;

    }

    public Optional<Borrow> getBorrowById(long id){
        return borrowRepostory.findById(id);
    }

    public List<Borrow> getBorrowByEndAt(Date date){
        return borrowRepostory.findByEndAt(date);
    }

    public void delete(Borrow borrow){
        borrowRepostory.delete(borrow);
    }

    public void deleteById(Long id){
        borrowRepostory.deleteById(id);
    }

    public void deleteByProduct(long product){
        List<Borrow> borrows = getBorrowByProduct(product);
        for (Borrow borrow: borrows) {
            deleteById(borrow.getId());
        }
    }

    public List<Borrow> getBorrowByProduct(long product){
        return borrowRepostory.findBorrowByProduct(product);
    }

    public Borrow BorrowReturnedIsFalse(long product){
        return borrowRepostory.borrowIsFalse(product);
    }

    public List<Borrow> borrowByUser(long user){
        return borrowRepostory.borrowByUser(user);
    }

    public List<Borrow> getBorrowByUser(long user){
        return borrowRepostory.findBorrowByUser(user);
    }

}



