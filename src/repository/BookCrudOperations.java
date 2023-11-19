package repository;

import JDBC.ConnectionDB;
import model.Books;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookCrudOperations implements CrudOperations<Books>{
    private final ConnectionDB connectionDB;

    public BookCrudOperations(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }

    @Override
    public List<Books> findAll() {
        List<Books> book = new ArrayList<>();
        try(Connection connection = connectionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM books");
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()){
                Books books = new Books(
                        resultSet.getInt("book_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getInt("author_id"),
                        resultSet.getBoolean("borrowed")
                );
                book.add(books);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public List<Books> saveAll(List<Books> toSave) {
        try(Connection connection = connectionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO books (title, description, author_id, borrowed) VALUES (?, ?, ?, ?)")
        ) {
            for (Books books : toSave){
                statement.setString(1, books.getTitle());
                statement.setString(2, books.getDescription());
                statement.setInt(3, books.getAuthorId());
                statement.setBoolean(4, books.isBorrowed());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public Books save(Books toSave) {
        try (Connection connection = connectionDB.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO books (title, description, author_id, borrowed) VALUES (?, ?, ?, ?)")
        ) {
            statement.setString(1, toSave.getTitle());
            statement.setString(2, toSave.getDescription());
            statement.setInt(3, toSave.getAuthorId());
            statement.setBoolean(4, toSave.isBorrowed());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Backup failed, no rows affected");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    toSave.setBookId(generatedKeys.getInt(1));
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
    public Books delete(Books toDelete) {
        try (Connection connection = connectionDB.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM books WHERE book_id = ?")
        ) {
            statement.setInt(1, toDelete.getBookId());

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
