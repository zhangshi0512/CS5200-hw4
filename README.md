# JDBC Project: University Application System

## Overview
This JDBC project is designed to manage the university application process, facilitating operations related to applicants, degrees, letter writers, ratings, recommendation letters, reviewers, and users.

## Project Structure
The project is organized into model classes representing the data entities and DAO classes for interacting with the database.

### Model Classes
Located in `src/main/java/com/university/application/models`, these classes represent the entities in the database:
- `Applicant.java`: Represents an applicant.
- `Degree.java`: Represents a degree program.
- `LetterWriter.java`: Represents an author of a recommendation letter.
- `Rating.java`: Represents a rating given to an applicant.
- `RecLetter.java`: Represents a recommendation letter.
- `Reviewer.java`: Represents a reviewer.
- `User.java`: Represents a user of the system.

### DAO Classes
Located in `src/main/java/com/university/application/dao`, these classes handle CRUD operations:
- `ApplicantDao.java`: Manages applicants.
- `DegreeDao.java`: Manages degree programs.
- `LetterWriterDao.java`: Manages letter writers.
- `RatingDao.java`: Manages ratings.
- `RecLetterDao.java`: Manages recommendation letters.
- `ReviewerDao.java`: Manages reviewers.
- `UserDao.java`: Manages users.
- `ConnectionManager.java`: Handles database connections.

### Utility Classes
- `Inserter.java` (in `src/main/java/com/university/application/test`): Used for inserting initial data into the database for testing.

## Getting Started
1. **Configure Database Connection**: Edit `ConnectionManager.java` to set up your database connection details.
2. **Initialize Database**: Run `Inserter.java` to populate your database with initial data.
3. **Utilize DAO Classes**: Use DAO classes to perform database operations for various entities.

## Usage
- **Applicant Operations**: Use `ApplicantDao` to add, retrieve, update, or delete applicant records.
- **Degree Management**: Use `DegreeDao` to manage degree programs.
- (Include similar instructions for other DAO classes)

## Contributing
Contributions to the project are welcome. Please submit pull requests for any enhancements or bug fixes.

## License
This project is licensed under the MIT License. See the LICENSE file for more details.

---