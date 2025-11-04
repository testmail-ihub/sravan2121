#!/usr/bin/perl
use strict;
use warnings;
use DBI;
require './deploy_config.pl';

# Load environment and credentials from deploy_config.pl
my $db_host     = $ENV{'DB_HOST'};
my $db_port     = $ENV{'DB_PORT'};
my $db_name     = $ENV{'DB_NAME'};
my $db_user     = $ENV{'DB_USER'};
my $db_password = $ENV{'DB_PASSWORD'};

# Connect to the database
my $dsn = "DBI:mysql:database=$db_name;host=$db_host;port=$db_port";
my $dbh = DBI->connect($dsn, $db_user, $db_password, { RaiseError => 1, PrintError => 0 });

# Check database version
my $version_query = "SELECT version FROM deployment_metadata ORDER BY applied_at DESC LIMIT 1";
my $sth = $dbh->prepare($version_query);
$sth->execute();
my ($db_version) = $sth->fetchrow_array();
$sth->finish();

# Check service status (example: check if a required table exists)
my $service_status = 'OK';
my $status_query = "SHOW TABLES LIKE 'required_service_table'";
my $sth2 = $dbh->prepare($status_query);
$sth2->execute();
if ($sth2->rows == 0) {
    $service_status = 'MISSING_REQUIRED_TABLE';
}
$sth2->finish();

$dbh->disconnect();

# Output results
print "Database Version: $db_version\n";
print "Service Status: $service_status\n";

exit($service_status eq 'OK' ? 0 : 1);
