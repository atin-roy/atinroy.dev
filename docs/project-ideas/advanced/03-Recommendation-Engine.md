# Recommendation Engine (E-Commerce)

**Tier:** 3-Advanced  
**Stack:** Next.js Frontend, Spring Boot Backend, Python ML Pipeline, PostgreSQL

A machine learning-powered recommendation system suggesting products to users based on behavior and preferences.

## Learning Goals

- ML pipeline integration with backend
- Collaborative filtering and content-based filtering
- Real-time recommendations vs batch processing
- Model serving and inference
- Data pipeline for model training
- A/B testing recommendation strategies
- Scalability of ML systems

## User Stories

- [ ] Users see personalized product recommendations
- [ ] Recommendations improve as user browses/purchases
- [ ] Similar product recommendations on product pages
- [ ] Admin can see recommendation accuracy metrics
- [ ] System falls back gracefully if ML service unavailable
- [ ] New users see reasonable recommendations
- [ ] Recommendations update in real-time

## Bonus Features

- [ ] Multi-armed bandit for exploration vs exploitation
- [ ] Time-based recommendations (trending now, seasonal)
- [ ] Collaborative filtering with matrix factorization
- [ ] Content-based filtering (similar products)
- [ ] Hybrid approach combining multiple strategies
- [ ] Explain why product was recommended (transparency)
- [ ] A/B testing different recommendation algorithms
- [ ] User feedback loop (helpful/not helpful)
- [ ] Real-time model retraining with new data

## Architecture

```
Frontend (Next.js)
    ↓
Backend API (Spring Boot)
    ├── Recommendation Service
    ├── User Service (tracking behavior)
    └── Product Service
    ↓
ML Pipeline (Python)
    ├── Data Collection
    ├── Feature Engineering
    ├── Model Training
    └── Model Serving (FastAPI or similar)
    ↓
Databases
    ├── PostgreSQL (users, products, interactions)
    └── Redis (caching recommendations)
```

## Database Schema

```
User
  - id (PK)
  - email
  - createdAt

Product
  - id (PK)
  - name
  - category
  - price
  - features (JSON)
  - embedding (vector, for similarity)

UserInteraction
  - id (PK)
  - userId (FK -> User)
  - productId (FK -> Product)
  - interactionType (view, add_to_cart, purchase, like)
  - timestamp
  - duration (for views)

UserPreference
  - userId (PK -> User)
  - productId (FK -> Product)
  - rating (1-5, nullable)
  - preference_score
  - timestamp

Recommendation
  - id (PK)
  - userId (FK -> User)
  - productId (FK -> Product)
  - algorithm (collaborative, content_based, trending)
  - score
  - shown_at
  - clicked_at (nullable)
  - purchased_at (nullable)

ModelVersion
  - id (PK)
  - algorithm
  - version
  - metrics (JSON - precision, recall, NDCG)
  - trained_at
  - deployed_at (nullable)
  - performance_score
```

## Spring Boot Architecture

```
Controllers:
  - RecommendationController (get recommendations)
  - FeedbackController (user feedback on recommendations)
  - AdminAnalyticsController (model metrics, A/B test results)

Services:
  - RecommendationService (orchestrate recommendations)
  - InteractionTracker (log user actions)
  - ModelEvaluationService (track recommendation quality)
  - ExperimentService (A/B testing framework)

Integrations:
  - ML Service Client (call Python model)
  - CacheManager (Redis for caching)
  - MetricsCollector (for model monitoring)
```

## ML Pipeline (Python)

```
Data Collection
    → Raw interactions from PostgreSQL

Feature Engineering
    → User features (purchase history, categories viewed)
    → Product features (category, price, popularity)
    → Interaction features (recency, frequency, weight)

Model Training
    → Collaborative Filtering (user-product matrix)
    → Content-Based Filtering (product similarity)
    → Hybrid approach (weighted combination)

Model Serving
    → FastAPI endpoint for predictions
    → Batch predictions for caching
    → Real-time scoring for online recommendations
```

## API Endpoints

```
RECOMMENDATIONS:
GET    /api/recommendations           - Get personalized recommendations
GET    /api/recommendations/similar   - Similar products
GET    /api/users/:id/preferences     - Get user preferences
POST   /api/feedback/:productId       - User feedback on recommendation

ADMIN:
GET    /api/admin/recommendations/metrics  - Model performance
GET    /api/admin/experiments             - A/B test results
POST   /api/admin/models/deploy           - Deploy new model
GET    /api/admin/models/versions         - Model versions

ML SERVICE (Python):
POST   /predict                        - Get recommendations
POST   /train                          - Retrain model
GET    /metrics                        - Model metrics
```

## ML Algorithms

### Collaborative Filtering
```python
# User-User: Recommend products liked by similar users
# Item-Item: Recommend similar products
# Matrix Factorization: Latent factor models

from sklearn.decomposition import NMF
# Decompose user-product matrix
```

### Content-Based Filtering
```python
# Calculate product similarity based on features
# Use TF-IDF, cosine similarity for vectors

from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
```

### Hybrid Approach
```python
# Combine collaborative + content-based
# Weighted average: 0.7 * collab + 0.3 * content
```

## Key Challenges

1. **Cold Start Problem:** New users/products with no interactions
2. **Sparsity:** User-product matrix is very sparse
3. **Scalability:** Computing recommendations for millions of users
4. **Freshness:** Balancing accuracy with real-time updates
5. **Serendipity:** Recommending items that truly surprise users
6. **Feedback Loop:** How to measure recommendation quality

## Evaluation Metrics

| Metric | Formula | What it Measures |
|--------|---------|------------------|
| Precision@K | (Relevant items in top K) / K | Accuracy of top K |
| Recall@K | (Relevant items in top K) / Total relevant | Coverage of relevant items |
| NDCG@K | Normalized Discounted Cumulative Gain | Ranking quality |
| Coverage | % of catalog recommended | Diversity |
| Diversity | Similarity between recommendations | Non-repetitiveness |

## Useful Technologies

**Backend:**
- Spring Boot
- PostgreSQL + pgvector extension (for embeddings)
- Redis (caching)

**ML Pipeline:**
- Scikit-learn (collaborative filtering)
- TensorFlow/PyTorch (neural collaborative filtering)
- FastAPI (model serving)
- Pandas (data processing)

**Integration:**
- Docker (containerize ML service)
- Apache Airflow (ML pipeline orchestration)
- MLflow (experiment tracking)

## Useful Links & Resources

- [Collaborative Filtering Guide](https://developers.google.com/machine-learning/recommendation/overview)
- [ML Fairness in Recommendations](https://www.tensorflow.org/responsible_ai)
- [FastAPI for ML Serving](https://fastapi.tiangolo.com/)
- [pgvector for Embeddings](https://github.com/pgvector/pgvector)
- [Scikit-learn NMF](https://scikit-learn.org/stable/modules/generated/sklearn.decomposition.NMF.html)

## Implementation Hints

- Start with simple content-based filtering
- Gradually add collaborative filtering
- Use caching aggressively (recommendations don't change hourly)
- Test cold start solutions (category-based defaults)
- Monitor recommendation quality continuously
- A/B test different algorithms on subset of users
- Explain recommendations to build user trust
