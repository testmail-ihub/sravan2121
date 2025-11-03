import os
import pyodbc
import pandas as pd
from azure.identity import DefaultAzureCredential
from azure.mgmt.datafactory import DataFactoryManagementClient

# Configuration – replace with your actual connection details or use environment variables
DB_CONNECTION_STRING = os.getenv(
    "DB_CONNECTION_STRING",
    "Driver={ODBC Driver 17 for SQL Server};Server=your_server;Database=your_database;Trusted_Connection=yes;"
)
TARGET_TABLE = "dbo.standard_table"
ADF_SUBSCRIPTION_ID = os.getenv("ADF_SUBSCRIPTION_ID")
ADF_RESOURCE_GROUP = os.getenv("ADF_RESOURCE_GROUP")
ADF_FACTORY_NAME = os.getenv("ADF_FACTORY_NAME")
ADF_PIPELINE_NAME = os.getenv("ADF_PIPELINE_NAME")


def load_cleaned_data_to_target():
    """Read cleaned data from the standard table and load it into Azure Data Factory.
    
    Steps:
    1. Connect to the SQL Server database and read the target table into a pandas DataFrame.
    2. (Optional) Perform any final transformations.
    3. Use Azure Data Factory SDK to trigger a pipeline that will ingest the data downstream.
    """
    # 1. Read cleaned data
    conn = pyodbc.connect(DB_CONNECTION_STRING)
    query = f"SELECT * FROM {TARGET_TABLE}"
    df = pd.read_sql(query, conn)
    conn.close()
    print(f"Fetched {len(df)} rows from {TARGET_TABLE}.")

    # 2. Final transformations (if needed) – placeholder
    # Example: df['processed_timestamp'] = pd.Timestamp.utcnow()

    # 3. Trigger ADF pipeline
    credential = DefaultAzureCredential()
    adf_client = DataFactoryManagementClient(credential, ADF_SUBSCRIPTION_ID)
    run_response = adf_client.pipelines.create_run(
        resource_group_name=ADF_RESOURCE_GROUP,
        factory_name=ADF_FACTORY_NAME,
        pipeline_name=ADF_PIPELINE_NAME,
        parameters={"inputData": df.to_json(orient="records")}
    )
    print(f"ADF pipeline triggered. Run ID: {run_response.run_id}")


if __name__ == "__main__":
    load_cleaned_data_to_target()
