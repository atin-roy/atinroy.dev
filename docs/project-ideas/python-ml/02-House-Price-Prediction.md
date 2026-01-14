# House Price Prediction

**Tier:** 2-Intermediate (ML)  
**Stack:** Python, scikit-learn, Pandas, Matplotlib, XGBoost

A regression project predicting house prices based on multiple features.

## Learning Goals

- Regression algorithms (Linear, Ridge, Lasso)
- Feature engineering and selection
- Handling categorical variables (encoding)
- Dealing with missing data strategies
- Regularization (Ridge, Lasso)
- Ensemble methods (Random Forest, Gradient Boosting)
- RMSE and R² metrics
- Preventing overfitting with regularization

## Project Tasks

1. [ ] Load Boston Housing or Ames Housing dataset
2. [ ] Explore data (describe, info, missing values)
3. [ ] Data visualization (distribution plots, correlation heatmap)
4. [ ] Handle missing values (imputation, removal)
5. [ ] Encode categorical variables (one-hot, label encoding)
6. [ ] Feature scaling (standardization or normalization)
7. [ ] Train multiple regression models:
   - Linear Regression
   - Ridge Regression
   - Lasso Regression
   - Random Forest
   - XGBoost
8. [ ] Evaluate models using RMSE, MAE, R² score
9. [ ] Analyze feature importance
10. [ ] Hyperparameter tuning with GridSearchCV
11. [ ] Cross-validation and learning curves

## Bonus Tasks

- [ ] Feature engineering (create new features)
- [ ] Polynomial features for non-linear relationships
- [ ] Outlier detection and handling
- [ ] Stacking multiple models
- [ ] Residual analysis
- [ ] Prediction intervals/confidence bounds
- [ ] Deploy as Flask/FastAPI API
- [ ] Visualize predictions vs actual values

## Dataset Options

- Boston Housing (classic, but deprecated)
- Ames Housing (kaggle.com/c/house-prices-advanced-regression-techniques)
- California Housing (scikit-learn built-in)

## Technologies & Libraries

```
pandas           # Data manipulation
numpy            # Numerical computing
scikit-learn     # ML algorithms
xgboost          # Gradient boosting
matplotlib       # Visualization
seaborn          # Statistical plots
jupyter          # Interactive notebooks
```

## Key Concepts to Learn

- Regression vs Classification
- Loss functions (MSE, MAE)
- Regularization (L1, L2)
- Feature importance
- Correlation analysis
- Overfitting prevention
- Cross-validation

## Useful Resources

- [Scikit-learn Regression Guide](https://scikit-learn.org/stable/modules/regression.html)
- [XGBoost Documentation](https://xgboost.readthedocs.io/)
- [Feature Engineering Best Practices](https://scikit-learn.org/stable/modules/preprocessing.html)
- [Ames Housing Dataset Guide](https://www.kaggle.com/c/house-prices-advanced-regression-techniques/data)

## Expected Output

A comprehensive Jupyter notebook containing:
- EDA with visualizations
- Data preprocessing pipeline
- Model training and comparison
- Feature importance analysis
- Best model evaluation with RMSE and R²
- Predictions on test set with visualization
