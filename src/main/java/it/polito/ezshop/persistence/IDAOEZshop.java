package it.polito.ezshop.persistence;

import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;

import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.data.SaleTransaction;
import it.polito.ezshop.data.User;
import it.polito.ezshop.model.ConcreteProductType;
import it.polito.ezshop.data.Customer;

public interface IDAOEZshop {

    public User searchUser(String username, String password) throws DAOException;

    public ArrayList<ProductType> getAllProducTypet() throws DAOException;

    public void createProductType(ProductType productType) throws DAOException;

    public ConcreteProductType getProductTypeByBarCode(String barCode) throws DAOException;

    public List<ProductType> getProductTypeByDescription(String description) throws DAOException;

    public boolean updateQuantity(Integer productId, int toBeAdded) throws DAOException;

    public void updatePosition(Integer productId, String position) throws DAOException;

    public boolean searchPosition(String position) throws DAOException;

    public Integer insertUser(String username, String password, String role) throws DAOException;

    public boolean removeUser(Integer id) throws DAOException;

    public java.util.List<User> getAllUsers() throws DAOException;

    public User searchUserById(Integer id) throws DAOException;

    public boolean updateRights(Integer id, String role) throws DAOException;

    public Integer insertCustomer(String customerName) throws DAOException;

    public boolean updateCustomer(Integer id, String newCustomerName, String newCustomerCard) throws DAOException;

    public boolean deleteCustomer(Integer id) throws DAOException;

    public Customer getCustomer(Integer id) throws DAOException;

    public ArrayList<Customer> getAllCustomers() throws DAOException;
    public boolean createNewCard(String newCard) throws DAOException;
    public boolean bindCardToCustomer(String card, Integer customerId) throws DAOException;
    public boolean updatePoints(String customerCard, int pointsToBeAdded) throws DAOException;

    public Integer insertSaleTransaction() throws DAOException;

    public boolean insertProductToSale(Integer transactionId, String productCode, int amount) throws DAOException;

    public boolean removeProductToSale(Integer transactionId, String productCode, int amount) throws DAOException;

    public SaleTransaction selectSaleTransaction(Integer transactionId) throws DAOException;

}
