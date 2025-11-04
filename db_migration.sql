-- db_migration.sql
-- Alters tables and inserts version metadata for integrated deployment

-- Example: Alter table to add a new column
ALTER TABLE app_metadata
  ADD COLUMN deployment_version VARCHAR(32);

-- Insert version metadata
INSERT INTO app_metadata (deployment_version, deployed_at)
VALUES ('v1.0.0', CURRENT_TIMESTAMP);

-- Additional table changes can be added below as needed
-- Ensure all changes are compatible with deploy_config.pl and orchestration scripts
