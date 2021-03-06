package fr.ugesellsloaning.api.entities;


import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
public class Comment implements Serializable {
    public Comment(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        createdAt = dateFormat.format(d).toString();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @NotBlank
    @Column(length = 2000)
    String content;

    int rate;

    String createdAt;

    long product;

    String lastName;

    String firstName;

    long user;

    //@ManyToOne(optional = true, fetch = FetchType.LAZY)
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    //@Fetch(FetchMode.JOIN)
    //User user;

}
