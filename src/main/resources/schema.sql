DROP TABLE IF EXISTS users, items, requests, bookings, comments CASCADE;

CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    user_email VARCHAR NOT NULL
);

ALTER TABLE users ADD CONSTRAINT IF NOT EXISTS uq_user_email UNIQUE (user_email);

CREATE TABLE IF NOT EXISTS requests (
    request_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    description VARCHAR(2000),
    requestor_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
    created TIMESTAMP
);

CREATE TABLE IF NOT EXISTS items (
    item_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    item_name VARCHAR(255) NOT NULL,
    item_description VARCHAR(2000),
    item_available BOOLEAN DEFAULT 'true',
    owner_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
    request_id BIGINT REFERENCES requests (request_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bookings (
    booking_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    item_id BIGINT REFERENCES items (item_id) ON DELETE CASCADE,
    booker_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
    status VARCHAR
);

CREATE TABLE IF NOT EXISTS comments (
    comment_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    text VARCHAR(2000),
    item_id BIGINT REFERENCES items (item_id) ON DELETE CASCADE,
    author_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
    created TIMESTAMP
);