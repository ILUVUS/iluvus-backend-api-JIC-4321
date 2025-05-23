# ILUVUS - BACKEND API

<p align="center">
<img src="./img/icon.png" width="300" height="auto" />
</p>

### Description

We're working on a social media app called ILUVUS, which focuses on celebrating racial minorities and marginalized communities like the LGBTQ+. Our goal is to connect these diverse communities in a world where discrimination and intolerance are still prevalent. ILUVUS aims to address this issue by promoting positivity, celebrating achievements, and combatting discrimination.

We're developing our own platform instead of using existing ones because they don't prioritize the celebration and recognition of these communities' accomplishments. Our focus is on offering a unique user experience that celebrates diversity. By doing this, we can ensure strict moderation of hateful posts and messages and promote positivity effectively.

## Installation Guide: Getting Started

### Technology Stack:

-   **Spring Boot**: A Java-based framework for building robust and scalable backend applications.

-   **Google Cloud Platform (GCP)**: Google Cloud Run for deploying and running containerized applications.

-   **Google Cloud Storage**: A scalable and secure object storage solution provided by Google Cloud Platform.

-   **GitHub Actions**: GitHub's built-in CI/CD solution for automating workflows, including building, testing, and deploying applications.

### Dependencies

-   Maven

-   Generally, use the following [link](https://www.baeldung.com/install-maven-on-windows-linux-mac) to install maven on your system.

-   For Mac OS: [Instruction](https://help.mulesoft.com/s/article/How-to-Setup-Maven-for-Mac-OS)

-   For Windows: [Instruction](https://mkyong.com/maven/how-to-install-maven-in-windows/)

-   JDK 8+

-   Use this link [https://adoptium.net/temurin/releases/](https://adoptium.net/temurin/releases/) to download and install OpenJDK version that is compatible with your Operation System.

### Installing & Execute

1. Use the following link to install maven on your system.

[https://www.baeldung.com/install-maven-on-windows-linux-mac](https://www.baeldung.com/install-maven-on-windows-linux-mac)

-   For Mac OS: [Instruction](https://help.mulesoft.com/s/article/How-to-Setup-Maven-for-Mac-OS)

-   For Windows: [Instruction](https://mkyong.com/maven/how-to-install-maven-in-windows/)

2. Clone the ILUVUS Backend API Repository:

```bash

git clone https://github.com/ILUVUS/iluvus-backend-api-JIA-3317.git

```

3. Create `application.properties` in `/src/main/resources`. Modify the following content:

```java
spring.data.mongodb.uri=mongodb+srv://<USERNAME:<PASSWORD>@iluvusdb.mocs11o.mongodb.net/iluvus
spring.data.mongodb.database=iluvus
iluvus.email.passwordtoken=<GMAIL_GENERATED_PASSWORD>
```

Modify `<USERNAME>` and `<PASSWORD>` with provided information.

4. Go to the repository directory on your Operation System. Run SpringBoot:

```bash

mvn spring-boot:run

```

4. Check the API with your browser: [127.0.0.1/8080](127.0.0.1/8080)

5. Your browser should return: `Welcome to ILUVUS. API is running well!`

### Deployment on Google Cloud

1. [Create a new Google Cloud Project](https://developers.google.com/workspace/guides/create-project)

2. [Create an Artifact Registry Repository](https://cloud.google.com/artifact-registry/docs/repositories/create-repos)

    - Name the Repo "iluvus", all lowercase
    - Make sure the repo is created under the same service account

3. [Create a Cloud Run Service](https://cloud.google.com/run/docs/deploying)

    - Make sure the service is created under the same service account

4. Locate to GCP Dashboard, use this to replace the `PROJECT_ID` on Github Secrets.

 <img src='./img/gcp/dashboard.png' width=500 />

5. Locate to GCP Cloud Run Dashboard, copy the Service Name and use this to replace `PROJECT_SERVICE` on Github Secrets.

<img src='./img/gcp/cloudrun.png' width=700 />

6. [Create a Google Service Account Credential](https://developers.google.com/workspace/guides/create-credentials#create_credentials_for_a_service_account)

    - Copy the content of the JSON file obtained from the step 5 above. Use the content to replace the `GG_CREDENTIAL` on Github Secrets.

7. Create APPCONFIG

Modify the following content:

```java
spring.data.mongodb.uri=mongodb+srv://<USERNAME:<PASSWORD>@iluvusdb.mocs11o.mongodb.net/iluvus
spring.data.mongodb.database=iluvus
iluvus.email.passwordtoken=<GMAIL_GENERATED_PASSWORD>
```

Modify `<USERNAME>` and `<PASSWORD>` with provided information.

8. Use the APPCONFIG Content to replace the APPCONFIG on Github Secrets.

After all, the Github Secrets should map with the above steps like below:

<img src='./img/gcp/githubsecrets.png' width=700 />

# Release Notes
## Version 2.0.0

### Primary Features
- Users can now create community posts to share their achievements and interests.
- Users can now share posts they like within their respective communities' feed (sharable link or under someone's profile).
- Users can now optionally include and/or view sources in a community.
- Users can now create chatrooms to chat one-on-one with other users.
- Users can additionally create group chatrooms to chat with 3-4 other users of their choosing.
- Users can now block other users of their choosing.
- Users can now review and report other users in their community who have violated community guidelines to a community moderator for final review.

### Additional Features
- Users can additionally view warnings for potential sensitive or triggering content on reported posts that are still under review.
- Users can now view "Topics of the Day" on the home page to keep up with current events on the application.
- Users can now explore popular topics from communities to keep up with and take part in community discussions.
- Users can now look up BOTH communities and other users and click on their icons to view their respective pages.
- Users can now search for specific posts using keywords.
- Users can now add filters to their search to narrow down content by specific criteria.
- Users can now receive notifications for likes and comments on their posts.
- Users can now set their profile picture for their account.
- Users can now add a personal bio to their profile page.
- Users can now display both their skills and interests on their profile page.
- Users can now toggle to set their profile settings to be general or professional.
- Users can now view and agree to the terms and conditions of using the application upon account creation.
- Users can edit their profile to display their job and relationship status if needed.
- Community owners can now designate whether their community is professional or general upon creating a community
- Users can now search for both professional and general communities by name
- Users can join both professional and general communities (before they could only join general)
- Users can now leave both private and public communities that they no longer wish to follow
  
### Bug Fixes Across Development
- The loading time for the profile picture, posts, and communities was slowing down the entire app
- Notifications for direct messaging/chats and sharing posts had a default icon that wasn't displaying properly.
- The community view was breaking when more than one community was included on the platform.
- User Searching rendering is breaking, have to type slowly to ensure user will show up.
- After sharing posts, other icons were being pushed off to the side.
- Clicking on a picture that has already been added while creating a post was causing the application to crash.
- The "Forgot my password" link not working on the login screen.
- Community Posts were not showing up on the home-feed screen.
- Announcement icon in communities was not working previously.
- Interests list topics in both the community and profile screen were not loading when users looked up topics
- Google Cloud Deployment was not syncing up with local environment testing
- Profile Icon (no picture) was not loading properly anytime the user navigated to the screen
- Publish post icon was not displaying properly
- Users were first unable to leave communities and then after adding the feature unable to leave upon request
- Create community button was not displayed properly

### Known Issues Across Development

- If the owner of a community reports a post, that post will automatically be deleted
- Creating posts with pictures is no longer publishing correctly.
- Duplicate chats for one-on-one users can be created.
- Users cannot view posts with videos due to loading time concerns.
- Loading time across the application was too long.
- User Searching would autofill with viewed user after returning from clicked profile view.
- Users could not search other users to tag.
- Forgot my password link not working on login screen.
- Community posts not showing up on home screen.
- Profile not loading without making a post first.

## Version 1.0.0

### Features

-   Community Users can view videos in Media Viewer.
-   Community Users can create new post with videos.
-   Professional Users can view their Created Groups in its own view.
-   All Users can view their Following Groups in its own view.
-   Users can view their birthday on Profile page.
-   Live error indicators in Registration Form.
-   Communities are removed if a user account is removed from ILUVUS.
-   Users can receive notifications for uplifts, comments, tags, reports, and other types from communities.
-   Users can view the posts that are relevant to their interests in the newsfeed.
-   Users can select the interest topic when creating posts.
-   Added the profile page to display user information.
-   Users can select and edit their interests on the profile page.
-   Added users as moderators at the creating community view to manage reporting posts (keep or remove from the community).
-   Moderator users can view and accept or reject reporting posts.
-   Community owner can set profile pictures for their community at the creating point.
-   Users can now upload and share images along with their posts.
-   Users can view/zoom images in a post.
-   Implemented image processing capabilities such as resizing and compression.
-   Ability to tag other users in a post has been added.
-   Communities can now be set as either public or private.
-   Added a "Request Join" button for private communities.
-   Owners of private communities can now accept/decline join requests.
-   All login passwords are now hashed to increase security.
-   Create text-only posts and display them on the community posts.
-   Like and dislike a post in a community.
-   Comment on a post in a community.
-   Report a post in a community.
-   Display the number of uplift(like) on the community posts.
-   Display the comment view on the community posts.
-   Search community by the community’s name
-   View, and join community
-   Register inputs indicator
-   Date picker for Date of Birth input on Register screen

### Bug Fixes Across Development

-   Users could not get the relevant post properly on Home Screen after re-selecting interests.
-   User could not post Text only post.
-   Professional Users could not receive verification code.
-   Users must be 18 or older to sign up an account.
-   Community View buttons were flickery.
-   Reported post automatically removed once there is more than 5 reports.
-   Fixed the problem where notifications disappear.
-   Fixed the image display on the posts in the communities.
-   Change to switch button when asking for the professional or regular user at the registration screen.
-   The community list is displayed in order.
-   Corrected the display of the number of likes/uplifts.
-   Fixed the problem where posts were not displaying properly.
-   Fixed the report button and added a report message when reporting.
-   Register new user with empty username and password
-   Create new community button did not show up
-   Verify does not work with userId

### Known Issues Across Development

-   Login Form Scrollview does not scroll down properly.
-   Status bar text blends with background color.
-   The algorithm to sort the posts by interests sometimes goes wrong.
-   The post page is frozen if no images are selected.
-   Users can tag people not in the community.
-   Registration will navigate to verification screen even the user existed.
-   Verification page may send 2 emails.

## License

This project is licensed under the MIT License.

## Contributors:

-   [Arjun Ramani](#)
-   [Binaya Timsina](#)
-   [Doan Tran](#)
-   [Thuan Vo](#)
-   [Tyler Lin](#)
-   [Shreya Devaraju](#)
-   [Christeena Joby](#)
-   [Devika Papal](#)
-   [Sahil Virani](#)
-   [Roham Wahabzada](#)

## Acknowledgments

Ideas, Inspiration, and Project belong to [Jay Elliott](#)
