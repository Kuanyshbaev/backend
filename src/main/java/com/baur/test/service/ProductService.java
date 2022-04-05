package com.baur.test.service;

import com.baur.test.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Product getProduct(int id) {
        DataSource dataSource = jdbcTemplate.getDataSource();

        if (dataSource != null) {
            try (Connection connection = dataSource.getConnection();
                 Statement st = connection.createStatement();
                 ResultSet res = st.executeQuery("SELECT id, name, price FROM products WHERE id = " + id)) {
                if (res.next()) {
                    Product product = new Product();

                    product.setId(res.getInt("id"));
                    product.setName(res.getString("name"));
                    product.setPrice(res.getBigDecimal("price"));

                    return product;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No datasource");
        }

        return null;
    }

    public List<Product> getProductList() {
        List<Product> list = new ArrayList<>();
        DataSource dataSource = jdbcTemplate.getDataSource();

        if (dataSource != null) {
            try (Connection connection = dataSource.getConnection();
                 Statement st = connection.createStatement();
                 ResultSet res = st.executeQuery("SELECT id, name, price FROM products")) {
                while (res.next()) {
                    Product product = new Product();
                    product.setId(res.getInt("id"));
                    product.setName(res.getString("name"));
                    product.setPrice(res.getBigDecimal("price"));

                    list.add(product);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No datasource");
        }

        return list;
    }

    public void saveOrder(List<Product> products) {
        DataSource dataSource = jdbcTemplate.getDataSource();

        if (dataSource != null) {
            try (Connection con = dataSource.getConnection();
                 PreparedStatement ps = con.prepareStatement("INSERT INTO orders (product_id, amount) VALUES (?, ?)")) {
                for (Product product : products) {
                    ps.setInt(1, product.getId());
                    ps.setInt(2, product.getAmount());
                    ps.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No datasource");
        }
    }

    public void saveProduct(Product product) {
        DataSource dataSource = jdbcTemplate.getDataSource();

        if (dataSource != null) {
            try (Connection con = dataSource.getConnection();
                 PreparedStatement ps = con.prepareStatement("INSERT INTO products (id, name, price) VALUES (?, ?, ?) " +
                         " ON DUPLICATE KEY UPDATE name = VALUES(name), price = VALUES(price)")) {
                ps.setInt(1, product.getId());
                ps.setString(2, product.getName());
                ps.setBigDecimal(3, product.getPrice());
                ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No datasource");
        }
    }

    public void deleteProduct(int id) {
        DataSource dataSource = jdbcTemplate.getDataSource();

        if (dataSource != null) {
            try (Connection con = dataSource.getConnection();
                 PreparedStatement ps = con.prepareStatement("DELETE FROM products WHERE id = ?")) {
                ps.setInt(1, id);
                ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No datasource");
        }
    }
}
