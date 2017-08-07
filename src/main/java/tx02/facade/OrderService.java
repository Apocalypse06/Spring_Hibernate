package tx02.facade;

import tx02.model.OrderBean;

public interface OrderService {
	
     public void processOrder(OrderBean ob);
     int findTotalOrderAmount(OrderBean ob);
}
