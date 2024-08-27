package com.app.bookstoreapi.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.app.bookstoreapi.entity.Customer;
import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>{
    @Query("select c from Customer c where c.firstName=:firstName")
    Optional<List<Customer>> findByFirstName(String firstName);
    @Query("select c from Customer c where c.lastName=:lastName")
    Optional<List<Customer>> findByLastName(String lastName);
    @Query("select c from Customer c where c.age=:age")
    Optional<List<Customer>> findByAge(Integer age);
    @Query("select c from Customer c where c.number=:number")
    Optional<List<Customer>> findByNumber(Long number);
    Optional<Customer> findByUsername(String username);
}
