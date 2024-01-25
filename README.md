# ILUVUS - BACKEND API

<p align="center">
<img src="./img/icon.png" width="300" height="auto" />
</p>

### Description

We're working on a social media app called ILUVUS, which focuses on celebrating racial minorities and marginalized communities like the LGBTQ+. Our goal is to connect these diverse communities in a world where discrimination and intolerance are still prevalent. ILUVUS aims to address this issue by promoting positivity, celebrating achievements, and combatting discrimination.

We're developing our own platform instead of using existing ones because they don't prioritize the celebration and recognition of these communities' accomplishments. Our focus is on offering a unique user experience that celebrates diversity. By doing this, we can ensure strict moderation of hateful posts and messages and promote positivity effectively.

## Getting Started

### Technology Stack:

- ** Spring Boot **: A Java-based framework for building robust and scalable backend applications.
- ** Google Cloud Platform (GCP)**: Google Cloud Run for deploying and running containerized applications.
- **Google Cloud Storage**: A scalable and secure object storage solution provided by Google Cloud Platform.
- **GitHub Actions**: GitHub's built-in CI/CD solution for automating workflows, including building, testing, and deploying applications.

### Dependencies

* Maven 
	* Generally, use the following [link](https://www.baeldung.com/install-maven-on-windows-linux-mac) to install maven on your system.
		* For Mac OS: [Instruction](https://help.mulesoft.com/s/article/How-to-Setup-Maven-for-Mac-OS)
		* For Windows: [Instruction](https://mkyong.com/maven/how-to-install-maven-in-windows/)
* JDK 8+
	* Use this link [https://adoptium.net/temurin/releases/]( https://adoptium.net/temurin/releases/) to download and install OpenJDK version that is compatible with your Operation System.

### Installing & Execute

1. Use the following link to install maven on your system.
[https://www.baeldung.com/install-maven-on-windows-linux-mac](https://www.baeldung.com/install-maven-on-windows-linux-mac)

- For Mac OS: [Instruction](https://help.mulesoft.com/s/article/How-to-Setup-Maven-for-Mac-OS)
- For Windows: [Instruction](https://mkyong.com/maven/how-to-install-maven-in-windows/)

2. Clone the ILUVUS Backend API Repository:

```bash
git clone https://github.com/ILUVUS/iluvus-backend-api-JIA-3317.git
```

3. Go to the repository directory on your Operation System. Run SpringBoot:

```bash
mvn spring-boot:run
```

4. Check the API with your browser: [127.0.0.1/8080](127.0.0.1/8080)

5. Your browser should return: `Welcome to ILUVUS. API is running well!`

## Contributors:

[Arjun Ramani](#)
[Binaya Timsina](#)
[Doan Tran](#)
[Thuan Vo](#)
[Tyler Lin](#)

# Release Notes

## Version 0.1.0

### Features

- Search community by the community’s name
- View, and join community
- Register inputs indicator
- Date picker for Date of Birth input on Register screen

### Bug Fixes

- Register new user with empty username and password
- Create new community button did not show up

### Known Issues

- (optional)

## License

This project is licensed under the MIT License.

## Acknowledgments

Ideas, Inspiration, and Project belong to [James Elliott](#)
