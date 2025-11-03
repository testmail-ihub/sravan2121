#!/usr/bin/perl
use strict;
use warnings;
use DBI;
use Text::CSV;

# Configuration (replace with actual values or load from etl_config.pl)
my $db_host = 'source-db-host';
my $db_port = '3306';
my $db_name = 'source_database';
my $db_user = 'source_user';
my $db_pass = 'source_password';
my $output_csv = 'data/raw_data.csv';

# Connect to the source database
my $dsn = "DBI:mysql:database=$db_name;host=$db_host;port=$db_port";
my $dbh = DBI->connect($dsn, $db_user, $db_pass, { RaiseError => 1, AutoCommit => 1 })
    or die "Could not connect to database: $DBI::errstr";

# Prepare and execute the extraction query (customize as needed)
my $sql = "SELECT * FROM source_table";  # <-- modify the table and columns as required
my $sth = $dbh->prepare($sql);
$sth->execute();

# Open CSV file for writing
my $csv = Text::CSV->new({ binary => 1, eol => "\n" })
    or die "Cannot use CSV: " . Text::CSV->error_diag();
open my $fh, ">", $output_csv or die "Could not open '$output_csv': $!";

# Write header row
my @columns = @{$sth->{NAME_lc}};  # column names in lower case
$csv->print($fh, \@columns);

# Write data rows
while (my $row = $sth->fetchrow_hashref) {
    my @values = @{$row}{@columns};
    $csv->print($fh, \@values);
}

close $fh;
$sth->finish;
$dbh->disconnect;

print "Data extraction completed successfully. CSV saved to $output_csv\n";
