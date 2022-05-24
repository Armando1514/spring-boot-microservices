package sfg.beer.order.service.sm.actions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import sfg.beer.order.service.config.JmsConfig;
import sfg.beer.order.service.domain.BeerOrder;
import sfg.beer.order.service.domain.BeerOrderEventEnum;
import sfg.beer.order.service.domain.BeerOrderStatusEnum;
import sfg.beer.order.service.repositories.BeerOrderRepository;
import sfg.beer.order.service.services.BeerOrderManagerImpl;
import sfg.beer.order.service.web.mappers.BeerOrderMapper;

import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
public class AllocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;


    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        BeerOrder beerOrder = beerOrderRepository.findOneById(UUID.fromString(beerOrderId));

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_QUEUE,
                beerOrderMapper.beerOrderToDto(beerOrder));

        log.debug("Sent allocation request for order id: " + beerOrderId);
    }
}
