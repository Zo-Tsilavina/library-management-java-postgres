package repository;

import JDBC.ConnectionDB;
import model.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorCrudOperations implements CrudOperations<Author> {
    private final ConnectionDB connectionDB;

    public AuthorCrudOperations(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        try(Connection connection = connectionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM authors");
            ResultSet resultSet = statement.executeQuery()
        ){
            while(resultSet.next()){
                Author author = new Author(
                        resultSet.getInt("author_id"),
                        resultSet.getString("author_name")
                );
                authors.add(author);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return authors;
    }

    @Override
    public List<Author> saveAll(List<Author> toSave) {
        try (Connection connection = connectionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO auhors (author_name) VALUES (?)")
        ){
            for (Author author : toSave){
                statement.setString(1,author.getAuthorName());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public Author save(Author toSave) {
        try (Connection connection = connectionDB.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO authors (author_name) VALUES (?)")
        ) {
            statement.setString(1, toSave.getAuthorName());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Backup failed, no rows affected");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    toSave.setAuthorId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Failed to retrieve generated ID for author");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toSave;
    }


    @Override
    public Author delete(Author toDelete) {
        try (Connection connection = connectionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM authors WHERE author_id = ?")
        ){
            statement.setInt(1, toDelete.getAuthorId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0){
                throw new SQLException("Delete failed, no records deleted");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return toDelete;
    }
}
