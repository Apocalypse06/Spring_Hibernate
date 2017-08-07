package tx01.facade;

import tx01.model.OrderBean;

public interface OrderService {
	
     public void processOrder(OrderBean ob);
     int findTotalOrderAmount(OrderBean ob);
}
