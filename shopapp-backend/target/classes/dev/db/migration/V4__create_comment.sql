CREATE TABLE comments(
    id INT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME,
    product_id INT,
    user_id INT ,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
