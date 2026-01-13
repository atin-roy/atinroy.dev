# Next.js (App Router) Quick Reference

Modern Next.js patterns with App Router. Covers features you might forget.

## File-based Routing
```
app/
├── page.tsx                    → /
├── about/page.tsx              → /about
├── blog/[slug]/page.tsx        → /blog/:slug (dynamic)
├── shop/[...slug]/page.tsx     → /shop/* (catch-all)
├── dashboard/layout.tsx        → Shared layout for /dashboard/*
└── api/users/route.ts          → API endpoint /api/users
```

## Server vs Client Components
```tsx
// SERVER COMPONENT (default in App Router)
// ✅ Can: fetch data directly, access backend, smaller bundle
// ❌ Cannot: use hooks (useState, useEffect), browser APIs, event listeners

async function Page() {
  const data = await fetch('https://api.example.com/data');
  const json = await data.json();
  
  return <div>{json.title}</div>;
}

// CLIENT COMPONENT (opt-in with 'use client')
// ✅ Can: use hooks, interactivity, browser APIs
// ❌ Cannot: access backend directly (use Server Actions instead)

'use client'
import { useState } from 'react';

export default function Counter() {
  const [count, setCount] = useState(0);
  return <button onClick={() => setCount(count + 1)}>{count}</button>;
}
```

**Rule of thumb:** Start with Server Components, use `'use client'` only when you need interactivity.

## Data Fetching Patterns
```tsx
// 1. Server Component (recommended)
async function Page() {
  const res = await fetch('https://api.example.com/data', {
    cache: 'force-cache' // Default: SSG-like behavior
  });
  return <div>{res.title}</div>;
}

// 2. Dynamic (SSR-like)
async function Page() {
  const res = await fetch('https://api.example.com/data', {
    cache: 'no-store' // Always fresh data
  });
  return <div>{res.title}</div>;
}

// 3. Revalidate (ISR-like)
async function Page() {
  const res = await fetch('https://api.example.com/data', {
    next: { revalidate: 60 } // Revalidate every 60 seconds
  });
  return <div>{res.title}</div>;
}

// 4. Parallel Data Fetching
async function Page() {
  const [users, posts] = await Promise.all([
    fetch('/api/users').then(r => r.json()),
    fetch('/api/posts').then(r => r.json())
  ]);
  return <Dashboard users={users} posts={posts} />;
}
```

## Server Actions (Form Handling)
```tsx
// app/actions.ts
'use server'

export async function createUser(formData: FormData) {
  const name = formData.get('name');
  // Save to database
  await db.users.create({ name });
  revalidatePath('/users'); // Refresh cached data
}

// app/page.tsx (Server Component)
import { createUser } from './actions';

export default function Page() {
  return (
    <form action={createUser}>
      <input name="name" />
      <button type="submit">Submit</button>
    </form>
  );
}
```

**Why Server Actions?** No need for API routes for simple mutations. Type-safe, direct DB access.

## Layouts & Templates
```tsx
// app/dashboard/layout.tsx (persistent across navigation)
export default function DashboardLayout({ children }: { children: React.ReactNode }) {
  return (
    <div>
      <nav>Dashboard Nav</nav>
      {children}
    </div>
  );
}

// app/dashboard/template.tsx (re-renders on navigation)
export default function DashboardTemplate({ children }: { children: React.ReactNode }) {
  return <div className="animate-fade-in">{children}</div>;
}
```

**Layout vs Template:** Layout persists state, Template doesn't (useful for animations).

## Loading & Error States
```tsx
// app/dashboard/loading.tsx (automatic Suspense boundary)
export default function Loading() {
  return <Skeleton />;
}

// app/dashboard/error.tsx (automatic Error boundary)
'use client'
export default function Error({ error, reset }: { error: Error; reset: () => void }) {
  return (
    <div>
      <h2>Something went wrong!</h2>
      <button onClick={reset}>Try again</button>
    </div>
  );
}
```

## Metadata (SEO)
```tsx
// Static metadata
export const metadata = {
  title: 'My Page',
  description: 'Page description',
  openGraph: {
    title: 'My Page',
    images: ['/og-image.jpg'],
  },
};

// Dynamic metadata
export async function generateMetadata({ params }: { params: { id: string } }) {
  const post = await fetchPost(params.id);
  return {
    title: post.title,
    description: post.excerpt,
  };
}
```

## Route Handlers (API Routes)
```typescript
// app/api/users/route.ts
import { NextRequest, NextResponse } from 'next/server';

// GET /api/users
export async function GET(request: NextRequest) {
  const users = await db.users.findMany();
  return NextResponse.json(users);
}

// POST /api/users
export async function POST(request: NextRequest) {
  const body = await request.json();
  const user = await db.users.create(body);
  return NextResponse.json(user, { status: 201 });
}

// Dynamic routes: app/api/users/[id]/route.ts
export async function GET(
  request: NextRequest,
  { params }: { params: { id: string } }
) {
  const user = await db.users.findUnique({ where: { id: params.id } });
  return NextResponse.json(user);
}
```

## Streaming & Suspense
```tsx
import { Suspense } from 'react';

async function SlowComponent() {
  await new Promise(resolve => setTimeout(resolve, 3000));
  return <div>Loaded!</div>;
}

export default function Page() {
  return (
    <div>
      <h1>Page loads instantly</h1>
      <Suspense fallback={<div>Loading slow part...</div>}>
        <SlowComponent />
      </Suspense>
    </div>
  );
}
```

**Why?** Stream UI progressively instead of waiting for all data. Better UX!

## Environment Variables
```bash
# .env.local (never commit!)
DATABASE_URL="postgres://..."
NEXT_PUBLIC_API_URL="https://api.example.com" # Exposed to browser
```

```typescript
// Access in code
const dbUrl = process.env.DATABASE_URL; // Server-only
const apiUrl = process.env.NEXT_PUBLIC_API_URL; // Available in browser
```

**Trade-off:** `NEXT_PUBLIC_*` increases bundle size. Only expose what's necessary!
