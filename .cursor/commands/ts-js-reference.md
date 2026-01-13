# TypeScript/JavaScript Quick Reference

Modern TS/JS features and patterns. Focus on post-ES6 features you might forget.

## Type Assertions & Guards
```typescript
// Type assertion (use sparingly - you're telling TS "trust me")
const myCanvas = document.getElementById("main") as HTMLCanvasElement;

// Type guard (TS narrows type automatically)
function isString(value: unknown): value is string {
  return typeof value === "string";
}

// Discriminated unions (powerful pattern!)
type Result = 
  | { success: true; data: string }
  | { success: false; error: Error };

function handle(result: Result) {
  if (result.success) {
    console.log(result.data); // TS knows: data exists here
  } else {
    console.log(result.error); // TS knows: error exists here
  }
}
```

## Utility Types (Built-in TypeScript)
```typescript
// Partial - makes all properties optional
type User = { name: string; age: number; email: string };
type PartialUser = Partial<User>; // { name?: string; age?: number; email?: string }

// Pick - select specific properties
type UserPreview = Pick<User, "name" | "email">; // { name: string; email: string }

// Omit - exclude specific properties
type UserWithoutEmail = Omit<User, "email">; // { name: string; age: number }

// Record - create object type with specific keys
type Roles = "admin" | "user" | "guest";
type Permissions = Record<Roles, string[]>; // { admin: string[], user: string[], guest: string[] }

// ReturnType - extract function return type
function getUser() { return { name: "Atin", age: 25 }; }
type UserType = ReturnType<typeof getUser>; // { name: string; age: number }
```

## Array Methods (Functional Programming)
```typescript
const numbers = [1, 2, 3, 4, 5];

// map - transform each element
numbers.map(n => n * 2); // [2, 4, 6, 8, 10]

// filter - keep elements matching condition
numbers.filter(n => n > 2); // [3, 4, 5]

// reduce - accumulate to single value
numbers.reduce((acc, n) => acc + n, 0); // 15

// find/findIndex - first match
numbers.find(n => n > 3); // 4
numbers.findIndex(n => n > 3); // 3

// some/every - boolean checks
numbers.some(n => n > 4); // true
numbers.every(n => n > 0); // true

// flatMap - map + flatten (one level)
[[1, 2], [3, 4]].flatMap(arr => arr); // [1, 2, 3, 4]
```

## Async/Await Patterns
```typescript
// Basic
async function fetchUser(id: string): Promise<User> {
  const response = await fetch(`/api/users/${id}`);
  return response.json();
}

// Error handling
try {
  const user = await fetchUser("123");
} catch (error) {
  if (error instanceof Error) {
    console.error(error.message);
  }
}

// Promise.all - parallel execution (all must succeed)
const [users, posts] = await Promise.all([
  fetchUsers(),
  fetchPosts()
]);

// Promise.allSettled - parallel, handles failures
const results = await Promise.allSettled([
  fetchUsers(),
  fetchPosts()
]);
results.forEach(result => {
  if (result.status === "fulfilled") {
    console.log(result.value);
  } else {
    console.error(result.reason);
  }
});
```

## Optional Chaining & Nullish Coalescing
```typescript
// Optional chaining (?.) - short-circuit on null/undefined
const user = { profile: { name: "Atin" } };
const city = user?.profile?.address?.city; // undefined (no error!)

// Nullish coalescing (??) - default only for null/undefined
const port = process.env.PORT ?? 3000; // Use 3000 if PORT is null/undefined
// vs
const port2 = process.env.PORT || 3000; // Use 3000 if PORT is falsy (0, "", false)
```

## Destructuring Patterns
```typescript
// Object destructuring with rename & defaults
const { name: userName, age = 18 } = user;

// Array destructuring with rest
const [first, second, ...rest] = [1, 2, 3, 4, 5];

// Function parameters
function greet({ name, age }: { name: string; age: number }) {
  return `${name} is ${age}`;
}

// Nested destructuring
const { profile: { email } } = user;
```

## Modern Object/Array Operations
```typescript
// Object spread (shallow copy + merge)
const merged = { ...defaults, ...userSettings };

// Array spread
const combined = [...arr1, ...arr2];

// Object.entries / Object.keys / Object.values
const obj = { a: 1, b: 2 };
Object.entries(obj); // [["a", 1], ["b", 2]]
Object.keys(obj);    // ["a", "b"]
Object.values(obj);  // [1, 2]

// Grouping (ES2024)
const people = [
  { name: "Atin", role: "dev" },
  { name: "Bob", role: "dev" },
  { name: "Alice", role: "designer" }
];
Object.groupBy(people, person => person.role);
// { dev: [{...}, {...}], designer: [{...}] }
```

## Generic Functions
```typescript
// Make functions work with multiple types
function identity<T>(arg: T): T {
  return arg;
}

// With constraints
function getProperty<T, K extends keyof T>(obj: T, key: K) {
  return obj[key]; // Type-safe!
}

const user = { name: "Atin", age: 25 };
getProperty(user, "name"); // ✅ "Atin"
getProperty(user, "email"); // ❌ TS error: "email" not in User
```

**Why these patterns?** They leverage TypeScript's type system for safer, more maintainable code!
