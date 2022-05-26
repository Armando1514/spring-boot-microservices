package sfg.beer.order.service.web.mappers;

import brewery.model.CustomerDto;
import org.mapstruct.Mapper;
import sfg.beer.order.service.domain.Customer;

@Mapper(uses = {DateMapper.class})
public interface CustomerMapper {
    CustomerDto customerToDto(Customer customer);

    Customer dtoToCustomer(Customer dto);
}