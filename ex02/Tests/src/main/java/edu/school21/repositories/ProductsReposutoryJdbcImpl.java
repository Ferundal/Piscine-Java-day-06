package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductsReposutoryJdbcImpl implements ProductsRepository {
    private final DataSource dataSource;

    public ProductsReposutoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        final String QUERY_TEMPLATE = "SELECT * FROM products";
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(QUERY_TEMPLATE);
        List<Product> result = new LinkedList<Product>();
        while (resultSet.next()) {
            result.add(new Product(resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price")));
        }
        connection.close();
        return (result);
    }

    @Override
    public void update(Product product) throws SQLException {
        final String QUERY_TEMPLATE = "UPDATE products SET " +
                "name=?, " +
                "price=? " +
                "WHERE id=?";
        PreparedStatement query;
        Connection connection = dataSource.getConnection();
        query = connection.prepareStatement(QUERY_TEMPLATE);

        query.setString(1, product.getName());
        query.setInt(2, product.getPrice());
        query.setLong(3, product.getId());
        try {
            query.executeQuery();
        } catch (SQLException sqlException) {

        }
        connection.close();
    }

    @Override
    public void save(Product product) throws SQLException {
        final String QUERY_TEMPLATE = "INSERT INTO products (name, price) VALUES (?, ?)";

        Connection connection = dataSource.getConnection();
        PreparedStatement query = connection.prepareStatement(QUERY_TEMPLATE);

        query.setString(1, product.getName());
        query.setInt(2, product.getPrice());
        ResultSet resultSet;
        try {
            query.executeQuery();
            resultSet = connection.createStatement().executeQuery("CALL IDENTITY()");
            resultSet.next();
            if (resultSet.next()) {
                product.setId(resultSet.getLong("id"));
            }
        } catch (SQLException sqlException) {

        }
        connection.close();
    }

    @Override
    public Optional<Product> findById(Long id) throws SQLException {
        final String QUERY_TEMPLATE = "SELECT * FROM products WHERE id = ";
        Connection connection = null;
        Optional<Product> optionalProduct;

        connection = dataSource.getConnection();
        Statement statement = connection.createStatement();

        String query = QUERY_TEMPLATE + id;
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();


        optionalProduct = Optional.of(new Product(id,
                resultSet.getString("name"),
                resultSet.getInt("price")));
        return optionalProduct;
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM products WHERE id=?";
        if (findById(id).isPresent()) {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.execute();
            connection.close();
        }
    }
}
