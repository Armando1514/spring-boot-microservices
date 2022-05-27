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

        boolean pendingInventory = false;
        boolean allocationError = false;
        boolean sendResponse = true;

        //set allocation error
        if (request.getBeerOrder().getCustomerRef() != null) {
            if (request.getBeerOrder().getCustomerRef().equals("fail-allocation")){
                allocationError = true;
            }  else if (request.getBeerOrder().getCustomerRef().equals("partial-allocation")) {
                pendingInventory = true;
            } else if (request.getBeerOrder().getCustomerRef().equals("dont-allocate")){
                sendResponse = false;
            }
        }


        boolean finalPendingInventory = pendingInventory;

        request.getBeerOrder().getBeerOrderLines().forEach(beerOrderLineDto -> {
            if (finalPendingInventory) {
                beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity() - 1);
            } else {
                beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity());
            }
        });


        if(sendResponse) {
            jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE,
                    AllocateOrderResult.builder()
                            .beerOrderDto(request.getBeerOrder())
                            .pendingInventory(pendingInventory)
                            .allocationError(allocationError)
                            .build());
        }
    }
}
