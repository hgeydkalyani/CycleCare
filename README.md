# CycleCare - Period Tracking Application

![CycleCare](https://img.shields.io/badge/CycleCare-Period%20Tracker-FF6B9D)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)
![Angular](https://img.shields.io/badge/Angular-17-red)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)

A modern, full-stack period tracking application inspired by Flo, built with Spring Boot and Angular.

## 🌸 Features

- **Smart Predictions**: AI-powered cycle predictions based on historical data
- **Beautiful Calendar**: 28-day cycle visualization with period, ovulation, and fertile window tracking
- **Symptom Tracking**: Log symptoms, moods, and flow intensity
- **Dashboard**: Comprehensive overview of cycle statistics and upcoming predictions
- **Responsive Design**: Flo-inspired pink/purple theme optimized for all devices

## 🏗️ Architecture

### Backend (Spring Boot)
```
backend/
├── src/main/java/com/cyclecare/backend/
│   ├── entity/          # JPA Entities (User, CycleData)
│   ├── repository/      # Spring Data JPA Repositories
│   ├── service/         # Business Logic Layer
│   ├── controller/      # REST API Controllers
│   ├── dto/             # Data Transfer Objects
│   └── config/          # Configuration (CORS, etc.)
├── src/main/resources/
│   ├── application.yml  # Application Configuration
│   └── schema.sql       # Database Schema
└── pom.xml             # Maven Dependencies
```

### Frontend (Angular)
```
frontend/
├── src/app/
│   ├── components/      # Angular Components
│   │   ├── dashboard/
│   │   ├── calendar/
│   │   └── symptom-tracker/
│   ├── services/        # API Services
│   ├── models/          # TypeScript Models
│   └── app.component.ts # Main App Component
├── src/styles.css      # Global Styles
├── angular.json        # Angular Configuration
└── package.json        # NPM Dependencies
```

## 📋 Prerequisites

### Required Software
- **Java 17** or higher
- **Maven 3.6+**
- **Node.js 18+** and npm
- **MySQL 8.0+**
- **Angular CLI** (`npm install -g @angular/cli`)

## 🚀 Installation & Setup

### 1. Database Setup

```sql
-- Create database
CREATE DATABASE cyclecare;

-- Run the schema (located in backend/src/main/resources/schema.sql)
-- Or let Spring Boot auto-create tables with ddl-auto: update
```

**Configure MySQL credentials:**
Edit `backend/src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/cyclecare
    username: root          # Your MySQL username
    password: root          # Your MySQL password
```

### 2. Backend Setup

```bash
# Navigate to backend directory
cd cyclecare/backend

# Install dependencies and run
mvn clean install
mvn spring-boot:run
```

Backend will start on: `http://localhost:8080`

**Verify backend:**
```bash
# Test API endpoint
curl http://localhost:8080/api/cycle/predict
```

### 3. Frontend Setup

```bash
# Navigate to frontend directory
cd cyclecare/frontend

# Install dependencies
npm install

# Start development server
ng serve
```

Frontend will start on: `http://localhost:4200`

## 🔌 API Endpoints

### Cycle Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/cycle/predict` | Get next cycle prediction |
| POST | `/api/cycle` | Save new cycle data |
| GET | `/api/cycle` | Fetch all cycle data |

### User Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users` | Create new user |
| GET | `/api/users` | Get all users |
| GET | `/api/users/email/{email}` | Get user by email |

### Request/Response Examples

**POST /api/cycle**
```json
{
  "startDate": "2026-02-01",
  "cycleLength": 28,
  "symptoms": "Cramps, Fatigue",
  "flowIntensity": "Medium",
  "mood": "Normal",
  "notes": "Mild symptoms",
  "userId": 1
}
```

**GET /api/cycle/predict**
```json
{
  "nextPeriodDate": "2026-03-01",
  "ovulationDate": "2026-02-15",
  "fertileWindowStart": "2026-02-10",
  "fertileWindowEnd": "2026-02-16",
  "averageCycleLength": 28,
  "predictionAccuracy": "High",
  "message": "Your next period is predicted to start on 2026-03-01"
}
```

## 🎨 UI Components

### Dashboard
- **Prediction Card**: Next period date with accuracy indicator
- **Cycle Statistics**: Average cycle length, ovulation, fertile window
- **Recent Cycles**: History of logged periods with symptoms

### Calendar
- **28-Day View**: Visual representation of your cycle
- **Color Coding**:
  - 🔴 Red: Period days
  - 🥚 Green: Ovulation day
  - 💚 Blue: Fertile window
- **Navigation**: Previous/Next month controls

### Symptom Tracker
- **Period Logging**: Date and cycle length
- **Symptom Selection**: Common symptoms checklist
- **Flow & Mood**: Intensity and emotional state tracking
- **Notes**: Additional observations

## 🎓 Viva/Interview Talking Points

### Technical Stack Justification
1. **Spring Boot**: Enterprise-grade backend with robust ecosystem
2. **Angular**: Modern SPA framework with TypeScript for type safety
3. **MySQL**: Reliable relational database for structured health data
4. **REST API**: Industry-standard, stateless communication

### Architecture Patterns
- **Layered Architecture**: Clean separation (Entity → Repository → Service → Controller)
- **DTO Pattern**: Decoupling internal entities from API contracts
- **Repository Pattern**: Abstraction over data access
- **Dependency Injection**: Spring's IoC container for loose coupling

### Key Features to Highlight
1. **Predictive Algorithm**: Calculates average cycle length with variance analysis
2. **Data Validation**: Jakarta Validation annotations ensure data integrity
3. **CORS Configuration**: Secure cross-origin resource sharing
4. **Responsive Design**: Mobile-first approach with CSS Grid/Flexbox
5. **RESTful Design**: Resource-oriented URLs with proper HTTP methods

### Potential Enhancements
- User authentication (Spring Security + JWT)
- Data encryption for sensitive health information
- Push notifications for period reminders
- Export data as PDF reports
- Integration with wearable devices
- Machine learning for more accurate predictions
- Multi-language support

## 🔧 Troubleshooting

### Backend Issues

**Port 8080 already in use:**
```bash
# Find and kill process
lsof -ti:8080 | xargs kill -9
# Or change port in application.yml
```

**MySQL connection refused:**
```bash
# Check MySQL is running
sudo service mysql status
# Start if needed
sudo service mysql start
```

**Lombok not working:**
- Install Lombok plugin in your IDE (IntelliJ/Eclipse)
- Enable annotation processing

### Frontend Issues

**Module not found errors:**
```bash
# Clear cache and reinstall
rm -rf node_modules package-lock.json
npm install
```

**CORS errors:**
- Verify backend is running on port 8080
- Check CORS configuration in `WebConfig.java`

**Port 4200 in use:**
```bash
ng serve --port 4300
```

## 📊 Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Cycle Data Table
```sql
CREATE TABLE cycle_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    start_date DATE NOT NULL,
    cycle_length INT NOT NULL,
    symptoms TEXT,
    flow_intensity VARCHAR(50),
    mood VARCHAR(100),
    notes TEXT,
    user_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

## 🎯 Testing

### Backend Testing
```bash
# Run unit tests
mvn test

# Test specific endpoint
curl -X POST http://localhost:8080/api/cycle \
  -H "Content-Type: application/json" \
  -d '{"startDate":"2026-02-01","cycleLength":28,"userId":1}'
```

### Frontend Testing
```bash
# Run Angular tests
ng test

# Build for production
ng build --configuration production
```

## 📝 Project Checklist

- [x] Spring Boot backend with layered architecture
- [x] MySQL database integration
- [x] RESTful API endpoints
- [x] Angular frontend with routing
- [x] Flo-inspired responsive UI
- [x] Cycle prediction algorithm
- [x] Calendar visualization
- [x] Symptom tracking form
- [x] CORS configuration
- [x] Data validation
- [x] Complete documentation

## 👨‍💻 Developer

**Your Name**  
Final Year Engineering Student  
[Your Email] | [LinkedIn] | [GitHub]

## 📄 License

This project is created for educational purposes as a final year engineering project.

---

**Built with ❤️ using Spring Boot & Angular**
