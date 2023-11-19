package repository;

import JDBC.ConnectionDB;
import model.Subscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscribeCrudOperations implements CrudOperations<Subscription>{
    private final ConnectionDB connectionDB;

    public SubscribeCrudOperations(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }

    @Override
    public List<Subscription> findAll() {
        List<Subscription> subscriptions = new ArrayList<>();
        try(Connection connection = connectionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM subscribes");
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()){
                Subscription subscription = new Subscription(
                        resultSet.getInt("subscription_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("book_id"),
                        resultSet.getDate("date_borrowed"),
                        resultSet.getDate("date_returned")
                );
                subscriptions.add(subscription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }

    @Override
    public List<Subscription> saveAll(List<Subscription> toSave) {
        String sql = "INSERT INTO subscribes (user_id, book_id, date_borrowed, date_returned) VALUES (?, ?, ?, ?)";
        try (Connection connection = connectionDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            for (Subscription subscription : toSave) {
                statement.setInt(1, subscription.getUserId());
                statement.setInt(2, subscription.getBookId());
                statement.setDate(3, (Date) subscription.getDateBorrowed());
                statement.setDate(4, (Date) subscription.getDateReturned());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public Subscription save(Subscription toSave) {
        try (Connection connection = connectionDB.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO subscribes (user_id, book_id, date_borrowed, date_returned) VALUES (?, ?, ?, ?)")
        ) {
            statement.setInt(1, toSave.getUserId());
            statement.setInt(2, toSave.getBookId());
            statement.setDate(3, (Date) toSave.getDateBorrowed());
            statement.setDate(4, (Date) toSave.getDateReturned());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Backup failed, no rows affected");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    toSave.setSubscriptionId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Failed to retrieve generated ID for book");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public Subscription delete(Subscription toDelete) {
        try (Connection connection = connectionDB.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM subscribes WHERE subscription_id = ?")
        ) {
            statement.setInt(1, toDelete.getSubscriptionId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Delete failed, no records deleted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toDelete;
    }
}
