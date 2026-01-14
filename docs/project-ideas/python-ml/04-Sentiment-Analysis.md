# Sentiment Analysis - Movie Reviews

**Tier:** 2-Intermediate (ML)  
**Stack:** Python, NLTK, scikit-learn, TensorFlow/Keras (for bonus)

Classify movie reviews as positive or negative using NLP techniques.

## Learning Goals

- Natural Language Processing (NLP) basics
- Text preprocessing (tokenization, stemming, lemmatization)
- Text vectorization (TF-IDF, Word2Vec, Embeddings)
- Bag of Words vs word embeddings
- Binary classification with text data
- RNN/LSTM for sequence modeling (bonus)
- Handling imbalanced datasets

## Project Tasks

1. [ ] Load movie review dataset (IMDb, or create one)
2. [ ] Explore data distribution (positive/negative ratio)
3. [ ] Text preprocessing:
   - Convert to lowercase
   - Remove punctuation
   - Tokenize
   - Remove stopwords
   - Stemming or Lemmatization
4. [ ] Text vectorization:
   - TF-IDF Vectorizer
   - Or Word Embeddings
5. [ ] Train classifiers:
   - Naive Bayes
   - Logistic Regression
   - Support Vector Machine
6. [ ] Evaluate models (accuracy, precision, recall, F1-score)
7. [ ] Create confusion matrix
8. [ ] Test on custom reviews
9. [ ] Visualize word frequencies for positive/negative reviews

## Bonus Tasks

- [ ] Use pre-trained word embeddings (Word2Vec, GloVe)
- [ ] Build LSTM neural network for sequence classification
- [ ] Aspect-based sentiment analysis
- [ ] Multi-class sentiment (5-star ratings)
- [ ] Use BERT for state-of-the-art results
- [ ] Deploy as REST API
- [ ] Real-time sentiment analysis from Twitter/social media
- [ ] Visualize word embeddings with t-SNE

## Datasets

- IMDb Movie Reviews (50,000 reviews)
- Movie Review Data (Pang & Lee dataset)
- Amazon reviews
- Twitter sentiment dataset
- Hugging Face datasets library

## Technologies & Libraries

```
nltk               # Natural Language Processing
scikit-learn       # Machine learning
pandas             # Data manipulation
numpy              # Numerical computing
matplotlib         # Visualization
seaborn            # Statistical plots
tensorflow/keras   # Deep learning (bonus)
gensim             # Word embeddings (bonus)
```

## Text Preprocessing Pipeline Example

```python
import nltk
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer
import re

def preprocess_text(text):
    # Convert to lowercase
    text = text.lower()
    
    # Remove URLs and special characters
    text = re.sub(r'http\S+|www\S+|[^a-z\s]', '', text)
    
    # Tokenize
    tokens = nltk.word_tokenize(text)
    
    # Remove stopwords
    stop_words = set(stopwords.words('english'))
    tokens = [t for t in tokens if t not in stop_words]
    
    # Lemmatization
    lemmatizer = WordNetLemmatizer()
    tokens = [lemmatizer.lemmatize(t) for t in tokens]
    
    return ' '.join(tokens)
```

## Model Comparison

| Model | Speed | Accuracy | Interpretability |
|-------|-------|----------|------------------|
| Naive Bayes | Fast | Medium | High |
| Logistic Regression | Fast | Medium | High |
| SVM | Slow | High | Low |
| LSTM | Slow | Very High | Low |
| BERT | Very Slow | Very High | Low |

## Key Concepts to Learn

- Tokenization and lemmatization
- Stop words
- TF-IDF (Term Frequency - Inverse Document Frequency)
- Word embeddings
- Bag of Words
- Sequence models (RNN, LSTM)
- Attention mechanisms (bonus)

## Useful Resources

- [NLTK Documentation](https://www.nltk.org/)
- [Scikit-learn Text Feature Extraction](https://scikit-learn.org/stable/modules/feature_extraction.html#text-feature-extraction)
- [IMDb Dataset](https://ai.stanford.edu/~amaas/data/sentiment/)
- [Word2Vec Tutorial](https://radimrehurek.com/gensim/models/word2vec.html)
- [BERT for Sentiment Analysis](https://huggingface.co/transformers/task_summary.html#sentiment-classification)

## Expected Output

A Jupyter notebook with:
- Dataset exploration and class distribution
- Text preprocessing pipeline implementation
- Multiple classifier training and comparison
- Model evaluation metrics and confusion matrices
- Visualizations of most common positive/negative words
- Test predictions on sample reviews
- ROC curves for model comparison
