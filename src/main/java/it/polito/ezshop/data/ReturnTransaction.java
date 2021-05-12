package it.polito.ezshop.data;

import java.util.List;

public interface ReturnTransaction {

    Integer getReturnId();

    void setReturnId(Integer returnId);
    
    Integer getTransactionId();

    void setTransactionId(Integer transactionId);

    List<TicketEntry> getEntries();

    void setEntries(List<TicketEntry> entries);

    double getPrice();

    void setPrice(double price);
    
}
