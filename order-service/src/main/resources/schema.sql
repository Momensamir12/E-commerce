CREATE TABLE orders (
    oid serial PRIMARY KEY,
    buyer_id varchar NOT NULL,   -- ID of the user who placed the order
    total_amount DECIMAL(10, 2) NOT NULL,
    order_status VARCHAR(20) DEFAULT 'PENDING',
	address varchar,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE order_Items (
    id SERIAL PRIMARY KEY, -- Auto-incremented primary key
    order_id BIGINT NOT NULL,   -- Foreign key to Orders table
    product_id BIGINT NOT NULL, -- ID of the product being ordered
    quantity INT NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);
