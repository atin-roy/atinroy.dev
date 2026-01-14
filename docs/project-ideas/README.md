# Project Ideas Repository

A curated collection of learning projects designed to build real-world experience with **Next.js + Spring Boot + Python/ML**.

## 📚 How to Use This Repository

1. **Find a project** matching your learning goals and current skill level
2. **Read the project idea** file to understand the scope
3. **Use the command** `@project-ideas` to create a structured implementation plan
4. **Find your plan** in `/plans/` directory to guide your implementation
5. **Build it** and track your progress

## 🎯 Project Categories

### Beginner Projects (1-3 hours each)

Projects to master fundamentals:

| Project | Stack | Focus |
|---------|-------|-------|
| [Calculator App](./beginner/01-Calculator-App.md) | Next.js | React hooks, state management |
| [Weather App](./beginner/02-Weather-App.md) | Next.js | API integration, async data |
| [Quiz App](./beginner/03-Quiz-App.md) | Next.js | Component composition, logic |

**Learn:** React basics, Tailwind CSS, simple state management.

### Intermediate Projects (5-20 hours each)

Projects to build full-stack confidence:

| Project | Stack | Focus |
|---------|-------|-------|
| [Todo App](./intermediate/01-Todo-App.md) | Next.js + Spring Boot | Full-stack CRUD, databases |
| [Blog Platform](./intermediate/02-Blog-Platform.md) | Next.js + Spring Boot | Relationships, pagination, auth |
| [GitHub User Explorer](./intermediate/03-GitHub-User-Explorer.md) | Next.js + Spring Boot | External APIs, caching |

**Learn:** Full-stack development, API design, database modeling, authentication.

### Advanced Projects (30+ hours each)

Projects to master system design:

| Project | Stack | Focus |
|---------|-------|-------|
| [Real-Time Chat App](./advanced/01-Real-Time-Chat-App.md) | Next.js + Spring Boot + WebSocket | Concurrency, real-time systems |
| [E-Commerce Platform](./advanced/02-E-Commerce-Platform.md) | Next.js + Spring Boot + Postgres | System design, payments, scale |

**Learn:** Complex systems, concurrency, scalability, payment integration.

### Python/ML Learning (2-10 hours each)

Projects for machine learning fundamentals:

| Project | Tech Stack | Focus |
|---------|-----------|-------|
| [Iris Classifier](./python-ml/01-Iris-Classifier.md) | scikit-learn | Classification basics |
| [House Price Prediction](./python-ml/02-House-Price-Prediction.md) | scikit-learn, XGBoost | Regression, feature engineering |
| [MNIST Digit Recognition](./python-ml/03-MNIST-Digit-Recognition.md) | TensorFlow/Keras | Neural networks, CNNs |
| [Sentiment Analysis](./python-ml/04-Sentiment-Analysis.md) | NLTK, scikit-learn | NLP, text processing |

**Learn:** Machine learning fundamentals, data preprocessing, neural networks, NLP.

## 🗂️ Directory Structure

```
project-ideas/
├── README.md (you are here)
├── beginner/          # 1-3 hour projects
├── intermediate/      # 5-20 hour projects
├── advanced/          # 30+ hour projects
├── python-ml/         # ML/Data science projects
└── plans/             # Implementation plans (auto-generated)
    ├── Todo-App-plan.md
    ├── Blog-Platform-plan.md
    └── ...
```

## 🚀 How to Create a Plan

Use the `@project-ideas` command in Cursor:

```
@project-ideas: Create a plan for the Todo App
```

This generates a detailed plan at `/plans/[PROJECT]-plan.md` with:
- Phase-by-phase breakdown
- Daily milestones
- Common pitfalls
- Key resources
- Success checklist

## 💡 Recommended Learning Path

### Month 1: Front-End Foundation
- Calculator App (learn React basics)
- Weather App (learn API calls)
- Quiz App (complex state)

### Month 2: Full-Stack Basics
- Todo App (first backend interaction)
- GitHub User Explorer (caching, external APIs)

### Month 3: Database & Auth
- Blog Platform (relationships, auth, pagination)

### Month 4+: Advanced & Specialization
- Real-Time Chat App (WebSockets, concurrency)
- E-Commerce Platform (payments, scale)

### Parallel: Python/ML Learning
- Iris Classifier (after Month 1)
- House Price Prediction (Month 2-3)
- MNIST (Month 3-4)
- Sentiment Analysis (Month 4+)

## 🎓 What You'll Learn

