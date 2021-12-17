## File Service

File service provides ability to upload and download files to server storage

### Initial Setup

Firstly, build the project<br/>
`./gradlew installDist`

Next build Dockerfile<br/>
`docker build -t file_service .`

And then run application<br/>
`docker run -p 3050:3050 my-application`

### API

#### Upload file into storage

POST `/file/upload` <br/>
Body: `{ file: "<PATH_TO_FILE>" }`

cURL for testing:<br/>
`curl --location --request POST 'http://0.0.0.0:8082/file/upload' \
--header 'Content-Disposition: attachment; filename=\"${file.name}\"' \
--form 'file=@"<PATH_TO_FILE>"'`

#### Download file from storage

GET `/file/<FILE_NAME>` <br/>

`curl --location --request GET 'http://localhost:8082/file/airbnb.xml' \
--header 'Content-Disposition: attachment; filename=\"${file.name}\"'`
