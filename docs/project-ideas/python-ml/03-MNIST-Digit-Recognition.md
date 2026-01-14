# MNIST Digit Recognition with Neural Networks

**Tier:** 2-Intermediate (ML)  
**Stack:** Python, TensorFlow/Keras, Pandas, Matplotlib

A deep learning project building a neural network to recognize handwritten digits.

## Learning Goals

- Neural network fundamentals
- Convolutional Neural Networks (CNN)
- TensorFlow/Keras API
- Image preprocessing and augmentation
- Activation functions and loss functions
- Backpropagation basics
- Model training, validation, and testing
- Overfitting prevention (dropout, regularization)
- Tensorboard for visualization

## Project Tasks

1. [ ] Load MNIST dataset from keras.datasets
2. [ ] Explore data (shape, visualization of samples)
3. [ ] Normalize pixel values (0-255 to 0-1)
4. [ ] Build neural network architecture:
   - Input layer (28x28=784 or 28x28x1 for CNN)
   - 2-3 hidden layers (128, 64 neurons)
   - Output layer (10 neurons, softmax)
5. [ ] Compile model (Adam optimizer, categorical crossentropy)
6. [ ] Train model on training set
7. [ ] Validate on validation set during training
8. [ ] Evaluate on test set
9. [ ] Visualize training history (loss and accuracy curves)
10. [ ] Test on custom handwritten digits (optional)
11. [ ] Save and load trained model

## Bonus Tasks

- [ ] Build a CNN instead of dense network
- [ ] Data augmentation (rotation, shift, zoom)
- [ ] Batch normalization
- [ ] Dropout for regularization
- [ ] Ensemble multiple models
- [ ] Visualize what the network learned (activation maps)
- [ ] Build a Flask/FastAPI API to serve predictions
- [ ] Deploy to cloud (Heroku, Google Cloud)
- [ ] Use transfer learning with pre-trained models
- [ ] Compare different architectures

## Model Architecture Options

### Simple Dense Network
```
Input (784) 
  → Dense(128, relu)
  → Dropout(0.2)
  → Dense(64, relu)
  → Dropout(0.2)
  → Dense(10, softmax)
```

### Convolutional Neural Network (Better)
```
Input (28, 28, 1)
  → Conv2D(32, 3x3, relu)
  → MaxPooling2D(2x2)
  → Conv2D(64, 3x3, relu)
  → MaxPooling2D(2x2)
  → Flatten()
  → Dense(128, relu)
  → Dropout(0.5)
  → Dense(10, softmax)
```

## Technologies & Libraries

```
tensorflow      # Deep learning framework
keras           # High-level API (in TensorFlow)
numpy           # Numerical computing
pandas          # Data manipulation
matplotlib      # Visualization
jupyter         # Interactive notebooks
```

## Key Concepts to Learn

- Forward propagation
- Backpropagation
- Activation functions (ReLU, Softmax)
- Loss functions (Categorical Crossentropy)
- Optimizers (SGD, Adam)
- Epochs, batches, iterations
- Overfitting and underfitting
- Convolutional filters and feature maps
- Pooling and dimensionality reduction

## Useful Resources

- [TensorFlow/Keras MNIST Tutorial](https://www.tensorflow.org/tutorials/quickstart/beginner)
- [Keras Sequential API](https://keras.io/api/models/sequential/)
- [CNN Basics](https://towardsdatascience.com/convolutional-neural-networks-explained-9cc5188127bc)
- [MNIST Dataset Details](http://yann.lecun.com/exdb/mnist/)

## Expected Output

A Jupyter notebook containing:
- Data exploration with sample digit visualizations
- Model architecture explanation
- Training history with loss/accuracy plots
- Test set evaluation and accuracy
- Confusion matrix and per-digit accuracy
- Predictions on sample test images
- Comparison of dense vs CNN architectures (bonus)
