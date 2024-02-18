DROP TABLE IF EXISTS users, items, item_requests, items_requests, bookings, comments CASCADE;

CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    user_email VARCHAR NOT NULL,
    CONSTRAINT uq_user_email UNIQUE (user_email)
);

CREATE TABLE IF NOT EXISTS item_requests (
    item_request_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    description VARCHAR(2000),
    user_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
    created TIMESTAMP
);

CREATE TABLE IF NOT EXISTS items (
    item_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    item_name VARCHAR(255) NOT NULL,
    item_description VARCHAR(2000),
    item_available BOOLEAN DEFAULT 'true',
    user_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
    item_request_id BIGINT REFERENCES item_requests (item_request_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bookings (
    booking_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    item_id BIGINT REFERENCES items (item_id) ON DELETE CASCADE,
    user_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
    status VARCHAR
);

CREATE TABLE IF NOT EXISTS comments (
    comment_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    text VARCHAR(2000),
    item_id BIGINT REFERENCES items (item_id) ON DELETE CASCADE,
    user_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
    created TIMESTAMP
);

CREATE TABLE IF NOT EXISTS items_requests (
    item_id BIGINT REFERENCES items (item_id) ON DELETE CASCADE,
    item_request_id BIGINT REFERENCES item_requests (item_request_id) ON DELETE CASCADE
);