CREATE TABLE IF NOT EXISTS monitor_data (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    timestamp INTEGER NOT NULL,
    node_identifier TEXT,
    cpu REAL,
    memory_total INTEGER,
    memory_available INTEGER,
    memory_percent REAL,
    network_bytes_sent INTEGER,
    network_bytes_recv INTEGER
);

CREATE TABLE IF NOT EXISTS process_info (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    monitor_data_id INTEGER,
    node_identifier TEXT,
    pid INTEGER,
    name TEXT,
    cpu_percent REAL,
    memory_percent REAL,
    FOREIGN KEY(monitor_data_id) REFERENCES monitor_data(id)
);
