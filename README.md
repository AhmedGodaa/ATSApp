# ATSApp

### Project Information

this project is a microservices architecture for a simple Document management service. The project is divided into 2
services:

#### Check Application Apis With Example Responses For All Cases

[![Run in Postman](https://run.pstmn.io/button.svg)](https://documenter.getpostman.com/view/27239903/2sA3kbhyYC)

<h4 align="center">🐋 Docker Hub Images `Click On The Service` </h4>
<div align="center" style="text-align: center;">
<table style="text-align: center;">
  <tr>
    <th><a href="https://hub.docker.com/u/ahmedgodaa">Services</a></th>
    <th><a href="https://hub.docker.com/r/ahmedgodaa/ats-user-management-service">User Management Service</a></th>
    <th><a href="https://hub.docker.com/r/ahmedgodaa/ats-workspace-service">Workspace Service</a></th>

  </tr>
  <tr>
    <td><strong>Latest Image Version</strong></td>
    <td><a href="https://hub.docker.com/r/ahmedgodaa/ats-user-management-service"><img src="https://img.shields.io/docker/v/ahmedgodaa/ats-user-management-service?sort=semver" alt="Docker Image Version (latest semver)"></a></td>
    <td><a href="https://hub.docker.com/r/ahmedgodaa/ats-workspace-service"><img src="https://img.shields.io/docker/v/ahmedgodaa/ats-workspace-service?sort=semver" alt="Docker Image Version (latest semver)"></a></td>

  </tr>
  <tr>
    <td><strong>Latest Image Size</strong></td>
    <td><a href="https://hub.docker.com/r/ahmedgodaa/ats-user-management-service"><img src="https://img.shields.io/docker/image-size/ahmedgodaa/ats-user-management-service?sort=semver" alt="Docker Image Size (latest semver)"></a></td>
    <td><a href="https://hub.docker.com/r/ahmedgodaa/ats-workspace-service"><img src="https://img.shields.io/docker/image-size/ahmedgodaa/ats-workspace-service?sort=semver" alt="Docker Image Size (latest semver)"></a></td>

  </tr>
<tr>
<td><strong>Latest Image Pulls</strong></td>
<td><a href="https://hub.docker.com/r/ahmedgodaa/ats-user-management-service"><img src="https://img.shields.io/docker/pulls/ahmedgodaa/ats-user-management-service.svg" alt="Docker Pulls"></a></td>
<td><a href="https://hub.docker.com/r/ahmedgodaa/ats-workspace-service"><img src="https://img.shields.io/docker/pulls/ahmedgodaa/ats-workspace-service.svg" alt="Docker Pulls"></a></td>

</tr>
</table>
</div>

## Actions Workflow

#### Build and Push Docker Images

<p align="center">
  <img width="600"  src="assets/actions_workflow.png">
</p>


**User Management Workflow**

* [`.github/workflows/build-and-push-docker-image.yml`](.github/workflows/user_management_service_deploy.yml)

**Workspace Workflow**

* [`.github/workflows/build-and-push-docker-image.yml`](.github/workflows/workspace_service_deploy.yml)

**1. User Management Service**
<p align="center">
  <img width="100%"  src="assets/actions_workflow_version.png">
</p>
<p align="center">
  <img width="100%"  src="assets/actions_workflow_steps.png">
</p>

## Api First Approach `Don't Copy & Repeat Models Across MicroServices & Different Client`

### Swagger UI

```shell
curl --location 'localhost:8080/swagger-ui/index.html'
```

<p align="center">
  <img width="100%"  src="assets/swagger_ui.png">
</p>

### Swagger Json

```shell
curl --location 'localhost:8080/swagger'
```

### Generate Angular Client

**Angular Client**

```shell
npx @openapitools/openapi-generator-cli generate -i swagger.json -g typescript-angular -o user-management-service-angular-client
```

**Spring Client**

```shell
npx @openapitools/openapi-generator-cli generate -i swagger.json -g spring -o user-management-service-spring-client
```

### Generated Angular Client

<p align="center">
  <img width="100%"  src="assets/openapi_generator_client.png">
</p>

### Generate Java Spring Client

<p align="center">
  <img width="100%"  src="assets/openapi_generator_java_client.png">
</p>

### Deploy to `Github Package` or `Maven Central` or `JFrog Artifactory`

<p align="center">
  <img width="100%"  src="assets/repository_artifactory.png">
</p>

### **Spring Artifactory**

> ⚡️🚀
> This `artifactory` or `dependency` can be used in any microservice **without need to copy the models across the
microservices**.


<p align="center">
  <img width="100%"  src="assets/spring_artifactory.png">
</p>

## **Angular Artifactory**

> ⚡️🚀 This `artifactory` or `library` will help instead every time any api schema changes in the backend manually change
> it in the frontend, just update the library version and use it in the frontend.

<p align="center">
  <img width="100%"  src="assets/angular_artifactory.png">
</p>

## **Kafka**

used to handle the events in the system, where each event is published to a specific topic and consumed by the service
that listen to this topic.

### Kafka Flow

<p align="center">
  <img width="100%"  src="assets/kafka_flow.png">
</p>

### Kafka Topics

have 5 topics for the workspace service, where topic used as queue for each event in the system.

```java

@Bean
public KafkaAdmin.NewTopics applicationKafkaTopics() {
    return new KafkaAdmin.NewTopics(
            new NewTopic(CREATE_WORKSPACE_TOPIC, 5, (short) 1),
            new NewTopic(UPDATE_WORKSPACE_TOPIC, 5, (short) 1),
            new NewTopic(DELETE_WORKSPACE_TOPIC, 5, (short) 1),
            new NewTopic(ADD_MEMBER_TOPIC, 5, (short) 1),
            new NewTopic(REMOVE_MEMBER_TOPIC, 5, (short) 1)
    );
}
```

<p align="center">
  <img width="100%"  src="assets/kafka_topics.png">
</p>

### Kafka Producer Factory

```java
   public <K, V> KafkaProducer<K, V> createProducer() {
    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    return new KafkaProducer<>(props);
}
```

### Kafka Generic Producer

```java
    private StringMessageResponse handleWorkspaceEvent(Object event, String topic, String operationType) {
    try {
        String jsonEvent = eventSerializer.serialize(event);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, operationType + "_workspace_key", jsonEvent);
        CompletableFuture<StringMessageResponse> future = new CompletableFuture<>();

        kafkaEventProducer.send(record, (metadata, exception) -> {
            if (exception == null) {
                future.complete(new StringMessageResponse("Workspace " + operationType + " event sent successfully"));
            } else {
                future.complete(new StringMessageResponse("Failed to process " + operationType + " workspace event: " + exception.getMessage()));
            }
        });

        return future.get();

    } catch (JsonProcessingException e) {
        return new StringMessageResponse("Failed to serialize event: " + e.getMessage());
    } catch (Exception e) {
        return new StringMessageResponse("Failed to process workspace event due to an internal error: " + e.getMessage());
    }
}
```

## **Authentication:**

1. In the system, we have authenticated APIs and non-authenticated APIs that are permitted for all users.
2. We also have sign-in and sign-up processes.
3. During sign-in, if the user is not verified, they will not be able to sign in until they verify their account.
4. In the sign-up process, the user is saved in the database, and their password is encoded.

<p align="center">
  <img width="500"  src="assets/authentication_flow.png">
</p>

### SignIn :

#### Request - `SignIn`

```json
{
  "email": "admin@atsapp.com",
  "password": "A**@***4"
}
```

#### Response - `SignIn`

```json
{
  "message": "User SignIn Successfully",
  "data": {
    "id": 1,
    "email": "admin@atsapp.com",
    "fullName": "Admin",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImFkbWluQGF0c2FwcC5jb20iLCJzdWIiOiJhZG1pbkBhdHNhcHAuY29tIiwiaWF0IjoxNzI0OTI5MjA4LCJleHAiOjE3MjUwMTU2MDh9.amcLM10LtC3w4nkKf4acu8ZPo31cW9pBn_wQJY6jhk0",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImFkbWluQGF0c2FwcC5jb20iLCJzdWIiOiJhZG1pbkBhdHNhcHAuY29tIn0.OicYsNQIaEMxANBO73Dn1PlzMoBFvsJJm9Qz8loTRn8"
  }
}
```

### SignUp :

#### Request - `SignUp`

```json
{
  "fullName": "Ahmed Ali Mahmoud",
  "phoneNumber": "0120******",
  "email": "admin@atsapp.com",
  "password": "A**@***4",
  "nationID": "30102210401734"
}
```

#### Response - `SignUp`

```json
{
  "message": "User Signup Successfully, Please Check Your Mail For Verification"
}
```

<p align="center">
  <img width="100%"  src="assets/verification_mail.png">
</p>

```html
<a href="https://localhost:8080/auth/verify-email?token=919bd0a1-593d-4ba8-b996-2820ec7d1e7e">VERIFY EMAIL</a>
```

## **Password Reset**

1. There is an API where the user sends an email to request a password reset.
2. The system sends a reset password email with an OOB (Out-Of-Band) code to the provided email address.
3. The user receives the OOB code and then submits it to another API, along with their email and new password.

<p align="center">
  <img width="600"  src="assets/password_reset_flow.png">
</p>

### Password Reset Request OOB :

#### Request - `Password Reset`

```shell
curl --location --request POST 'localhost:8080/auth/password/request-reset?email=admin@atsapp.com'
```

#### Response - `Password Reset`

```json
{
  "message": "Password Reset Email Sent Successfully"
}
```

<p align="center">
  <img width="100%"  src="assets/password_reset_mail.png">
</p>

### Reset Password :

#### Request - `Reset Password`

```json
{
  "email": "admin@atsapp.com",
  "oobCode": "279326",
  "newPassword": "A**@***4"
}
```

#### Response - `Reset Password`

```json
{
  "message": "Password Reset Successfully"
}
```

## **Tokens**

Whenever the user signs in, they receive a token and a refresh token. The token expires after 1 hour, and the refresh
token expires whenever a new refresh token for the same user is generated. Once the token expires, the frontend
interceptor receives a `401 or 403` status code for the request. If this happens, it calls the refresh token API, which
is
permitted by default, and sends the refresh token. This API returns a new token and a new refresh token.

<p align="center">
  <img width="500"  src="assets/token_and_refresh_token.png">
</p>

## **Authorization**

System have two roles: `ROLE_ADMIN` and `ROLE_USER`. To make a user an admin, the issuer must already be an
admin. If the system contains no users yet and the database is empty, and someone signs up, they will be a user. So, how
do we make them an admin? There is a data loader in the Spring Boot app that checks if the first admin exists with a
constant email. If not, it will save the admin in the database with a default password. This admin can then make other
users admins, and those users will be able to make others admins as well. APIs with paths starting with /admin can only
be called by admins, but they can also call other APIs. Normal users can only call non-admin APIs.


<p align="center">
  <img width="500"  src="assets/admin_and_dataloaders_flow.png">
</p>

## **Api Validation & Exception Handling**

Validate each attribute in each request ensure request fit the acceptance criteria.
handle exceptions and return a proper response of the reason to the client.


<p align="center">
  <img width="500"  src="assets/exception_flow.png">
</p>

**Example:** SignUp Api Validation & Example Responses

![image](https://github.com/user-attachments/assets/a0eba1de-9be9-4d3e-96e2-e35a5cbbfba7)

**Model DTO:** CreateUserRequest

```java

@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "Full name must not be blank")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Phone number must not be blank")
    @Pattern(regexp = "^01[0125][0-9]{8}$", message = "Invalid Egyptian phone number")
    private String phoneNumber;


    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, and one special character")
    private String password;

// etc...
}
```

## **Data Loaders & Email Template:**

1. When the system starts, a data loader checks if the database is empty.
2. If the database is empty, the data loader saves default HTML email templates for verification, authentication,
   successful verification, failed verification, and default admin.
3. If these templates already exist in the database, they are neither saved nor updated.
4. There are APIs available to manage these email templates:

<p align="center">
  <img width="600"  src="assets/email_template_flow.png">
</p>

## **Scheduling**

1. A scheduler runs every day at 5 PM.
2. The scheduler checks for expired reset password tokens, expired verification tokens, and users who are not verified
   after one day of registration.
3. If the tokens are expired, the scheduler deletes them.

<p align="center">
  <img width="700"  src="/assets/schedulers_flow.png">
</p>

## Api Gateway & Api Versioning

Api Gateway and api versioning using **WSO2**;

### WSO2
![Untitled-2024-09-07-1227](https://github.com/user-attachments/assets/15996209-7a24-4eef-9190-29ebb7d7e6c6)


#### Api Resources

Used open api definition to add the apis with it's constraints of the backend instead add it manually.

<p align="center">
  <img width="100%"  src="/assets/api_resources_wso2.gif">
</p>

#### Api Gateway

System have two services `user-management-service` and `workspace-service` and each service have its own version.

For user-management the mapping is `/management` the workspace the mapping is `/workspace` this go under the same URL
only mapping different.


<p align="center">
  <img width="100%"  src="/assets/wso2_admin.png">
</p>

#### Api Versioning

Each Service have 2 versions  `v1` and `v2` and each version have its own instance of the application.

#### Testing Different Versions - Developer Portal

> 🔴 Note: Different Responses come from different versions of the same service.
> `v1` user information was not hidden from response
> `v2` user information was hidden from response


<p align="center">
  <img width="100%"  src="/assets/api_version_wso2.gif">
</p>

**`v1` Response**

<p align="center">
  <img width="100%"  src="/assets/wso2_version1_response.png">
</p>

**`v2` Response**

<p align="center">
  <img width="100%"  src="/assets/wso2_version2_response.png">
</p>

#### Api Gateway - Developer Portal

> Workspace goes under the **Same URL** which is **`https:locahost:8243`** and the user-management goes under the same
> URL but
> difference in mapping which is `/management` and `/workspace`.

<p align="center">
  <img width="100%"  src="/assets/wso2_api_gateway_response.png">
</p>

### Kubernetes Api Gateway & Api Versioning with Ingress

```yml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
   name: api-ingress
   namespace: development
   annotations:
      nginx.ingress.kubernetes.io/rewrite-target: /
spec:
   rules:
      - host: localhost
        http:
           paths:
              # User Management Service V1 + Api Gateway
              - path: /v1/management
                pathType: Prefix
                backend:
                   service:
                      name: user-management-service-v1
                      port:
                         number: 8080
              # User Management Service V2 + Api Gateway
              - path: /v2/management
                pathType: Prefix
                backend:
                   service:
                      name: user-management-service-v2
                      port:
                         number: 8080
              # Workspace Service V1 + Api Gateway
              - path: /v1/workspace
                pathType: Prefix
                backend:
                   service:
                      name: workspace-service-v1
                      port:
                         number: 8086
              # Workspace Service V2 + Api Gateway
              - path: /v2/workspace
                pathType: Prefix
                backend:
                   service:
                      name: workspace-service-v2
                      port:
                         number: 8086
```

### PreSigned Urls & Upload File

#### Problem Statement

To upload file to system using a multipart file this will use doubled resources.

1. Upload the file to the server.
2. Upload the file to the cloud storage.

we can avoid this by using pre-signed urls where the client will upload the file directly to the cloud storage.

#### PreSigned Urls Constraints

1. URL Time To Live.
2. HTTP Method.
3. Max File Size.
4. File Name.
5. Content Type.

#### Example Code

```java 
    public String generateUploadUrl(String fileName, String contentType) throws IOException {
    BlobId blobId = BlobId.of("atsapp-7d40e.appspot.com", fileName); // Replace with your bucket name
    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
    byte[] credentialBytes = firebaseCredential.toString().getBytes();
    InputStream inputStream = new ByteArrayInputStream(credentialBytes);
    GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
    Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

    URL url = storage.signUrl(blobInfo, 15, TimeUnit.MINUTES, Storage.SignUrlOption.httpMethod(HttpMethod.POST));
    return url.toString();
}
```

#### Example Generated URL

<p align="center">
  <img width="100%"  src="/assets/pre_signed_url.png">
</p>

### Regular Upload

<p align="center">
  <img width="100%"  src="/assets/upload_file_postman.png">
</p>

Files are saved by current `Timestamp` of now to avoid duplicate names in the storage bucket.

Files are kept under folder of teh workspace id to organize the file system.


<p align="center">
  <img width="100%"  src="/assets/file_directory_storage.png">
  <img width="100%"  src="/assets/file_storage.png">
</p>

Then File Path Saved in the database to be able to retrieve it later.

<p align="center">
  <img width="100%"  src="/assets/file_saved_in_db.png">
</p>
