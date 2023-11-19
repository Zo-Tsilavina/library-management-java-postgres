DROP TABLE IF EXISTS clients;
CREATE TABLE clients(
    user_id INT PRIMARY KEY REFERENCES Users(id),
    client_status VARCHAR(50) NOT NULL
);