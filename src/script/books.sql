DROP TABLE IF EXISTS books;
CREATE TABLE books(
    book_id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description text,
    author_id INT NOT NULL,
    borrowed BOOLEAN DEFAULT false
);