package com.app.bookstoreapi.service;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.app.bookstoreapi.entity.Customer;
import com.app.bookstoreapi.repo.CustomerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Customer customer=customerRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
        return new User(customer.getUsername(),customer.getPassword(),new ArrayList<>());
    }
}
