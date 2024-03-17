Feature: Library operations

Scenario: User can find added books to the Library by title

    Given the following books in the Library
    | title | author | date |
    | The Hobbit | J.R.R. Tolkien | 21 8 1937 |
    | The Lord of the Rings | J.R.R. Tolkien | 29 7 1954 |
    | The Silmarillion | J.R.R. Tolkien |  15 9 1977 |
    | The Children of Hurin | J.R.R. Tolkien |  17 4 2007 |
    When the user searches for the book "The Hobbit" by title
    Then the user should see the book "The Hobbit" in the search results

Scenario: User can find added books to the Library by author

    Given the following books in the Library
    | title | author | date |
    | The Hobbit | J.R.R. Tolkien | 21 8 1937 |
    | The Lord of the Rings | J.R.R. Tolkien | 29 7 1954 |
    | The Silmarillion | J.R.R. Tolkien |  15 9 1977 |
    | The Children of Hurin | Zé manel |  17 4 2007 |
    When the user searches books of the author "J.R.R. Tolkien"
    Then the user should see 3 books in the search results

Scenario: User can find added books to the Library by date
    Given the following books in the Library
    | title | author | date |
    | The Hobbit | J.R.R. Tolkien | 21 8 1937 |
    | The Lord of the Rings | J.R.R. Tolkien | 29 7 1954 |
    | The Silmarillion | J.R.R. Tolkien |  15 9 1977 |
    | The Children of Hurin | J.R.R. Tolkien |  17 4 2007 |
    When the user searches books published in 2007
    Then the user should see 1 books in the search results
    And the user should see the book "The Children of Hurin" in the search results


Scenario: User can remove books from library by title
    Given the following books in the Library
    | title | author | date |
    | The Hobbit | J.R.R. Tolkien | 21 8 1937 |
    | The Lord of the Rings | J.R.R. Tolkien | 29 7 1954 |
    | The Silmarillion | J.R.R. Tolkien |  15 9 1977 |
    | The Children of Hurin | J.R.R. Tolkien |  17 4 2007 |
    When the user removes the book "The Hobbit" from the library
    And the user searches for the book "The Hobbit" by title
    Then the user should not see the book "The Hobbit" in the search results
    And the user should see 0 books in the search results


