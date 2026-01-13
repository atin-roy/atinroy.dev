# Python Quick Reference

Modern Python patterns and features (PEP8 compliant). Focus on Python 3.10+.

## List/Dict/Set Comprehensions
```python
# List comprehension (preferred over loops)
squares = [x**2 for x in range(10)]
evens = [x for x in range(10) if x % 2 == 0]

# Dict comprehension
word_lengths = {word: len(word) for word in ["hello", "world"]}

# Set comprehension
unique_lengths = {len(word) for word in ["hello", "world", "hi"]}

# Nested comprehension
matrix = [[i*j for j in range(3)] for i in range(3)]

# Generator expression (memory efficient for large data)
sum_of_squares = sum(x**2 for x in range(1000000))
```

**Why?** More Pythonic, often faster than explicit loops. **Trade-off:** Less readable if too complex (max 2-3 lines).

## Type Hints (Python 3.5+)
```python
from typing import List, Dict, Optional, Union, Tuple, Callable

# Basic types
def greet(name: str) -> str:
    return f"Hello, {name}"

# Collections
def process_users(users: List[Dict[str, str]]) -> List[str]:
    return [u["name"] for u in users]

# Optional (can be None)
def find_user(id: int) -> Optional[User]:
    return db.get(id)  # Returns User or None

# Union (multiple types)
def process(value: Union[int, str]) -> str:
    return str(value)

# Modern syntax (Python 3.10+)
def find_user(id: int) -> User | None:  # Instead of Optional[User]
    pass

# Callable (function type)
def apply(func: Callable[[int, int], int], x: int, y: int) -> int:
    return func(x, y)

# Generic
from typing import TypeVar
T = TypeVar('T')
def first(items: List[T]) -> T:
    return items[0]
```

## Dataclasses (Python 3.7+)
```python
from dataclasses import dataclass, field

# Instead of manually writing __init__, __repr__, __eq__
@dataclass
class User:
    name: str
    email: str
    age: int = 0  # Default value
    tags: List[str] = field(default_factory=list)  # Mutable default
    
    def __post_init__(self):
        # Validation after initialization
        if self.age < 0:
            raise ValueError("Age cannot be negative")

user = User("Atin", "atin@example.com", 25)
print(user)  # User(name='Atin', email='atin@example.com', age=25, tags=[])
```

**Why?** Reduces boilerplate, automatically generates common methods.

## Pattern Matching (Python 3.10+)
```python
def handle_response(response):
    match response:
        case {"status": 200, "data": data}:
            return data
        case {"status": 404}:
            raise NotFoundError()
        case {"status": code} if code >= 500:
            raise ServerError(code)
        case _:  # Default case
            raise UnknownError()

# Structural pattern matching
def describe_point(point):
    match point:
        case (0, 0):
            return "Origin"
        case (0, y):
            return f"Y-axis at {y}"
        case (x, 0):
            return f"X-axis at {x}"
        case (x, y):
            return f"Point at {x}, {y}"
```

## Decorators
```python
# Function decorator
def timer(func):
    import time
    def wrapper(*args, **kwargs):
        start = time.time()
        result = func(*args, **kwargs)
        print(f"{func.__name__} took {time.time() - start:.2f}s")
        return result
    return wrapper

@timer
def slow_function():
    time.sleep(2)

# Class decorator
from functools import wraps

def singleton(cls):
    instances = {}
    @wraps(cls)
    def get_instance(*args, **kwargs):
        if cls not in instances:
            instances[cls] = cls(*args, **kwargs)
        return instances[cls]
    return get_instance

@singleton
class Database:
    pass
```

## Context Managers
```python
# Built-in
with open("file.txt") as f:
    content = f.read()

# Multiple context managers
with open("input.txt") as infile, open("output.txt", "w") as outfile:
    outfile.write(infile.read())

# Custom context manager
from contextlib import contextmanager

@contextmanager
def timer_context(name):
    start = time.time()
    try:
        yield
    finally:
        print(f"{name} took {time.time() - start:.2f}s")

with timer_context("operation"):
    # Some slow operation
    pass
```

## Async/Await (Python 3.5+)
```python
import asyncio

async def fetch_data(url: str) -> dict:
    async with aiohttp.ClientSession() as session:
        async with session.get(url) as response:
            return await response.json()

async def main():
    # Sequential
    user = await fetch_data("/api/user/1")
    posts = await fetch_data("/api/posts")
    
    # Parallel (faster!)
    user, posts = await asyncio.gather(
        fetch_data("/api/user/1"),
        fetch_data("/api/posts")
    )

# Run
asyncio.run(main())
```

## Walrus Operator := (Python 3.8+)
```python
# Assignment within expression
# Before
match = re.search(pattern, text)
if match:
    print(match.group(1))

# After (more concise)
if match := re.search(pattern, text):
    print(match.group(1))

# In comprehensions
[clean for line in file if (clean := line.strip())]
```

## Common Idioms
```python
# Enumerate (better than range(len()))
for i, item in enumerate(items):
    print(f"{i}: {item}")

# Zip (iterate multiple lists)
names = ["Alice", "Bob"]
ages = [25, 30]
for name, age in zip(names, ages):
    print(f"{name} is {age}")

# Dict.get with default
value = config.get("key", "default_value")

# Unpacking
first, *middle, last = [1, 2, 3, 4, 5]  # first=1, middle=[2,3,4], last=5

# Dictionary unpacking
defaults = {"a": 1, "b": 2}
overrides = {"b": 3, "c": 4}
merged = {**defaults, **overrides}  # {"a": 1, "b": 3, "c": 4}

# F-strings with expressions
name = "Atin"
print(f"{name.upper()}")  # ATIN
print(f"{2 + 2 = }")      # 2 + 2 = 4 (Python 3.8+)

# Exception chaining
try:
    risky_operation()
except ValueError as e:
    raise CustomError("Operation failed") from e
```

## Virtual Environments
```bash
# Create venv
python -m venv .venv

# Activate
source .venv/bin/activate  # Linux/Mac
.venv\Scripts\activate     # Windows

# Install dependencies
pip install -r requirements.txt

# Freeze current packages
pip freeze > requirements.txt
```

**Why venv?** Isolate project dependencies, avoid version conflicts.