### Next.js
- ✅ Server vs Client Components
- ✅ Server Actions
- ✅ API Routes & Route Handlers
- ✅ Data Fetching (SSR, ISR, CSR)
- ✅ Dynamic Routes
- ✅ Middleware
- ✅ Image Optimization
- ✅ SEO & Meta Tags

### Spring Boot
- ✅ REST API Design
- ✅ Spring Data JPA
- ✅ Entity Relationships
- ✅ Transaction Management
- ✅ Spring Security & JWT
- ✅ Exception Handling
- ✅ Logging & Debugging
- ✅ Testing (Unit & Integration)
- ✅ WebSockets
- ✅ Caching Strategies

### Full-Stack
- ✅ Frontend-Backend Communication
- ✅ API Design Patterns
- ✅ Database Modeling
- ✅ Authentication Flow
- ✅ Error Handling
- ✅ Performance Optimization
- ✅ Deployment

### Python/ML
- ✅ NumPy & Pandas
- ✅ Data Exploration & Visualization
- ✅ Supervised Learning (Classification & Regression)
- ✅ Feature Engineering
- ✅ Neural Networks with Keras/TensorFlow
- ✅ NLP Fundamentals
- ✅ Model Evaluation & Metrics

## 📊 Project Complexity Matrix

```
            Frontend  Backend  Database  External API
Beginner      ⭐⭐    ⭐       -         ⭐
Intermediate  ⭐⭐⭐   ⭐⭐⭐    ⭐⭐       ⭐⭐
Advanced      ⭐⭐⭐   ⭐⭐⭐⭐   ⭐⭐⭐     ⭐⭐⭐
```

## 🔗 Technology Stack Details

### Frontend (Next.js)
```
TypeScript
React 18+
Tailwind CSS
TanStack Query (optional, for server state)
NextAuth.js (for authentication)
```

### Backend (Spring Boot)
```
Java 17+
Spring Boot 3.x
Spring Data JPA
PostgreSQL
Spring Security
```

### Python/ML
```
Python 3.9+
pandas & NumPy
scikit-learn
TensorFlow/Keras
NLTK
Matplotlib/Seaborn
Jupyter
```

## ✅ Pre-Project Checklist

Before starting any project:

- [ ] Do I understand the core technologies involved?
- [ ] Do I have the development environment set up?
- [ ] Have I read the project idea file completely?
- [ ] Do I have the implementation plan ready?
- [ ] Do I understand the learning goals?
- [ ] Have I identified potential challenges?

## 🆘 Getting Stuck?

1. **Check the plan** - Review your phase milestones
2. **Read the hints** - Each project idea has implementation hints
3. **Review resources** - Check the "Useful Links" section
4. **Break it down** - Divide the task into smaller pieces
5. **Ask for help** - Use `@explain` or `@debug` commands

## 📈 Progress Tracking

Create a file `PROJECT_PROGRESS.md` in your project directory:

```markdown
# [Project Name] Progress

## Setup Phase
- [x] Environment setup
- [x] Database created
- [ ] API running

## Core Features
- [x] Feature 1
- [ ] Feature 2
- [ ] Feature 3

## Bonus Features
- [ ] Bonus 1
- [ ] Bonus 2

## Learnings & Gotchas
- Learned: How to use X
- Gotcha: Don't do Y because...
```

## 🎯 Success Criteria

After completing a project, you should be able to:

1. **Explain** what the project does and why
2. **Defend** your architectural decisions
3. **Extend** the project with new features
4. **Debug** any issues that arise
5. **Deploy** the project to production
6. **Discuss** trade-offs and alternatives

## 📚 Additional Resources

- **Next.js Docs:** https://nextjs.org/docs
- **Spring Boot Docs:** https://spring.io/projects/spring-boot
- **Scikit-learn Guide:** https://scikit-learn.org/
- **TensorFlow/Keras:** https://www.tensorflow.org/

## 🤝 Contributing

Found an issue or have a better project idea?

1. Create a new file following the template
2. Add to this README
3. Include phase-by-phase breakdown
4. Add learning goals and resources

## 🎓 Philosophy

These projects are designed with one core principle:

> **Building over watching.** Get your hands dirty, make mistakes, and learn from them.

Don't just read about how to build a todo app—actually build one. Make it beautiful. Break it and fix it. That's where real learning happens.

---

**Last Updated:** January 2026  
**Total Projects:** 11 (8 Web, 4 ML)  
**Estimated Learning Hours:** 200+
