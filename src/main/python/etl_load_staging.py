import pandas as pd
import pyodbc
import os

# Configuration – replace with your actual connection details or use environment variables
DB_CONNECTION_STRING = os.getenv(
    "DB_CONNECTION_STRING",
    "Driver={ODBC Driver 17 for SQL Server};Server=your_server;Database=your_database;Trusted_Connection=yes;"
)
STAGING_TABLE = "dbo.staging_table"
CSV_PATH = os.getenv("CSV_PATH", "data/input.csv")


def load_csv_to_staging():
    """Load raw CSV data into the staging table.
    
    Steps:
    1. Read the CSV with pandas (handles headers, types, missing values).
    2. Connect to the SQL Server database via pyodbc.
    3. Use a bulk insert (executemany) for performance.
    """
    # 1. Read CSV
    df = pd.read_csv(CSV_PATH)
    # Ensure column names are stripped of whitespace
    df.columns = [col.strip() for col in df.columns]

    # 2. Open DB connection
    conn = pyodbc.connect(DB_CONNECTION_STRING)
    cursor = conn.cursor()

    # 3. Prepare INSERT statement dynamically based on columns
    columns = ", ".join([f"[{col}]" for col in df.columns])
    placeholders = ", ".join(["?" for _ in df.columns])
    insert_sql = f"INSERT INTO {STAGING_TABLE} ({columns}) VALUES ({placeholders})"

    # 4. Execute bulk insert
    data_tuples = [tuple(row) for row in df.itertuples(index=False, name=None)]
    cursor.executemany(insert_sql, data_tuples)
    conn.commit()
    cursor.close()
    conn.close()
    print(f"Loaded {len(df)} rows into {STAGING_TABLE}.")


if __name__ == "__main__":
    load_csv_to_staging()
