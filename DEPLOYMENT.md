# Deployment Guide

Guide for deploying the Student Form Application to production.

## 📋 Pre-Deployment Checklist

### Security

- [ ] Change database credentials from default
- [ ] Enable HTTPS for API endpoints
- [ ] Add API authentication (JWT/OAuth)
- [ ] Configure CORS for production domain
- [ ] Set up rate limiting
- [ ] Enable SQL query logging (disable in prod)
- [ ] Sanitize all error messages
- [ ] Add file type validation (whitelist)
- [ ] Configure firewall rules

### Configuration

- [ ] Update backend URL in Android app
- [ ] Set production database connection
- [ ] Configure file upload limits
- [ ] Set up backup strategy
- [ ] Configure logging levels
- [ ] Add monitoring/alerting
- [ ] Set up CDN for file delivery (optional)

### Testing

- [ ] Run full test suite
- [ ] Perform security audit
- [ ] Load testing
- [ ] Test on multiple devices
- [ ] Verify all API endpoints
- [ ] Test file upload limits
- [ ] Verify PDF generation

## 🖥️ Backend Deployment

### Option 1: Traditional Server (Linux)

#### 1. Install Prerequisites

```bash
# Update system
sudo apt update && sudo apt upgrade -y

# Install JDK 17
sudo apt install openjdk-17-jdk -y

# Install MySQL
sudo apt install mysql-server -y

# Secure MySQL
sudo mysql_secure_installation
```

#### 2. Configure MySQL

```bash
# Login to MySQL
sudo mysql -u root -p

# Create database and user
CREATE DATABASE student_form_db;
CREATE USER 'studentapp'@'localhost' IDENTIFIED BY 'your_secure_password';
GRANT ALL PRIVILEGES ON student_form_db.* TO 'studentapp'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

#### 3. Update Configuration

Edit `backend/src/main/resources/application.properties`:

```properties
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/student_form_db
spring.datasource.username=studentapp
spring.datasource.password=your_secure_password

# Production settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
logging.level.com.scan.studentformbackend=INFO

# File storage
file.upload-dir=/var/www/uploads
```

#### 4. Build Application

```bash
cd backend
mvn clean package -DskipTests
```

#### 5. Create Service File

Create `/etc/systemd/system/studentform.service`:

```ini
[Unit]
Description=Student Form Backend
After=mysql.service

[Service]
User=www-data
WorkingDirectory=/opt/studentform
ExecStart=/usr/bin/java -jar /opt/studentform/student-form-backend-1.0.0.jar
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

#### 6. Deploy and Start

```bash
# Create directory
sudo mkdir -p /opt/studentform
sudo mkdir -p /var/www/uploads

# Copy JAR
sudo cp target/student-form-backend-1.0.0.jar /opt/studentform/

# Set permissions
sudo chown -R www-data:www-data /opt/studentform
sudo chown -R www-data:www-data /var/www/uploads

# Start service
sudo systemctl daemon-reload
sudo systemctl enable studentform
sudo systemctl start studentform

# Check status
sudo systemctl status studentform
```

#### 7. Configure Nginx (Reverse Proxy)

Install Nginx:

```bash
sudo apt install nginx -y
```

Create `/etc/nginx/sites-available/studentform`:

```nginx
server {
    listen 80;
    server_name your-domain.com;

    location /api {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        
        # Increase upload size
        client_max_body_size 10M;
    }
}
```

Enable and restart:

```bash
sudo ln -s /etc/nginx/sites-available/studentform /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl restart nginx
```

#### 8. Set Up SSL with Let's Encrypt

```bash
sudo apt install certbot python3-certbot-nginx -y
sudo certbot --nginx -d your-domain.com
```

### Option 2: Docker Deployment

#### 1. Create Dockerfile

Create `backend/Dockerfile`:

```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/student-form-backend-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### 2. Create docker-compose.yml

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: student_form_db
      MYSQL_USER: studentapp
      MYSQL_PASSWORD: studentpassword
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  backend:
    build: ./backend
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/student_form_db
      SPRING_DATASOURCE_USERNAME: studentapp
      SPRING_DATASOURCE_PASSWORD: studentpassword
    volumes:
      - ./uploads:/app/uploads
    depends_on:
      - mysql

volumes:
  mysql_data:
```

#### 3. Deploy with Docker

```bash
# Build and run
docker-compose up -d

# View logs
docker-compose logs -f

# Stop
docker-compose down
```

### Option 3: Cloud Deployment (AWS/Azure/GCP)

#### AWS Elastic Beanstalk

```bash
# Install EB CLI
pip install awsebcli

# Initialize
eb init -p java-17 student-form-backend

# Create environment
eb create production-env

# Deploy
eb deploy
```

#### Heroku

```bash
# Login
heroku login

# Create app
heroku create student-form-backend

# Add MySQL
heroku addons:create cleardb:ignite

# Deploy
git push heroku main
```

## 📱 Android App Deployment

### 1. Update Production Configuration

Update `RetrofitClient.kt`:

```kotlin
private const val BASE_URL = "https://your-domain.com/"
```

### 2. Generate Signing Key

