/* load_data.sql */
-- Load the cleaned CSV into the target database table using LOAD DATA INFILE
-- Adjust the file path, table name, and column list as needed

LOAD DATA INFILE 'data/clean_data.csv'
INTO TABLE target_table
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(column1, column2, column3, column4);