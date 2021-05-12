CREATE TABLE IF NOT EXISTS monitoring (
  id BIGSERIAL,
  msg text NOT NULL,
  preview_start timestamp,
  actual_start timestamp,
  PRIMARY KEY (id)
);


INSERT into monitoring (msg, preview_start, actual_start) VALUES ('a message', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP+interval '2 hours');