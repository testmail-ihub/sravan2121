/*
   clean_data.sql - Data cleaning script for ETL process
   ---------------------------------------------------
   1. Remove duplicate rows from the staging table.
   2. Validate and transform columns as needed.
   3. Insert cleaned records into the standard target table.
   4. Capture any error records and insert them into a bad_records table.
*/

-- Variables (adjust as needed)
DECLARE @StagingTable NVARCHAR(128) = 'dbo.staging_table';
DECLARE @TargetTable  NVARCHAR(128) = 'dbo.standard_table';
DECLARE @BadTable     NVARCHAR(128) = 'dbo.bad_records';

-- 1. Remove duplicates from staging and capture duplicates
WITH CTE_Dedup AS (
    SELECT *, ROW_NUMBER() OVER (PARTITION BY /* list columns that define uniqueness */ * ORDER BY (SELECT 0)) AS rn
    FROM @StagingTable
)
INSERT INTO @BadTable (/* columns */)
SELECT /* columns */
FROM CTE_Dedup
WHERE rn > 1; -- duplicates go to bad table

DELETE FROM @StagingTable
WHERE EXISTS (
    SELECT 1 FROM CTE_Dedup d WHERE d.rn > 1 AND d.PrimaryKey = @StagingTable.PrimaryKey
);

-- 2. Validate and transform data (example: trim strings, handle nulls)
UPDATE @StagingTable
SET ColumnA = LTRIM(RTRIM(ColumnA)),
    ColumnB = CASE WHEN ColumnB = '' THEN NULL ELSE ColumnB END;

-- 3. Insert cleaned data into target table
INSERT INTO @TargetTable (ColumnA, ColumnB, ColumnC, ...)
SELECT ColumnA, ColumnB, ColumnC, ...
FROM @StagingTable;

-- 4. Optionally, move any rows that still cause errors to bad table using TRY...CATCH (SQL Server example)
BEGIN TRY
    -- The INSERT above is within a TRY block; if it fails, control goes to CATCH
END TRY
BEGIN CATCH
    INSERT INTO @BadTable (ErrorMessage, FailedRow)
    SELECT ERROR_MESSAGE(), * FROM @StagingTable;
END CATCH;

-- Clean up staging table after successful load
TRUNCATE TABLE @StagingTable;
