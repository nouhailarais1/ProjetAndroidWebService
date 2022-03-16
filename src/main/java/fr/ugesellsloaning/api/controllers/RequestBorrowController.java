package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.RequestBorrow;
import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.services.RequestBorrowServices;
import fr.ugesellsloaning.api.services.UserServices;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api( tags={"Operations Demande d'emprun \"RequestBorrow\""})
@RestController
@RequestMapping("/api/requestBorrow")
public class RequestBorrowController {
    @Autowired
    RequestBorrowServices requestBorrowServices;

    @Autowired
    UserServices userServices;

    @GetMapping(path = "/")
    public List<RequestBorrow> list(){
        return (List<RequestBorrow>) requestBorrowServices.listRequestBorrow();
    }

    @PostMapping(path = "/")
    public void add(@Valid @RequestBody RequestBorrow requestBorrow){
        String email = "kilomn@mail.com";
        User user = userServices.getUserByEmail(email);
        requestBorrow.setUser(user.getId());

        requestBorrowServices.save(requestBorrow);
    }

    @GetMapping(path = "/{id}")
    public Optional<RequestBorrow> getById(@PathVariable(value = "id")  long id){
        return  requestBorrowServices.getRequestBorrowById(id);
    }

    @GetMapping(path = "product/{product}")
    public List<RequestBorrow> getByProduct(@PathVariable(value = "product")  long product){
        return  requestBorrowServices.getRequestBorrowByProduct(product);
    }

    @PutMapping(value = "/")
    public void edit(@Valid @RequestBody RequestBorrow requestBorrow){
        requestBorrowServices.save(requestBorrow);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id")  long id){
        requestBorrowServices.deleteById(id);
    }
}
