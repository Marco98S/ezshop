package it.polito.ezshop.persistence;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.data.User;
import it.polito.ezshop.data.*;
import it.polito.ezshop.model.*;

public class DAOEZShop implements IDAOEZshop {

    private DataSource dataSource = new DataSource();

    @Override
    public User searchUser(String username, String password) throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = "SELECT * FROM user where username= '" + username + "' AND password='" + password +"'";

            resultSet = statement.executeQuery(query);
            User user = null;
            while (resultSet.next()) {
                user = new ConcreteUser();
                String name = resultSet.getString("username");
                String pass = resultSet.getString("password");
                String role = resultSet.getString("role");
                Integer id = resultSet.getInt("id");
                user.setUsername(name);
                user.setPassword(pass);
                user.setId(id);
                user.setRole(role);
            }

            return user;
        } catch (SQLException ex) {
            throw new DAOException("Impossibile to execute query: " + ex.getMessage());
        } finally {
            dataSource.close(connection);
        }
    }

    @Override
    public ArrayList<ProductType> getAllProducTypet() throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = "SELECT * FROM product_type";
            resultSet = statement.executeQuery(query);
            ArrayList<ProductType> productTypeList = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer quantity = resultSet.getInt("quantity");
                String location = resultSet.getString("location");
                String notes = resultSet.getString("note");
                String description = resultSet.getString("description");
                String barCode = resultSet.getString("bar_code");
                Double pricePerUnit = resultSet.getDouble("price_per_unit");
                Double discountRate = resultSet.getDouble("discount_rate");
                ProductType product = new ConcreteProductType(id, description, barCode, notes, quantity, pricePerUnit,
                        discountRate, location);
                productTypeList.add(product);
            }
            return productTypeList;
        } catch (SQLException ex) {
            throw new DAOException("Impossibile to execute query: " + ex.getMessage());
        } finally {
            dataSource.close(connection);
        }
    }

    @Override
    public void insertUser(String username, String password, String role, Integer id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            String query = "INSERT INTO user(username, password, role, id) VALUES(?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Impossible to execute query: " + ex.getMessage());
        } finally {
            dataSource.close(connection);
        }
    }

    @Override
    public Integer getLastUserId() throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = "SELECT MAX(id) FROM user";
            resultSet = statement.executeQuery(query);
            return (resultSet.next() ? resultSet.getInt(1) : 0);
        } catch (SQLException ex) {
            throw new DAOException("Impossible to execute query: " + ex.getMessage());
        } finally {
            dataSource.close(connection);
        }
    }

    @Override
    public boolean removeUser(Integer id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            String query = "DELETE FROM user WHERE id='" + id + "'";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            throw new DAOException("Impossible to execute query: " + ex.getMessage());
        } finally {
            dataSource.close(connection);
        }
    }

    @Override
    public List<User> getAllUsers() throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = "SELECT * FROM user";
            resultSet = statement.executeQuery(query);
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User u = new ConcreteUser(resultSet.getString("username"), resultSet.getInt("id"),
                        resultSet.getString("password"), resultSet.getString("role"));
                users.add(u);
            }
            return users;
        } catch (SQLException ex) {
            throw new DAOException("Impossible to execute query: " + ex.getMessage());
        } finally {
            dataSource.close(connection);
        }
    }

    @Override
    public void createProductType(ProductType productType) throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder();
            query.append("insert into product_type (description, note, bar_code, price_per_unit) values (");
            query.append("'" + productType.getProductDescription() + "','");
            query.append(productType.getNote() + "','");
            query.append(productType.getBarCode() + "','");
            query.append(productType.getPricePerUnit() + "');");
            statement.executeUpdate(query.toString());
            String q = "select id from product_type where bar_code = '" + productType.getBarCode() + "'";
            resultSet = statement.executeQuery(q);
            int id = resultSet.getInt("id");
            productType.setId(id);
        } catch (SQLException ex) {
            throw new DAOException("Impossibile to execute query: " + ex.getMessage());
        } finally {
            dataSource.close(connection);
        }
    }
    

    @Override
    public ConcreteProductType getProductTypeByBarCode(String barCode) throws DAOException {
        Connection connection = null;
        Statement statment = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statment = connection.createStatement();
            String query = "select * from product_type where bar_code = '" + barCode + "';";
            resultSet = statment.executeQuery(query);
            ConcreteProductType productType = null;
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer quantity = resultSet.getInt("quantity");
                String location = resultSet.getString("location");
                String notes = resultSet.getString("note");
                String description = resultSet.getString("description");
                String bar_code = resultSet.getString("bar_code");
                Double pricePerUnit = resultSet.getDouble("price_per_unit");
                Double discountRate = resultSet.getDouble("discount_rate");
                productType = new ConcreteProductType(id, description, bar_code, notes, quantity, pricePerUnit,
                        discountRate, location);
            }
            return productType;
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e);
        } finally {
            dataSource.close(connection);
        }
        return null;
    }



    @Override
    public User searchUserById(Integer id) throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = "SELECT * FROM user WHERE id= '" + id + "'";
            resultSet = statement.executeQuery(query);
            User u;
            if (!resultSet.next())
                u = null;
            u = new ConcreteUser(resultSet.getString("username"), resultSet.getInt("id"),
                    resultSet.getString("password"), resultSet.getString("role"));
            return u;
        } catch (SQLException ex) {
            throw new DAOException("Impossibile to execute query: " + ex.getMessage());
        } finally {
            dataSource.close(connection);
        }
    }

    @Override
    public boolean updateRights(Integer id, String role) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            String query = "UPDATE user SET role= ? WHERE id= ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, role);
            preparedStatement.setInt(2, id);
            int ps = preparedStatement.executeUpdate();
            if(ps <= 0)
                return false;
        } catch (SQLException ex) {
            throw new DAOException("Impossible to execute query: " + ex.getMessage());
        } finally {
            dataSource.close(connection);
        }
        return true;
    }

    public Integer insertCustomer(String customerName) throws DAOException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();

            // Insert
            PreparedStatement pstm;

            pstm = connection.prepareStatement("insert into customer(name) values (?)");
            pstm.setString(1, customerName);
            pstm.execute();

            // Recover the id
            String query = "SELECT id FROM customer WHERE name= '" + customerName + "';";

            resultSet = statement.executeQuery(query);
            System.out.println("id: " + resultSet.getString("id"));
            Integer id = resultSet.getInt("id");

            return id;

        } catch (SQLException ex) {
            throw new DAOException("Impossibile to execute query: " + ex.getMessage());
        } finally {
            dataSource.close(connection);
        }
    }

    @Override
    public boolean updateCustomer(Integer id, String newCustomerName, String newCustomerCard) throws DAOException{

        Connection connection = null;
        Statement statement = null;
        int update;

        try{
            connection= dataSource.getConnection();
            statement = connection.createStatement();

            //update query creation
            String query= "UPDATE customer SET name= '" + newCustomerName + "'";

            if(newCustomerCard.isEmpty()){ //remove previous card and its points
                query= query + ", card= '" + null + "', points= '0'";
            
            }else if(newCustomerCard!= null){ //numeric value: create new card with 0 points
                query= query + ", card= '" + newCustomerCard + "', points= '" + 0 + "'";
            }

            query= query + "WHERE customer.id= '"+ id + "';";

            //update execution
            update= statement.executeUpdate(query);
            System.out.println("Update query executed succesfully?--> update= " + update);

        }catch(SQLException ex){
            throw new DAOException("Impossibile to execute query: " + ex.getMessage());
        } finally {
        	dataSource.close(connection);
        }        

       if(update!=1){ //something goes wrong
            return false;
        }

        //exactly one row was affected by our update
        return true;

    }
    
    @Override
    public boolean deleteCustomer(Integer id) throws DAOException{

        Connection connection = null;
        PreparedStatement prstm = null;
        boolean result= false;

        try{
            connection= dataSource.getConnection();
            prstm = connection.prepareStatement("DELETE FROM customer WHERE id=?;");
            prstm.setInt(1, id);

            int del = prstm.executeUpdate();

            if(del!=1){ //Something goes wrong
                result= false;
            }else{
                result= true;
            }

        }catch(SQLException ex){
            throw new DAOException("Impossibile to execute query: " + ex.getMessage());
        } finally {
        	dataSource.close(connection);
        } 

        return result;

    }

    @Override
    public Customer getCustomer(Integer id) throws DAOException{

        Connection connection = null;
        Statement statement = null;
        Customer c = null;

        try{
            connection= dataSource.getConnection();
            statement= connection.createStatement();

            String query= "SELECT * FROM customer WHERE id = '"+ id + "';";
            ResultSet resultSet= statement.executeQuery(query);

            if(resultSet.next()){
                c = new ConcreteCustomer(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("card"), resultSet.getInt("points"));
            }


        }catch(SQLException ex){
            throw new DAOException("Impossibile to execute query: " + ex.getMessage());
        } finally {
        	dataSource.close(connection);
        } 

        return c;
    }



    @Override
    public ArrayList<Customer> getAllCustomers() throws DAOException{

        Connection connection = null;
        Statement statement = null;
        ArrayList<Customer> customers= new ArrayList<>();

        try{
            connection= dataSource.getConnection();
            statement= connection.createStatement();

            String query= "SELECT * FROM customer";
            ResultSet resultSet= statement.executeQuery(query);


            while(resultSet.next()){
                Customer c= new ConcreteCustomer(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("card"), resultSet.getInt("points"));
                customers.add(c);
            }
            
        }catch(SQLException ex){
            throw new DAOException("Impossibile to execute query: " + ex.getMessage());
        }finally{
        	dataSource.close(connection);
        }
		return customers;
	}

    //TODO: capire se questo metodo serve davvero nel db: dovrei creare una tabella Card
    @Override
    public boolean createNewCard(String newCard) throws DAOException{

        /*
        Connection connection = null;
        Statement statement = null;

        try{}
        */
        return true;
    }

    @Override
    public boolean bindCardToCustomer(String card, Integer customerId) throws DAOException{

        Connection connection = null;
        Statement statement = null;

        try{
            connection= dataSource.getConnection();
            statement= connection.createStatement();

            String query= "SELECT * FROM customer WHERE customerId= '"+ customerId + "' OR card= '"+ card + "';";
            ResultSet rs= statement.executeQuery(query);

            boolean customerExistance= false;
            while(rs.next()){   //check all the rows to find a non-existing user or an already assigned card

                if(rs.getInt("id")==customerId){
                    customerExistance= true;
                }
                if(rs.getString("card")==card){
                    System.out.println("This card is already attached to a customer");
                    return false;
                }
            }

            if(customerExistance!=true){
                System.out.println("The given customer doesn't exist");
                return false;
            }

            query= "UPDATE customer SET card= '" + card + "' WHERE id= '"+ customerId + "';";
            statement.executeUpdate(query);

        }catch(SQLException ex){
            throw new DAOException("Impossibile to execute query: " + ex.getMessage());
        }

        return true;
    }


	
	@Override
	public List<ProductType> getProductTypeByDescription(String description) throws DAOException {
	   	Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = "select * from product_type where description = '" + description + "';";
            resultSet = statement.executeQuery(query);
            List<ProductType> productTypeList = new ArrayList<>();
            while(resultSet.next()) {
            	Integer id = resultSet.getInt("id");
            	Integer quantity = resultSet.getInt("quantity");
            	String location = resultSet.getString("location");
            	String notes = resultSet.getString("note");
            	String desc = resultSet.getString("description");
            	String bar_code = resultSet.getString("bar_code");
            	Double pricePerUnit = resultSet.getDouble("price_per_unit");
            	Double discountRate = resultSet.getDouble("discount_rate");
            	ProductType productType = new ConcreteProductType(id, desc, bar_code, notes, quantity, pricePerUnit, discountRate, location);
            	productTypeList.add(productType);
            }
            return productTypeList;
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e);
		} finally {
			dataSource.close(connection);
		}
		return null;
	}



	@Override
	public boolean updateQuantity(Integer productId, int toBeAdded) throws DAOException {
		// TODO Auto-generated method stub
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = "select quantity, location from product_type where id = " + productId;
            resultSet = statement.executeQuery(query);
            int quantity = 0;
            String location = null;
            if(resultSet.next()) {
            	quantity = resultSet.getInt("quantity");
            	location = resultSet.getString("location");
            }
            int value = quantity + toBeAdded;
            if(value < 0 || location == null ||  location.isEmpty()) {
            	return false;
            }
            query = "update product_type  set quantity = " + value + " where id = " +  productId;
            statement.executeUpdate(query.toString());
            return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		} finally {
			dataSource.close(connection);
		}
	}

   


    public boolean updatePoints(String customerCard, int pointsToBeAdded) throws DAOException{

        Connection connection = null;
        Statement statement = null;
        ResultSet rs;

        try{
            connection= dataSource.getConnection();
            statement= connection.createStatement();

            String query= "SELECT * FROM customer WHERE card= '" + customerCard + "';";
            rs= statement.executeQuery(query);

            if(!rs.next()){ //if doesn't exist a customer with this card
                System.out.println("A customer with the inserted card doesn't exist");
                return false;
            }

            if(pointsToBeAdded<0){ //Check if the previous points are enough
                if(rs.getInt("points")< (0-pointsToBeAdded)){
                    System.out.println("There are not enough points on the card to be subtracted");
                    return false;
                }
            }

            //UPDATE POINTS ON CARD
            //String updateQuery= "UPDATE customer SET points= '"+ (rs.getInt("points")+ pointsToBeAdded)+ "' WHERE id = '" + rs.getInt("id")+ "';";
            PreparedStatement prstm= connection.prepareStatement( "UPDATE customer SET points= ? WHERE id = ?;");
            prstm.setInt(1, (rs.getInt("points")+ pointsToBeAdded));
            prstm.setInt(2, rs.getInt("id"));

            int result = prstm.executeUpdate();
            if(result!=1){ //Something goes wrong
                return false;
            }


        }catch(SQLException ex){
            throw new DAOException("Impossibile to execute query: " + ex.getMessage());
        }finally{
        	dataSource.close(connection);
        }

        return true;
    }
           

	
	
	@Override
	public void updatePosition(Integer productId, String position) throws DAOException {
		// TODO Auto-generated method stub
		Connection connection = null;
		Statement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			String query = "update product_type set location = " + position + " where id = " + productId;
			statement.executeUpdate(query);
		}catch (Exception e) {
			
		} finally {
			dataSource.close(connection);
		}
	}


	@Override
	public boolean searchPosition(String position) throws DAOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			String query = "select * from product_type where location = '" + position + "'";
			resultSet = statement.executeQuery(query);
			return (resultSet.next() ? true : false);
		}catch (Exception e) {
			System.out.println(e);
		} finally {
			dataSource.close(connection);
		}
		return false;
	}
}