```bash
keytool -genkey -v -keystore studentform.keystore -alias studentform -keyalg RSA -keysize 2048 -validity 10000
```

### 3. Configure Gradle

Add to `app/build.gradle`:

```groovy
android {
    signingConfigs {
        release {
            storeFile file('../studentform.keystore')
            storePassword 'your_password'
            keyAlias 'studentform'
            keyPassword 'your_password'
        }
    }
    
    buildTypes {
        release {
            signingConfig signingConfigs.release
            minifyEnabled true
            shrinkResources true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}
```

### 4. Build Release APK

```bash
./gradlew assembleRelease
```

APK will be in: `app/build/outputs/apk/release/app-release.apk`

### 5. Build App Bundle (for Play Store)

```bash
./gradlew bundleRelease
```

AAB will be in: `app/build/outputs/bundle/release/app-release.aab`

### 6. Google Play Store Deployment

1. **Create Developer Account**
    - Go to play.google.com/console
    - Pay $25 one-time fee

2. **Create New App**
    - Fill in app details
    - Upload screenshots
    - Write description

3. **Upload App Bundle**
    - Go to Release → Production
    - Upload app-release.aab
    - Fill in release notes

4. **Content Rating**
    - Complete questionnaire
    - Get rating

5. **Set Pricing**
    - Free or Paid

6. **Review & Publish**
    - Submit for review
    - Wait for approval (2-7 days)

### 7. Alternative Distribution

#### Direct APK Distribution

```bash
# Host on your server
scp app/build/outputs/apk/release/app-release.apk user@server:/var/www/downloads/

# Share download link
https://your-domain.com/downloads/app-release.apk
```

#### Firebase App Distribution

```bash
# Install Firebase CLI
npm install -g firebase-tools

# Login
firebase login

# Initialize
firebase init

# Upload APK
firebase appdistribution:distribute app/build/outputs/apk/release/app-release.apk \
  --app YOUR_FIREBASE_APP_ID \
  --groups testers
```

## 🔍 Post-Deployment Monitoring

### Set Up Logging

#### Backend Logging (Logback)

Create `backend/src/main/resources/logback-spring.xml`:

```xml
<configuration>
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>/var/log/studentform/app.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>/var/log/studentform/app.%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <root level="INFO">
        <appender-ref ref="FILE" />
    </root>
</configuration>
```

### Health Checks

```bash
# Backend health
curl https://your-domain.com/api/students/all

# Check disk space
df -h /var/www/uploads

# Check MySQL
mysql -u root -p -e "SELECT COUNT(*) FROM student_form_db.students;"
```

### Set Up Monitoring

#### Using Prometheus + Grafana

```bash
# Add to pom.xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

Enable in `application.properties`:

```properties
management.endpoints.web.exposure.include=health,metrics,prometheus
management.endpoint.health.show-details=always
```

## 🔄 Backup Strategy

### Database Backup

```bash
# Daily backup script
#!/bin/bash
DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_DIR="/backups/mysql"
mkdir -p $BACKUP_DIR

mysqldump -u root -p student_form_db > $BACKUP_DIR/backup_$DATE.sql
gzip $BACKUP_DIR/backup_$DATE.sql

# Keep only last 30 days
find $BACKUP_DIR -name "*.sql.gz" -mtime +30 -delete
```

Add to crontab:

```bash
0 2 * * * /opt/scripts/backup_db.sh
```

### File Backup

```bash
# Daily file backup
#!/bin/bash
DATE=$(date +%Y%m%d)
tar -czf /backups/files/uploads_$DATE.tar.gz /var/www/uploads/
find /backups/files -name "*.tar.gz" -mtime +30 -delete
```

## 📊 Performance Optimization

### Backend

```properties
# Connection pooling
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5

# JVM options
-Xms512m -Xmx2048m -XX:+UseG1GC
```

### Database Indexes

```sql
CREATE INDEX idx_email ON students(email);
CREATE INDEX idx_created_at ON students(created_at);
CREATE INDEX idx_customer_name ON students(customer_name);
```

## 🚨 Troubleshooting Production Issues

### Backend Not Starting

```bash
# Check logs
sudo journalctl -u studentform -n 50

# Check port
sudo netstat -tulpn | grep 8080

# Check disk space
df -h
```

### Database Connection Issues

```bash
# Check MySQL status
sudo systemctl status mysql

# Check connection
mysql -u studentapp -p student_form_db
```

### File Upload Issues

```bash
# Check permissions
ls -la /var/www/uploads

# Fix permissions
sudo chown -R www-data:www-data /var/www/uploads
sudo chmod -R 755 /var/www/uploads
```

## ✅ Production Checklist

- [ ] Backend deployed and running
- [ ] Database configured and accessible
- [ ] SSL certificate installed
- [ ] Android app published or distributed
- [ ] Backups configured
- [ ] Monitoring set up
- [ ] Logs configured
- [ ] Health checks working
- [ ] Error tracking enabled
- [ ] Performance tested
- [ ] Security audit completed
- [ ] Documentation updated
- [ ] Team trained

---

**Congratulations on your deployment! 🎉**
