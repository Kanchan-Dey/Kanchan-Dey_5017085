package com.app.bookstoreapi.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.app.bookstoreapi.dto.CustomerDTO;
import com.app.bookstoreapi.entity.Customer;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE=Mappers.getMapper(CustomerMapper.class);
    CustomerDTO customertoCustomerDTO(Customer customer);
    Customer customerDTOtoCustomer(CustomerDTO customerDTO);
}
