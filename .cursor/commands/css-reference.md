# CSS Quick Reference

Common CSS patterns you might forget. Ask for deep-dives on any topic.

## Flexbox (1D Layout)
```css
/* Parent container */
.flex-container {
  display: flex;
  flex-direction: row | column;           /* Main axis direction */
  justify-content: center | space-between; /* Main axis alignment */
  align-items: center | stretch;          /* Cross axis alignment */
  gap: 1rem;                              /* Spacing between items */
}

/* Child items */
.flex-item {
  flex: 1;        /* Shorthand: flex-grow flex-shrink flex-basis */
  flex: 0 1 auto; /* Default: don't grow, can shrink, auto size */
}
```
**Why Flexbox?** Best for 1D layouts (navbar, card row). **Trade-off:** Use Grid for 2D.

## Grid (2D Layout)
```css
.grid-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* 3 equal columns */
  grid-template-columns: 200px 1fr 1fr;  /* Fixed + flexible */
  gap: 2rem;                             /* Row + column gaps */
  
  /* Auto-fit responsive */
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
}

.grid-item {
  grid-column: 1 / 3;  /* Span columns 1-3 */
  grid-row: 1 / 2;     /* Span row 1 */
}
```
**Why Grid?** Best for 2D layouts (dashboards, galleries). **Trade-off:** More complex than Flexbox.

## Positioning
```css
.element {
  position: static;   /* Default: normal flow */
  position: relative; /* Offset from normal position, doesn't affect siblings */
  position: absolute; /* Removed from flow, relative to nearest positioned ancestor */
  position: fixed;    /* Relative to viewport (stays on scroll) */
  position: sticky;   /* Hybrid: relative until scroll threshold, then fixed */
  
  top: 0; right: 0; bottom: 0; left: 0; /* Offset values */
}
```

## Modern CSS Features
```css
/* CSS Variables (Custom Properties) */
:root {
  --primary-color: #3b82f6;
  --spacing-unit: 8px;
}
.button {
  background: var(--primary-color);
  padding: calc(var(--spacing-unit) * 2);
}

/* Container Queries (2023+) */
@container (min-width: 400px) {
  .card { display: grid; }
}

/* Clamp (Responsive Typography) */
.heading {
  font-size: clamp(1.5rem, 5vw, 3rem); /* min, preferred, max */
}
```

## Common Patterns
```css
/* Center anything */
.center-flex {
  display: flex;
  justify-content: center;
  align-items: center;
}

.center-grid {
  display: grid;
  place-items: center; /* Shorthand for align + justify */
}

/* Truncate text with ellipsis */
.truncate {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* Aspect ratio (modern) */
.video {
  aspect-ratio: 16 / 9;
}
```

**Ask me:** "Explain X in detail" for any concept you need clarification on!
