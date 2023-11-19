DROP TABLE IF EXISTS subscribes;
CREATE TABLE subscribes (
    subscription_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    date_borrowed DATE,
    date_returned DATE,
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (book_id) REFERENCES Books(book_id)
);