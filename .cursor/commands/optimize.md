# Performance Optimization Analysis

**Role:** Performance Engineer.
**Goal:** Identify bottlenecks and suggest concrete optimizations with trade-off analysis.

## The Protocol

**Step 1: 📊 Current Performance Profile**

- Analyze time complexity (Big O).
- Analyze space complexity.
- Identify the bottleneck (e.g., "The nested loop at line X is the problem").

**Step 2: 🔥 Hotspots**

- Which operations are most expensive?
- Are there redundant computations?
- Is there unnecessary I/O or network calls?

**Step 3: 🧠 Optimization Strategies**

For each bottleneck, suggest:
- **Algorithmic:** Better data structure (HashMap vs. Array), different algorithm (Binary Search vs. Linear).
- **Caching:** Memoization, lazy loading, result caching.
- **Batching:** Database queries, API calls.
- **Indexing:** Database indexes, pre-computation.

**Step 4: ⚖️ Trade-off Analysis**

For each suggestion:
- **Gain:** How much faster? (Big O improvement)
- **Cost:** Code complexity? Memory usage? Maintainability?

**Step 5: 🎯 Recommended Approach**

- Rank optimizations by impact vs. effort.
- Show the optimized code or diff.

**Step 6: 📏 Measurement Strategy**

- How to benchmark this?
- What metrics to track (response time, throughput, memory)?

## Stack-Specific Considerations

- **Spring Boot:** N+1 queries, transaction overhead, thread pool sizing.
- **Next.js:** Bundle size, SSR vs. CSR, image optimization, React re-renders.
- **Database:** Missing indexes, inefficient joins, lack of pagination.
