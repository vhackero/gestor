# Repository Guidelines

## Project Structure & Module Organization
- `codigoFuente/basegestor/`: core backend services and data access (Spring/JPA). Java sources live under `src/main/java`, tests under `src/test/java`.
- `codigoFuente/gestorWeb/`: web application packaged as a WAR. UI assets and JSPs are in `WebContent/`.
- `codigoFuente/gestorscheduler/`: scheduled jobs module with its own `pom.xml` and `src/` tree.
- `codigoFuente/bd/` and `codigoFuente/*.sql`: database snapshots and seed scripts.
- `manuales de instalacion/`: installation manuals and operational notes.
- `devops/`: deployment artifacts (treat as sensitive; avoid editing without explicit need).

## Build, Test, and Development Commands
- Maven is the build tool. Use the bundled version in `codigoFuente/apache-maven-3.5.4-normal/` if needed.
- Build a module: `mvn -f codigoFuente/basegestor/pom.xml clean install` (repeat for `gestorWeb` or `gestorscheduler`).
- Run tests: `mvn -f codigoFuente/basegestor/pom.xml test` (uses Surefire with TestNG/JUnit).
- Package the web app: `mvn -f codigoFuente/gestorWeb/pom.xml package` (WAR output in `codigoFuente/gestorWeb/target/`).
- Local services: see `codigoFuente/gestorWeb/docker-compose.yml` and `codigoFuente/gestorWeb/mysql-init/` for database setup.

## Coding Style & Naming Conventions
- Follow existing formatting in each module; Java code uses standard 4-space indentation and brace-on-same-line style.
- Package names follow `mx.gob.sedesol...` and classes use `PascalCase`.
- Test classes end with `*Test` (for example `CrudEncuestaTest.java`).
- Keep public API changes consistent across `basegestor` and `gestorWeb` DTO/service layers.

## Testing Guidelines
- Frameworks: TestNG and JUnit are configured in module `pom.xml` files via Surefire.
- Place unit and integration tests in `src/test/java` with matching package paths.
- Run module tests before PRs that change business logic or data access.

## Commit & Pull Request Guidelines
- Commit messages are short, descriptive, and often in Spanish; keep a concise summary (for example, "Ajusta filtros de bajas").
- When applicable, include ticket or release identifiers used in branch names (for example, `R016_10102025`).
- PRs should include: a clear description of the change, testing notes (`mvn test` or manual steps), and screenshots for UI updates.
- If database changes are needed, attach or reference the SQL scripts in `codigoFuente/bd/`.

## Security & Configuration Tips
- Do not commit credentials; keep local secrets out of `devops/` and configuration files.
- Prefer environment-specific configs in deployment tooling over hard-coded values in source.
