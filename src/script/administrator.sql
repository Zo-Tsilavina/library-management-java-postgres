DROP TABLE IF EXISTS administrators;
CREATE TABLE administrators (
    user_id INT PRIMARY KEY REFERENCES Users(id),
    admin_role VARCHAR(50) NOT NULL
);