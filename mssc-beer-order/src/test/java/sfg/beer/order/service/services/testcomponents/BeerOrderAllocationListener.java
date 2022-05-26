package sfg.beer.order.service.services.testcomponents;


import brewery.model.events.AllocateOrderRequest;
import brewery.model.events.AllocateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import sfg.beer.order.service.config.JmsConfig;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderAllocationListener {


    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(Message msg){
        AllocateOrderRequest request = (AllocateOrderRequest) msg.getPayload();

        request.getBeerOrder().getBeerOrderLines().forEach(beerOrderLineDto -> {
            beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity());
        });

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE,
                AllocateOrderResult.builder()
                        .beerOrderDto(request.getBeerOrder())
                        .pendingInventory(false)
                        .allocationError(false)
                        .build());
    }
}
