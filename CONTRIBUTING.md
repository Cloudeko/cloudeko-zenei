Thank you for your interest in contributing to the Cloudeko Zenei Auth Service! We appreciate your willingness to help improve and enhance this project.

**Setting Up Your Development Environment**

1. **Prerequisites**

    * **Java 21:** Ensure you have Java 21 installed. You can download it from [https://openjdk.org/](https://openjdk.org/).

    * **Maven:**  Install Maven. Check the installation instructions at [https://maven.apache.org/](https://maven.apache.org/).

2. **Generate Development Keys**

    * **Execute the Script:** Run the script to create `dev-private-key.pem` and `dev-public-key.pem`.

       ```bash
       ./generate-dev-keys.sh
       ```

3. **Build and Run**

    * **Build:**

      ```bash
      mvn clean install
      ```

    * **Run:**

      ```bash
      mvn quarkus:dev
      ```

<hr />

**Making Contributions**

1. **Fork the Repository**
2. **Create a Branch**
3. **Make Your Changes**
4. **Commit Your Changes**
5. **Push to Your Branch**
6. **Open a Pull Request**

**Code Style**

* Please follow the existing code style and formatting.

**Additional Notes**

* Ensure your code is well-documented.
* Write unit tests to accompany your changes.
* If you're introducing a new feature or making a significant change, consider opening an issue for discussion beforehand.

**We look forward to your contributions!**

Feel free to ask if you have any questions.