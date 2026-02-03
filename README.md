# Hawaii Compliance Temporal Worker (Java Spring Boot)

A robust Java Spring Boot implementation of the Hawaii Compliance Temporal worker, designed to handle TVR registration, complaint investigation, violation appeals, and annual inspection workflows.

## 🚀 Features

- **Java 21** with Spring Boot 3.x
- **Temporal SDK** for workflow orchestration
- **Production-ready** with Docker support
- **Health endpoints** for monitoring
- **Environment-based configuration**
- **Render-compatible** deployment

## 📋 Workflows

### 1. TVR Registration Workflow
- Initial review (2 hours)
- Zoning verification (1 day)
- NCUC processing (3-7 days, if required)
- Inspection scheduling (2-3 days)
- Final approval (1-2 days)

### 2. Complaint Investigation Workflow
- Initial assessment (1 day)
- Evidence collection (3-7 days)
- Site visit (2-3 days)
- Investigation report (2-3 days)
- Violation determination (1-2 days)
- Notice generation (1 day)

### 3. Violation Appeal Workflow
- Document review (3-5 days)
- Legal review (1-2 weeks)
- Hearing scheduling (1 week)
- Decision making (3-5 days)
- Notification (1 day)

### 4. Annual Inspection Workflow
- Scheduling (2-3 days)
- On-site inspection (1 day)
- Report generation (1-2 days)
- Follow-up (if needed, 3 days)
- Compliance verification (1 day)

## 🛠️ Local Development

### Prerequisites
- Java 21+
- Maven 3.9+
- Temporal server (for local testing)

### Running Locally

1. **Clone and build:**
   ```bash
   cd temporal-worker-java
   mvn clean package
   ```

2. **Run with default settings:**
   ```bash
   java -jar target/temporal-worker-java-1.0.0.jar
   ```

3. **Run with custom Temporal server:**
   ```bash
   TEMPORAL_ADDRESS=localhost:7233 \
   TEMPORAL_NAMESPACE=default \
   TEMPORAL_TASK_QUEUE=tvr-compliance-queue \
   java -jar target/temporal-worker-java-1.0.0.jar
   ```

### Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `PORT` | 8080 | Server port |
| `TEMPORAL_ADDRESS` | localhost:7233 | Temporal server address |
| `TEMPORAL_NAMESPACE` | default | Temporal namespace |
| `TEMPORAL_TASK_QUEUE` | tvr-compliance-queue | Task queue name |

## 🐳 Docker Deployment

### Build Docker Image
```bash
docker build -t hawaii-compliance-worker .
```

### Run with Docker
```bash
docker run -p 8080:8080 \
  -e TEMPORAL_ADDRESS=ap-south-1.aws.api.temporal.io:7233 \
  -e TEMPORAL_NAMESPACE=default \
  -e TEMPORAL_TASK_QUEUE=tvr-compliance-queue \
  hawaii-compliance-worker
```

## ☁️ Render Deployment

### Prerequisites
- Render account
- Temporal Cloud credentials

### Deployment Steps

1. **Push to GitHub:**
   ```bash
   git add .
   git commit -m "Deploy Java Temporal worker"
   git push origin main
   ```

2. **Create Render Web Service:**
   - Connect your GitHub repository
   - Set build command: `mvn clean package`
   - Set start command: `java -jar target/temporal-worker-java-1.0.0.jar`
   - Add environment variables:
     - `TEMPORAL_ADDRESS`: `ap-south-1.aws.api.temporal.io:7233`
     - `TEMPORAL_NAMESPACE`: `default`
     - `TEMPORAL_TASK_QUEUE`: `tvr-compliance-queue`

3. **Deploy:** Render will automatically build and deploy your service

## 📊 Health Endpoints

### Health Check
```bash
GET /api/health
```

Response:
```json
{
  "status": "healthy",
  "service": "Hawaii Compliance Temporal Worker",
  "timestamp": 1641234567890,
  "workerRunning": true,
  "taskQueue": "tvr-compliance-queue",
  "namespace": "default"
}
```

### Service Info
```bash
GET /api/info
```

Response:
```json
{
  "service": "Hawaii Compliance Temporal Worker",
  "version": "1.0.0",
  "description": "Temporal Worker for Hawaii Compliance Dashboard",
  "workflows": ["TVRRegistrationWorkflow", "ComplaintInvestigationWorkflow", "ViolationAppealWorkflow", "AnnualInspectionWorkflow"],
  "activities": ["performInitialReview", "verifyZoning", "processNCUC", ...]
}
```

## 🔧 Configuration

### application.properties
```properties
# Spring Boot Configuration
server.port=${PORT:8080}
spring.application.name=hawaii-compliance-worker

# Management/Health Endpoints
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=always

# Temporal Configuration
temporal.connection.target=${TEMPORAL_ADDRESS:localhost:7233}
temporal.connection.namespace=${TEMPORAL_NAMESPACE:default}
temporal.worker.task-queue=${TEMPORAL_TASK_QUEUE:tvr-compliance-queue}
```

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Integration Tests
```bash
mvn verify -P integration-test
```

## 📝 Logs

The worker provides detailed logging for:
- Workflow execution
- Activity completion
- Temporal connection status
- Error handling

## 🔄 Monitoring

### Spring Boot Actuator
- `/actuator/health` - Health status
- `/actuator/info` - Application info

### Custom Endpoints
- `/api/health` - Worker-specific health
- `/api/info` - Workflow and activity information

## 🚨 Troubleshooting

### Common Issues

1. **Temporal Connection Failed**
   - Check `TEMPORAL_ADDRESS` environment variable
   - Verify Temporal server is running
   - Check network connectivity

2. **Worker Not Starting**
   - Check logs for startup errors
   - Verify Java version (21+)
   - Check Maven dependencies

3. **Health Check Failing**
   - Ensure worker is properly registered
   - Check Temporal connection
   - Verify task queue configuration

## 📚 Architecture

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Spring Boot   │    │   Temporal SDK   │    │  Temporal Cloud │
│   Application   │◄──►│   Java Client    │◄──►│   Server        │
└─────────────────┘    └──────────────────┘    └─────────────────┘
         │                       │                       │
         ▼                       ▼                       ▼
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   REST API      │    │   Workflows      │    │   Task Queue    │
│  Endpoints      │    │   Activities     │    │   Management    │
└─────────────────┘    └──────────────────┘    └─────────────────┘
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.

## 🆘 Support

For issues and questions:
- Create an issue in the repository
- Check the troubleshooting section
- Review the logs for detailed error information
