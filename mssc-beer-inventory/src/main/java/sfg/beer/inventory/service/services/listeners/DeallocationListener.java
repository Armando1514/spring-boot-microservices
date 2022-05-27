package sfg.beer.inventory.service.services.listeners;


import brewery.model.events.AllocateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import sfg.beer.inventory.service.config.JmsConfig;
import sfg.beer.inventory.service.services.AllocationService;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeallocationListener {

    private final AllocationService allocationService;

    @JmsListener(destination = JmsConfig.DEALLOCATE_ORDER_QUEUE)
    public void listen(AllocateOrderRequest request){
        allocationService.deallocateOrder(request.getBeerOrder());
    }
}
