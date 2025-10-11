# ljsa_java8

- By ManassehRandriamitsiry

how to set up :
- default Main : 
-     Main
-     JAVA_VERSION = 8;
-     FX_VERSION = 8(Embabed with java8);
- Database: SQLite (no separate installation required)
- The application will automatically create a SQLite database file (ljsa.db) in the project directory
- To initialize the database schema, the application will use:
-     ljsa_sqlite.sql
- GitHub Actions: The project now uses a CI/CD workflow that builds JAR and EXE files
- !!! don't forget to change your JAVA_VERSION to 8
- enjoy