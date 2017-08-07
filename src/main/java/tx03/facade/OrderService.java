package tx03.facade;

import tx03.model.OrderBean;

public interface OrderService {
	
     public void processOrder(OrderBean ob);
     int findTotalOrderAmount(OrderBean ob);
}
