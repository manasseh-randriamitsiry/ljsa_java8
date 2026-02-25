# ljsa_java8

- By ManassehRandriamitsiry

## Setup
- Java version: 17
- JavaFX version: 21.0.2 (managed by Maven)
- Database: SQLite (no separate installation required)
- The application automatically creates `ljsa.db` in the project directory
- Schema is initialized from `src/main/resources/ljsa_sqlite.sql`

## Run
- `./mvnw clean package`
- `./mvnw javafx:run`

## Notes
- GitHub Actions builds JAR and EXE artifacts.

## Generate Windows EXE
- Push to `master` (or run workflow manually from Actions tab).
- Workflow: `JavaFX CI/CD with Java 17 and EXE Release`.
- The `.exe` is produced in the `ljsa-exe` artifact.
- For tags like `v1.0.0`, the release job publishes `ljsa.exe` and JAR assets.
