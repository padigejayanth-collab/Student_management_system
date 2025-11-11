# Backend Deployment Fixes

This document outlines the fixes applied to make the backend work correctly in deployment.

## Changes Made

### 1. Dynamic API URL Configuration
**Problem**: The frontend JavaScript had a hardcoded API URL (`http://localhost:8080/api`) that only worked locally.

**Solution**: Updated JavaScript files to use dynamic API URL detection:
- `webapp/public/js/app.js`
- `webapp/src/main/resources/public/js/app.js`

The API URL now automatically detects the current host and uses relative paths for deployment.

### 2. Security Configuration
**Problem**: Spring Security was blocking access to student API endpoints (`/api/students/**`).

**Solution**: Updated `SecurityConfig.java` to allow unauthenticated access to all API endpoints:
- `/api/**` - All API endpoints are now accessible
- Static resources are properly configured

### 3. Index.html Paths
**Problem**: Root `index.html` had incorrect paths for CSS and JavaScript files.

**Solution**: Updated paths to use absolute paths that work with Spring Boot:
- Changed from `webapp/public/css/style.css` to `/css/style.css`
- Changed from `webapp/public/js/app.js` to `/js/app.js`

## How It Works Now

### Local Development
1. Start Spring Boot: `cd webapp && mvn spring-boot:run`
2. Access: `http://localhost:8080`
3. API automatically uses: `http://localhost:8080/api`

### Production Deployment
1. The API URL automatically detects the deployment host
2. If deployed to `https://yourdomain.com`, API uses: `https://yourdomain.com/api`
3. No configuration changes needed!

## Deployment Steps

### Option 1: Traditional Deployment
```bash
cd webapp
mvn clean package
java -jar target/student-management-system-1.0.0.jar
```

### Option 2: Docker Deployment
```bash
cd webapp
docker build -t sms-webapp:latest .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://your-db-host:3306/studentdb \
  -e SPRING_DATASOURCE_USERNAME=your_username \
  -e SPRING_DATASOURCE_PASSWORD=your_password \
  sms-webapp:latest
```

### Option 3: Cloud Deployment (Heroku, AWS, etc.)
1. Set environment variables:
   - `SPRING_DATASOURCE_URL`
   - `SPRING_DATASOURCE_USERNAME`
   - `SPRING_DATASOURCE_PASSWORD`
   - `SPRING_JPA_HIBERNATE_DDL_AUTO=update`
   - `APP_JWT_SECRET` (use a strong secret in production)

2. Deploy the JAR file or use the Docker image

## Environment Variables

The application uses environment variables for production configuration:

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | Database connection URL | `jdbc:mysql://localhost:3306/studentdb` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `root` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | (local password) |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | Hibernate DDL mode | `update` |
| `APP_JWT_SECRET` | JWT secret key | (placeholder) |

## Testing the Deployment

1. **Check Backend Health**:
   ```bash
   curl http://your-domain/api/students
   ```

2. **Check Frontend**:
   - Open `http://your-domain` in browser
   - Try logging in or creating an account
   - Verify API calls work (check browser console)

3. **Common Issues**:
   - **CORS errors**: Already fixed - CORS allows all origins
   - **404 on API**: Check that SecurityConfig allows `/api/**`
   - **Database connection**: Verify environment variables are set correctly

## Files Modified

1. `webapp/public/js/app.js` - Dynamic API URL
2. `webapp/src/main/resources/public/js/app.js` - Dynamic API URL (Spring Boot served version)
3. `webapp/src/main/java/com/sms/config/SecurityConfig.java` - Allow API access
4. `index.html` (root) - Fixed paths for deployment

## Next Steps

1. ✅ Backend API URL is now dynamic
2. ✅ Security configuration allows API access
3. ✅ Static file paths are correct
4. ⚠️ **Important**: Set strong `APP_JWT_SECRET` in production
5. ⚠️ **Important**: Use `SPRING_JPA_HIBERNATE_DDL_AUTO=none` in production after initial setup
6. ⚠️ **Important**: Use HTTPS in production
7. ⚠️ **Important**: Configure proper database connection pooling for production

