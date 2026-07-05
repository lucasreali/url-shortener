CREATE TABLE url_accesses (
    id UUID PRIMARY KEY,
    short_code VARCHAR(10) NOT NULL,
    accessed_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_url_accesses_short_code ON url_accesses (short_code);
